package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderEffect {

	protected Requirement requirement;
	public boolean activated = false;
	public boolean clonable =false;
	
	public LeaderEffect(Requirement req) {
		requirement = req;
	}

	public boolean isActivated() {
		return activated;
	}
	
	public ExtraAction activate(AdvancedPlayer player){
		clonable = true;
		return new NullAction(player.getId());
	}
	
	public abstract void resetActivation();

	public boolean isClonable() {
		// TODO Auto-generated method stub
		return clonable;
	}
	
	public abstract String getType();
	public abstract String getDesc();

		
	

}
