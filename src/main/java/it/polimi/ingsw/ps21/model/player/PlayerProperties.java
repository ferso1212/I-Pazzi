package it.polimi.ingsw.ps21.model.player;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;
import it.polimi.ingsw.ps21.model.properties.Property;

/**This class is used to store mutable resource and points values associated to a player.
*@author fabri
 */
public class PlayerProperties {
	
	/**The properties are stored in a PropertiesSet object.
	 */
	private PropertiesSet properties;
	
	/**Returns the property with the matching id.
	 * @param id the id to search for.
	 * @return the property with the matching id.
	 */
	public Property getProperty(PropertiesId id)
	{
		return properties.getProperty(id);
	}
	
	/**
	 * Increases the value of all the properties in this object by a number of units specified in another ImmProperties object.
	 * @param props the object containing the properties to add.
	 * @return true if the operation succeeds, otherwise false.
	 */
	public boolean increaseProperties(ImmProperties props)
	{
		ArrayList<PropertiesId> propIdsToScan = props.getPropertiesIds();
		for(PropertiesId id: propIdsToScan)
		{
			if((this.properties.getProperty(id).addValue(props.getPropertyValue(id)))==false) return false;
		}
		return true;
	}
	
	/**
	 * Decreases the value of all the properties in this object by a number of units specified in another ImmProperties object, 
	 * plus the value of the modifier of each property.
	 * @param props the object containing the properties to add.
	 * @return true if the operation succeeds, otherwise false.
	 */
	public boolean payProperties(ImmProperties propsToPay)
	{
		ArrayList<PropertiesId> propIdsToScan = propsToPay.getPropertiesIds();
		for(PropertiesId id: propIdsToScan)
		{
			if((this.properties.getProperty(id).payValue(propsToPay.getPropertyValue(id)))==false) return false;
		}
		return true;
	}
	
	
}
