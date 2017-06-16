package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
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
		super(new ImmProperties(0,0,0,0,0,0,0));
	}
	
	@Override
	public ExtraAction activate(Player player) {
		player.getModifiers().getActionMods().setNoPlacementBonus();
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return new String("Forbid tower space bonuses");
		
	}

	@Override
	public String getDesc() {
		return new String("You don’t get the bonuses when you take a Development Card from the towers (through a Family Member or as an effect of another card.)");
	}

}
