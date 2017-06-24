package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.TrackBonuses;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardBuilder;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.ExcommunicationDeck;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.LeaderDeck;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.SubDeck;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.excommunications.ActionExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.CardDiceExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.DiceExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.FinalCardVPointsExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.FinalVPointsExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.PropAdditionExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.ServantsValueExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.VenturePointsExcommunication;
import it.polimi.ingsw.ps21.model.excommunications.WorkExcommunication;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesBuilder;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;


/**
 * Constructor of different match variables configurable by file (These
 * match settings are the same for all matches starting from the same Server
 * Session If there are any problems with file, default values;
 */
public class MatchFactory {
	private final static Logger LOGGER = Logger.getLogger(MatchFactory.class.getName());
	private final String excommunicationsPath = (new File("")).getAbsolutePath().concat("/configuration/excommunications.xml");;
	private static MatchFactory instance = null;
	private final String greenPath = (new File("")).getAbsolutePath().concat("/configuration/green-deck.xml");
	private final String yellowPath = (new File("")).getAbsolutePath().concat("/configuration/yellow-deck.xml");
	private final String bluePath = (new File("")).getAbsolutePath().concat("/configuration/blue-deck.xml");
	private final String purplePath = (new File("")).getAbsolutePath().concat("/configuration/purple-deck.xml");
	private final String boardPath = (new File("")).getAbsolutePath().concat("/configuration/board.xml");
	private final String serverPath = (new File("")).getAbsolutePath().concat("/configuration/server-config.xml");
	private String leaderDeckPath = (new File("")).getAbsolutePath().concat("/configuration/leader-deck.xml");
	private DocumentBuilder builder = null;
	private Deck configuratedDeck;
	private ExcommunicationDeck excommunications;
	private ImmProperties[] privileges = null;
	private ImmProperties[] initialProperties = null;
	private Map<DevelopmentCardType, Requirement[]> cardAddingRequirement = null;
	private Map<DevelopmentCardType, int[]> cardBonuses = null;
	private Map<DevelopmentCardType, ImmProperties[]> towersBonuses = null;
	private TrackBonuses trackBonuses = null;
	private int councilPrivileges = 0;
	private ImmProperties councilBonuses = null;
	private ImmProperties[] marketBonuses = null;
	private int[] marketPrivileges = null;
	private int timeoutServer = 0;
	private int timeoutRound = 0;
	private LeaderDeck leaderDeck = null;


/**
	 * @throws ParserConfigurationException if it fails to create DocumentBuilder for parsing xml files
	 */

