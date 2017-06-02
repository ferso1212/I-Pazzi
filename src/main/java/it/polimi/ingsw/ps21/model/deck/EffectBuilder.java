package it.polimi.ingsw.ps21.model.deck;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.ArrayList;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps21.model.match.BuildingCardException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class EffectBuilder {
	
	
	
	private static EffectBuilder instance = null;
	
	
	
	private EffectBuilder(){
		
	}

	public static EffectBuilder instance(){
		if (instance == null) instance = new EffectBuilder();
		return instance;
	}
	
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
	
	public EffectSet makePermanentEffect(Element effectNode){
		return new EffectSet(new NullEffect());
	}
	
	private Effect parseEffect(Element node){
		switch (node.getTagName()){
		case "NullEffect":
			return new NullEffect();
		case "CouncilEffect":
		{
			int value = Integer.parseInt(node.getAttributeNode("value").getNodeValue());
			return new CouncilBonus(value);
		}
		case "BlockTowerEffect":
			return new BlockTowerEffect();
		case "PropEffect":
		{
			NodeList propNodes = node.getChildNodes();
			int i=0;
			while(i < propNodes.getLength() && propNodes.item(i).getNodeType() != Node.ELEMENT_NODE) i++;
			ImmProperties cost = makeCost( (Element) propNodes.item(i));
			i++;
			while(i < propNodes.getLength() && propNodes.item(i).getNodeType() != Node.ELEMENT_NODE) i++;
			ImmProperties bonus = makeImmProperites( (Element) propNodes.item(i));
			return new PropEffect(new Requirement(new CardsNumber(0), cost), bonus);
		}
		}
		return new NullEffect();
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
