package it.polimi.ingsw.ps21.model;

import it.polimi.ingsw.ps21.model.ImmProperties;

public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, Requirement reqs[], InstantEffect ins,
			PermanentEffect perm1, PermanentEffect perm2) {
		super(name, era, reqs, ins, perm1, perm2);
	}
	
	public CharacterCard(String name, int era, Requirement req, InstantEffect ins,
			PermanentEffect perm1, PermanentEffect perm2) {
		super(name, era, req, ins, perm1, perm2);
	}
	public CharacterCard(String name, int era, Requirement reqs[], InstantEffect ins,
			PermanentEffect perm1){
		super(name, era, reqs, ins, perm1);
	}
	
}
