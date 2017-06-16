package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class PropertyTest {
	private Property prop;
	private static final int INIT_VALUE=7;
	private static final int VALUE_TO_ADD=5;
	private static final int VALUE_TO_PAY=3;
	private static final int VALUE_TO_SET=9;
	private static final int ADDITION_MODIFIER=3;
	private static final int LITTLE_PAYMENT_MODIFIER=1;
	private static final int BIG_PAYMENT_MODIFIER=10;
	private static final int VALUE_TO_COMPARE=10;
	private static final int NEGATIVE_ADDITION_MODIFIER=-10;
	
	@Before
	public void setup()
	{
		this.prop=new Property(PropertiesId.COINS, INIT_VALUE);
	}
	

	@Test
	public void simplePropTest() {
		assertEquals(INIT_VALUE, this.prop.getValue());
		prop.addValue(VALUE_TO_ADD);
		assertEquals(INIT_VALUE + VALUE_TO_ADD, prop.getValue());
		prop.payValue(VALUE_TO_PAY);
		assertEquals(INIT_VALUE+ VALUE_TO_ADD - VALUE_TO_PAY, prop.getValue());
		prop.setValue(VALUE_TO_SET);
		assertEquals(VALUE_TO_SET, prop.getValue());
		prop.payValue(-VALUE_TO_PAY);
		assertEquals(VALUE_TO_SET, prop.getValue()); //payValue() should not do anything if the passed value is negative
		prop.addValue(-VALUE_TO_PAY);
		assertEquals(VALUE_TO_SET, prop.getValue()); //addValue() should not do anything if the passed value is negative
	}
	
	@Test
	public void modifiedPropTest()
	{
		
		prop.setAdditionModifier(ADDITION_MODIFIER);
		prop.setPaymentModifier(LITTLE_PAYMENT_MODIFIER);
		prop.addValue(VALUE_TO_ADD);
		assertEquals(INIT_VALUE + VALUE_TO_ADD + ADDITION_MODIFIER, prop.getValue());
		prop.payValue(VALUE_TO_PAY);
		assertEquals(INIT_VALUE+ VALUE_TO_ADD + ADDITION_MODIFIER - VALUE_TO_PAY - LITTLE_PAYMENT_MODIFIER, prop.getValue());
		prop.setValue(VALUE_TO_SET);
		assertEquals(VALUE_TO_SET, prop.getValue());
		prop.setPaymentModifier(BIG_PAYMENT_MODIFIER);
		prop.payValue(VALUE_TO_PAY);
		assertEquals(VALUE_TO_SET, prop.getValue());
		
		
	}
	
	@Test
	public void comparisonTest()
	{
		assertEquals(INIT_VALUE>=VALUE_TO_COMPARE, prop.greaterOrEqual(VALUE_TO_COMPARE));
	}
	
	@Test
	public void cloneTest()
	{
		Property clone= prop.clone();
		assert(clone!=prop);
	}
	
	@Test
	public void negativeAdditionMod()
	{
		prop.setAdditionModifier(NEGATIVE_ADDITION_MODIFIER);
		prop.addValue(VALUE_TO_ADD);
		assertEquals(prop.getValue(), Math.max(INIT_VALUE + VALUE_TO_ADD + NEGATIVE_ADDITION_MODIFIER, INIT_VALUE));
	}
}