	private MatchFactory() {
		try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		builder = factory.newDocumentBuilder();
		} catch(ParserConfigurationException e) {
			LOGGER.log(Level.SEVERE, "Error creating file parser", e);
			builder = null;
		}
	}

	/**
	 * 
	 * greenPath
	 *            path of the XML file for greenDeck configuration
	 * yellowPath
	 *            path of the XML file for yellowDeck configuration
	 * bluePath
	 *            path of the XML file for blueDeck configuration
	 * purplePath
	 *            path of the XML file for purpleDeck configuration
	 * 
	 * 
	 */

	public synchronized static MatchFactory instance() {
		if (instance == null)
			instance = new MatchFactory();
		return instance;
	}

	private SubDeck<TerritoryCard> makeGreenDeck() throws BuildingDeckException {
		Document configuration;
		SubDeck<TerritoryCard> result;
		CardBuilder cardFactory = CardBuilder.instance();
		try {
			File greenDeckFile = new File(greenPath);
			if (builder != null){
			configuration = builder.parse(greenDeckFile);
			configuration.normalize();
			}
			else throw new BuildingDeckException("Error creating Parser");
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing green Subedeck");
		} catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening green card file");
		} 
		Element greenSubDeck = configuration.getDocumentElement();
		NodeList greenCards = greenSubDeck.getElementsByTagName("TerritoryCard");
		result = new SubDeck<>();
		for (int i = 0; i < greenCards.getLength(); i++) {
			Node cardNode = greenCards.item(i);
			if (cardNode.getNodeType() == Node.ELEMENT_NODE)
				try {
					result.addCard((TerritoryCard) cardFactory.makeDevelopmentCard((Element) cardNode,
							DevelopmentCardType.TERRITORY));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Territory card number " + i);
				} catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Territory card number " + i);
				}
		}

		return result;
	}

	private SubDeck<CharacterCard> makeBlueDeck() throws BuildingDeckException {
		CardBuilder cardFactory = CardBuilder.instance();
		Document configuration;
		SubDeck<CharacterCard> result;
		try {
			File blueDeckFile = new File(bluePath);
			if (builder != null) {
			configuration = builder.parse(blueDeckFile);
			configuration.normalize();
			}
			else throw new BuildingDeckException("Error creating Parser");
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		} catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening blue card file");
		} 

		Element blueSubDeck = configuration.getDocumentElement();
		NodeList blueCards = blueSubDeck.getElementsByTagName("CharacterCard");
		result = new SubDeck<>();
		for (int i = 0; i < blueCards.getLength(); i++) {
			Node cardNode = blueCards.item(i);
			if (cardNode.getNodeType() == Node.ELEMENT_NODE)
				try {
					result.addCard((CharacterCard) cardFactory.makeDevelopmentCard((Element) cardNode,
							DevelopmentCardType.CHARACTER));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Character card number " + i);
				} catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}

		return result;
	}

	private SubDeck<BuildingCard> makeYellowDeck() throws BuildingDeckException {
		CardBuilder cardFactory = CardBuilder.instance();
		Document configuration;
		SubDeck<BuildingCard> result;
		try {
			File yellowFile = new File(yellowPath);
			if (builder != null){
			configuration = builder.parse(yellowFile);
			configuration.normalize();
			} else throw new BuildingDeckException("Error creating Parser");
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		} catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening blue card file");
		}

		Element blueSubDeck = configuration.getDocumentElement();
		NodeList blueCards = blueSubDeck.getElementsByTagName("BuildingCard");
		result = new SubDeck<>();
		for (int i = 0; i < blueCards.getLength(); i++) {
			Node cardNode = blueCards.item(i);
			if (cardNode.getNodeType() == Node.ELEMENT_NODE)
				try {
					result.addCard((BuildingCard) cardFactory.makeDevelopmentCard((Element) cardNode,
							DevelopmentCardType.BUILDING));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Character card number " + i);
				} catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}

		return result;
	}

	private SubDeck<VentureCard> makePurpleDeck() throws BuildingDeckException {
		CardBuilder cardFactory = CardBuilder.instance();
		Document configuration;
		SubDeck<VentureCard> result;
		try {
			File purpleFile = new File(purplePath);
			if (builder != null){
			configuration = builder.parse(purpleFile);
			configuration.normalize();
			} else throw new BuildingDeckException("Error creating Parser");
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		} catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening blue card file");
		}

		Element blueSubDeck = configuration.getDocumentElement();
		NodeList blueCards = blueSubDeck.getElementsByTagName("VentureCard");
		result = new SubDeck<>();
		for (int i = 0; i < blueCards.getLength(); i++) {
			Node cardNode = blueCards.item(i);
			if (cardNode.getNodeType() == Node.ELEMENT_NODE)
				try {
					result.addCard((VentureCard) cardFactory.makeDevelopmentCard((Element) cardNode,
							DevelopmentCardType.VENTURE));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Character card number " + i);
				} catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}

		return result;
	}

	/**
	 * If the deck is already configurated it will be utilized by every Match
	 * 
	 * @return
	 * @throws BuildingDeckException
	 */
	public synchronized Deck makeDeck() throws BuildingDeckException {
		if (configuratedDeck == null) {
			configuratedDeck = new Deck();
			configuratedDeck.setGreenDeck(makeGreenDeck());
			configuratedDeck.setBlueDeck(makeBlueDeck());
			configuratedDeck.setYellowDeck(makeYellowDeck());
			configuratedDeck.setPurpleDeck(makePurpleDeck());
			
		}
		return configuratedDeck.copy();
	}
	
	/**
	 * This deck is created for advanced match only
	 * @return
	 * @throws BuildingDeckException if there is some problem in parsing of file, or if file can't be open
	 */
	public synchronized LeaderDeck makeLeaderDeck() throws BuildingDeckException{
		if (leaderDeck == null){
			try {
				LeaderDeck result = new LeaderDeck();
				File leaderDeckFile = new File(leaderDeckPath);
				Document configuration = builder.parse(leaderDeckFile);
				Element leaderDeckNode = configuration.getDocumentElement();
				NodeList leaderCards = leaderDeckNode.getElementsByTagName("LeaderCard");
				for (int i=0; i<leaderCards.getLength(); i++){
					result.addCard(CardBuilder.makeLeaderCard((Element) leaderCards.item(i)));
				}
				leaderDeck = result;
				return leaderDeck.copy();
			} catch (SAXException e) {
				LOGGER.log(Level.SEVERE, "Error parsing file", e);
				throw new BuildingDeckException("Error parsing file");
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Error opening leader configuration file", e);
				throw new BuildingDeckException("Error opening leader configuration file");
			} catch (BuildingCardException e) {
				LOGGER.log(Level.SEVERE, "Error creating leader card", e);
				throw new BuildingDeckException("Error creating leader card");
			}
			
		} else return leaderDeck.copy() ;
	}
	
	public synchronized ExcommunicationDeck makeExcommunicationDeck() throws BuildingDeckException {
		if (excommunications == null){
			excommunications = new ExcommunicationDeck();
			File excommunicationsFile = new File(excommunicationsPath);
			try {
				Document configuration = builder.parse(excommunicationsFile);
				Element deck= configuration.getDocumentElement();
				NodeList excommunicationsNodes = deck.getElementsByTagName("Excommunication");
				for (int i=0; i<excommunicationsNodes.getLength(); i++){
					Element excommunication =  (Element) excommunicationsNodes.item(i);
					int id = Integer.parseInt(excommunication.getAttribute("id"));
					int period = Integer.parseInt(excommunication.getAttribute("period"));
					NodeList childs = excommunication.getChildNodes();
					for (int j=0; j<childs.getLength(); j++){
						if(childs.item(j).getNodeType() == Node.ELEMENT_NODE){
							Element excommunicationType = (Element) childs.item(j);
						switch (excommunicationType.getNodeName()){
						case "ActionExcommunication":
						{
							boolean noMarketAction;
							boolean delayFirstAction;
							if (Integer.parseInt(excommunicationType.getAttribute("delayFirstAction")) == 0) delayFirstAction = false;
							else delayFirstAction = true;
							if (Integer.parseInt(excommunicationType.getAttribute("noMarketAction")) == 0) noMarketAction = false;
							else noMarketAction = true;
							excommunications.addCard(new ActionExcommunication(id, period, delayFirstAction, noMarketAction));
						}
							break;
						case "PropAdditionExcommunication":
						{
							Element properties = (Element) excommunicationType.getElementsByTagName("Properties").item(0);
							excommunications.addCard(new PropAdditionExcommunication(id, period, PropertiesBuilder.makeImmProperites(properties)));
						}
							break;
						case "CardDiceExcommunication":
						{
							int diceValue = Integer.parseInt(excommunicationType.getAttribute("diceValue"));
							DevelopmentCardType card;
							if (excommunicationType.getElementsByTagName("Green").getLength()!=0) card =DevelopmentCardType.TERRITORY;
							else
								if (excommunicationType.getElementsByTagName("Yellow").getLength()!=0) card =DevelopmentCardType.BUILDING;
								else
									if (excommunicationType.getElementsByTagName("Blue").getLength()!= 0) card =DevelopmentCardType.VENTURE;
									else card = DevelopmentCardType.VENTURE;
							excommunications.addCard(new CardDiceExcommunication(id, period, card, diceValue));
						}
							break;
						case "DiceExcommunication":
						{
							int white = Integer.parseInt(excommunicationType.getAttribute("whiteValue"));
							int black = Integer.parseInt(excommunicationType.getAttribute("blackValue"));
							int orange = Integer.parseInt(excommunicationType.getAttribute("orangeValue"));
							excommunications.addCard(new DiceExcommunication(id, period, white, orange, black));
						}
						case "FinalCardVPointsExcommunication":
						{
							DevelopmentCardType cardType;
							if (excommunicationType.getElementsByTagName("Green").getLength() > 0) cardType = DevelopmentCardType.TERRITORY;
							else if (excommunicationType.getElementsByTagName("Yellow").getLength() > 0) cardType = DevelopmentCardType.BUILDING;
							else if (excommunicationType.getElementsByTagName("Blue").getLength() > 0) cardType = DevelopmentCardType.CHARACTER;
							else cardType = DevelopmentCardType.VENTURE;
							excommunications.addCard(new FinalCardVPointsExcommunication(id, period, cardType));
						}
							break;
						case "FinalVPointsExcommunication":
						{
							int victoryPointsReductionDivisor = Integer.parseInt(excommunicationType.getAttribute("victoryDivisor"));
							int militaryDivisorVPointsReduction = Integer.parseInt(excommunicationType.getAttribute("militaryDivisor"));
							int vPointsReductionBuildingWoodDivisor = Integer.parseInt(excommunicationType.getAttribute("buildingWoodDivisor"));
							int vPointsReductionBuildingStoneDivisor = Integer.parseInt(excommunicationType.getAttribute("buildingStonesDivisor"));
							int vPointsReductionResDivisor = Integer.parseInt(excommunicationType.getAttribute("resDivisor"));
							excommunications.addCard(new FinalVPointsExcommunication(id, period, victoryPointsReductionDivisor, militaryDivisorVPointsReduction, vPointsReductionBuildingWoodDivisor, vPointsReductionBuildingStoneDivisor, vPointsReductionResDivisor));
						}
							break;
						case "ServantsValueExcommunication":
						{
							int value = Integer.parseInt(excommunicationType.getAttribute("servantsValue"));
							excommunications.addCard(new ServantsValueExcommunication(id, period, value));
						}
							break;
						case "VentureFinalPointsExcommunication":
							excommunications.addCard(new VenturePointsExcommunication(id, period));
							break;
						case "WorkExcommunication":
						{
							int value = Integer.parseInt(excommunicationType.getAttribute("value"));
							WorkType type;
							if (excommunicationType.getElementsByTagName("Harvest").getLength() !=0) type = WorkType.HARVEST;
							else type = WorkType.PRODUCTION;
							excommunications.addCard(new WorkExcommunication(id, period, type, value));
						}
							break;
							default:
								throw new BuildingCardException();
						}
						}
					}
				}
 				
				
			} catch (SAXException e) {
				LOGGER.log(Level.WARNING, "Error parsing excommunications file", e);
				throw new BuildingDeckException("Error creating Excommunication deck");
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Error opening excommunication file", e);
				throw new BuildingDeckException("Error creating Excommunication deck");
			} catch (BuildingCardException e) {
				LOGGER.log(Level.WARNING, "Invalid Excommunication Tag name", e);
				throw new BuildingDeckException("Error creating Excommunication deck");
			}
		}
		return excommunications.copy();
	}

	public synchronized ImmProperties[] makePrivileges() {
		if (privileges == null) {
			Document configuration;
			ArrayList<ImmProperties> bonuses = new ArrayList<>();
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element privileges = (Element) board.getElementsByTagName("Privileges").item(0);
				NodeList properties = privileges.getElementsByTagName("Properties");
				for (int i = 0; i < properties.getLength(); i++)
					if (properties.item(i).getNodeType() == Node.ELEMENT_NODE) bonuses.add(PropertiesBuilder.makeImmProperites((Element) properties.item(i)));
			} catch (SAXException | IOException | NullPointerException i) {
				LOGGER.log(Level.WARNING, "Error creating priviliges from file, returning default values", i);
				bonuses.add(
						new ImmProperties(new Property(PropertiesId.STONES, 1), new Property(PropertiesId.WOOD, 1))); // 1
																														// wood
																														// and
																														// 1
																														// stone
				bonuses.add(new ImmProperties(new Property(PropertiesId.COINS, 2))); // 2
																						// coins
				bonuses.add(new ImmProperties(new Property(PropertiesId.MILITARYPOINTS, 2))); // 2
				bonuses.add(new ImmProperties(new Property(PropertiesId.FAITHPOINTS, 2)));
			}
			// TODO
			privileges = bonuses.toArray(new ImmProperties[0]);
			return privileges;
		} else
			return privileges;
	}

	public synchronized ImmProperties[] makeInitialProperties() {
		if (initialProperties == null) {
			Document configuration;
			ArrayList<ImmProperties> bonuses = new ArrayList<>();
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element initialProps = (Element) board.getElementsByTagName("InitialProperties").item(0);
				NodeList properties = initialProps.getElementsByTagName("Properties");
				for (int i = 0; i < properties.getLength(); i++)
					if (properties.item(i).getNodeType() == Node.ELEMENT_NODE) bonuses.add(PropertiesBuilder.makeImmProperites((Element) properties.item(i)));
			} catch (SAXException | IOException | NullPointerException i) {
				LOGGER.log(Level.WARNING, "Error creating InitialProperties, returning default value", i);
				bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2),
						new Property(PropertiesId.COINS, 5), new Property(PropertiesId.SERVANTS, 3))); // 1
																										// wood
																										// and
																										// 1
																										// stone
				bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2),
						new Property(PropertiesId.COINS, 6), new Property(PropertiesId.SERVANTS, 3))); // 1
																										// wood
																										// and
																										// 1
																										// stone
				bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2),
						new Property(PropertiesId.COINS, 7), new Property(PropertiesId.SERVANTS, 3))); // 1
																										// wood
																										// and
																										// 1
																										// stone
				bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2),
						new Property(PropertiesId.COINS, 8), new Property(PropertiesId.SERVANTS, 3))); // 1
																										// wood
																										// and
																										// 1
																										// stone
			}
			// TODO
			initialProperties = bonuses.toArray(new ImmProperties[0]);
		}
		return initialProperties;
	}

	public synchronized Map<DevelopmentCardType, Requirement[]> makeCardAddingRequirements() {
		if (cardAddingRequirement == null) {
			Document configuration;
			EnumMap<DevelopmentCardType, Requirement[]> result = new EnumMap<>(DevelopmentCardType.class);
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element cardReqs = (Element) board.getElementsByTagName("CardRequirement").item(0);
				ArrayList<Requirement> requirements;

				Element territory = (Element) cardReqs.getElementsByTagName("TerritoryRequirement").item(0);
				requirements = new ArrayList<>();
				NodeList reqs = territory.getChildNodes();
				for (int i = 0; i < reqs.getLength(); i++) 
					if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE) requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
				
				result.put(DevelopmentCardType.TERRITORY, requirements.toArray(new Requirement[0]));

				Element building = (Element) cardReqs.getElementsByTagName("BuildingRequirement").item(0);
				requirements = new ArrayList<>();
				reqs = building.getChildNodes();
				for (int i = 0; i < reqs.getLength(); i++)
					if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE) requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
				result.put(DevelopmentCardType.BUILDING, requirements.toArray(new Requirement[0]));

				Element character = (Element) cardReqs.getElementsByTagName("CharacterRequirement").item(0);
				requirements = new ArrayList<>();
				reqs = character.getChildNodes();
				for (int i = 0; i < reqs.getLength(); i++) 
					if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE) requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
				
				result.put(DevelopmentCardType.CHARACTER, requirements.toArray(new Requirement[0]));

				Element venture = (Element) cardReqs.getElementsByTagName("VentureRequirement").item(0);
				requirements = new ArrayList<>();
				reqs = venture.getChildNodes();
				for (int i = 0; i < reqs.getLength(); i++) 
					if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE) requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
				
				result.put(DevelopmentCardType.VENTURE, requirements.toArray(new Requirement[0]));

			} catch (SAXException | IOException | NullPointerException | XMLParseException i) {
				LOGGER.log(Level.WARNING, "Error creating Adding Card Requirement, returning default value", i);
				result = new EnumMap<>(DevelopmentCardType.class);
				Requirement[] reqs = new Requirement[6];
				for (int j=0; j<reqs.length; j++)  {
					reqs[j] = new Requirement(new CardsNumber(0), new ImmProperties(0));
				}
				for (DevelopmentCardType t : DevelopmentCardType.values()) {
					result.put(t, reqs);
				}
			}
			// TODO
			cardAddingRequirement = result;
		}
		return cardAddingRequirement;
	}

	public synchronized TrackBonuses makeTrackBonuses() {
		if (trackBonuses == null) {
			Document configuration;
			TrackBonuses result;
			int[] military = {0,0,0,0};
			int[] faith = new int[16];
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element militaryBonuses = (Element) board.getElementsByTagName("MilitaryBonuses").item(0);
				military[0] = Integer.parseInt(militaryBonuses.getAttribute("first"));
				military[1] = Integer.parseInt(militaryBonuses.getAttribute("second"));
				Element faithBonuses = (Element) board.getElementsByTagName("FaithBonuses").item(0);
				faith[0] = Integer.parseInt(faithBonuses.getAttribute("zero"));
				faith[1] = Integer.parseInt(faithBonuses.getAttribute("first"));
				faith[2] = Integer.parseInt(faithBonuses.getAttribute("second"));
				faith[3] = Integer.parseInt(faithBonuses.getAttribute("third"));
				faith[4] = Integer.parseInt(faithBonuses.getAttribute("fourth"));
				faith[5] = Integer.parseInt(faithBonuses.getAttribute("fifth"));
				faith[6] = Integer.parseInt(faithBonuses.getAttribute("sixth"));
				faith[7] = Integer.parseInt(faithBonuses.getAttribute("seventh"));
				faith[8] = Integer.parseInt(faithBonuses.getAttribute("eighth"));
				faith[9] = Integer.parseInt(faithBonuses.getAttribute("nineth"));
				faith[10] = Integer.parseInt(faithBonuses.getAttribute("tenth"));
				faith[11] = Integer.parseInt(faithBonuses.getAttribute("eleventh"));
				faith[12] = Integer.parseInt(faithBonuses.getAttribute("twelveth"));
				faith[13] = Integer.parseInt(faithBonuses.getAttribute("thirteenth"));
				faith[14] = Integer.parseInt(faithBonuses.getAttribute("fourteenth"));
				faith[15] = Integer.parseInt(faithBonuses.getAttribute("fifteenth"));
				result = new TrackBonuses(faith, military);

			}

			catch (SAXException | IOException |  NullPointerException i) {
				LOGGER.log(Level.WARNING, "Error creating TrackBonuses, returning default value", i);
				military[0] = 0;
				military[1] = 0;
				for (int k = 0; k < 15; k++) {
					faith[k] = 0;
				}
				result = new TrackBonuses(faith, military);
			}
			// TODO
			trackBonuses = result;
		}
		return trackBonuses;
	}

	public synchronized ImmProperties makeCouncilBonuses() {
		if (councilBonuses == null) {
			Document configuration;
			ImmProperties result;

			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element councilBonus = (Element) board.getElementsByTagName("CouncilBonus").item(0);
				result = PropertiesBuilder
						.makeImmProperites((Element) councilBonus.getElementsByTagName("Properties").item(0));
			} catch (IOException | SAXException | NullPointerException e) {
				LOGGER.log(Level.WARNING, "Error creating Counicil Bonus, returning default value", e);
				result = new ImmProperties(new Property(PropertiesId.COINS, 2));
			}
			councilBonuses = result;

		}
		return councilBonuses;
	}

	public synchronized int makeCouncilPrivileges() {
		if (councilPrivileges == 0) {
			Document configuration;
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element privileges = (Element) board.getElementsByTagName("CouncilPrivileges").item(0);
				int number = Integer.parseInt(privileges.getAttribute("value"));
				councilPrivileges = number;
			} catch (IOException | SAXException | NullPointerException x) {
				LOGGER.log(Level.WARNING, "Error creating Number of Council Privileges, returning default value", x);
				councilPrivileges = 1;
			}
		}
		return councilPrivileges;
	}

	
	public synchronized Map<DevelopmentCardType, int[]> makeCardBonus() {
		if (cardBonuses == null) {
			Document configuration;
			EnumMap<DevelopmentCardType, int[]> result = new EnumMap<>(DevelopmentCardType.class);
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element cardBonuses = (Element) board.getElementsByTagName("CardBonuses").item(0);
				int[] bonuses = new int[7];

				Element territory = (Element) cardBonuses.getElementsByTagName("TerritoryBonuses").item(0);
				for(int i=0; i<=6; i++)
				{
					bonuses[i] = Integer.parseInt(territory.getAttribute("value"+i));
				}
				result.put(DevelopmentCardType.TERRITORY, bonuses);

				bonuses = new int[7];
				Element building = (Element) cardBonuses.getElementsByTagName("BuildingBonuses").item(0);
				for(int i=0; i<=6; i++)
				{
					bonuses[i] = Integer.parseInt(building.getAttribute("value"+i));
				}
				result.put(DevelopmentCardType.BUILDING, bonuses);

				bonuses = new int[7];
				Element character = (Element) cardBonuses.getElementsByTagName("CharacterBonuses").item(0);
				for(int i=0; i<=6; i++)
				{
					bonuses[i] = Integer.parseInt(character.getAttribute("value"+i));
				}
				result.put(DevelopmentCardType.CHARACTER, bonuses);

				bonuses = new int[7];
				Element venture = (Element) cardBonuses.getElementsByTagName("VentureBonuses").item(0);
				for(int i=0; i<=6; i++)
				{
					bonuses[i] = Integer.parseInt(venture.getAttribute("value"+i));
				}
				result.put(DevelopmentCardType.VENTURE, bonuses);
			} catch (SAXException | IOException | NullPointerException i) {
			LOGGER.log(Level.WARNING, "Error creating Card Final Bonuses, returning default values", i);
				result = new EnumMap<>(DevelopmentCardType.class);
				int[] bonuses = new int[6];
				for (int h = 0; h < bonuses.length; h++) {
					bonuses[h] = 0;
				}
				for (DevelopmentCardType t : DevelopmentCardType.values()) {
					result.put(t, bonuses);
				}
			}
			// TODO
			cardBonuses = result;
		}
		return cardBonuses;
	}

	public synchronized Map<DevelopmentCardType, ImmProperties[]> makeTowersBonus() {
		if (towersBonuses == null) {
			Document configuration;
			EnumMap<DevelopmentCardType, ImmProperties[]> result = new EnumMap<>(DevelopmentCardType.class);
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element cardReqs = (Element) board.getElementsByTagName("TowersBonuses").item(0);
				ArrayList<ImmProperties> properties;

				Element territory = (Element) cardReqs.getElementsByTagName("TerritoryTower").item(0);
				properties = new ArrayList<>();
				NodeList props = territory.getChildNodes();
				for (int i = 0; i < props.getLength(); i++) {
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.TERRITORY, properties.toArray(new ImmProperties[0]));

				Element building = (Element) cardReqs.getElementsByTagName("BuildingTower").item(0);
				properties = new ArrayList<>();
				props = building.getChildNodes();
				for (int i = 0; i < props.getLength(); i++) {
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.BUILDING, properties.toArray(new ImmProperties[0]));

				Element character = (Element) cardReqs.getElementsByTagName("CharacterTower").item(0);
				properties = new ArrayList<>();
				props = character.getChildNodes();
				for (int i = 0; i < props.getLength(); i++) {
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.CHARACTER, properties.toArray(new ImmProperties[0]));

				Element venture = (Element) cardReqs.getElementsByTagName("VentureTower").item(0);
				properties = new ArrayList<>();
				props = venture.getChildNodes();
				for (int i = 0; i < props.getLength(); i++) {
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.VENTURE, properties.toArray(new ImmProperties[0]));

			} catch (SAXException | IOException | NullPointerException i) {
				LOGGER.log(Level.WARNING, "Error creating tower bonuses, returning default values", i);
				result = new EnumMap<>(DevelopmentCardType.class);
				ImmProperties[] properties = new ImmProperties[4];
				for (int j=0; j<4; j++) 
					properties[j] = new ImmProperties(0);
				
				for (DevelopmentCardType t : DevelopmentCardType.values()) 
					result.put(t, properties);
			}
			// TODO
			towersBonuses = result;
		}
		return towersBonuses;
	}

	public synchronized ImmProperties[] makeMarketBonuses() {
		if (marketBonuses == null) {
			Document configuration;
			ArrayList<ImmProperties> bonuses = new ArrayList<>();
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();
				Element board = configuration.getDocumentElement();
				Element market = (Element) board.getElementsByTagName("MarketBonuses").item(0);
				NodeList properties = market.getElementsByTagName("Properties");
				for (int i = 0; i < properties.getLength(); i++) {
					if (properties.item(i).getNodeType() == Node.ELEMENT_NODE) {
						bonuses.add(PropertiesBuilder.makeImmProperites((Element) properties.item(i)));
					}
				}
			} catch (SAXException | IOException | NullPointerException i) {
				LOGGER.log(Level.WARNING, "Error creating market bonuses, returning default values");
				bonuses = new ArrayList<>();
				bonuses.add(
						new ImmProperties(new Property(PropertiesId.STONES, 1), new Property(PropertiesId.WOOD, 1))); // 1
																														// wood
																														// and
																														// 1
																														// stone
				bonuses.add(new ImmProperties(new Property(PropertiesId.COINS, 2))); // 2
																						// coins
				bonuses.add(new ImmProperties(new Property(PropertiesId.MILITARYPOINTS, 2))); // 2
				bonuses.add(new ImmProperties(new Property(PropertiesId.FAITHPOINTS, 2)));
			}
			marketBonuses = bonuses.toArray(new ImmProperties[0]);
			return marketBonuses;
		} else
			return marketBonuses;
	}

	public int[] makeExcommunicationRequirements() {
		int[] result = new int[3];
		Document configuration;
		try {
			File boardFile = new File(boardPath);
			configuration = builder.parse(boardFile);
			Element board = configuration.getDocumentElement();
			Element excomReqs = (Element) board.getElementsByTagName("ExcommunicationRequirements").item(0);
			result[0] = Integer.parseInt(excomReqs.getAttribute("firstPeriod"));
			result[1] = Integer.parseInt(excomReqs.getAttribute("secondPeriod"));
			result[2] = Integer.parseInt(excomReqs.getAttribute("thirdPeriod"));
		} catch (SAXException | IOException | NullPointerException e) {
			LOGGER.log(Level.WARNING, "Error making excommunication requiremnt", e);
			result[0] = 3;
			result[1] = 4;
			result[2] = 5;
		}
		return result;
	}
	
	/**
	 * This method read server configuration file
	 * @return timeout expressed in milliseconds
	 */
	public int makeTimeoutRound(){
		if (timeoutRound == 0){
			int result;
			try {
			File serverFile = new File(serverPath);
				Document configuration = builder.parse(serverFile);
				Element server = configuration.getDocumentElement();
				Element timerServer = (Element) server.getElementsByTagName("TimeoutRound").item(0);
				result = Integer.parseInt(timerServer.getAttribute("value"));
			} catch (SAXException | IOException e) {
				LOGGER.log(Level.WARNING, "Error configuring server timeout from file, returning default values", e);
				result = 120000;
			}
			timeoutRound = result;
		}
		return timeoutRound;
		
	}
	
	public int makeTimeoutServer(){
		if (timeoutServer == 0){
			int result;
			try {
			File serverFile = new File(serverPath);
				Document configuration = builder.parse(serverFile);
				Element server = configuration.getDocumentElement();
				Element timerServer = (Element) server.getElementsByTagName("TimeoutLobby").item(0);
				result = Integer.parseInt(timerServer.getAttribute("value"));
			} catch (SAXException | IOException e) {
				LOGGER.log(Level.WARNING, "Error configuring server timeout from file, returning default values", e);
				result = 60000;
			}
			timeoutServer = result;
		}
		return timeoutServer;
		
	}

	
	public int[] makeMarketPrivileges() {
		if (marketPrivileges == null) {
			Document configuration;
			int result[] = new int[4];
			try {
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				Element board = configuration.getDocumentElement();
				Element markPrivileges = (Element) board.getElementsByTagName("MarketPrivileges").item(0);
				result[0] = Integer.parseInt(markPrivileges.getAttribute("first"));
				result[1] = Integer.parseInt(markPrivileges.getAttribute("second"));
				result[2] = Integer.parseInt(markPrivileges.getAttribute("third"));
				result[3] = Integer.parseInt(markPrivileges.getAttribute("fourth"));
			} catch (SAXException | IOException | NullPointerException e) {
				LOGGER.log(Level.WARNING, "Error creating market privileges, returning default value", e);
				result[0] = 0;
				result[1] = 0;
				result[2] = 0;
				result[3] = 2;
			}
			marketPrivileges = result;
		}
		return marketPrivileges;
		
	}
	
	

}
