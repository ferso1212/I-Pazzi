package it.polimi.ingsw.ps21.model;

import java.util.Collection;
import java.util.EnumMap;

public class PropertiesSet {

	// maps each of the possible id values to the corresponding property
	private EnumMap<PropertiesId, Property> propertiesMap;

	/**
	 * Constructs the PropertiesSet object by initializing the propertiesMap
	 * with the properties Ids and the corresponding initial values.
	 * 
	 * @param initValues
	 */
	public PropertiesSet(int... initValues) {
		this.propertiesMap = new EnumMap<PropertiesId, Property>(PropertiesId.class);
		int i = 0; // index to move through initValues[] array
		for (PropertiesId propId : PropertiesId.values()) // for each value in
															// the PropertiesId
															// enum
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
			if (initValues.length > i)
				value = initValues[i];
			else
				value = 0;

			Property newProp = new Property(propId, value);
			propertiesMap.put(propId, newProp);
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
	 * Returns a collection containing all the Property objects stored.
	 * 
	 * @return Collection of all the properties stored in the object.
	 */
	public Collection<Property> getProperties() {
		return this.propertiesMap.values();
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
}
