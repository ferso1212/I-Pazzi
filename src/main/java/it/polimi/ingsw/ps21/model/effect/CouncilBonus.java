package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;

/*
 * To be implemented
 */

public class CouncilBonus extends InstantLeaderEffect {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2345329791566968566L;
	private int bonuses;
	
	
	public CouncilBonus(Requirement reqs[],int councilBonusNumber){
		super(reqs);
		bonuses = councilBonusNumber;
	}
	
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		return new TakePrivilegesAction(player.getId(), bonuses);
	}

	@Override
	public String getType() {
		return new String("Council Bonus");
	}

	@Override
	public String getDesc() {
		return "You hace to choose " + bonuses +  " privileges";
	}

}
