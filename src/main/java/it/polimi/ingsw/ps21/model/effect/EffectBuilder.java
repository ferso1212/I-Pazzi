package it.polimi.ingsw.ps21.model.effect;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.MultiplierType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesBuilder;

public class EffectBuilder {
	private static final Logger LOGGER = Logger.getLogger(EffectBuilder.class.getName());
	
	
	
	private static EffectBuilder instance = null;
	
	
	
	private EffectBuilder(){
		
	}

	public static EffectBuilder instance(){
		if (instance == null) instance = new EffectBuilder();
		return instance;
	}
	/**
	 * This class parses an element in card xml file and return the described effectSet"
	 * @param node of the element that represent an EffectSet
	 * @return
	 */
	public EffectSet makeInstantEffect(Element effectNode){ // Mi trovo nel nodo InstantEffect
		NodeList subNodes = effectNode.getChildNodes(); // IL nodo figlio è Effect set
		ArrayList<Effect> effects = new ArrayList<>();
		for (int i=0; i< subNodes.getLength(); i++)
		{
			Node node = subNodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) // Nodo EffectSet
			{
					NodeList singleEffectNodes = node.getChildNodes();
					for (int j=0; j< singleEffectNodes.getLength(); j++)
					{
							if (singleEffectNodes.item(j).getNodeType() == Node.ELEMENT_NODE)
								effects.add(parseEffect((Element) singleEffectNodes.item(j))); // Node che punta all'elemento del singolo effetto nell'effectSet
					}
					
			}
		}
		if (effects.size() == 0)
			return new EffectSet(new NullEffect());
		else return new EffectSet(effects.toArray(new Effect[0]));
	}
	
	/**
	 * This class parses an element in card xml file and return the described effectSet"
	 * @param node of the element that represent an EffectSet
	 * @return
	 */
	public EffectSet[] makePermanentEffect(Element effectNode){ //Mi trovo nel nodo PermanentEffect
		NodeList subNodes = effectNode.getChildNodes(); // Il nodo figlio è Effect set
		ArrayList<EffectSet> effectSet = new ArrayList<>();
		for (int i=0; i< subNodes.getLength(); i++)
		{
			Node node = subNodes.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) // Nodo EffectSet
			{
					NodeList singleEffectNodes = node.getChildNodes();
					ArrayList<Effect> effects = new ArrayList<>();
					for (int j=0; j< singleEffectNodes.getLength(); j++)
					{
							if (singleEffectNodes.item(j).getNodeType() == Node.ELEMENT_NODE) effects.add(parseEffect((Element) singleEffectNodes.item(j))); // Node che punta all'elemento del singolo effetto nell'effectSet
					}
					effectSet.add(new EffectSet(effects.toArray(new Effect[0])));
			}
		}
		if (effectSet.size() == 0) return new EffectSet[0];
		else return effectSet.toArray(new EffectSet[0]);
	}
	
	/**
	 * This class parses an element in card xml file and return the described effect
	 * @param node of the element that represent an Effect
	 * @return
	 */
	private Effect parseEffect(Element node){
		switch (node.getTagName()){
		case "NullEffect":
			return new NullEffect();
		case "CouncilEffect":
		{
			int value = Integer.parseInt(node.getAttributeNode("value").getNodeValue());
			return new CouncilEffect(new ImmProperties(0), value);
		}
		case "BlockTowerEffect":
			return new BlockTowerEffect();
		case "PropEffect":
		{
			NodeList propNodes = node.getChildNodes();
			int i=0;
			while(i < propNodes.getLength() && propNodes.item(i).getNodeType() != Node.ELEMENT_NODE) i++;
			ImmProperties cost = PropertiesBuilder.makeCost( (Element) propNodes.item(i));
			i++;
			while(i < propNodes.getLength() && propNodes.item(i).getNodeType() != Node.ELEMENT_NODE) i++;
			ImmProperties bonus = PropertiesBuilder.makeImmProperites( (Element) propNodes.item(i));
			return new PropEffect(cost, bonus);
		}
		case "DiscountEffect":
		{
			ArrayList<DevelopmentCardType> types = new ArrayList<>();
			Element propNode = (Element) node.getElementsByTagName("Properties").item(0);
			if (node.getElementsByTagName("Blue").getLength() != 0) types.add(DevelopmentCardType.TERRITORY);
			if (node.getElementsByTagName("Yellow").getLength() != 0) types.add(DevelopmentCardType.BUILDING);
			if (node.getElementsByTagName("Green").getLength() != 0) types.add(DevelopmentCardType.CHARACTER);
			if (node.getElementsByTagName("Purple").getLength() != 0) types.add(DevelopmentCardType.VENTURE);
			try {
				return new DiscountEffect(PropertiesBuilder.makeImmProperites(propNode), types.toArray(new DevelopmentCardType[0]));
			} catch (TooManyArgumentException e) {
				return new NullEffect();
			}
		}
		case "PickAnotherCardEffect":
		{
			ArrayList<DevelopmentCardType> types = new ArrayList<>();
			int diceReq = Integer.parseInt(node.getAttribute("diceValue"));
			if (node.getElementsByTagName("Green").getLength() != 0) types.add(DevelopmentCardType.TERRITORY);
			if (node.getElementsByTagName("Blue").getLength() != 0) types.add(DevelopmentCardType.BUILDING);
			if (node.getElementsByTagName("Yellow").getLength() != 0) types.add(DevelopmentCardType.CHARACTER);
			if (node.getElementsByTagName("Purple").getLength() != 0) types.add(DevelopmentCardType.VENTURE);
			return new PickAnotherCard(diceReq, types.toArray(new DevelopmentCardType[0]));
		}
		case "PrivilegeEffect":
			int number = Integer.parseInt(node.getAttribute("number"));
			return new PrivilegeEffect(number);
		case "CardDiceEffect":
		{
			ArrayList<DevelopmentCardType> types = new ArrayList<>();
			int diceValue = Integer.parseInt(node.getAttribute("diceValue"));
			if (node.getElementsByTagName("Green").getLength() != 0) types.add(DevelopmentCardType.TERRITORY);
			if (node.getElementsByTagName("Blue").getLength() != 0) types.add(DevelopmentCardType.BUILDING);
			if (node.getElementsByTagName("Yellow").getLength() != 0) types.add(DevelopmentCardType.CHARACTER);
			if (node.getElementsByTagName("Purple").getLength() != 0) types.add(DevelopmentCardType.VENTURE);
			try {
				return new CardDiceEffect(diceValue, types.toArray(new DevelopmentCardType[0]));
			} catch (TooManyArgumentException e) {
				LOGGER.log(Level.WARNING, "Too many arguments in CardDiceEffect tag", e);
				return new NullEffect();
			}			
		}
		case "WorkDiceEffect":
		{
			WorkType type;
			int diceValue = Integer.parseInt(node.getAttribute("diceValue"));
			if (node.getElementsByTagName("Production").getLength() != 0) type = WorkType.PRODUCTION;
			else type = WorkType.HARVEST;
			return new WorkDiceEffect(diceValue, type);
			
		}
		case "MultiplierEffect":
		{
			ImmProperties cost = PropertiesBuilder.makeCost((Element) node.getElementsByTagName("Cost").item(0));
			ImmProperties bonus = PropertiesBuilder.makeImmProperites((Element) node.getElementsByTagName("Properties").item(0));
			MultiplierType type;
			int value;
			Element multiplierType = (Element) node.getElementsByTagName("MultiplierType").item(0);
			NodeList childs = multiplierType.getChildNodes();
			int i=0;
			Node child = childs.item(0);
			i++;
			while( child.getNodeType()!=Node.ELEMENT_NODE && i<childs.getLength()) {
				child = childs.item(i);
				i++;
			}
			Element secondFactor = (Element) child;
			switch (secondFactor.getNodeName()){
			case "Wood":
				type = MultiplierType.WOOD;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Stone":
				type = MultiplierType.STONE;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Coins":
				type = MultiplierType.COINS;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Servant":
				type = MultiplierType.SERVANT;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "VictoryPoints":
				type = MultiplierType.VICTORY_POINTS;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "MilitaryPoints":
				type = MultiplierType.MILITARY_POINTS;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "FaithPoints":
				type = MultiplierType.FAITH_POINTS;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Blue":
				type = MultiplierType.BLUE_CARD;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Green":
				type = MultiplierType.GREEN_CARD;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Yellow":
				type = MultiplierType.YELLOW_CARD;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			case "Purple":
				type = MultiplierType.PURPLE_CARD;
				value = Integer.parseInt(secondFactor.getAttribute("value"));
				break;
			default :
					return new NullEffect();
			}
				
			return new MultiplierEffect(cost, bonus, type, value);
		}
		case "ExtraWorkEffect":
		{
			WorkType type;
			int diceValue = Integer.parseInt(node.getAttribute("diceValue"));
			if (node.getElementsByTagName("Production").getLength() != 0) type = WorkType.PRODUCTION ;
			else type = WorkType.HARVEST;
			return new ExtraWorkEffect(diceValue, type);
		}
		default :
			return new NullEffect();
		}
	}

	public LeaderEffect makeLeaderEffect(Element node) {
		LeaderEffect result;
		NodeList childs = node.getChildNodes();
		int i=0;
		Node effectNode = childs.item(i);
		i++;
		while(i<childs.getLength() && effectNode.getNodeType()!=Node.ELEMENT_NODE){
			effectNode = childs.item(i);
			i++;
		}
		if (i == childs.getLength()) return new NullLeaderEffect();
		Element effectElement = (Element) effectNode;
		try {if (node.getNodeName()=="InstantLeaderEffect"){
			switch((effectElement).getNodeName()){
			case "CouncilBonus":{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				int number = Integer.parseInt(effectElement.getAttribute("number") );
				return new CouncilBonus(req,number);
			}
			case "MemberBonus": {
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				MembersColor color = PropertiesBuilder.makeMemberColor((Element)effectElement.getElementsByTagName("MemberColor").item(0));
				int bonus = Integer.parseInt(effectElement.getAttribute("value"));
				return new MemberBonus(req, color, bonus);
			}
			case "PropertiesBonus":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				ImmProperties bonus = PropertiesBuilder.makeImmProperites((Element)effectElement.getElementsByTagName("Properties").item(0));
				return new PropertiesBonus(req, bonus);
			}
			case "InstantWorkEffect":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				WorkType type = makeWorkType((Element)effectElement.getElementsByTagName("WorkType").item(0));
				int value = Integer.parseInt(effectElement.getAttribute("diceValue"));
				return new InstantWorkEffect(req, type, value);
			}
			default:
			 return new NullLeaderEffect();
			}
		}
		else {
			switch(effectElement.getNodeName()){
			case "ChurchSupport":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				return new ChurcSupport(req);				
			}
			case "DoubleResources":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				return new DoubleResources(req);	
			}
			case "LorenzioIlMagnifico":
			{
				ArrayList<Requirement> reqs = new ArrayList<>();
				NodeList reqNodes = effectElement.getElementsByTagName("Requirement");
				for (int j = 0; j < reqNodes.getLength(); j++) {
					if (reqNodes.item(j).getNodeType() == Node.ELEMENT_NODE) reqs.add(PropertiesBuilder.makeRequirement((Element) reqNodes.item(j)));
				}
				return new LorenzoIlMagnificoEffect(reqs.toArray(new Requirement[0]));
			}
			case "NoMilitaryForTerritory":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				DevelopmentCardType typ;
				Element typesElement = (Element) effectElement.getElementsByTagName("DevelopmentCardType").item(0);
				if (typesElement.getElementsByTagName("Building").getLength() > 0) typ =DevelopmentCardType.BUILDING;
				else if (typesElement.getElementsByTagName("Territory").getLength() > 0) typ = DevelopmentCardType.TERRITORY;
				else if (typesElement.getElementsByTagName("Character").getLength() > 0) typ  = DevelopmentCardType.CHARACTER;
				else typ = DevelopmentCardType.VENTURE;
				return new NoMilitaryForTerritory(req, typ);	
			}
			case "NoPayOccupiedTower":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				return new NoPayOccupiedTower(req);
			}
			case "OccupiedSpace":
			{
				Requirement req = PropertiesBuilder.makeRequirement((Element)effectElement.getElementsByTagName("Requirement").item(0));
				return new OccupiedSpaceEffect(req);
			}
			default:
				return new NullLeaderEffect();
			}
			}	
		} catch(XMLParseException e){
			LOGGER.log(Level.WARNING, "Unrecognized Leader Effect tag", e);
			return new NullLeaderEffect();
		}
	}

	private WorkType makeWorkType(Element item) {
		if (item.getElementsByTagName("Harvest").getLength() > 0) return WorkType.HARVEST;
		else return WorkType.PRODUCTION;
	}
}