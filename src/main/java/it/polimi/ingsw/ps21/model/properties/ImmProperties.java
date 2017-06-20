package it.polimi.ingsw.ps21.model.properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.PlayerData;

/**This class is used to store immutable resource and points values.
*For example, it is used to store card effects on resources and points.

*@author fabri
 */
public class ImmProperties implements Serializable{
	private final static Logger LOGGER = Logger.getLogger(ImmProperties.class.getName());
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PropertiesSet properties;

/**
 * Constructs the ImmProperties objects. Accepts an arbitrary number of integer parameters that are the initial values of the stored properties.
 * The number and the sorting of the parameters should be the same as in the PropertiesId enum: CONIS, WOOD, STONES, SERVANTS, VICOTRYPOINTS, MILITARYPOINTS, FAITHPOINTS.
 * @param initValues the first value is the initial value of the first property in the PropertiesId enum, 
 * the second value is the initial value of the second property in the PropertiesId enum, and so on.
 */
public ImmProperties(int...initValues)
{
	this.properties=new PropertiesSet(initValues);
}

public ImmProperties(Property...properties)
{
	this.properties=new PropertiesSet(properties);
}
/**Returns the value of the property with the matching id.
 * @param id the id of the property.
 * @return the value of the property.
 */
public int getPropertyValue(PropertiesId id)
{
	return this.properties.getProperty(id).getValue();
}

/**Returns an ArrayList of the ids of the properties stored in this object.
 * 
 * @return
 */
public ArrayList<PropertiesId> getPropertiesIds()
{
	ArrayList<Property> props = this.properties.getProperties();
	ArrayList<PropertiesId> output= new ArrayList<PropertiesId>();
	for(Property prop: props)
	{
		output.add(prop.getId());
	}
	return output;
}


/**Compares all the properties of this object with the properties' values in the object passed as argument.
 * If all the properties in this object have a value equal or greater than the value of the corresponding property in the object passed as argument, true is returned.
 * @param setToCompare ImmProperties containing the values to compare
 * @return true if, for each property in the object passed as argument, the value of that property is < than the value of the corresponding property in this object.
 */
public boolean greaterOrEqual(ImmProperties propsToCompare)
{
	for(PropertiesId propId: propsToCompare.getPropertiesIds())
	{
		if(this.getPropertyValue(propId) < propsToCompare.getPropertyValue(propId)) return false;
	}
	return true;
}

/**Compares this object with the PropertiesSet passed as argument.
 * If all the properties in this object have a value equal or greater than the value of the corresponding property in the set passed as argument, true is returned.
 * @param setToCompare PropertiesSet containing the values to compare
 * @return true if, for each property in the set passed as argument, the value of that property is < than the value of the corresponding property in this set.
 */
public boolean greaterOrEqual(PropertiesSet propsToCompare)
{
	return this.properties.greaterOrEqual(propsToCompare);
}

/** Returns a string in the format: "value1 prop1name, value2 prop2name, value3 prop3name". Only properties with a value != 0 are reported in the string.
 * For example: "5 coins, 3 wood pieces, 7 coins"
 */
public String toString()
{
	return properties.toString();
}

/**Adds the values in this object to the ones in the object passed as argument and returns a new ImmProperties object that contains the sum.
 * @param addend the object that will be added
 * @return a new ImmProperties object that contains the sum between this object and the object passed as argument
 */
public ImmProperties sum(ImmProperties addend)
{
	int[] sum= new int[this.getPropertiesIds().size()];
	
	int i=0;
	for(PropertiesId propId: PropertiesId.values())
	{
		sum[i]=this.getPropertyValue(propId)+ addend.getPropertyValue(propId);
		i++;
	}
	return new ImmProperties(sum);
}

/** Checks if all the properties have value=0.
 * @return true if all the properties in this set have value equal to 0.
 */
public boolean isNull()
{
	return this.properties.isNull();
}
}
