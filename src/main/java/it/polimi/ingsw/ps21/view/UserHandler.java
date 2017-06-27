package it.polimi.ingsw.ps21.view;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.ActionRequest;
import it.polimi.ingsw.ps21.controller.CompletedActionMessage;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.ExcommunicationMessage;
import it.polimi.ingsw.ps21.controller.ExecutedChoice;
import it.polimi.ingsw.ps21.controller.LeaderChoice;
import it.polimi.ingsw.ps21.controller.MatchController;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class UserHandler extends Observable implements Visitor, Runnable, Observer {
	private final static Logger LOGGER = Logger.getLogger(UserHandler.class.getName());
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
		try {
			choice.setChosen(connection.reqVaticanChoice());
			choice.setVisited();
			notifyObservers(new ExecutedChoice(this.playerId));
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "Current player is not connected.", e);
			choice.setChosen(false);
			setChanged();
			notifyObservers(new ExecutedChoice(this.playerId));
		}
	}

	@Override
	public void visit(CostChoice choice) {
		try {
			choice.setChosen(connection.reqCostChoice(choice.getChoices()));
			choice.setVisited();
			setChanged();
			notifyObservers(new ExecutedChoice(this.playerId));
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "Current player is not connected.", e);
			setChanged();
			notifyObservers("playerDisconnected");
		}
		}

	@Override
	public void visit(CouncilChoice choice) {
		// TODO need to pass possible privileges
		try {
			choice.setPrivilegesChosen(connection.reqPrivilegesChoice(choice.getNumberOfChoices(), choice.getPrivilegesValues()));
			choice.setVisited();
			setChanged();
			notifyObservers(new ExecutedChoice(this.playerId));
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "Current player is not connected.", e);
			setChanged();
			notifyObservers("playerDisconnected");
		}
	}

	@Override
	public void visit(EffectChoice choice) {
		try {
			choice.setEffectChosen(connection.reqEffectChoice(choice.getPossibleEffects()));
			choice.isVisited();
			setChanged();
			notifyObservers(new ExecutedChoice(this.playerId));
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "Current player is not connected.", e);
			setChanged();
			notifyObservers("playerDisconnected");
		}
		}

	@Override
	public void visit(WorkMessage message) {
		try {
			connection.sendMessage(message.getMessage());
			DevelopmentCard cardPossibilities[] = message.getChoices();
			int choices[] = new int[cardPossibilities.length];
			for (int i=0; i<choices.length; i++){
				choices[i] = connection.reqWorkChoice(cardPossibilities[i]);
			}
			message.setChosenCardsAndEffects(choices);
			message.setVisited();
			setChanged();
			notifyObservers(new ExecutedChoice(this.playerId));
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "Current player is not connected.", e);
			setChanged();
			notifyObservers("playerDisconnected");
		}
	}
	
	@Override
	public void visit(LeaderChoice message)
	{
		int chosenLeader=connection.reqLeaderCardChoice(message.getChoices());
		message.setChosenCard(chosenLeader);
		message.setVisited();
		setChanged();
		notifyObservers(new ExecutedChoice(this.playerId));
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
		try {
			if (o instanceof MatchController) {
				if (arg instanceof ExtraActionRequest) {
					ExtraActionRequest request = (ExtraActionRequest) arg;
					if (request.getDest()== this.playerId){
						ExtraActionData[] actions = request.getPossibilities();
						ExtraActionData chosen = actions[connection.reqExtraActionChoice(actions)];
						if (timeoutExpired)
							connection.sendMessage("Timeout expired");
						else {
							setChanged();
							notifyObservers(chosen);
						}
					}
				} 
				else if(arg instanceof EndData)
				{
					connection.matchEnded((EndData)arg);
				}
				else if (arg instanceof ActionRequest) {
					ActionRequest req = (ActionRequest) arg;
					if (req.getDest() != this.playerId)
						return;
					else {
						this.timeoutExpired = false;
						ActionData newAction = connection.reqAction(req.getId());
						if (timeoutExpired)
							connection.sendMessage("\nAction timeout expired");
						else {
							setChanged();
							notifyObservers(newAction);
						}
					}
				}
				 else if (arg instanceof MatchData) {
					connection.remoteUpdate((MatchData) arg);
				} else if (arg instanceof String) {
					if (((String) arg).compareTo("Match Started") == 0) {
						connection.matchStarted();

					} else
						connection.sendMessage((String) arg);

				} 
				
				else if (arg instanceof Message) {
					if (((Message) arg).getDest() == this.playerId) {
						if (arg instanceof TimeoutExpiredMessage) {
							this.timeoutExpired = true;
							connection.sendMessage(((TimeoutExpiredMessage)arg).getMessage());
						}
						else if(arg instanceof AcceptedAction)
						{
							visit((AcceptedAction)arg);
						}
						else if(arg instanceof RefusedAction)
						{
							visit((RefusedAction)arg);
						}
						else if (arg instanceof CostChoice)
						{
							CostChoice message = (CostChoice) arg;
							visit(message);
						}
						else if (arg instanceof EffectChoice){
							EffectChoice message = (EffectChoice) arg;
							visit(message);
							
						}
						else if (arg instanceof CompletedActionMessage){
							connection.sendMessage(((CompletedActionMessage)arg).getMessage());
						}
						else if (arg instanceof WorkMessage){
							WorkMessage message = (WorkMessage) arg;
							visit(message);
						}
						else if (arg instanceof CouncilChoice){
							CouncilChoice message = (CouncilChoice) arg;
							visit(message);
						}
						else if (arg instanceof ExcommunicationMessage) {
							ExcommunicationMessage message= (ExcommunicationMessage)arg;
							visit(message);
							
						}
						else if (arg instanceof LeaderChoice)
						{
							LeaderChoice message= (LeaderChoice)arg;
							visit(message);
						}
					}
				}
			}
		} catch (DisconnectedException e) {
			LOGGER.log(Level.WARNING, "Current player is not connected.", e);
			setChanged();
			notifyObservers("playerDisconnected");
		}
	}

	private void visit(ExcommunicationMessage message) {
		connection.sendMessage(message.getMessage());
		message.setVisited();
	}

	private void parseExtraAction(ExtraAction action) {
		// TODO
	}
	
	public String getName()
	{
		return this.connection.getName();
	}

	public void setConnection(Connection newConnection)
	{
		this.connection=newConnection;
	}
	
	public void notifyReconnection()
	{
		setChanged();
		connection.sendMessage("\nReconnected to match");
		notifyObservers("reconnection");
	}
}
