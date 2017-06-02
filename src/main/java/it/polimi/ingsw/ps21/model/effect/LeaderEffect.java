package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderEffect extends Effect{

	protected Requirement requirement;
	public boolean activated = false;
	public boolean clonable =false;
	
	public LeaderEffect(Requirement req,ImmProperties cost) {
		super(cost);
		requirement = req;
	}

	public boolean isActivated() {
		return activated;
	}
	
	public ExtraAction activate(Player player){
		clonable = true;
		return new NullAction();
	}
	
	public abstract void resetActivation();

	public boolean isClonable() {
		// TODO Auto-generated method stub
		return clonable;
	}
		
	

}
