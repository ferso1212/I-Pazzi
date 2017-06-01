package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.Effect;
import it.polimi.ingsw.ps21.model.deck.EffectSet;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.PropEffect;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.SubDeck;
import it.polimi.ingsw.ps21.model.deck.TerritoryCard;
import it.polimi.ingsw.ps21.model.deck.VentureCard;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;
import it.polimi.ingsw.ps21.model.properties.Property;

public class MatchBuilder {
	private final static Logger LOGGER = Logger.getLogger(MatchBuilder.class.getName());
	private static File greenDeckFile;
	private static File blueDeckFile;
	private static File yellowDeckFile;
	private static File purpleDeckFile;	
	private static DocumentBuilder builder;
	private static Deck configuratedDeck;
	/**
	 * Constructor 
	 * @throws ParserConfigurationException
	 * @throws IOException 
	 */
	
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
	public static void initialize(String greenPath, String yellowPath, String bluePath, String purplePath) throws ParserConfigurationException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		builder = factory.newDocumentBuilder();
		File greenDeckFile = new File(greenPath);
		if (!(greenDeckFile.exists() && greenDeckFile.canRead())) throw new IOException("Impossible to open file");
		File blueDeckFile = new File(bluePath);
		if (!(blueDeckFile.exists() && blueDeckFile.canRead())) throw new IOException("Impossible to open file");
		File purpleDeckFile = new File(purplePath);
		if (!(purpleDeckFile.exists() && purpleDeckFile.canRead())) throw new IOException("Impossible to open file");
		File yellowDeckFile = new File(yellowPath);
		if (!(yellowDeckFile.exists() && yellowDeckFile.canRead())) throw new IOException("Impossible to open file");
	}
	
	
	private static SubDeck<TerritoryCard> makeGreenDeck() throws BuildingDeckException{
		Document configuration;
		SubDeck<TerritoryCard> result;
		try {
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
				if (cardNode.getNodeType() == cardNode.ELEMENT_NODE)
					try {
					result.addCard(makeTerritoryCard((Element) cardNode));
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
		
	private static SubDeck<CharacterCard> makeBlueDeck() throws BuildingDeckException{
		Document configuration;
		SubDeck<CharacterCard> result;
		try {
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
				if (cardNode.getNodeType() == cardNode.ELEMENT_NODE)
					try {
					result.addCard(makeCharacterCard((Element) cardNode));
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
	private static SubDeck<BuildingCard> makeYellowDeck(){
		Document configuration;
		// To be implemented
		return new SubDeck<BuildingCard>();
	}
	private static SubDeck<VentureCard> makePurpleDeck(){
		// To be implemented
		return new SubDeck<VentureCard>();
	}
	
	
	/*
	 * Card Generation methods
	 */
	
	/**
	 * 
	 * @param cardNode
	 * @return
	 */
	private static TerritoryCard makeTerritoryCard(Element cardNode) throws BuildingCardException{
		NamedNodeMap cardAttributes = cardNode.getAttributes();
		String cardName = cardAttributes.getNamedItem("name").getNodeValue();
		int cardEra = Integer.parseInt(cardAttributes.getNamedItem("era").getNodeValue());
		int diceReq = Integer.parseInt(cardAttributes.getNamedItem("diceRequirement").getNodeValue());
		// To be implemented
		ArrayList<Effect> instantEffect = new ArrayList<>();
		// To be implemented
		ArrayList<PropEffect> permanentEffects = new ArrayList<>();
		NodeList cardProps = cardNode.getChildNodes();
		try {
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == prop.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "InstantEffect":
					
					// TODO implement makeEffect instantEffect.add(makeEffect((Element) prop));
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
		return new TerritoryCard(cardName, cardEra, diceReq, new EffectSet(instantEffect.get(0)), new EffectSet((PropEffect []) permanentEffects.toArray(new PropEffect[0])));
		}
			/* catch (XMLParseException x) {
				throw new BuildingCardException();
			}*/
		finally{}
	}



	private static CharacterCard makeCharacterCard(Element cardNode) throws BuildingCardException {
		NamedNodeMap cardAttributes = cardNode.getAttributes();
		String cardName = cardAttributes.getNamedItem("name").getNodeValue();
		int cardEra = Integer.parseInt(cardAttributes.getNamedItem("era").getNodeValue());
		ArrayList<Requirement> cardReq = new ArrayList<>();
		ArrayList<ImmProperties> cardCosts = new ArrayList<>();
		// To be implemented
		Effect instantEffect = new PropEffect(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0)), new ImmProperties(1,0,0,1,0));
		// To be implemented
		Effect permanentEffects[] = new Effect[1];
		NodeList cardProps = cardNode.getChildNodes();
		// TODO implement makeEffectSet and EffectSet variable
		try {
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == prop.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "Requirement":
					cardReq.add(makeRequirement((Element)prop));
					break;
				case "Cost":
					cardCosts.add(makeCost((Element) prop));
					break;
				case "InstantEffect":
					instantEffect = new PropEffect(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0)), new ImmProperties(1,0,0,1,0));
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
		return new CharacterCard(cardName, cardEra, (Requirement []) cardReq.toArray(new Requirement[0]), (ImmProperties []) cardCosts.toArray(new ImmProperties[0]), new EffectSet(instantEffect), new EffectSet(permanentEffects));
		}
			catch (XMLParseException x) {
				LOGGER.log(Level.SEVERE, "Error parsing the XML", x);
				throw new BuildingCardException();
			}
	}
	
	private static VentureCard makeVentureCard(Element cardNode) throws BuildingCardException {
		NamedNodeMap cardAttributes = cardNode.getAttributes();
		String cardName = cardAttributes.getNamedItem("name").getNodeValue();
		int cardEra = Integer.parseInt(cardAttributes.getNamedItem("era").getNodeValue());
		ArrayList<Requirement> cardReq = new ArrayList<>();
		ArrayList<ImmProperties> cardCosts = new ArrayList<>();
		// TODO implement InstantEffect e PermanentEffect in xml
		Effect instantEffect = new PropEffect(new Requirement(new CardsNumber(), new ImmProperties()), new ImmProperties());
		// TODO
		Effect permanentEffects = new PropEffect((Requirement [])cardReq.toArray(new Requirement[0]), new ImmProperties(0,0,0));
		
		NodeList cardProps = cardNode.getChildNodes();
		try {
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == prop.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "Requirement":
					cardReq.add(makeRequirement((Element)prop));
					break;
				case "Cost":
					cardCosts.add(makeCost((Element) prop));
					break;
				case "InstantEffect":
					instantEffect = new PropEffect(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0)), new ImmProperties(1,0,0,1,0));
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
		return new VentureCard(cardName, cardEra, (Requirement []) cardReq.toArray(new Requirement[0]), (ImmProperties []) cardCosts.toArray(new ImmProperties[0]),new EffectSet(instantEffect), new EffectSet(permanentEffects));
		}
			catch (XMLParseException x) {
				LOGGER.log(Level.SEVERE, "Error parsing the XML", x);
				throw new BuildingCardException();
				
			}
	}
	/**
	 * @param cost is the node that identify Cost Element in XML configuration file
	 * @return
	 */
	private static ImmProperties makeCost(Element cost) {
		NodeList costChilds = cost.getElementsByTagName("Properties"); // Must be only one in xml
		return makeImmProperites((Element) costChilds.item(0));
	}
	

	/**
	 * @param cost is the node that identify Requirement Entity in XML configuration file
	 * @return
	 * @throws XMLParseException 
	 */
	private static Requirement makeRequirement(Element req) throws XMLParseException { // Must be a Requirement Element
		if (req.getTagName() != "Requirement") throw new XMLParseException("Not a Requirement element");
		else {
			CardsNumber tempCardNum = new CardsNumber(0, 0, 0, 0); //Temporary values
			ImmProperties props = new ImmProperties(0,0,0,0,0,0, 0); //Temporary Values
			NodeList cardNumNodes = req.getElementsByTagName("CardsNumber");
			for (int i= 0; i<cardNumNodes.getLength(); i++){
				Node cardNode = cardNumNodes.item(i);
				if (cardNode.getNodeType() == cardNode.ELEMENT_NODE){
					tempCardNum = makeCardNums((Element) cardNode);
				}
			}
			
			NodeList propsNodes = req.getElementsByTagName("Properties"); // Check on Child element with tag name properties (
			for (int i= 0; i<propsNodes.getLength(); i++){
				Node propsNode = propsNodes.item(i);
				if (propsNode.getNodeType() == propsNode.ELEMENT_NODE){
					props = makeImmProperites((Element) propsNode);
				}
			}
			return new Requirement(tempCardNum, props);
		}
		
	}

	private static ImmProperties makeImmProperites(Element propsNode) {
		int tempPropsValue[] = {0,0,0,0,0,0,0};// 
		NodeList propChilds = propsNode.getChildNodes();
		for (int i=0; i < propChilds.getLength(); i++){
			Node valueNode = propChilds.item(i);
			if(valueNode.getNodeType() == valueNode.ELEMENT_NODE)
				tempPropsValue[PropertiesId.valueOf(((Element) valueNode).getTagName().toUpperCase()).ordinal()] = Integer.parseInt(valueNode.getAttributes().getNamedItem("value").getNodeValue());
			}		
		return new ImmProperties(tempPropsValue);
	}

	private static CardsNumber makeCardNums(Element cardNode) {
		int tempCardValues[] = {0,0,0,0} ;
		NodeList children = cardNode.getChildNodes();
		for (int i =0; i < children.getLength(); i++){
			Node child = children.item(i);
			if (child.getNodeType() == child.ELEMENT_NODE){
				int tempValue = Integer.parseInt(child.getAttributes().getNamedItem("value").getNodeValue());
				switch (((Element) child).getTagName()){
				case "Green":
					tempCardValues[0]= tempValue;
					break;
				case "Yellow":
					tempCardValues[1]= tempValue;
					break;
				case "Blue":
					tempCardValues[2]= tempValue;
					break;
				case "Purple":
					tempCardValues[3]= tempValue;
					break;
				}
			}
		}
		return new CardsNumber(tempCardValues[0],tempCardValues[1], tempCardValues[2], tempCardValues[3]);
	}
	
	
	private Effect makeEffect(Element prop) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * If the deck is already configurated it will be utilized by every Match
	 * @return
	 * @throws BuildingDeckException
	 */
	public static Deck makeDeck() throws BuildingDeckException{
		if (configuratedDeck == null){
			configuratedDeck = new Deck();
			configuratedDeck.setGreenDeck(makeGreenDeck());
			configuratedDeck.setBlueDeck(makeBlueDeck());
			configuratedDeck.setYellowDeck(makeYellowDeck());
			configuratedDeck.setPurpleDeck(makePurpleDeck()); 
		}
		return (Deck) configuratedDeck.clone();
	}


}
