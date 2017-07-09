package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;

/*
 * To be implemented
 */
public class ChurcSupport extends PermanentLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -519957114738998722L;

	public ChurcSupport(Requirement reqs[]){
		super(reqs);
	}

	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		player.getAdvMod().setVaticanSupportBonus(true);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "ChurchSupportLeaderEffect";
	}

	@Override
	public String getDesc() {
		return "You earn 5 victory point bonus every time you accept church support";
	}

}
