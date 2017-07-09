package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


/**
 * This effect is used in Cards without InstantEffect or PermanentEffect to avoid use of NULL Pointer 
 * @author gullit
 *
 */
public class NullEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2815907921669518763L;

	public NullEffect() {
		super(new ImmProperties(0));
	}

	@Override
	public ExtraAction activate(Player player){
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "NullEffect";
	}

	@Override
	public String getDesc() {
		return "This effect does nothing";
	}

}
