package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.MembersColor;

public class FixedDiceValuesEffect extends PermanentLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7001930598983769233L;
	private int whiteValue;
	private int blackValue;
	private int orangeValue;
	public FixedDiceValuesEffect(Requirement reqs[], int orange, int black, int white) {
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
		return "This effect set the orange dice to "+ orangeValue + ", the black dice to " + blackValue + "and the white to " + whiteValue +
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
