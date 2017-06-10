package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.MatchController;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class UserHandler extends Observable implements Visitor, Runnable, Observer {
	private PlayerColor playerId;
	private Connection connection;
	private String name;

	public UserHandler(PlayerColor playerId, Connection connection) {
		super();
		this.playerId = playerId;
		this.connection = connection;
		this.name = this.connection.getName();
		this.connection.sendMessage(this.name + "'s UserHandler created.");
	}

	private Action reqUserAction() {
		return null;
	}

	@Override
	public void visit(VaticanChoice choice) {
		choice.setChosen(connection.reqVaticanChoice());
	}

	@Override
	public void visit(CostChoice choice) {
		choice.setChosen(connection.reqCostChoice(choice.getChoices()));
	}

	@Override
	public void visit(CouncilChoice choice) {
		// TODO need to pass possible privileges
		choice.setPrivilegesChosen(connection.reqPrivilegesChoice(choice.getNumberOfChoices()));
		choice.setVisited();
	}

	@Override
	public void visit(EffectChoice choice) {
		// TODO Auto-generated method stub
		choice.isVisited();

	}

	@Override
	public void visit(WorkMessage message) {

	}

	@Override
	public void visit(AcceptedAction message) {
		connection.sendMessage(message.getMessage());
		message.setVisited();
	}

	@Override
	public void visit(RefusedAction message) {
		connection.sendMessage(message.getMessage());
		message.setVisited();
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public PlayerColor getPlayerId() {
		return playerId;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MatchController) {
			if (arg instanceof Message) {
				// TODO
			}

			else if (arg instanceof PlayerColor) {
				PlayerColor currentPlayer = (PlayerColor) arg;
				if (currentPlayer.compareTo(playerId) == 0) {
					Action userAction = reqUserAction();
					notifyObservers(userAction);
				}

			} else if (arg instanceof ExtraAction[]) {
				// TODO
			}
			else if (arg instanceof MatchData){
				// TODO
			}
			else if(arg instanceof String)
			{
				if(((String)arg).compareTo("match started")==0) connection.matchStarted();
				else connection.sendMessage((String)arg);
			}
		}
		
	}

}
