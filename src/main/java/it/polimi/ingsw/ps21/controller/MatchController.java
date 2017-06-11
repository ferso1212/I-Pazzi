package it.polimi.ingsw.ps21.controller;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController extends Observable implements Observer {
	private EnumMap<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match match;
	private boolean matchEnded = false;
	private Action currentAction;

	public MatchController(Match match, UserHandler... handlers) {
		super();
		this.match = match;
		handlersMap = new EnumMap<>(PlayerColor.class);
		match.addObserver(this);
		for (UserHandler handler : handlers) {
			this.handlersMap.put(handler.getPlayerId(), handler);
			this.addObserver(handler);
			handler.addObserver(this);
		}
	}

	public void gameLoop() {
		
		while (!this.matchEnded) {
			
				roundLoop();
				
			}
		}
	

	private void actionLoop() {
		Message returnMessage = currentAction.isLegal(this.currentPlayer, this.match);
		notifyObservers(returnMessage);
		while (!returnMessage.isVisited()) {

		}
		ExtraAction[] extraActions = this.match.doAction(this.currentAction);
		Queue<ExtraAction> poolExtraActions = new ArrayDeque<>();
		for (ExtraAction e : extraActions) {
			if (!(e instanceof NullAction))
				poolExtraActions.add(e);
		}
		while (!poolExtraActions.isEmpty()) {
			ExtraAction currentExtraAction = poolExtraActions.peek();
			notifyObservers(currentExtraAction);
			returnMessage = currentExtraAction.isLegal(this.currentPlayer, this.match);
			notifyObservers(returnMessage);
			while (returnMessage.isVisited()) {

			}
			extraActions = this.match.doAction(currentExtraAction);
			poolExtraActions.poll();
			for (ExtraAction e : extraActions) {
				if (!(e instanceof NullAction))
					poolExtraActions.add(e);
			}

		}
	}

	public void roundLoop() {
		boolean isNewRound=false;
		while(!isNewRound)
		{isNewRound=false;
		currentPlayer = match.getCurrentPlayer();
		ActionRequest message = new ActionRequest(currentPlayer.getId());
		setChanged();
		notifyObservers(message); //asks userHandlers to visit the message
		while (!message.isVisited()){/*wait for the message to be visited*/}
		this.currentAction = message.getChoosenAction();
		actionLoop();
		isNewRound=match.setNextPlayer();
		}
	}

	@Override
	public void update(Observable source, Object arg) {
		if (source != match && !handlersMap.containsValue(source)) {
			throw new IllegalArgumentException();
		}
		if (source instanceof Match && (arg instanceof String)) {
			String message = (String) arg;
			if (message.compareTo("Match Started") == 0) {
				setChanged();
				notifyObservers(message);
				gameLoop();
			}
		}
		if (source == handlersMap.get(currentPlayer.getId()) && (arg instanceof ExtraAction)) {
			ExtraAction action = (ExtraAction) arg;
			Message mess = action.isLegal(currentPlayer, match);
			notifyObservers(mess);
		}
		
	}
	
	private void setupLeaderCards()
	{
		
	}

}
