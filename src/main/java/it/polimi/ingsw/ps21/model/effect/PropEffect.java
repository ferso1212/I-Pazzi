package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.Property;

public class PropEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -998102579744108732L;
	private ImmProperties bonus;
	
	public PropEffect(ImmProperties cost, ImmProperties bonus) {
		super(cost);
		this.bonus = bonus;
	}
	
	
	/**
	 * Check if at least one Requirement is satisfied by player
	 */
	@Override
	public boolean isActivable(Player player) {
		boolean check = false;
		check = player.getProperties().greaterOrEqual(cost); // Basta che uno dei requisiti sia attivabile
		return check;
	}
	@Override
	public ExtraAction activate(Player player) {
		player.getProperties().payProperties(cost);
		ImmProperties toAdd= new ImmProperties(0);
		toAdd=toAdd.sum(bonus);
		if(player instanceof AdvancedPlayer)
		{
			if(((AdvancedPlayer)player).getAdvMod().acquiresDoubleResources())
			{
				toAdd=toAdd.sum(new ImmProperties(new Property(PropertiesId.COINS, bonus.getPropertyValue(PropertiesId.COINS)),
						new Property(PropertiesId.WOOD, bonus.getPropertyValue(PropertiesId.WOOD)),
						new Property(PropertiesId.STONES, bonus.getPropertyValue(PropertiesId.STONES))
						,new Property(PropertiesId.SERVANTS, bonus.getPropertyValue(PropertiesId.SERVANTS))));
				



			}
		}
		player.getProperties().increaseProperties(toAdd);
		
		return new NullAction(player.getId());
	}

	@Override
	public String getType() {
		return "Property Effect";
	}

	@Override
	public String getDesc() {
		String output= new String("You instantly acquire ");
		output = output.concat(bonus.toString());
		return output;
	}

}
