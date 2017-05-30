package it.polimi.ingsw.ps21.model.player;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PersonalBonusTile {
	private ImmProperties harvBonus;
	private int harvDiceReq;
	private ImmProperties prodBonus;
	private int prodDiceReq;
	
	public ImmProperties getTileBonus(WorkType type, int value) throws IllegalArgumentException
	{
		switch(type)
		{
		case HARVEST:
		{
			if(value>=harvDiceReq) return harvBonus;
			else return new ImmProperties(0,0,0,0,0,0,0); 
		}
		
		case PRODUCTION:
		{
			if(value>=prodDiceReq) return prodBonus;
			else return new ImmProperties(0,0,0,0,0,0,0);
		}
		default: throw new IllegalArgumentException();
		}
		
	}
	
	public int getDiceReq(WorkType type)
	{
		if(type==WorkType.HARVEST) return this.harvDiceReq;
		else return this.prodDiceReq;
	}
}
