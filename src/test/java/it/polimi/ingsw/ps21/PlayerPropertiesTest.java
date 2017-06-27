package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Test;

import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;
import it.polimi.ingsw.ps21.model.properties.Property;

public class PlayerPropertiesTest {

	@Test
	public void test() {
		PlayerProperties props= new PlayerProperties(new PropertiesSet(1));
		assertEquals(props.getProperty(PropertiesId.COINS).getValue(), 1);
		assertEquals(props.getProperty(PropertiesId.FAITHPOINTS).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.MILITARYPOINTS).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.WOOD).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.STONES).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.VICTORYPOINTS).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.SERVANTS).getValue(), 0);
		
	}
	
	@Test
	public void testSecondConstructor()
	{
		PlayerProperties props= new PlayerProperties(new Property(PropertiesId.COINS, 1));
		assertEquals(props.getProperty(PropertiesId.COINS).getValue(), 1);
		assertEquals(props.getProperty(PropertiesId.FAITHPOINTS).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.MILITARYPOINTS).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.WOOD).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.STONES).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.VICTORYPOINTS).getValue(), 0);
		assertEquals(props.getProperty(PropertiesId.SERVANTS).getValue(), 0);
	}
	
	@Test
	public void testMethods()
	{
		PlayerProperties props= new PlayerProperties(3,4,5,6,7,8,9);
		assertEquals(true, props.greaterOrEqual(new ImmProperties(1,2,3,4,5,6,7)));
		assertEquals(true, props.payProperties(new ImmProperties(1,2,3,4,5,6,7)));
		assertEquals(false, props.payProperties(new ImmProperties(8,8,8,8,8,8,8)));
		RequirementAndCost[] reqsCosts = new RequirementAndCost[2];
		reqsCosts[0]= new RequirementAndCost(new Requirement(new CardsNumber(0), new ImmProperties(1,1,1,1,1,1,1)), new ImmProperties(1,1,1,1,1,1,1));
		reqsCosts[1]= new RequirementAndCost(new Requirement(new CardsNumber(0), new ImmProperties(7,7,7,7,7,7,7)), new ImmProperties(7,7,7,7,7,7,7));
		assertEquals(1, props.getPayableRequirementsAndCosts(reqsCosts).size());
		PlayerProperties clonedProps;
		try {
			clonedProps = props.clone();
			assertEquals(clonedProps.getProperty(PropertiesId.COINS).getValue(), props.getProperty(PropertiesId.COINS).getValue());
		} catch (CloneNotSupportedException e) {
			fail();
		}
		
	}

}
