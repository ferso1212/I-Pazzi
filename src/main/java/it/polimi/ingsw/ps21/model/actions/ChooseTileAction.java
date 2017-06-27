package it.polimi.ingsw.ps21.model.actions;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;

public class ChooseTileAction extends Action {

	public ChooseTileAction(PlayerColor playerId, PersonalBonusTile tiles[]) {
		super(playerId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Message update(Player player, Match match) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtraAction[] activate(Player player, Match match)
			throws NotExecutableException, RequirementNotMetException, InsufficientPropsException {
		// TODO Auto-generated method stub
		return null;
	}

}
