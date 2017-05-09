package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.ImmProperties;

public class DevelopmentCard {

	protected String name;
	protected int cardEra;
	protected ImmProperties req1;
	protected ImmProperties req2;
	protected InstantEffect instantEffect;
	protected PermanentEffect permanentEffect1;
	protected PermanentEffect permanentEffect2;
	
	
	public DevelopmentCard(String name, int era, ImmProperties req, InstantEffect ins,PermanentEffect perm){
		this.name = name;
		cardEra = era;
		req1 = req;
		req2 = null;
		instantEffect = ins;
		permanentEffect1 = perm;
		permanentEffect2 = null;		
	}
	
	public DevelopmentCard(String name, int era, ImmProperties req1, ImmProperties req2, InstantEffect ins, PermanentEffect perm1, PermanentEffect perm2){
		this.name = name;
		cardEra = era;
		this.req1 = req1;
		this.req2 = req2;
		instantEffect = ins;
		permanentEffect1 = perm1;
		permanentEffect2 = perm2;		
	}
	
	public ImmProperties getRequirements(){
		if (req2 != null)
		{
			// choicing the peroperty 
		}
		return req1;
	}
	public int getEra(){
		return cardEra;
	}
	
	@Override
	public String toString(){ 
		StringBuilder temp = new StringBuilder();
		temp.append("Nome Carta: " + name + "; Era: " + cardEra + "\nRequisiti carta: ");
		if (req2 == null) temp.append(req1.toString());
		else temp.append(req1.toString() + " o " + req2.toString());
		return temp.toString();
	}

}

