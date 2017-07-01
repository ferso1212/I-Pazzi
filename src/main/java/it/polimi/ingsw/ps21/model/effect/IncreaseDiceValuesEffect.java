package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.MembersColor;

public class IncreaseDiceValuesEffect extends PermanentLeaderEffect {


	/**
	 * 
	 */
	private int whiteValue;
	private int blackValue;
	private int orangeValue;
	public IncreaseDiceValuesEffect(Requirement reqs[], int orange, int black, int white) {
		super(reqs);
		this.whiteValue = white;
		this.orangeValue = orange;
		this.blackValue = black;
	}
	@Override
	public String getType() {
		return "Fixed Dice Leader Effect";
	}

	@Override
	public String getDesc() {
		return "This effect increase the orange dice value of "+ orangeValue + ", the black dice value of " + blackValue + "and the white dice value of " + whiteValue +
					" for all your next actions";
	}
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable = true;
		this.activated = true;
		player.getFamily().getMember(MembersColor.ORANGE).setModifier(orangeValue);
		player.getFamily().getMember(MembersColor.WHITE).setModifier(whiteValue);
		player.getFamily().getMember(MembersColor.BLACK).setModifier(blackValue);
		return new NullAction(player.getId());
	}
}
