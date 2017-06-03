package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.WorkModifier;

/*
 * To be implemented
 */
public class WorkBonus extends InstantLeaderEffect {

	private WorkType type;
	private int value;
	
	public WorkBonus(Requirement req, WorkType type, int value) {
		super(req);
		this.type = type;
		this.value = value;
		// TODO Auto-generated constructor stub
	}
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		WorkModifier modifier = player.getModifiers().getWorkMods();
		if (type == WorkType.HARVEST) modifier.setHarvestModifier(modifier.getHarvMod() + value);
		else modifier.setProductionModifier(modifier.getHarvMod() + value);
		return null;
	}
	@Override
	public String getType() {
		return "WorkBonusEffect";
	}
	@Override
	public String getDesc() {
		return "This effect add a permanent bonus value of " + value + " on dice value for effect of " + type.toString() + " card";
	}

}
