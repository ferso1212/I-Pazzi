package it.polimi.ingsw.ps21.model;

import it.polimi.ingsw.ps21.model.ImmProperties;

public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, ImmProperties req1, ImmProperties req2, InstantEffect ins,
			PermanentEffect perm1, PermanentEffect perm2) {
		super(name, era, req1, req2, ins, perm1, perm2);
		// TODO Auto-generated constructor stub
	}
	
	public CharacterCard(String name, int era, ImmProperties req1, InstantEffect ins,
			PermanentEffect perm1){
		super(name, era, req1, ins, perm1);
	}
	
}
