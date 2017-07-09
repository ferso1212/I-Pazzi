package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.WorkModifier;

/*
 * To be implemented
 */
public class WorkBonus extends InstantLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 228328698349112514L;
	private WorkType type;
	private int value;
	
	public WorkBonus(Requirement reqs[], WorkType type, int value) {
		super(reqs);
		this.type = type;
		this.value = value;
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
