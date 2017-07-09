package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.MembersColor;


/*
 * To be implemented
 */

public class MemberBonus extends InstantLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5455657945308338307L;
	private int valueBonus;
	
	public MemberBonus(Requirement reqs[], int value){
		super(reqs);
		this.valueBonus = value;
	}
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		player.getFamily().getMember(MembersColor.NEUTRAL).increaseModifier(valueBonus);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Member Bonus Effect";
	}

	@Override
	public String getDesc() {
		return "This effect increase your neutral member value of " +  valueBonus;
	}

}
