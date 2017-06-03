package it.polimi.ingsw.ps21.model.properties;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps21.model.deck.CardsNumber;

public class PropertiesBuilder {
	
	public static ImmProperties makeCost(Element cost) {
		NodeList costChilds = cost.getElementsByTagName("Properties"); // Must be only one in xml
		return makeImmProperites((Element) costChilds.item(0));
	}
	
	public static ImmProperties makeImmProperites(Element propsNode) {
		int tempPropsValue[] = {0,0,0,0,0,0,0};// 
		NodeList propChilds = propsNode.getChildNodes();
		for (int i=0; i < propChilds.getLength(); i++){
			Node valueNode = propChilds.item(i);
			if(valueNode.getNodeType() == valueNode.ELEMENT_NODE)
				tempPropsValue[PropertiesId.valueOf(((Element) valueNode).getTagName().toUpperCase()).ordinal()] = Integer.parseInt(valueNode.getAttributes().getNamedItem("value").getNodeValue());
			}		
		return new ImmProperties(tempPropsValue);
	}
	
	public static CardsNumber makeCardNums(Element cardNode) throws XMLParseException {
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

}
