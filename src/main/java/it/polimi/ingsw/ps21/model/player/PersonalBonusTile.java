package it.polimi.ingsw.ps21.model.player;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PersonalBonusTile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -244048267142678617L;
	private ImmProperties harvBonus;
	private int harvDiceReq;
	private ImmProperties prodBonus;
	private int prodDiceReq;
	private int id;
	
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

	public PersonalBonusTile(int id, ImmProperties harvBonus, int harvDiceReq, ImmProperties prodBonus, int prodDiceReq) {
		super();
		this.id = id;
		this.harvBonus = harvBonus;
		this.harvDiceReq = harvDiceReq;
		this.prodBonus = prodBonus;
		this.prodDiceReq = prodDiceReq;
	}
	
	public String toString()
	{
		StringBuilder b= new StringBuilder();
		b.append("Production \tDice value required to activate: "+ prodDiceReq + ";" + "\tBonus: " + prodBonus.toString());
		b.append("\n Harvest \tDice value required to activate: "+ harvDiceReq + ";" +"\tBonus: " + harvBonus.toString());
		return b.toString();
	}
	
	public int getID(){
		return this.id;
	}
}
