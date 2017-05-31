package it.polimi.ingsw.ps21.model.deck;

import java.util.Map;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/*
 * To be implemented
 */

public class CouncilBonus extends InstantLeaderEffect {
	
	private Map<Integer,ImmProperties> bonusPossibilities;
	private ImmProperties chosenBonus[];
	
	
	public CouncilBonus(int councilBonusNumber){
		chosenBonus = new ImmProperties[councilBonusNumber];
	}
	
	@Override
	public void activate(Player player) throws UnchosenException {
		for (ImmProperties i : chosenBonus){
			if (i==null) throw new UnchosenException();
 		}
	}

}
