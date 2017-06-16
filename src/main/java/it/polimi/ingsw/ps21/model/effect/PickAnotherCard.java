package it.polimi.ingsw.ps21.model.effect;


import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.PickAnotherCardAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PickAnotherCard extends Effect {

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
		StringBuilder result = new StringBuilder("");
		result.append("This effect allows player to pick another card of type ");
		for (int i=0; i<types.length; i++){
			result.append(types[i].toString());
			if (i != types.length) result.append(", ");
		}
		result.append("with a dice value of " + diceReq + "without placing any FamilyMember");
		return result.toString();
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

}
