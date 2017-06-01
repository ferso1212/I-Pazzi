package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;
import java.util.logging.Level;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
	
	public EffectSet makeInstanEffect(Element effectNode){
		return new EffectSet(new NullEffect());
	}
	
	public EffectSet makePermanentEffect(Element effectNode){
		return new EffectSet(new NullEffect());
	}
	
	private Effect parseEffect(Element node){
		return new NullEffect();
	}
	

}
