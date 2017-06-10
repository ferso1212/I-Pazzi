package it.polimi.ingsw.ps21.model.effect;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.ArrayList;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.TooManyArgumentException;
import it.polimi.ingsw.ps21.model.match.BuildingCardException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesBuilder;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class EffectBuilder {
	
	
	
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
	public EffectSet makeInstanEffect(Element effectNode){ // Mi trovo nel nodo InstantEffect
		NodeList subNodes = effectNode.getChildNodes(); // IL nodo figlio è Effect set
		ArrayList<Effect> effectSet = new ArrayList<>();
		for (int i=0; i< subNodes.getLength(); i++)
		{
			Node node = subNodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) // Nodo EffectSet
			{
					NodeList singleEffectNodes = node.getChildNodes();
					for (int j=0; j< singleEffectNodes.getLength(); j++)
					{
							if (singleEffectNodes.item(j).getNodeType() == Node.ELEMENT_NODE) effectSet.add(parseEffect((Element) singleEffectNodes.item(j))); // Node che punta all'elemento del singolo effetto nell'effectSet
					}
			}
		}
		if (effectSet.size() == 0) return new EffectSet(new NullEffect());
		else return new EffectSet(effectSet.toArray(new Effect[0]));
	}
	
	/**
	 * This class parses an element in card xml file and return the described effectSet"
	 * @param node of the element that represent an EffectSet
	 * @return
	 */
	public EffectSet makePermanentEffect(Element effectNode){
		return new EffectSet(new NullEffect());
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
			Element costNode = (Element) node.getElementsByTagName("Cost").item(0);
			Element propNode = (Element) node.getElementsByTagName("Properties").item(0);
			if (node.getElementsByTagName("Territory").getLength() != 0) types.add(DevelopmentCardType.TERRITORY);
			if (node.getElementsByTagName("Building").getLength() != 0) types.add(DevelopmentCardType.BUILDING);
			if (node.getElementsByTagName("Character").getLength() != 0) types.add(DevelopmentCardType.CHARACTER);
			if (node.getElementsByTagName("Venture").getLength() != 0) types.add(DevelopmentCardType.VENTURE);
			try {
				return new DiscountEffect(PropertiesBuilder.makeCost(costNode), PropertiesBuilder.makeImmProperites(propNode), types.toArray(new DevelopmentCardType[0]));
			} catch (TooManyArgumentException e) {
				return new NullEffect();
			}
		}
		case "PickAnotherCard":
		{
			ArrayList<DevelopmentCardType> types = new ArrayList<>();
			int diceReq = Integer.parseInt(node.getAttribute("diceValue"));
			if (node.getElementsByTagName("Territory").getLength() != 0) types.add(DevelopmentCardType.TERRITORY);
			if (node.getElementsByTagName("Building").getLength() != 0) types.add(DevelopmentCardType.BUILDING);
			if (node.getElementsByTagName("Character").getLength() != 0) types.add(DevelopmentCardType.CHARACTER);
			if (node.getElementsByTagName("Venture").getLength() != 0) types.add(DevelopmentCardType.VENTURE);
			return new PickAnotherCard(diceReq, types.toArray(new DevelopmentCardType[0]));
		}
		case "PrivilegeEffect":
			int number = Integer.parseInt(node.getAttribute("number"));
			return new PrivilegeEffect(number);
		}
		return new NullEffect();
	}
}
