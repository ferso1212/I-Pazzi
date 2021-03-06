package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.modelmbean.XMLParseException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import it.polimi.ingsw.ps21.model.effect.EffectBuilder;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.effect.LeaderEffect;
import it.polimi.ingsw.ps21.model.effect.NullEffect;
import it.polimi.ingsw.ps21.model.match.BuildingCardException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesBuilder;

/**
 * Class defined with the use of singleton pattern that create card based on the Element in a xml file
 * @author daniele
 *
 */
public class CardBuilder {
		private static final Logger LOGGER = Logger.getLogger(CardBuilder.class.getName());
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

			ArrayList<RequirementAndCost> cardReqandCost = new ArrayList<>();
			// To be implemented
			EffectSet instantEffect = new EffectSet(new NullEffect());
			EffectSet permanentEffects[] = new EffectSet[0];
			NodeList cardProps = cardNode.getChildNodes();
			for (int i=0; i < cardProps.getLength(); i++){
			Node prop = cardProps.item(i);
			if (prop.getNodeType() == Node.ELEMENT_NODE){ // Evito i nodi che non rappresentano elementi in xml
				switch (prop.getNodeName()) {
				case "RequirementAndCost":
					cardReqandCost.add(makeRequirementAndCost((Element)prop));
					break;
				case "InstantEffect":
					instantEffect = effectBuilder.makeInstantEffect((Element) prop);
					break;
				case "PermanentEffect":
					permanentEffects = effectBuilder.makePermanentEffect((Element) prop);
					break;
				default:
					throw new BuildingCardException();
					}
				}
			}
			if (cardType == DevelopmentCardType.TERRITORY)
				return new TerritoryCard(cardName, cardEra, diceReq, instantEffect, permanentEffects);				
			else 
				if (cardType == DevelopmentCardType.CHARACTER)
					return new CharacterCard(cardName, cardEra, cardReqandCost.toArray(new RequirementAndCost[0]), instantEffect, permanentEffects);
				else
					if (cardType == DevelopmentCardType.VENTURE)
						return new VentureCard(cardName, cardEra, cardReqandCost.toArray(new RequirementAndCost[0]),instantEffect, permanentEffects[0]);
					else
						return new BuildingCard(cardName, cardEra, cardReqandCost.toArray(new RequirementAndCost[0]), diceReq, instantEffect, permanentEffects);
		}

		private RequirementAndCost makeRequirementAndCost(Element prop) {
			Requirement req;
			ImmProperties cost;
			Element reqElement =  (Element) prop.getElementsByTagName("Requirement").item(0);
			Element costElement = (Element) prop.getElementsByTagName("Cost").item(0);
			try {
				req = PropertiesBuilder.makeRequirement(reqElement);
				cost = PropertiesBuilder.makeCost(costElement);
			} catch (XMLParseException e) {
				LOGGER.log(Level.WARNING, "Error parsing RequirementAndCost", e);
				req = new Requirement(new CardsNumber(0), new ImmProperties(0));
				cost = new ImmProperties(0);
			}
			return new RequirementAndCost(req, cost);
		}

		public static LeaderCard makeLeaderCard(Element cardNode) throws BuildingCardException {
			EffectBuilder effectBuilder = EffectBuilder.instance();
			NamedNodeMap cardAttributes = cardNode.getAttributes();
			String cardName = cardAttributes.getNamedItem("name").getNodeValue();
			LeaderEffect effect;
			NodeList childs = cardNode.getChildNodes();
			int i=0;
			Node child = childs.item(0);
			i++;
			while(child.getNodeType()!= Node.ELEMENT_NODE && i<childs.getLength()){
				child = childs.item(i);
				i++;
			}
			if (i== childs.getLength()) throw new BuildingCardException();
			Element effectNode = (Element) child;
			effect = effectBuilder.makeLeaderEffect(effectNode);
			return new LeaderCard(cardName, effect);
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


}
