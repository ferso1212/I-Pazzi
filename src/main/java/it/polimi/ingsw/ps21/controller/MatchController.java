package it.polimi.ingsw.ps21.controller;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.UnsettedMatch;
import it.polimi.ingsw.ps21.model.match.VaticanSupport;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController extends Observable implements Observer {
	private EnumMap<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match match;
	private boolean matchEnded = false;
	private Action currentAction;

	public MatchController(UnsettedMatch match, UserHandler... handlers) {
		super();
		this.match = match;
		handlersMap = new EnumMap<>(PlayerColor.class);
		for (UserHandler handler : handlers) {
			this.handlersMap.put(handler.getPlayerId(), handler);
			this.addObserver(handler);
			handler.addObserver(this);

		}
		match.addObserver(this);
		this.match = match.startMatch();
	}

	public void gameLoop() {
		while (!this.matchEnded) {
			if (match instanceof VaticanSupport) {
				// TODO VaticanSupport
			} else{
				roundLoop();
				this.match=match.setNextPlayer();
			}
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
		currentPlayer = match.getCurrentPlayer();
		ActionRequest message = new ActionRequest(currentPlayer.getId());
		notifyObservers(message);
		while (!message.isVisited()){
		}this.currentAction = message.getChoosenAction();
		actionLoop();
	}

	@Override
	public void update(Observable source, Object arg) {
		if (source != match && !handlersMap.containsValue(source)) {
			throw new IllegalArgumentException();
		}
		if (source == handlersMap.get(currentPlayer.getId()) && (arg instanceof ExtraAction)) {
			ExtraAction action = (ExtraAction) arg;
			Message mess = action.isLegal(currentPlayer, match);
			notifyObservers(mess);
		}
		if (source == match && (arg instanceof String)) {
			String message = (String) arg;
			if (message.compareTo("Match Started") == 0) {
				notifyObservers(message);
				gameLoop();
			}
		}
	}

}
