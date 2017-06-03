package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardBuilder;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.SubDeck;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


// TODO implements signleton and factory for every effect
public class MatchFactory {
	private final static Logger LOGGER = Logger.getLogger(MatchFactory.class.getName());
	private static MatchFactory instance = null;
	private final String greenPath = (new File("")).getAbsolutePath().concat("/green-deck.xml").toString();
	private final String yellowPath = (new File("")).getAbsolutePath().concat("/yellow-deck.xml").toString();
	private final String bluePath = (new File("")).getAbsolutePath().concat("/blue-deck.xml").toString();
	private	final String purplePath = (new File("")).getAbsolutePath().concat("/purple-deck.xml").toString();
	private	final String privilegesPath = (new File("")).getAbsolutePath().concat("/privileges.xml").toString();
	private DocumentBuilder builder;
	private Deck configuratedDeck;
	/**
	 * Constructor 
	 * @throws ParserConfigurationException
	 * @throws IOException 
	 */
	
	
	private MatchFactory() throws ParserConfigurationException{
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
	public static MatchFactory instance() throws ParserConfigurationException, IOException{
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
	public Deck makeDeck() throws BuildingDeckException{
		if (configuratedDeck == null){
			configuratedDeck = new Deck();
			configuratedDeck.setGreenDeck(makeGreenDeck());
			configuratedDeck.setBlueDeck(makeBlueDeck());
			configuratedDeck.setYellowDeck(makeYellowDeck());
			configuratedDeck.setPurpleDeck(makePurpleDeck()); 
		}
		return (Deck) configuratedDeck.clone();
	}


	
	public ImmProperties[] makePrivileges(){
		Document configuration;
		try{
			File privilegesFile = new File(privilegesPath);
			configuration = builder.parse(privilegesFile);
			configuration.normalize();
		}
		catch (SAXException | IOException i){
			ArrayList<ImmProperties> defaultPrivileges = new ArrayList<>();
			defaultPrivileges.add(new ImmProperties(0,1,1)); // 1 wood and 1 stone
			defaultPrivileges.add(new ImmProperties(2,0,0,0)); // 2 coins
			defaultPrivileges.add(new ImmProperties(0,0,0,2)); // 2 
			defaultPrivileges.add(new ImmProperties(0,0,0,0,0));
		}
				
		ArrayList<ImmProperties> bonuses = new ArrayList<>();
		// TODO 
		return bonuses.toArray(new ImmProperties[0]);
	}
}
