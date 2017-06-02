package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;
import java.util.jar.Attributes.Name;
import java.util.logging.Level;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps21.model.match.BuildingCardException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

/**
 * Class defined with the use of singleton pattern that create card based on the Element in a xml file
 * @author daniele
 *
 */
public class CardBuilder {
		private static CardBuilder instance = null;
		
		
		
		private CardBuilder(){
			
		}

		public static CardBuilder instance(){
			if (instance == null) instance = new CardBuilder();
			return instance;
		}
		
		/*
		 * Card Generation methods
		 */
		
		
		/**
		 * 
		 * @param cardNode
		 * @return
		 */


		public DevelopmentCard makeDevelopmentCard(Element cardNode, DevelopmentCardType cardType) throws BuildingCardException {
			EffectBuilder effectBuilder = EffectBuilder.instance();
			NamedNodeMap cardAttributes = cardNode.getAttributes();
			String cardName = cardAttributes.getNamedItem("name").getNodeValue();
			int cardEra = Integer.parseInt(cardAttributes.getNamedItem("period").getNodeValue());
			int diceReq = 0;
			if (cardType == DevelopmentCardType.BUILDING || cardType == DevelopmentCardType.TERRITORY) 			
				 diceReq = Integer.parseInt(cardAttributes.getNamedItem("diceRequirement").getNodeValue());

			ArrayList<Requirement> cardReq = new ArrayList<>();
			ArrayList<ImmProperties> cardCosts = new ArrayList<>();
			// To be implemented
			EffectSet instantEffect = new EffectSet(new NullEffect());
			ArrayList<EffectSet> permanentEffects = new ArrayList<>();
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
						instantEffect = effectBuilder.makeInstanEffect((Element) prop);
						break;
					case "PermanentEffect":
						permanentEffects.add(effectBuilder.makePermanentEffect((Element) prop));
						break;
					default:
						throw new BuildingCardException();
						}
					}
				}
				if (cardType == DevelopmentCardType.TERRITORY)
					return new TerritoryCard(cardName, cardEra, diceReq, instantEffect, new EffectSet((PropEffect []) permanentEffects.toArray(new PropEffect[0])));				
				else 
					if (cardType == DevelopmentCardType.CHARACTER)
						return new CharacterCard(cardName, cardEra, cardReq.toArray(new Requirement[0]), cardCosts.toArray(new ImmProperties[0]), instantEffect,
								new EffectSet(permanentEffects.toArray(new Effect[0])));
					else
						if (cardType == DevelopmentCardType.VENTURE)
							return new VentureCard(cardName, cardEra, cardReq.toArray(new Requirement[0]), cardCosts.toArray(new ImmProperties[0]),instantEffect, permanentEffects.get(0));
						else
							return new BuildingCard(cardName, cardEra, cardReq.toArray(new Requirement [0]), cardCosts.toArray(new ImmProperties [0]), diceReq, instantEffect, permanentEffects.toArray(new EffectSet[0]));
			}
				catch (XMLParseException x) {
					throw new BuildingCardException();
				}
		}
		
		public TerritoryCard makeTerritoryCard(Element cardNode) throws BuildingCardException{
			EffectBuilder effectBuilder = EffectBuilder.instance();
			NamedNodeMap cardAttributes = cardNode.getAttributes();
			String cardName = cardAttributes.getNamedItem("name").getNodeValue();
			int cardEra = Integer.parseInt(cardAttributes.getNamedItem("period").getNodeValue());
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
			return new TerritoryCard(cardName, cardEra, diceReq, new EffectSet(instantEffect.get(0)), new EffectSet(permanentEffects.toArray(new PropEffect[0])));
			}
				/* catch (XMLParseException x) {
					throw new BuildingCardException();
				}*/
			finally{}
		}
		
	
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
		
		



		

		/**
		 * @param cost is the node that identify Cost Element in XML configuration file
		 * @return
		 */

		

		/**
		 * @param cost is the node that identify Requirement Entity in XML configuration file
		 * @return
		 * @throws XMLParseException 
	 */
		
		
		private CardsNumber makeCardNums(Element cardNode) throws XMLParseException {
			if (cardNode.getTagName() != "CardsNumber") throw new XMLParseException("not a CardsNumber Element");
			int i= 0;
			int green = 0;
			int yellow = 0;
			int blue = 0;
			int purple = 0;
			
			NodeList colors = cardNode.getElementsByTagName("Green");
			while(i < colors.getLength()) {
				if (colors.item(i).getNodeType() == cardNode.ELEMENT_NODE) break;
				i++;
			}
			Element subElement = (Element) colors.item(i); 
			green = Integer.parseInt(subElement.getAttributeNode("value").getNodeValue());
			
			colors = cardNode.getElementsByTagName("Blue");
			while(i < colors.getLength()) {
				if (colors.item(i).getNodeType() == cardNode.ELEMENT_NODE) break;
				i++;
			}
			subElement = (Element) colors.item(i); 
			blue = Integer.parseInt(subElement.getAttributeNode("value").getNodeValue());
	
			colors = cardNode.getElementsByTagName("Yellow");
			while(i < colors.getLength()) {
				if (colors.item(i).getNodeType() == cardNode.ELEMENT_NODE) break;
				i++;
			}
			subElement = (Element) colors.item(i); 
			yellow = Integer.parseInt(subElement.getAttributeNode("value").getNodeValue());
			
			colors = cardNode.getElementsByTagName("Purple");
			while(i < colors.getLength()) {
				if (colors.item(i).getNodeType() == cardNode.ELEMENT_NODE) break;
				i++;
			}
			subElement = (Element) colors.item(i); 
			purple = Integer.parseInt(subElement.getAttributeNode("value").getNodeValue());
			return new CardsNumber(green, yellow, blue, purple);
		}

		private ImmProperties makeCost(Element cost) {
			NodeList costChilds = cost.getElementsByTagName("Properties"); // Must be only one in xml
			return makeImmProperites((Element) costChilds.item(0));
		}
		
		private ImmProperties makeImmProperites(Element propsNode) {
			int tempPropsValue[] = {0,0,0,0,0,0,0};// 
			NodeList propChilds = propsNode.getChildNodes();
			for (int i=0; i < propChilds.getLength(); i++){
				Node valueNode = propChilds.item(i);
				if(valueNode.getNodeType() == valueNode.ELEMENT_NODE)
					tempPropsValue[PropertiesId.valueOf(((Element) valueNode).getTagName().toUpperCase()).ordinal()] = Integer.parseInt(valueNode.getAttributes().getNamedItem("value").getNodeValue());
				}		
			return new ImmProperties(tempPropsValue);
		}

}
