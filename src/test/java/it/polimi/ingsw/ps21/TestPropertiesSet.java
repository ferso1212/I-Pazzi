package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;
import it.polimi.ingsw.ps21.model.properties.Property;

public class TestPropertiesSet {

	@Test
	public void testConstructors() {
		PropertiesSet props= new PropertiesSet(0);
		for(PropertiesId p: PropertiesId.values())
		{
			assertEquals(0, props.getProperty(p).getValue());
		}
		assertEquals(true, props.isNull());
		props= new PropertiesSet(new Property(PropertiesId.COINS,2), new Property(PropertiesId.STONES,3));
		for(PropertiesId p: PropertiesId.values())
		{
			if(p!=PropertiesId.COINS && p!= PropertiesId.STONES) assertEquals(0, props.getProperty(p).getValue());
		}
		assertEquals(3, props.getProperty(PropertiesId.STONES).getValue());
		assertEquals(2, props.getProperty(PropertiesId.COINS).getValue());
	}
	
	@Test
	public void testMethods() {
		PropertiesSet props= new PropertiesSet(1,2,3,4,5,6,7);
		assertEquals(false, props.greaterOrEqual(new PropertiesSet(12,13,14,15,16,17,18)));
		props.increaseProperties(new PropertiesSet(7,6,5,4,3,2,1));
		int i=1;
		for(PropertiesId p: PropertiesId.values())
		{
			assertEquals(props.getProperty(p).getValue(), i + (8-i));
			i++;
		}
		props= new PropertiesSet(3,3,3,3,3,3,3);
		props.payProperties(new PropertiesSet(1,1,1,1,1,1,1));
		for(PropertiesId p: PropertiesId.values())
		{
			assertEquals(props.getProperty(p).getValue(), 3 -1);
			
		}
		PropertiesSet clone= props.clone();
		for(PropertiesId p: PropertiesId.values())
		{
			assertEquals(props.getProperty(p).getValue(), clone.getProperty(p).getValue());
			
		}
		System.out.println("\nValues in the PropertiesSet: " + props.toString());
	}

}
