package it.polimi.ingsw.ps21.model.properties;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.MembersColor;

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
			if(valueNode.getNodeType() == Node.ELEMENT_NODE)
				tempPropsValue[PropertiesId.valueOf(((Element) valueNode).getTagName().toUpperCase()).ordinal()] = Integer.parseInt(((Element)valueNode).getAttribute("value"));
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
		
		NodeList colors = cardNode.getChildNodes();
		while(i < colors.getLength()) {
			if (colors.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element color = (Element) colors.item(i);
				if (color.getNodeName() == "Green"){
					green = Integer.parseInt(color.getAttributeNode("value").getNodeValue());
				}
				else	if (color.getNodeName() == "Blue"){
					blue = Integer.parseInt(color.getAttributeNode("value").getNodeValue());
					
				}
				else	if (color.getNodeName() == "Yellow"){
					yellow = Integer.parseInt(color.getAttributeNode("value").getNodeValue());
				}
				else  if (color.getNodeName() == "Purple"){
					purple = Integer.parseInt(color.getAttributeNode("value").getNodeValue());
				}
			}
			i++;
		}
		return new CardsNumber(green, blue, yellow, purple);
	}
	
	public static Requirement makeRequirement(Element req) throws XMLParseException { // Must be a Requirement Element
		if (req.getTagName() != "Requirement") throw new XMLParseException("Not a Requirement element");
		else {
			CardsNumber tempCardNum = new CardsNumber(0, 0, 0, 0); //Temporary values
			ImmProperties props = new ImmProperties(0,0,0,0,0,0, 0); //Temporary Values
			NodeList reqChilds = req.getChildNodes();
			for (int i= 0; i<reqChilds.getLength(); i++){
				if (reqChilds.item(i).getNodeType()== Node.ELEMENT_NODE){
					if (reqChilds.item(i).getNodeName() == "CardsNumber"){
						Node cardNode = reqChilds.item(i);
							tempCardNum = PropertiesBuilder.makeCardNums((Element) cardNode);
					}
					else	if (reqChilds.item(i).getNodeName() == "Properties"){
						Node propsNode = reqChilds.item(i);
							props = PropertiesBuilder.makeImmProperites((Element) propsNode);
						}
					}	
				}
			return new Requirement(tempCardNum, props);
		}
	}
	
	public static MembersColor makeMemberColor(Element memberColor){
		if (memberColor.getElementsByTagName("Black").getLength() > 0) return MembersColor.BLACK;
		if (memberColor.getElementsByTagName("Orange").getLength() > 0) return MembersColor.ORANGE;
		if (memberColor.getElementsByTagName("Neutral").getLength() > 0) return MembersColor.NEUTRAL;
		else return MembersColor.WHITE;
	}

}
