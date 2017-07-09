package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.TakePrivilegesAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PrivilegeEffect extends Effect{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7922759253820298215L;
	private int numberOfPrivileges;

	public PrivilegeEffect(int numberOfPrivileges) {
		super(new ImmProperties(0,0,0,0,0,0,0));
		this.numberOfPrivileges = numberOfPrivileges;
	}

	@Override
	public ExtraAction activate(Player player) {
		return new TakePrivilegesAction(player.getId(), this.numberOfPrivileges);
	}

	@Override
	public String getType() {
		return new String("Privilege Effect");
	}

	@Override
	public String getDesc() {
		return "You can choose " + numberOfPrivileges + " different Council Privilege.";
	}
	
	

}
