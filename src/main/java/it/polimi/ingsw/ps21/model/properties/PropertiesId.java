package it.polimi.ingsw.ps21.model.properties;

import java.io.Serializable;

public enum PropertiesId implements Serializable{
	COINS("coins"), WOOD("wood pieces"), STONES("stones"), SERVANTS("servants"), VICTORYPOINTS("victory points"), MILITARYPOINTS("military points"), FAITHPOINTS("faith points");
	
	private String propertyName;
	
	PropertiesId(String name)
	{
		this.propertyName=name;
	}
	
	public String toString()
	{
		return this.propertyName;
	}
	
}
