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
public class ImmProperties implements Serializable, Cloneable{
	private final static Logger LOGGER = Logger.getLogger(ImmProperties.class.getName());
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private PropertiesSet properties;

/**
 * Constructs the ImmProperties objects. Accepts an arbitrary number of integer parameters that are the initial values of the stored properties.
 * The number and the sorting of the parameters should be the same as in the PropertiesId enum.
 * @param initValues the first value is the initial value of the first property in the PropertiesId enum, 
 * the second value is the initial value of the second property in the PropertiesId enum, and so on.
 */
public ImmProperties(int...initValues)
{
	this.properties=new PropertiesSet(initValues);
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

/**Performs a deep copy of the object.
 * 
 */
public ImmProperties clone()
{
	try {
		return (ImmProperties)(super.clone());
	} catch (CloneNotSupportedException e) {
		LOGGER.log(Level.SEVERE, "Clone failed", e);
		return null;
	}
}

public String toString()
{
	return properties.toString();
}
}
