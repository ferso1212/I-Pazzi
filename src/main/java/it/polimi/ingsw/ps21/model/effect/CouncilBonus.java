package it.polimi.ingsw.ps21.model.effect;

import java.util.Map;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/*
 * To be implemented
 */

public class CouncilBonus extends InstantLeaderEffect {
	
	private Map<Integer,ImmProperties> bonusPossibilities;
	private int bonuses;
	
	
	public CouncilBonus(int councilBonusNumber){
		super();
		bonuses = councilBonusNumber;
	}
	
	@Override
	public ExtraAction activate(Player player) {
		return new TakePrivilegesAction(player.getId(), bonuses);
	}

	@Override
	public String getType() {
		return new String("Council Bonus");
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
