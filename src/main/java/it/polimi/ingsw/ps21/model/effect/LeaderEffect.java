package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderEffect extends Effect{

	public boolean activated = false;
	public boolean clonable =false;
	
	public LeaderEffect() {
		super(new ImmProperties(0));
	}

	public boolean isActivated() {
		return activated;
	}
	
	public ExtraAction activate(Player player){
		clonable = true;
		return new NullAction(player.getId());
	}
	
	public abstract void resetActivation();

	public boolean isClonable() {
		// TODO Auto-generated method stub
		return clonable;
	}
		
	

}
