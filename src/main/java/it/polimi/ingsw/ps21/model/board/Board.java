package it.polimi.ingsw.ps21.model.board;

import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.ExcommunicationDeck;
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
	

	public Board(int playerNumber, boolean isAdvanced)throws BuildingDeckException {

		MatchFactory file = MatchFactory.instance();
		this.towers = new EnumMap<>(DevelopmentCardType.class);
		this.councilPalace = new CouncilPalace(1, file.makeCouncilBonuses(), 0, file.makeCouncilPrivileges());
		this.developmentDeck = file.makeDeck();
		developmentDeck.shuffle();
		this.cardBonus = file.makeCardBonus();
		this.possibleValuesPrivileges = file.makePrivileges();
		this.trackBonuses = file.makeTrackBonuses();
		this.excommunicationRequirements = new int[3];
		this.excommunicationRequirements = file.makeExcommunicationRequirements();
		ExcommunicationDeck excomDeck = file.makeExcommunicationDeck();
		excomDeck.shuffle();
		this.excommunications = new Excommunication[3];
		this.excommunications[0] = excomDeck.getExcommunication(1);
		this.excommunications[1] = excomDeck.getExcommunication(2);
		this.excommunications[2] = excomDeck.getExcommunication(3);
		if (!isAdvanced) {
			if(playerNumber < 4){
				this.marketPlaces = new SingleMarketSpace[2];
			}
			
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
				if (!isAdvanced) this.marketPlaces[i] = new SingleMarketSpace(1, file.makeMarketBonuses()[i], file.makeMarketPrivileges()[i]);
				else this.marketPlaces[i] = new AdvSingleMarketSpace(1, file.makeMarketBonuses()[i], file.makeMarketPrivileges()[i]);
			}
		}
		case 3: {
			for (int i = 0; i < 2; i++) {
				if (!isAdvanced) this.marketPlaces[i] = new SingleMarketSpace(1, file.makeMarketBonuses()[i], file.makeMarketPrivileges()[i]);
				else this.marketPlaces[i] = new AdvSingleMarketSpace(1, file.makeMarketBonuses()[i], file.makeMarketPrivileges()[i]);
			}
			this.multipleHarvPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.HARVEST);
			this.multipleProdPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.PRODUCTION);
		}
			break;
		case 4: {
			for (int i = 0; i < 4; i++) {
				if (!isAdvanced) this.marketPlaces[i] = new SingleMarketSpace(1, file.makeMarketBonuses()[i], file.makeMarketPrivileges()[i]);
				else this.marketPlaces[i] = new AdvSingleMarketSpace(1, file.makeMarketBonuses()[i], file.makeMarketPrivileges()[i]);
			}
			this.multipleHarvPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.HARVEST);
			this.multipleProdPlace = new MultipleWorkSpace(1, new ImmProperties(0), 3, WorkType.PRODUCTION);

		}



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
			for (int i = 0; i < 4; i++) {
				myTower.getTowerSpace(i).setCard(deck.getCard(era, type));
			}
		}
	}

	public void removeCardsAndMembers() {
		for (DevelopmentCardType type : DevelopmentCardType.values()) {
			for (SingleTowerSpace s : this.towers.get(type).getTower()) {
				s.reset();
			}
		}
		for(SingleMarketSpace m: this.marketPlaces) m.reset();
		this.singleHarvPlace.reset();
		this.singleProdPlace.reset();
		this.multipleHarvPlace.reset();
		this.multipleProdPlace.reset();
		this.councilPalace.reset();
			
	}

	public void newSetBoard(int era) {
		this.removeCardsAndMembers();
		try {
			placeCards(era, this.developmentDeck);
		} catch (IllegalCardException e) {
			LOGGER.log(Level.FINEST, "There is an IllegalCardException", e);
		}
	}

	public Tower getTower(DevelopmentCardType type) {
		return towers.get(type);
	}

	public SingleWorkSpace getSingleWorkSpace(WorkType type) {
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

	public MultipleWorkSpace getMultipleWorkSpace(WorkType type) {
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
	public SingleMarketSpace getMarketSpace(int position) throws IllegalArgumentException {
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

	public SingleMarketSpace[] getMarketPlaces() {
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

	public EnumMap<DevelopmentCardType, Tower> getTowers() {
		return towers;
	}
	
	

}
