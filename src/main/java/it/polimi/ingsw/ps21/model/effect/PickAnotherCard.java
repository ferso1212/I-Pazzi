package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.PickAnotherCardAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PickAnotherCard extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473595752354703590L;
	private int diceReq;
	private DevelopmentCardType[] types;
	
	public PickAnotherCard(int diceReq, DevelopmentCardType...types){
		super(new ImmProperties(0));
		this.diceReq = diceReq;
		if (types.length!=0) this.types = types;
		else 
		{
			this.types =  DevelopmentCardType.values();
			
		}
		
	}
	@Override
	public ExtraAction activate(Player player) {
		return new PickAnotherCardAction(player, diceReq, types);
	}

	@Override
	public String getType() {
		return "Pick Another Card Effect";
	}

	@Override
	public String getDesc() {
		StringBuilder result = new StringBuilder("");
		result.append("You can pick another card of type ");
		for (int i=0; i<types.length; i++){
			result.append(types[i].toString());
			if (i != types.length) result.append(", ");
		}
		result.append("with a dice value of " + diceReq + " without placing any FamilyMember");
		return result.toString();
	}

}
