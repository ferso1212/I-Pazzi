package it.polimi.ingsw.ps21.model;


public class DevelopmentCard extends Card{

	protected int cardEra;
	protected InstantEffect instantEffect;
	protected PermanentEffect permanentEffect1;
	protected PermanentEffect permanentEffect2;
	
	
	public DevelopmentCard(String name, int era, Requirement req, InstantEffect ins,PermanentEffect perm){
		super(name, req);
		cardEra = era;
		instantEffect = ins;
		permanentEffect1 = perm;
		permanentEffect2 = null;		
	}
	
	public DevelopmentCard(String name, int era, Requirement reqs[], InstantEffect ins, PermanentEffect perm1, PermanentEffect perm2){
		super(name, reqs);
		cardEra = era;
		instantEffect = ins;
		permanentEffect1 = perm1;
		permanentEffect2 = perm2;		
	}
	
	public DevelopmentCard(String name, int era, Requirement[] reqs, InstantEffect ins, PermanentEffect perm1) {
		// TODO Auto-generated constructor stub
		super(name, reqs);
		cardEra = era;
		instantEffect = ins;
		permanentEffect1 = perm1;
	}

	public DevelopmentCard(String name, int era, Requirement req, InstantEffect ins, PermanentEffect perm1,
			PermanentEffect perm2) {
		super(name, req);
		cardEra = era; 
		instantEffect = ins; 
		permanentEffect1 = perm1;
		permanentEffect2 = perm2;
	}

	public int getEra(){
		return cardEra;
	}
	
	@Override
	public String toString(){ 
		StringBuilder temp = new StringBuilder();
		temp.append("Nome Carta: " + name + "; Era: " + cardEra + "\nRequisiti carta: ");
		for (Requirement r: requires){
			temp.append(r.toString());
		}
		return temp.toString();
	}
	
	public Effect[] getEffect(){
		
	}

	@Override
	public Requirement getRequirement() throws Exception {
		if (chosenReq == null) throw new Exception();
		return chosenReq;
	}

}

