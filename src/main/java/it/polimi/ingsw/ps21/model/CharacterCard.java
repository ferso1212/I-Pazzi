package it.polimi.ingsw.ps21.model;

public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, Requirement reqs[],ImmProperties cost, Effect ins,
			Effect perms[]) {
		super(name, era, reqs, cost, ins, perms);
	}
	
	public CharacterCard(String name, int era, Requirement req, ImmProperties cost, Effect ins,  Effect perms []) {
		super(name, era, req, cost, ins, perms);
	}
	public CharacterCard(String name, int era, Requirement reqs[], ImmProperties cost, Effect ins,
			Effect perm1){
		super(name, era, reqs, cost, ins, perm1);
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.CHARACTER;
	}
}
 