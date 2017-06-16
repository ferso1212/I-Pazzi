package it.polimi.ingsw.ps21.view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.ActionRequest;
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
import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.WorkAction;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class UserHandler extends Observable implements Visitor, Runnable, Observer {
	private PlayerColor playerId;
	private Connection connection;
	private String name;
	private boolean timeoutExpired;

	public UserHandler(PlayerColor playerId, Connection connection) {
		super();
		this.playerId = playerId;
		this.connection = connection;
		this.name = this.connection.getName();
		// this.connection.sendMessage(this.name + "'s UserHandler created.");
		this.connection.setID(this.playerId);
		this.timeoutExpired = false;
	}

	@Override
	public void visit(VaticanChoice choice) {
		choice.setChosen(connection.reqVaticanChoice());
		choice.setVisited();
	}

	@Override
	public void visit(CostChoice choice) {
		choice.setChosen(connection.reqCostChoice(choice.getChoices()));
		choice.setVisited();
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
			if (arg instanceof ExtraAction[]) {
				// TODO
				ExtraAction[] actions = (ExtraAction[]) arg;
				ArrayList<ExtraAction> actionsForMe = new ArrayList<>();
				for (ExtraAction action : actions) {
					if (action.getPlayerId() == this.playerId) {
						actionsForMe.add(action);
					}
				}
			} else if (arg instanceof ActionRequest) {
				ActionRequest req = (ActionRequest) arg;
				if (req.getDest() != this.playerId)
					return;
				else {
					this.timeoutExpired = false;
					ActionData newAction = connection.reqAction();
					if (timeoutExpired)
						connection.sendMessage("Timeout expired");
					else {
						notifyObservers(newAction);
					}
				}
			} else if (arg instanceof MatchData) {
				connection.remoteUpdate((MatchData) arg);
			} else if (arg instanceof String) {
				if (((String) arg).compareTo("Match Started") == 0) {
					connection.matchStarted();
					setChanged();

				} else
					connection.sendMessage((String) arg);

			} else if (arg instanceof Message) {
				if (((Message) arg).getDest() == this.playerId) {
					if (arg instanceof TimeoutExpiredMessage) {
						this.timeoutExpired = true;
						connection.sendMessage(((TimeoutExpiredMessage)arg).getMessage());
					}
					else if(arg instanceof AcceptedAction)
					{
						connection.sendMessage(((AcceptedAction)arg).getMessage());
					}
				}
			}
		}

	}

	private void parseExtraAction(ExtraAction action) {
		// TODO
	}

	// TODO
	private void parseAction(ActionData action) {
		Action userAction = new NullAction(playerId);
		switch (action.getType()){
		case COUNCIL:
			break;
		case HARVEST:
			break;
		case MARKET:
			break;
		case NULL:
			break;
		case PLAY_LEADERCARD:
			break;
		case PRODUCTION:
			break;
		case TAKE_CARD:
			break;
		default:
			break;
		}
		notifyObservers(userAction);
	}

}
