package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/* 
 * To be Implemented
 */
public class LorenzoIlMagnificoEffect extends PermanentLeaderEffect {
	/**
	 * 
	 */
	private static final long serialVersionUID = 90083515942042488L;

	public LorenzoIlMagnificoEffect(Requirement reqs[]) {
		super(reqs);
	}
	private LeaderCard chosenLeaderCopy;
	
	@Override
	public ExtraAction activate(AdvancedPlayer player) {
		chosenLeaderCopy.getEffect().activate(player);
		return new NullAction(player.getId());
	}
	
	public void setChoose(LeaderCard copied){
		chosenLeaderCopy = copied;
	}

	@Override
	public String getType() {
		return new String("Clone Leader card");
	}

	@Override
	public String getDesc() {
		return new String("Copy the skill of another leader played by an opponent. The chosen skill cannot be changed.");
	}
}
