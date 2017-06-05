package it.polimi.ingsw.ps21.model.board;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class Board {

	private final static Logger LOGGER = Logger.getLogger(Board.class.getName());
	private EnumMap<DevelopmentCardType, Tower> towers;
	private TrackBonuses trackBonuses;
	private SingleMarketSpace[] marketPlaces;
	private SingleWorkSpace singleHarvPlace;
	private SingleWorkSpace singleProdPlace;
	private MultipleWorkSpace multipleHarvPlace;
	private MultipleWorkSpace multipleProdPlace;
	private CouncilPalace councilPalace;
	private Map<DevelopmentCardType, int[]> cardBonus;
	private Deck developmentDeck;
	private ImmProperties[] possibleValuesPrivileges;
	private Excommunication[] excommunications;
	private int[] excommunicationRequirements;
	

	public Board(int playerNumber, boolean isAdvanced) throws ParserConfigurationException, BuildingDeckException {

		MatchFactory file = MatchFactory.instance();
		this.towers = new EnumMap<>(DevelopmentCardType.class);
		this.councilPalace = new CouncilPalace(1, file.makeCouncilBonuses(), 0, file.makeCouncilPrivileges());
		this.developmentDeck = file.makeDeck();
		this.cardBonus = file.makeCardBonus();
		this.possibleValuesPrivileges = file.makePrivileges();
		this.trackBonuses = file.makeTrackBonuses();
		this.excommunicationRequirements = new int[3];
		this.excommunicationRequirements = file.makeExcommunicationRequirements;

		if (!isAdvanced) {
			if(playerNumber < 4)
				this.marketPlaces = new SingleMarketSpace[2];
			else this.marketPlaces = new SingleMarketSpace[4];
			for (DevelopmentCardType type : DevelopmentCardType.values()) {
				this.towers.put(type, new Tower(false, file.makeTowersBonus().get(type)));
			}
			this.singleHarvPlace = new SingleWorkSpace(1, new ImmProperties(0), WorkType.HARVEST);
			this.singleProdPlace = new SingleWorkSpace(1, new ImmProperties(0), WorkType.PRODUCTION);
		}else{
			if(playerNumber < 4)
				this.marketPlaces = new AdvSingleMarketSpace[2];
			else this.marketPlaces = new AdvSingleMarketSpace[4];
			for (DevelopmentCardType type : DevelopmentCardType.values()) {
				this.towers.put(type, new Tower(true, file.makeTowersBonus().get(type)));
			}
			this.singleHarvPlace = new AdvSingleWorkSpace(1, new ImmProperties(0), WorkType.HARVEST);
			this.singleProdPlace = new AdvSingleWorkSpace(1, new ImmProperties(0), WorkType.PRODUCTION);
		}

		switch (playerNumber) {
		case 2: {
			for (int i = 0; i < 2; i++) {
				marketPlaces[i].instantBonus = file.makeMarketBonuses()[i];
			}
		}
		case 3: {
			for (int i = 0; i < 2; i++) {
				marketPlaces[i].instantBonus = file.makeMarketBonuses()[i];
			}
			this.multipleHarvPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.HARVEST);
			this.multipleProdPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.PRODUCTION);
		}
			break;
		case 4: {
			for (int i = 0; i < 4; i++) {
				marketPlaces[i].instantBonus = file.makeMarketBonuses()[i];
			}
			this.multipleHarvPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.HARVEST);
			this.multipleProdPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.PRODUCTION);

		}

			break;

		default:
			break;
		}

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

	public void newSetBoard(int era) {
		this.removeCardsAndMembers();
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			Tower t = this.towers.get(type);
			for (SingleTowerSpace floor : t.getTower()) {
				try {
					floor.setCard(developmentDeck.getCard(era, type));
				} catch (IllegalCardException e) {
					// era sbagliata
				}
			}
		}
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
	 * @param position
	 *            from 0 to 3
	 * @return a single MarketSpace
	 */
	public SingleSpace getMarketSpace(int position) throws IllegalArgumentException {
		if ((position >= 0) && (position < this.marketPlaces.length)) {
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

	public int getDevelopmentFinalBonus(DevelopmentCardType type, int numberOfCards) {
		if (numberOfCards > this.cardBonus.get(type).length) {
			return this.cardBonus.get(type)[this.cardBonus.get(type).length - 1];
		} else {
			return this.cardBonus.get(type)[numberOfCards];
		}
	}

	public TrackBonuses getTrackBonuses() {
		return this.trackBonuses;
	}

	public SingleSpace[] getMarketPlaces() {
		return marketPlaces;
	}

	public Map<DevelopmentCardType, int[]> getCardBonus() {
		return cardBonus;
	}
	
	public int getExcommunicationRequirement (int period) {
		return this.excommunicationRequirements[period-1];
	}
	
	public Excommunication[] getExcommunications(){
		return this.excommunications;
	}

	public ImmProperties[] getPossibleValuesPrivileges() {
		return possibleValuesPrivileges;
	}
	
	public Deck getDeck()
	{
		return this.developmentDeck;
	}

}
