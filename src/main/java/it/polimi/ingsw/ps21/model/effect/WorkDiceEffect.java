package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class WorkDiceEffect extends Effect {
	
	private int diceValue;
	private WorkType type;
	
	public WorkDiceEffect(int diceValue, WorkType type) {
		super(new ImmProperties(0));
		this.diceValue = diceValue;
		this.type = type;
	}

	@Override
	public ExtraAction activate(Player player) {
		player.getModifiers().getWorkMods().setWorkMod(type, diceValue);
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Work Dice Effect";
	}

	@Override
	public String getDesc() {
		return "This effect increase your dice of value of " + diceValue + " for " + type + " actions"; 
	}

}
