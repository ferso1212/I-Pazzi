package it.polimi.ingsw.ps21.model.match;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import it.polimi.ingsw.ps21.model.deck.BuildingCard;
import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.CharacterCard;
import it.polimi.ingsw.ps21.model.deck.Deck;
import it.polimi.ingsw.ps21.model.deck.Effect;
import it.polimi.ingsw.ps21.model.deck.IllegalCardException;
import it.polimi.ingsw.ps21.model.deck.OrCosts;
import it.polimi.ingsw.ps21.model.deck.OrRequirement;
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
	
	private Document configuration;
	private final String greenCardsFilePath = "/home/gullit/Progetti/Git/I-Pazzi/green-deck.xml";
	private final String yellowCardsFilePath = "/home/gullit/Progetti/Git/I-Pazzi/yellow-deck.xml";
	private final String blueCardsFilePath = "/home/gullit/Progetti/Git/I-Pazzi/blue-deck.xml";
	private final String purpleCardsFilePath = "/home/gullit/Progetti/Git/I-Pazzi/purple-deck.xml";
	private DocumentBuilder builder;
	
	public MatchBuilder() throws ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		builder = factory.newDocumentBuilder();
	}
	
	private SubDeck<TerritoryCard> makeGreenDeck() throws BuildingDeckException{
		File greenDeckFile = new File(greenCardsFilePath);
		SubDeck<TerritoryCard> result;
		try {
			configuration = builder.parse(greenDeckFile);
			configuration.normalize();
		} catch (SAXException e) {
			throw new BuildingDeckException("Error Parsing green Subedeck");
		}
		catch (IOException i) {
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
					throw new BuildingDeckException("Error creating Territory card number " + i);
				}
				catch (IllegalCardException x) {
					throw new BuildingDeckException("Error adding Territory card number " + i);
				}
		}
		
		return result;
	}
		
	private SubDeck<CharacterCard> makeBlueDeck() throws BuildingDeckException{
		File blueDeckFile = new File(blueCardsFilePath);
		SubDeck<CharacterCard> result;
		try {
			configuration = builder.parse(blueDeckFile);
			configuration.normalize();
		} catch (SAXException e) {
			throw new BuildingDeckException("Error Parsing blue Subedeck");
		}
		catch (IOException i) {
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
					throw new BuildingDeckException("Error creating Character card number " + i);
				}
				catch (IllegalCardException x) {
					throw new BuildingDeckException("Error adding Character card number " + i);
				}
		}
		
		return result;
	}
	private SubDeck<BuildingCard> makeYellowDeck(){
		// To be implemented
		return new SubDeck<BuildingCard>();
	}
	private SubDeck<VentureCard> makePurpleDeck(){
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
	private TerritoryCard makeTerritoryCard(Element cardNode) throws BuildingCardException{
		NamedNodeMap cardAttributes = cardNode.getAttributes();
		String cardName = cardAttributes.getNamedItem("name").getNodeValue();
		int cardEra = new Integer(cardAttributes.getNamedItem("era").getNodeValue());
		int diceReq = new Integer(cardAttributes.getNamedItem("diceRequirement").getNodeValue());
		OrRequirement cardReq = new OrRequirement();
		OrCosts cardCosts = new OrCosts();
		// To be implemented
		Effect instantEffect = new PropEffect(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0))), new ImmProperties(1,0,0,1,0));
		// To be implemented
		PropEffect permanentEffects[] = new PropEffect[1];
		NodeList cardProps = cardNode.getChildNodes();
		try {
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == prop.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "Requirement":
					cardReq.addRequirement(makeRequirement((Element)prop));
					break;
				case "Cost":
					cardCosts.addCost(makeCost((Element) prop));
					break;
				case "InstantEffect":
					instantEffect = new PropEffect(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0))), new ImmProperties(1,0,0,1,0));
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
		return new TerritoryCard(cardName, cardEra, diceReq, instantEffect, permanentEffects);
		}
			catch (XMLParseException x) {
				throw new BuildingCardException();
			}
	}
	
	private CharacterCard makeCharacterCard(Element cardNode) throws BuildingCardException {
		NamedNodeMap cardAttributes = cardNode.getAttributes();
		String cardName = cardAttributes.getNamedItem("name").getNodeValue();
		int cardEra = new Integer(cardAttributes.getNamedItem("era").getNodeValue());
		OrRequirement cardReq = new OrRequirement();
		OrCosts cardCosts = new OrCosts();
		// To be implemented
		Effect instantEffect = new PropEffect(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0))), new ImmProperties(1,0,0,1,0));
		// To be implemented
		Effect permanentEffects[] = new Effect[1];
		NodeList cardProps = cardNode.getChildNodes();
		try {
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == prop.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "Requirement":
					cardReq.addRequirement(makeRequirement((Element)prop));
					break;
				case "Cost":
					cardCosts.addCost(makeCost((Element) prop));
					break;
				case "InstantEffect":
					instantEffect = new PropEffect(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0))), new ImmProperties(1,0,0,1,0));
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
		return new CharacterCard(cardName, cardEra, cardReq, cardCosts, instantEffect, permanentEffects);
		}
			catch (XMLParseException x) {
				throw new BuildingCardException();
			}
	}
	
	private VentureCard makeVentureCard(Element cardNode) throws BuildingCardException {
		NamedNodeMap cardAttributes = cardNode.getAttributes();
		String cardName = cardAttributes.getNamedItem("name").getNodeValue();
		int cardEra = new Integer(cardAttributes.getNamedItem("era").getNodeValue());
		OrRequirement cardReq = new OrRequirement();
		OrCosts cardCosts = new OrCosts();
		// TODO implement InstantEffect e PermanentEffect in xml
		Effect instantEffect = new PropEffect(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0))), new ImmProperties(1,0,0,1,0));
		// TODO
		Effect permanentEffects = new PropEffect(cardReq, new ImmProperties(0,0,0));
		
		NodeList cardProps = cardNode.getChildNodes();
		try {
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == prop.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "Requirement":
					cardReq.addRequirement(makeRequirement((Element)prop));
					break;
				case "Cost":
					cardCosts.addCost(makeCost((Element) prop));
					break;
				case "InstantEffect":
					instantEffect = new PropEffect(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0))), new ImmProperties(1,0,0,1,0));
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
		return new VentureCard(cardName, cardEra, cardReq, cardCosts, instantEffect, permanentEffects);
		}
			catch (XMLParseException x) {
				throw new BuildingCardException();
			}
	}
	/**
	 * @param cost is the node that identify Cost Element in XML configuration file
	 * @return
	 */
	private ImmProperties makeCost(Element cost) {
		NodeList costChilds = cost.getElementsByTagName("Properties"); // Must be only one in xml
		return makeImmProperites((Element) costChilds.item(0));
	}
	

	/**
	 * @param cost is the node that identify Requirement Entity in XML configuration file
	 * @return
	 * @throws XMLParseException 
	 */
	private Requirement makeRequirement(Element req) throws XMLParseException { // Must be a Requirement Element
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

	private ImmProperties makeImmProperites(Element propsNode) {
		int tempPropsValue[] = {0,0,0,0,0,0,0};// 
		NodeList propChilds = propsNode.getChildNodes();
		for (int i=0; i < propChilds.getLength(); i++){
			Node valueNode = propChilds.item(i);
			if(valueNode.getNodeType() == valueNode.ELEMENT_NODE)
				tempPropsValue[PropertiesId.valueOf(((Element) valueNode).getTagName().toUpperCase()).ordinal()] = new Integer(valueNode.getAttributes().getNamedItem("value").getNodeValue());
			}		
		return new ImmProperties(tempPropsValue);
	}

	private CardsNumber makeCardNums(Element cardNode) {
		int tempCardValues[] = {0,0,0,0} ;
		NodeList children = cardNode.getChildNodes();
		for (int i =0; i < children.getLength(); i++){
			Node child = children.item(i);
			if (child.getNodeType() == child.ELEMENT_NODE){
				int tempValue = new Integer(child.getAttributes().getNamedItem("value").getNodeValue());
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

	public Deck makeDeck() throws BuildingDeckException{
		Deck result = new Deck();
		result.setGreenDeck(makeGreenDeck());
		result.setBlueDeck(makeBlueDeck());
		result.setYellowDeck(makeYellowDeck());
		result.setPurpleDeck(makePurpleDeck());	
		return result;
	}


}
