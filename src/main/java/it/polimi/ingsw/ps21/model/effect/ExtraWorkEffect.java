package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.ExtraWorkAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class ExtraWorkEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5126312262461054005L;
	private int diceValue;
	private WorkType type;
	public ExtraWorkEffect(int diceValue, WorkType type) {
		super(new ImmProperties(0));
		this.diceValue = diceValue;
		this.type = type;
	}

	@Override
	public ExtraAction activate(Player player) {
		return new ExtraWorkAction(player.getId(), diceValue, type);
	}

	@Override
	public String getType() {
		return "Extra Work Effect";
	}

	@Override
	public String getDesc() {
		return "You can make an extra " + type + " action with the dice value of " + diceValue ;
	}

}
