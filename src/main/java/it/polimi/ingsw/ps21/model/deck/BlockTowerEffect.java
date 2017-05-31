package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


/**
 * This is the worst effect of all development card effects, because it doesn't allow to player to get
 * bonus of the placement of a family memeber in a tower space
 * @author gullit
 *
 */
public class BlockTowerEffect extends Effect {

	public BlockTowerEffect(){
		super(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0,0,0))));
	}
	
	@Override
	public void activate(Player player) {
		player.getModifiers().getActionMods().setNoPlacementBonus();
	}

}