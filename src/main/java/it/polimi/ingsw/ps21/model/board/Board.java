package it.polimi.ingsw.ps21.model.board;

import java.util.EnumMap;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class Board {

	protected EnumMap<DevelopmentCardType, Tower> towers;
	protected int[] faithTrack;
	protected int militaryBonus1;
	protected int militaryBonus2;
	protected SingleSpace[] marketPlaces;
	protected SingleSpace singleHarvPlace;
	protected SingleSpace singleProdPlace;
	protected MultipleSpace multipleHarvPlace;
	protected MultipleSpace multipleProdPlace;
	protected CouncilPalace councilPalace;
	protected int[] greenCardBonus;
	protected int[] yellowCardBonus;
	protected int[] blueCardBonus;
	protected int[] purpleCardBonus;

	public Board(EnumMap<DevelopmentCardType, Tower> towers, int[] faithTrack, int militaryBonus1, int militaryBonus2,
			SingleSpace[] marketPlaces, SingleSpace singleHarvPlace, SingleSpace singleProdPlace,
			MultipleSpace multipleHarvPlace, MultipleSpace multipleProdPlace, CouncilPalace councilPalace,
			int[] greenCardBonus, int[] yellowCardBonus, int[] blueCardBonus, int[] purpleCardBonus) {
		super();
		this.towers = towers;
		this.faithTrack = faithTrack;
		this.militaryBonus1 = militaryBonus1;
		this.militaryBonus2 = militaryBonus2;
		this.marketPlaces = marketPlaces;
		this.singleHarvPlace = singleHarvPlace;
		this.singleProdPlace = singleProdPlace;
		this.multipleHarvPlace = multipleHarvPlace;
		this.multipleProdPlace = multipleProdPlace;
		this.councilPalace = councilPalace;
		this.greenCardBonus = greenCardBonus;
		this.yellowCardBonus = yellowCardBonus;
		this.blueCardBonus = blueCardBonus;
		this.purpleCardBonus = purpleCardBonus;
	}

	public void addToCouncil(Player player, FamilyMember member) throws NotOccupableException {
		councilPalace.occupy(member);
	}

	public void placeCards(int era, Deck deck) {
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			Tower myTower = towers.get(type);
			for (int i = 1; i < 5; i++) {
				myTower.getTowerSpace(i).setCard(deck.getCard(era, type));
			}
		}
	}

	public void removeCardsAndMembers() throws IllegalArgumentException {
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
			space.occupy(famMember);
			return true;
		} catch (NotOccupableException e) {
			return false;
		}

	}

}
