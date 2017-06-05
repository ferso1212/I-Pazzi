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

import it.polimi.ingsw.ps21.model.board.TrackBonuses;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardBuilder;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.SubDeck;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesBuilder;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;


// TODO implements signleton and factory for every effect
public class MatchFactory {
	private final static Logger LOGGER = Logger.getLogger(MatchFactory.class.getName());
	private static MatchFactory instance = null;
	private final String greenPath = (new File("")).getAbsolutePath().concat("/green-deck.xml").toString();
	private final String yellowPath = (new File("")).getAbsolutePath().concat("/yellow-deck.xml").toString();
	private final String bluePath = (new File("")).getAbsolutePath().concat("/blue-deck.xml").toString();
	private	final String purplePath = (new File("")).getAbsolutePath().concat("/purple-deck.xml").toString();
	private	final String boardPath = (new File("")).getAbsolutePath().concat("/board.xml").toString();
	private DocumentBuilder builder;
	private Deck configuratedDeck;
	private ImmProperties [] privileges = null;
	private ImmProperties[] initialProperties =null;
	private Map<DevelopmentCardType, Requirement[]> cardAddingRequirement = null;
	private Map<DevelopmentCardType, int[]> cardBonuses = null;
	private Map<DevelopmentCardType, ImmProperties[]> towersBonuses = null;
	private TrackBonuses trackBonuses = null;
	private int councilPrivileges = 0;
	private ImmProperties councilBonuses = null;
	private ImmProperties[] marketBonuses = null;
	/**
	 * Constructor of different match variables configurable by file (These match settings are the same for all matches
	 * starting from the same Server Session
	 * If there are any problems with file, default values;
	 * @throws ParserConfigurationException
	 * @throws IOException 
	 */
	
	
	private MatchFactory() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
			builder = factory.newDocumentBuilder();
		
	}
	/**
	 * 
	 * @param greenPath path of the XML file for greenDeck configuration
	 * @param yellowPath path of the XML file for yellowDeck configuration
	 * @param bluePath path of the XML file for blueDeck configuration
	 * @param purplePath path of the XML file for purpleDeck configuration
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	/*
	 * TODO Implement makeEffect
	 * TODO Implement makePurpleCard and makeYellowCard
	 */
	public synchronized static MatchFactory instance() throws ParserConfigurationException{
		if (instance == null) instance = new MatchFactory();
		return instance;
	}
	
	
	private SubDeck<TerritoryCard> makeGreenDeck() throws BuildingDeckException{
		Document configuration;
		SubDeck<TerritoryCard> result;
		CardBuilder cardFactory = CardBuilder.instance();
		try {
			File greenDeckFile = new File(greenPath);
			configuration = builder.parse(greenDeckFile);
			configuration.normalize();
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing green Subedeck");
		}
		catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening green card file");
		}
		
		Element greenSubDeck = configuration.getDocumentElement();
		NodeList greenCards = greenSubDeck.getElementsByTagName("TerritoryCard");
		result = new SubDeck<>();
		for (int i=0; i< greenCards.getLength(); i++)
		{
			Node cardNode = greenCards.item(i);
				if (cardNode.getNodeType() == Node.ELEMENT_NODE)
					try {
						result.addCard((TerritoryCard) cardFactory.makeDevelopmentCard((Element) cardNode, DevelopmentCardType.TERRITORY));
						} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Territory card number " + i);
				}
				catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Territory card number " + i);
				}
		}
		
		return result;
	}
		
	private SubDeck<CharacterCard> makeBlueDeck() throws BuildingDeckException{
		CardBuilder cardFactory = CardBuilder.instance();	
		Document configuration;
		SubDeck<CharacterCard> result;
		try {
			File blueDeckFile = new File(bluePath);
			configuration = builder.parse(blueDeckFile);
			configuration.normalize();
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		}
		catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening blue card file");
		}
		
		Element blueSubDeck = configuration.getDocumentElement();
		NodeList blueCards = blueSubDeck.getElementsByTagName("CharacterCard");
		result = new SubDeck<CharacterCard>();
		for (int i=0; i< blueCards.getLength(); i++)
		{
			Node cardNode = blueCards.item(i);
				if (cardNode.getNodeType() == Node.ELEMENT_NODE)
					try {
					result.addCard((CharacterCard) cardFactory.makeDevelopmentCard((Element) cardNode, DevelopmentCardType.CHARACTER));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Character card number " + i);
				}
				catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}
		
		return result;
	}
	
	private SubDeck<BuildingCard> makeYellowDeck() throws BuildingDeckException{
		CardBuilder cardFactory = CardBuilder.instance();	
		Document configuration;
		SubDeck<BuildingCard> result;
		try {
			File yelloFile = new File(yellowPath);
			configuration = builder.parse(yelloFile);
			configuration.normalize();
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		}
		catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening blue card file");
		}
		
		Element blueSubDeck = configuration.getDocumentElement();
		NodeList blueCards = blueSubDeck.getElementsByTagName("BuildingCard");
		result = new SubDeck<>();
		for (int i=0; i< blueCards.getLength(); i++)
		{
			Node cardNode = blueCards.item(i);
				if (cardNode.getNodeType() == Node.ELEMENT_NODE)
					try {
					result.addCard((BuildingCard) cardFactory.makeDevelopmentCard((Element) cardNode, DevelopmentCardType.BUILDING));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Character card number " + i);
				}
				catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}
		
		return result;
	}
	
	private SubDeck<VentureCard> makePurpleDeck() throws BuildingDeckException{
		CardBuilder cardFactory = CardBuilder.instance();	
		Document configuration;
		SubDeck<VentureCard> result;
		try {
			File purpleFile = new File(purplePath);
			configuration = builder.parse(purpleFile);
			configuration.normalize();
		} catch (SAXException e) {
			LOGGER.log(Level.WARNING, "SAXException", e);
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		}
		catch (IOException i) {
			LOGGER.log(Level.WARNING, "IOException", i);
			throw new BuildingDeckException("Error opening blue card file");
		}
		
		Element blueSubDeck = configuration.getDocumentElement();
		NodeList blueCards = blueSubDeck.getElementsByTagName("BuildingCard");
		result = new SubDeck<>();
		for (int i=0; i< blueCards.getLength(); i++)
		{
			Node cardNode = blueCards.item(i);
				if (cardNode.getNodeType() == Node.ELEMENT_NODE)
					try {
					result.addCard((VentureCard) cardFactory.makeDevelopmentCard((Element) cardNode, DevelopmentCardType.VENTURE));
				} catch (BuildingCardException c) {
					LOGGER.log(Level.WARNING, "Building Card Exception", c);
					throw new BuildingDeckException("Error creating Character card number " + i);
				}
				catch (IllegalCardException x) {
					LOGGER.log(Level.WARNING, "Building Deck Exception", x);
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}
		
		return result;
	}

	/**
	 * If the deck is already configurated it will be utilized by every Match
	 * @return
	 * @throws BuildingDeckException
	 */
	public synchronized Deck makeDeck() throws BuildingDeckException{
		if (configuratedDeck == null){
			configuratedDeck = new Deck();
			configuratedDeck.setGreenDeck(makeGreenDeck());
			configuratedDeck.setBlueDeck(makeBlueDeck());
			configuratedDeck.setYellowDeck(makeYellowDeck());
			configuratedDeck.setPurpleDeck(makePurpleDeck()); 
		}
		return (Deck) configuratedDeck.clone();
	}


	
	public synchronized ImmProperties[] makePrivileges(){
		if (privileges == null){
		Document configuration;
		ArrayList<ImmProperties> bonuses = new ArrayList<>();
		try{
			File boardFile = new File(boardPath);
			configuration = builder.parse(boardFile);
			configuration.normalize();
			Element board = configuration.getDocumentElement();
			Element privileges = (Element) board.getElementsByTagName("Privileges").item(0);
			NodeList properties = privileges.getElementsByTagName("Properties");
			for (int i=0; i<properties.getLength(); i++){
				if (properties.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					bonuses.add(PropertiesBuilder.makeImmProperites((Element) properties.item(i)));
				}
			}
		}
		catch (SAXException | IOException i){
			bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 1), new Property(PropertiesId.WOOD, 1))); // 1 wood and 1 stone
			bonuses.add(new ImmProperties(new Property(PropertiesId.COINS, 2))); // 2 coins
			bonuses.add(new ImmProperties(new Property(PropertiesId.MILITARYPOINTS, 2))); // 2 
			bonuses.add(new ImmProperties(new Property(PropertiesId.FAITHPOINTS, 2)));
		}
		// TODO 
		privileges = bonuses.toArray(new ImmProperties[0]);
		return privileges;}
		else return privileges;
	}
	
	public synchronized ImmProperties[] makeInitialProperties(){
		if (initialProperties == null){
		Document configuration;
		ArrayList<ImmProperties> bonuses = new ArrayList<>();
		try{
			File boardFile = new File(boardPath);
			configuration = builder.parse(boardFile);
			configuration.normalize();			
			Element board = configuration.getDocumentElement();
			Element initialProps = (Element) board.getElementsByTagName("InitialProperties").item(0);
			NodeList properties = initialProps.getElementsByTagName("Properties");
			for (int i=0; i<properties.getLength(); i++){
				if (properties.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					bonuses.add(PropertiesBuilder.makeImmProperites((Element) properties.item(i)) );
				}
			}
		}
		catch (SAXException | IOException i){
			bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2), new Property(PropertiesId.COINS, 5), new Property(PropertiesId.SERVANTS, 3))); // 1 wood and 1 stone
			bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2), new Property(PropertiesId.COINS, 6), new Property(PropertiesId.SERVANTS, 3))); // 1 wood and 1 stone
			bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2), new Property(PropertiesId.COINS, 7), new Property(PropertiesId.SERVANTS, 3))); // 1 wood and 1 stone
			bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 2), new Property(PropertiesId.WOOD, 2), new Property(PropertiesId.COINS, 8), new Property(PropertiesId.SERVANTS, 3))); // 1 wood and 1 stone
		}
		// TODO 
		initialProperties = bonuses.toArray(new ImmProperties[0]);}
		return initialProperties;
	}
	
	public synchronized Map<DevelopmentCardType, Requirement[]> makeCardAddingRequirements(){
	if (cardAddingRequirement == null){
		Document configuration;
		EnumMap<DevelopmentCardType, Requirement[]> result = new EnumMap<>(DevelopmentCardType.class);
		try{
			File boardFile = new File(boardPath);
			configuration = builder.parse(boardFile);
			configuration.normalize();			
			Element board = configuration.getDocumentElement();
			Element cardReqs = (Element) board.getElementsByTagName("CardRequirement").item(0);
			ArrayList<Requirement> requirements;
			
			Element territory = (Element) cardReqs.getElementsByTagName("TerritoryRequirement").item(0);			
			requirements = new ArrayList<>();
			NodeList reqs = territory.getChildNodes();
			for (int i=0; i< reqs.getLength(); i++){
				if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE)
					requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
			}
			result.put(DevelopmentCardType.TERRITORY, requirements.toArray(new Requirement[0]));
			
			Element building = (Element) cardReqs.getElementsByTagName("BuildingRequirement").item(0);			
			requirements = new ArrayList<>();
			reqs = building.getChildNodes();
			for (int i=0; i< reqs.getLength(); i++){
				if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE)
					requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
			}
			result.put(DevelopmentCardType.BUILDING, requirements.toArray(new Requirement[0]));
			
			Element character  = (Element) cardReqs.getElementsByTagName("CharacterRequirement").item(0);	
			requirements = new ArrayList<>();
			reqs = character.getChildNodes();
			for (int i=0; i< reqs.getLength(); i++){
				if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE)
					requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
			}
			result.put(DevelopmentCardType.CHARACTER, requirements.toArray(new Requirement[0]));

			Element venture = (Element) cardReqs.getElementsByTagName("VentureRequirement").item(0);		
			requirements = new ArrayList<>();
			reqs = building.getChildNodes();
			for (int i=0; i< reqs.getLength(); i++){
				if (reqs.item(i).getNodeType() == Node.ELEMENT_NODE)
					requirements.add(PropertiesBuilder.makeRequirement((Element) reqs.item(i)));
			}
			result.put(DevelopmentCardType.VENTURE, requirements.toArray(new Requirement[0]));
			
		}
		catch (SAXException | IOException | XMLParseException i){
			result = new EnumMap<>(DevelopmentCardType.class);
			Requirement[] reqs = new Requirement[6];
			for(Requirement r: reqs){
				r = new Requirement(new CardsNumber(0), new ImmProperties(0));
			}
			for (DevelopmentCardType t: DevelopmentCardType.values()){
				result.put(t, reqs);
			}
		}
		// TODO 
		cardAddingRequirement = result;
		}
		return cardAddingRequirement;
	}
	
	
	public synchronized TrackBonuses makeTrackBonuses(){
			if (trackBonuses == null){
			Document configuration;
			TrackBonuses result;
			int[] military = new int[2];
			int[] faith = new int[15];
			try{
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();			
				Element board = configuration.getDocumentElement();
				Element militaryBonuses = (Element) board.getElementsByTagName("MilitaryBonuses").item(0);
				military[0] = Integer.parseInt(militaryBonuses.getAttribute("first"));
				military[1] = Integer.parseInt(militaryBonuses.getAttribute("second"));
				Element faithBonuses = (Element) board.getElementsByTagName("FaithBonuses").item(0);
				faith[0] = Integer.parseInt(faithBonuses.getAttribute("first"));
				faith[1] = Integer.parseInt(faithBonuses.getAttribute("second"));
				faith[2] = Integer.parseInt(faithBonuses.getAttribute("third"));
				faith[3] = Integer.parseInt(faithBonuses.getAttribute("fourth"));
				faith[4] = Integer.parseInt(faithBonuses.getAttribute("fifth"));
				faith[5] = Integer.parseInt(faithBonuses.getAttribute("sixth"));
				faith[6] = Integer.parseInt(faithBonuses.getAttribute("seventh"));
				faith[7] = Integer.parseInt(faithBonuses.getAttribute("eghth"));
				faith[8] = Integer.parseInt(faithBonuses.getAttribute("nineth"));
				faith[9] = Integer.parseInt(faithBonuses.getAttribute("tenth"));
				faith[10] = Integer.parseInt(faithBonuses.getAttribute("eleventh"));
				faith[11] = Integer.parseInt(faithBonuses.getAttribute("twelveth"));
				faith[12] = Integer.parseInt(faithBonuses.getAttribute("thirtheenth"));
				faith[13] = Integer.parseInt(faithBonuses.getAttribute("fourteenth"));
				faith[14] = Integer.parseInt(faithBonuses.getAttribute("fifteenth"));
				result = new TrackBonuses(faith, military);
			
			}
			
			catch (SAXException | IOException i){
				military[0] = 0 ;
				military[1] = 0;
				for (int k=0; k<15; k++){
					faith[k]=0;
				}
				result = new TrackBonuses(faith, military);
			}
			// TODO 
			trackBonuses = result;}
			return trackBonuses;
		}
		
	public synchronized ImmProperties makeCouncilBonuses(){
			if (councilBonuses == null ){
				Document configuration;
				ImmProperties result;

				try{
					File boardFile = new File(boardPath);
					configuration = builder.parse(boardFile);
					configuration.normalize();	
					Element board = configuration.getDocumentElement();
					Element councilBonus = (Element) board.getElementsByTagName("CouncilBonus").item(0); 
					result = PropertiesBuilder.makeImmProperites((Element) councilBonus.getElementsByTagName("Properties").item(0));
				}
				catch (IOException | SAXException e){
					result = new ImmProperties(new Property(PropertiesId.COINS,2));
				}
				
			}
			return councilBonuses;
		}
		
	public synchronized int makeCouncilPrivileges(){
			if (councilPrivileges == 0 ){
				Document configuration;
				EnumMap<DevelopmentCardType, int[]> result = new EnumMap<>(DevelopmentCardType.class);
				try{
					File boardFile = new File(boardPath);
					configuration = builder.parse(boardFile);
					configuration.normalize();			
					Element board = configuration.getDocumentElement();
					Element privileges = (Element) board.getElementsByTagName("CouncilPrivileges").item(0);
					int number = Integer.parseInt(privileges.getAttribute("value"));
					councilPrivileges = number;
				} 
				catch (IOException | SAXException x){
					councilPrivileges = 1;
				}
			}
			return councilPrivileges;
		}

	public synchronized Map<DevelopmentCardType, int[] > makeCardBonus(){
			if (cardBonuses == null){
				Document configuration;
				EnumMap<DevelopmentCardType, int[]> result = new EnumMap<>(DevelopmentCardType.class);
				try{
					File boardFile = new File(boardPath);
					configuration = builder.parse(boardFile);
					configuration.normalize();			
					Element board = configuration.getDocumentElement();
					Element cardBonuses = (Element) board.getElementsByTagName("CardBonuses").item(0);
					int[] bonuses = new int[6];
					
					Element territory = (Element) cardBonuses.getElementsByTagName("TerritoryBonuses").item(0);			
					bonuses[0] = Integer.parseInt(territory.getAttribute("value1"));
					bonuses[1] = Integer.parseInt(territory.getAttribute("value2"));
					bonuses[2] = Integer.parseInt(territory.getAttribute("value3"));
					bonuses[3] = Integer.parseInt(territory.getAttribute("value4"));
					bonuses[4] = Integer.parseInt(territory.getAttribute("value5"));
					bonuses[5] = Integer.parseInt(territory.getAttribute("value6"));
					result.put(DevelopmentCardType.TERRITORY, bonuses);
					
					bonuses = new int[6];
					Element building = (Element) cardBonuses.getElementsByTagName("BuildingBonuses").item(0);			
					bonuses[0] = Integer.parseInt(building.getAttribute("value1"));
					bonuses[1] = Integer.parseInt(building.getAttribute("value2"));
					bonuses[2] = Integer.parseInt(building.getAttribute("value3"));
					bonuses[3] = Integer.parseInt(building.getAttribute("value4"));
					bonuses[4] = Integer.parseInt(building.getAttribute("value5"));
					bonuses[5] = Integer.parseInt(building.getAttribute("value6"));
					result.put(DevelopmentCardType.BUILDING, bonuses);
					
					bonuses = new int[6];
					Element character = (Element) cardBonuses.getElementsByTagName("CharacterBonuses").item(0);			
					bonuses[0] = Integer.parseInt(character.getAttribute("value1"));
					bonuses[1] = Integer.parseInt(character.getAttribute("value2"));
					bonuses[2] = Integer.parseInt(character.getAttribute("value3"));
					bonuses[3] = Integer.parseInt(character.getAttribute("value4"));
					bonuses[4] = Integer.parseInt(character.getAttribute("value5"));
					bonuses[5] = Integer.parseInt(character.getAttribute("value6"));
					result.put(DevelopmentCardType.BUILDING, bonuses);
					
					bonuses = new int[6];
					Element venture = (Element) cardBonuses.getElementsByTagName("VentureBonuses").item(0);			
					bonuses[0] = Integer.parseInt(venture.getAttribute("value1"));
					bonuses[1] = Integer.parseInt(venture.getAttribute("value2"));
					bonuses[2] = Integer.parseInt(venture.getAttribute("value3"));
					bonuses[3] = Integer.parseInt(venture.getAttribute("value4"));
					bonuses[4] = Integer.parseInt(venture.getAttribute("value5"));
					bonuses[5] = Integer.parseInt(venture.getAttribute("value6"));
					result.put(DevelopmentCardType.VENTURE, bonuses);
				}
				catch (SAXException | IOException i){
					result = new EnumMap<>(DevelopmentCardType.class);
					int[] bonuses = new int[6];
					for(int h=0; h<bonuses.length; h++ ){
						bonuses[h]=0;
					}
					for (DevelopmentCardType t: DevelopmentCardType.values()){
						result.put(t, bonuses);
					}
				}
				// TODO 
				cardBonuses = result;
				}
				return cardBonuses;
		}
		
	public synchronized Map<DevelopmentCardType, ImmProperties[]> makeTowersBonus(){
			if (towersBonuses == null){
			Document configuration;
			EnumMap<DevelopmentCardType, ImmProperties[]> result = new EnumMap<>(DevelopmentCardType.class);
			try{
				File boardFile = new File(boardPath);
				configuration = builder.parse(boardFile);
				configuration.normalize();			
				Element board = configuration.getDocumentElement();
				Element cardReqs = (Element) board.getElementsByTagName("TowersBonuses").item(0);
				ArrayList<ImmProperties> properties;
				
				Element territory = (Element) cardReqs.getElementsByTagName("TerritoryTower").item(0);			
				properties = new ArrayList<>();
				NodeList props = territory.getChildNodes();
				for (int i=0; i< props.getLength(); i++){
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.TERRITORY, properties.toArray(new ImmProperties[0]));
				
				Element building = (Element) cardReqs.getElementsByTagName("BuildingTower").item(0);			
				properties = new ArrayList<>();
				props = building.getChildNodes();
				for (int i=0; i< props.getLength(); i++){
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.TERRITORY, properties.toArray(new ImmProperties[0]));
				
				Element character = (Element) cardReqs.getElementsByTagName("CharacterTower").item(0);			
				properties = new ArrayList<>();
				props = character.getChildNodes();
				for (int i=0; i< props.getLength(); i++){
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.TERRITORY, properties.toArray(new ImmProperties[0]));

				Element venture = (Element) cardReqs.getElementsByTagName("VentureTower").item(0);			
				properties = new ArrayList<>();
				props = venture.getChildNodes();
				for (int i=0; i< props.getLength(); i++){
					if (props.item(i).getNodeType() == Node.ELEMENT_NODE)
						properties.add(PropertiesBuilder.makeImmProperites((Element) props.item(i)));
				}
				result.put(DevelopmentCardType.TERRITORY, properties.toArray(new ImmProperties[0]));
				
			}
			catch (SAXException | IOException i){
				result = new EnumMap<>(DevelopmentCardType.class);
				ImmProperties[] properties = new ImmProperties[4];
				for(ImmProperties r: properties){
					r = new ImmProperties(0);
				}
				for (DevelopmentCardType t: DevelopmentCardType.values()){
					result.put(t, properties);
				}
			}
			// TODO 
			towersBonuses = result;
			}
			return towersBonuses;
		}

	public synchronized ImmProperties[] makeMarketBonuses(){
		if (marketBonuses == null){
		Document configuration;
		ArrayList<ImmProperties> bonuses = new ArrayList<>();
		try{
			File boardFile = new File(boardPath);
			configuration = builder.parse(boardFile);
			configuration.normalize();
			Element board = configuration.getDocumentElement();
			Element market = (Element) board.getElementsByTagName("MarketBonuses").item(0);
			NodeList properties = market.getElementsByTagName("Properties");
			for (int i=0; i<properties.getLength(); i++){
				if (properties.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					bonuses.add(PropertiesBuilder.makeImmProperites((Element) properties.item(i)));
				}
			}
		}
		catch (SAXException | IOException i){
			bonuses.add(new ImmProperties(new Property(PropertiesId.STONES, 1), new Property(PropertiesId.WOOD, 1))); // 1 wood and 1 stone
			bonuses.add(new ImmProperties(new Property(PropertiesId.COINS, 2))); // 2 coins
			bonuses.add(new ImmProperties(new Property(PropertiesId.MILITARYPOINTS, 2))); // 2 
			bonuses.add(new ImmProperties(new Property(PropertiesId.FAITHPOINTS, 2)));
		}
		marketBonuses = bonuses.toArray(new ImmProperties[0]);
		return marketBonuses;}
		else return marketBonuses;
	}

}
