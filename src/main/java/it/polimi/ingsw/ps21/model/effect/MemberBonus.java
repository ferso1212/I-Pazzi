package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;

/*
 * To be implemented
 */

public class MemberBonus extends InstantLeaderEffect {

	private int valueBonus;
	
	public MemberBonus(Requirement reqs[], int value){
		super(reqs);
		this.valueBonus = value;
	}
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		for (MembersColor color: MembersColor.values()){
			player.getFamily().getMember(color).increaseModifier(valueBonus);}
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Member Bonus Effect";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "TO BE IMPLEMENTED";
	}

}
