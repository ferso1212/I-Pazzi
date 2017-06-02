package it.polimi.ingsw.ps21.model.board;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class Board {
	private final static Logger LOGGER = Logger.getLogger(Board.class.getName());
	protected EnumMap<DevelopmentCardType, Tower> towers;
	protected TrackBonuses trackBonuses;
	protected SingleSpace[] marketPlaces;
	protected SingleSpace singleHarvPlace;
	protected SingleSpace singleProdPlace;
	protected MultipleSpace multipleHarvPlace;
	protected MultipleSpace multipleProdPlace;
	protected CouncilPalace councilPalace;
	protected Map<DevelopmentCardType, int[]> cardBonus;
	
	public Board(EnumMap<DevelopmentCardType, Tower> towers, TrackBonuses trackBonuses, SingleSpace[] marketSpace,
			SingleSpace singleHarvPlace, SingleSpace singleProdPlace, MultipleSpace multipleHarvPlace,
			MultipleSpace multipleProdPlace, CouncilPalace councilPalace,
			EnumMap<DevelopmentCardType, int[]> cardBonus) {
		super();
		this.towers = towers;
		this.trackBonuses = trackBonuses;
		this.marketPlaces = marketSpace;
		this.singleHarvPlace = singleHarvPlace;
		this.singleProdPlace = singleProdPlace;
		this.multipleHarvPlace = multipleHarvPlace;
		this.multipleProdPlace = multipleProdPlace;
		this.councilPalace = councilPalace;
		this.cardBonus = cardBonus;
	}

	public void addToCouncil(Player player, FamilyMember member) throws NotOccupableException {
		councilPalace.occupy(player, member);
	}

	public void placeCards(int era, Deck deck) throws IllegalCardException {
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			Tower myTower = towers.get(type);
			for (int i = 1; i < 5; i++) {
				myTower.getTowerSpace(i).setCard(deck.getCard(era, type));
			}
		}
	}

	public void removeCardsAndMembers() {
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			Tower myTower = towers.get(type);
			for (int i = 1; i < 5; i++) {
				myTower.getTowerSpace(i).reset();
			}
		}
	}

	public void resetFaithPoints(Player player) {
		player.getProperties().getProperty(PropertiesId.FAITHPOINTS).setValue(0);
	}

	public Tower getTower(DevelopmentCardType type) {
		return towers.get(type);
	}

	public SingleSpace getSingleWorkSpace(WorkType type) {
		switch (type) {
		case HARVEST: {
			return this.singleHarvPlace;
		}

		case PRODUCTION: {
			return this.singleProdPlace;
		}

		default:
			return null;

		}
	}

	public MultipleSpace getMultipleWorkSpace(WorkType type) {
		switch (type) {
		case HARVEST: {
			return this.multipleHarvPlace;
		}

		case PRODUCTION: {
			return this.multipleProdPlace;
		}

		default:
			return null;

		}
	}

	/**
	 * 
	 * @param position from 0 to 3
	 * @return a single MarketSpace
	 */
	public SingleSpace getMarketSpace(int position) throws IllegalArgumentException {
		if ((position >= 0) && (position < 5)) {
			return this.marketPlaces[position];
		} else
			throw new IllegalArgumentException();
	}

	public CouncilPalace getCouncilPalace() {
		return councilPalace;
	}

	public boolean placeMember(Player player, FamilyMember famMember, Space space) {

		try {
			space.occupy(player, famMember);
			return true;
		} catch (NotOccupableException e) {
			LOGGER.log(Level.INFO, "Space not occupable", e);
			return false;
		}

	}
	
	public int getDevelopmentFinalBonus(DevelopmentCardType type, int numberOfCards){
		if (numberOfCards > this.cardBonus.get(type).length){
			return this.cardBonus.get(type)[this.cardBonus.get(type).length - 1];
		} else {
			return this.cardBonus.get(type)[numberOfCards];
		}
	}
	
	public TrackBonuses getTrackBonuses(){
		return this.trackBonuses;
	}

	public SingleSpace[] getMarketPlaces() {
		return marketPlaces;
	}

	public Map<DevelopmentCardType, int[]> getCardBonus() {
		return cardBonus;
	}

	
	
}
