package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.PickAnotherCardAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;

public class LeaderPickAnotherCardEffect extends InstantLeaderEffect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1747004586256614431L;
	private DevelopmentCardType types[];
	private int diceReq;
	public LeaderPickAnotherCardEffect(Requirement reqs[]) {
		super(reqs);
		types = new DevelopmentCardType[4];
		types[0] = DevelopmentCardType.BUILDING;
		types[1] = DevelopmentCardType.TERRITORY;
		types[2] = DevelopmentCardType.CHARACTER;
		types[3] = DevelopmentCardType.VENTURE;
		diceReq = 6;
				
	}
	
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		this.clonable=true;
		this.activated=true;
		return new PickAnotherCardAction(player, diceReq, types);
	}

	@Override
	public String getType() {
		return "Pick Another Card Leader Effect";
	}

	@Override
	public String getDesc() {
		return "You can pick one Development Card with dice value of 6";
	}

}
