package it.polimi.ingsw.ps21.model.properties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**Set of properties.
 * 
 * @author fabri
 *
 */
public class PropertiesSet implements Cloneable, Serializable{

	private final static Logger LOGGER = Logger.getLogger(PropertiesSet.class.getName());
	
	// maps each of the possible id values to the corresponding property
	private EnumMap<PropertiesId, Property> propertiesMap;

	/**
	 * Constructs the PropertiesSet object by initializing the propertiesMap with the properties Ids and the corresponding initial values. 
	 * The properties' Ids are taken from the PropertiesId enum.
	 * @param initValues
	 */
	public PropertiesSet(int... initValues) {
		this.propertiesMap = new EnumMap<>(PropertiesId.class);
		int i = 0; // index to move through initValues[] array
		for (PropertiesId propId : PropertiesId.values()) // for each value in the PropertiesId enum
		{
			/*
			 * Since the constructor accepts a dynamic number of integer
			 * parameters, it is possible that the number of parameters is less
			 * than the number of properties that should be set. In this case,
			 * the first k values in the initValues array are used to initialize
			 * the first k properties of the PropertiesId enum; the other
			 * properties (the ones for which has not been provided an initial
			 * value) are set to 0.
			 */
			int value;
			if (initValues.length > i) //checks if the current index is in the bounds of the array
				{value = initValues[i];}
			else
				{value = 0;}

			Property newProp = new Property(propId, value);
			propertiesMap.put(propId, newProp);
			i++;
		}
	}

	/**
	 * Returns the property object with the matching id.
	 * 
	 * @param id
	 * @return the Property object with the matching id.
	 */
	public Property getProperty(PropertiesId id) {
		return propertiesMap.get(id);
	}

	/**
	 * Returns an ArrayList containing all the Property objects stored.
	 * 
	 * @return ArrayList of all the properties stored in the object.
	 */
	public ArrayList<Property> getProperties() {
		ArrayList<Property> props= new ArrayList<Property>(this.propertiesMap.values());
		return props;
	}

	/**
	 * Increases the value of all the properties in this object by a number of units specified in another PropertiesSet object.
	 * 
	 * @param props
	 * @return true if the operations succeeds; otherwise, it returns false.
	 */
	public boolean increaseProperties(PropertiesSet props) {
		for (Property prop : props.getProperties()) {
			if ((this.getProperty(prop.getId()).addValue(prop.getValue())) == false)
				return false;
		}
		return true;
	}

	/** Decreases the value of all the properties in this object by a number of units specified in another PropertiesSet object.
	 * 
	 * @param props
	 * @return true if the operations succeeds; otherwise, it returns false.
	 */
	public boolean payProperties(PropertiesSet props) {
		for (Property prop : props.getProperties()) {
			if ((this.getProperty(prop.getId()).payValue(prop.getValue())) == false)
				return false;
		}
		return true;

	}
	
	/**Performs a deep copy of this object.
	 * 
	 */
	@Override
	public PropertiesSet clone()
	{
		PropertiesSet output;
		try {
			output=(PropertiesSet)super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "Clone failed", e);
			return null;
		}
	
		for(Property prop: this.propertiesMap.values())
		{
			//TODO
		}
		return output;
	}

	/** Returns a string in the format: "value1 prop1name, value2 prop2name, value3 prop3name". Only properties with a value != 0 are reported in the string.
	 * For example: "5 coins, 3 wood pieces, 7 coins"
	 */
	@Override
	public String toString() {
		String output= new String();
		Property[] propsToScan= propertiesMap.values().toArray(new Property[0]);
		for(int i=0; i<propsToScan.length; i++) 
		{
			if(propsToScan[i].getValue()!=0){//properties whose value is 0 are not reported in the string
			output.concat(propsToScan[i].toString());
			output.concat(", ");}
		}
		return output;
	}
	
	
}
