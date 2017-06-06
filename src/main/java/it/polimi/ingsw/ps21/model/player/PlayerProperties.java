package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;
import it.polimi.ingsw.ps21.model.properties.Property;

/**
 * This class is used to store mutable resource and points values associated to
 * a player.
 * 
 * @author fabri
 */
public class PlayerProperties implements Cloneable{

	/**
	 * The properties are stored in a PropertiesSet object.
	 */
	private PropertiesSet properties;

	/**
	 * Constructs the object taking a PropertiesSet.
	 * 
	 * @param properties
	 *            the properties set that will be included in the constructed
	 *            object.
	 */
	public PlayerProperties(PropertiesSet properties) {
		super();
		this.properties = properties;
	}

	/**
	 * Constructs the object taking an array of properties.
	 * 
	 * @param props
	 *            An array of property object.
	 */
	public PlayerProperties(Property... props) {
		this.properties = new PropertiesSet(props);
	}

	/**
	 * Constructs the object by initializing the contained properties' values
	 * with the properties Ids and the corresponding initial values. The
	 * properties' Ids are taken from the PropertiesId enum. If the number of
	 * parameters is < than the number of properties that should be set, the
	 * last properties (for which has not been provided an initial value) are
	 * automatically set to 0.
	 * 
	 * @param initValues
	 *            initValues sorted as in the PropertiesId enum (coins, wood,
	 *            stones, servants, victory points, military points, faith
	 *            points)
	 */
	public PlayerProperties(int... initValues) {
		this.properties = new PropertiesSet(initValues);
	}

	/**
	 * Returns the property with the matching id.
	 * 
	 * @param id
	 *            the id to search for.
	 * @return the property with the matching id.
	 */
	public Property getProperty(PropertiesId id) {
		return properties.getProperty(id);
	}

	/**
	 * Increases the value of all the properties in this object by a number of
	 * units specified in another ImmProperties object.
	 * 
	 * @param props
	 *            the object containing the properties to add.
	 * @return true if the operation succeeds, otherwise false.
	 */
	public boolean increaseProperties(ImmProperties props) {
		ArrayList<PropertiesId> propIdsToScan = props.getPropertiesIds();
		for (PropertiesId id : propIdsToScan) {
			if ((this.properties.getProperty(id).addValue(props.getPropertyValue(id))) == false)
				return false;
		}
		return true;
	}

	/**
	 * Decreases the value of all the properties in this object by a number of
	 * units specified in another ImmProperties object.
	 * 
	 * @param props
	 *            the object containing the properties to add.
	 * @return true if the operation succeeds, otherwise false.
	 */
	public boolean payProperties(ImmProperties propsToPay) {
		ArrayList<PropertiesId> propIdsToScan = propsToPay.getPropertiesIds();
		for (PropertiesId id : propIdsToScan) {
			if ((this.properties.getProperty(id).payValue(propsToPay.getPropertyValue(id))) == false)
				return false;
		}
		return true;
	}

	/**
	 * Decreases the value of all the properties in this object by a number of
	 * units specified in another ImmProperties object, minus the discount.
	 * 
	 * @param props
	 *            the object containing the properties to add.
	 * @return true if the operation succeeds, otherwise false.
	 */
	public boolean payProperties(ImmProperties propsToPay, PropertiesSet discount) {
		ArrayList<PropertiesId> propIdsToScan = propsToPay.getPropertiesIds();
		for (PropertiesId id : propIdsToScan) {
			if ((this.properties.getProperty(id)
					.payValue(propsToPay.getPropertyValue(id) - discount.getProperty(id).getValue())) == false)
				return false;
		}
		return true;
	}

	/**
	 * @return the properties
	 */
	public PropertiesSet getPropertiesSet() {
		return properties;
	}

	/**
	 * Compares all the properties of this object with the properties' values in
	 * the object passed as argument. If all the properties in this object have
	 * a value equal or greater than the value of the corresponding property in
	 * the object passed as argument, true is returned.
	 * 
	 * @param setToCompare
	 *            ImmProperties containing the values to compare
	 * @return true if, for each property in the object passed as argument, the
	 *         value of that property is < than the value of the corresponding
	 *         property in this object.
	 */
	public boolean greaterOrEqual(PlayerProperties propsToCompare) {
		return this.properties.greaterOrEqual(propsToCompare.getPropertiesSet());
	}

	/**
	 * Compares all the properties of this object with the properties' values in
	 * the ImmProperties object passed as argument. If all the properties in
	 * this object have a value equal or greater than the value of the
	 * corresponding property in the object passed as argument, true is
	 * returned.
	 * 
	 * @param setToCompare
	 *            ImmProperties containing the values to compare
	 * @return true if, for each property in the object passed as argument, the
	 *         value of that property is < than the value of the corresponding
	 *         property in this object.
	 */
	public boolean greaterOrEqual(ImmProperties propsToCompare) {
		return !(propsToCompare.greaterOrEqual(properties));
	}

	/**Accepts in input an array of costs and returns an arraylist containing the costs that the player can pay (because he has enough resources).
	 * 
	 * @param costsToCheck the array of costs that will be checked
	 * @return the costs that the player can pay
	 */
	public ArrayList<ImmProperties> getPayableCosts(ImmProperties[] costsToCheck) {
		ArrayList<ImmProperties> payableCosts = new ArrayList<ImmProperties>();
		for (ImmProperties cost : costsToCheck) {
			if (this.greaterOrEqual(cost)) {
				payableCosts.add(cost);
			}
		}
		return payableCosts;
	}
	

	/**Performs a deep copy of this object.
	 * @return a copy of this object.
	 * @throws CloneNotSupportedException 
	 */
	public PlayerProperties clone() throws CloneNotSupportedException
	{
		return (PlayerProperties) super.clone();
	}
	

}
