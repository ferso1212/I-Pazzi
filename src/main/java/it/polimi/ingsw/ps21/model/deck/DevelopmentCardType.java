package it.polimi.ingsw.ps21.model.deck;
import java.io.Serializable;

public enum DevelopmentCardType implements Serializable{
	TERRITORY("Territory"), CHARACTER("Character"), BUILDING("Building"), VENTURE("Venture");
	
	private String type;
	
	DevelopmentCardType(String type)
	{
		this.type=type;
	}
	
	public String toString()
	{
		return this.type;
	}

}
