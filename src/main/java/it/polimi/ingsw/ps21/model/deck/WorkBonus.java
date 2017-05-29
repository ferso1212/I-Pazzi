package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.WorkModifier;

/*
 * To be implemented
 */
public class WorkBonus extends InstantLeaderEffect {

	private WorkType type;
	private int value;
	
	public WorkBonus(WorkType type, int value) {
		super();
		this.type = type;
		this.value = value;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void activate(Player player) {
		WorkModifier modifier = player.getModifiers().getWorkMods();
		if (type == WorkType.HARVEST) modifier.setHarvestModifier(modifier.getHarvMod() + value);
		else modifier.setProductionModifier(modifier.getHarvMod() + value);

	}

}
