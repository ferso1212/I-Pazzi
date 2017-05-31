package it.polimi.ingsw.ps21.model.deck;
import java.io.Serializable;

public enum DevelopmentCardType implements Serializable{
	TERRITORY("Territory"), BUILDING("Building"), CHARACTER("Character"), VENTURE("Venture");
	
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
