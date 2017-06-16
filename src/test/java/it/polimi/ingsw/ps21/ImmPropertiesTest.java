package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;

public class ImmPropertiesTest {
	

	@Test
	public void testConstructor() {
		ImmProperties props= new ImmProperties(0);
		for(PropertiesId id: PropertiesId.values())
		{
			assertEquals(0, props.getPropertyValue(id));
		}
		
		ImmProperties props2= new ImmProperties(4,7,2);
		assertEquals(4, props2.getPropertyValue(PropertiesId.COINS));
		assertEquals(7, props2.getPropertyValue(PropertiesId.WOOD));
		assertEquals(2, props2.getPropertyValue(PropertiesId.STONES));
		assertEquals(0, props2.getPropertyValue(PropertiesId.SERVANTS));
		assertEquals(0, props2.getPropertyValue(PropertiesId.VICTORYPOINTS));
		assertEquals(0, props2.getPropertyValue(PropertiesId.MILITARYPOINTS));
		assertEquals(0, props2.getPropertyValue(PropertiesId.FAITHPOINTS));
	}
	
	@Test
	public void testMethods()
	{
		ImmProperties props= new ImmProperties(1,2,3,4,5,6,7);
		ImmProperties props2= new ImmProperties(4,7,2);
		assertEquals(false, props.greaterOrEqual(props2));
		PropertiesSet props3= new PropertiesSet(0,0,0,1,2,3,4);
		assertEquals(true, props.greaterOrEqual(props3));
		ImmProperties propSum= props.sum(props2);
		assertEquals(5, propSum.getPropertyValue(PropertiesId.COINS));
		assertEquals(9, propSum.getPropertyValue(PropertiesId.WOOD));
		assertEquals(5, propSum.getPropertyValue(PropertiesId.STONES));
		assertEquals(4, propSum.getPropertyValue(PropertiesId.SERVANTS));
		assertEquals(5, propSum.getPropertyValue(PropertiesId.VICTORYPOINTS));
		assertEquals(6, propSum.getPropertyValue(PropertiesId.MILITARYPOINTS));
		assertEquals(7, propSum.getPropertyValue(PropertiesId.FAITHPOINTS));
	}

}
