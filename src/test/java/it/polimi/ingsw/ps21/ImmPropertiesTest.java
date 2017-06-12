package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class ImmPropertiesTest {
	
	@Before
	public void setUp()
	{
		
	}

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
	
	

}
