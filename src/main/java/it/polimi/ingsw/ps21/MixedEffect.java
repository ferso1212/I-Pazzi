package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.Player;

public class MixedEffect {
	private PermanentEffect permEffects[];
	private InstantEffect instEffects[];
	
	public void addInstantEffect(Player player){
		for (InstantEffect i: instEffects){
			i.addInstantBonus(player);
		}
	}
	
	public void addPermanentEffect(Player player){
		for (PermanentEffect p: permEffects){
			p.addPermanentEffect(player);
		}
	}
	
	public MixedEffect(InstantEffect inst[], PermanentEffect perm[]){
		permEffects = perm.clone();
		instEffects = inst.clone();
	}
}
