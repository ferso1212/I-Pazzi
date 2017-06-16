package it.polimi.ingsw.ps21.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.match.VaticanRoundException;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.view.RoundTimer;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController extends Observable implements Observer {
	private EnumMap<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match match;
	private boolean matchEnded = false;
	private Action currentAction;

	private static enum ActionState {
		ACCEPTED, REFUSED, AWAITING_CHOICES,
	}

	private ActionState state;
	private RoundType roundType;
	private RoundTimer timer;
	private Thread timerThread;

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
		// this.timer = new RoundTimer(MatchFactory.instance().makeTimeoutRound()* 1000);
		this.timer = new RoundTimer(5000);
		this.timer.addObserver(this);
		timerThread = new Thread(this.timer);
		startMatch();
	}

	public void startMatch() {
		setChanged();
		MatchData matchinfo = new MatchData(match);
		notifyObservers(matchinfo);
		newRound();
	}

	private void getActionChoices() {

		if (state == ActionState.AWAITING_CHOICES) {
			Message returnMessage = currentAction.update(this.currentPlayer, this.match);
			if (returnMessage instanceof RefusedAction)
				state = ActionState.REFUSED;
			else if (returnMessage instanceof AcceptedAction) {
				state = ActionState.ACCEPTED;
			}
			setChanged();
			notifyObservers(returnMessage); // request choice to the user or
											// notify that the action has been
											// accepted or refused
			performAction();
		} else if (state == ActionState.ACCEPTED) {
			performAction();
		}

		/*
		 * while (!returnMessage.isVisited()) {
		 * 
		 * } ExtraAction[] extraActions =
		 * this.match.doAction(this.currentAction); Queue<ExtraAction>
		 * poolExtraActions = new ArrayDeque<>(); for (ExtraAction e :
		 * extraActions) { if (!(e instanceof NullAction))
		 * poolExtraActions.add(e); } while (!poolExtraActions.isEmpty()) {
		 * ExtraAction currentExtraAction = poolExtraActions.peek();
		 * notifyObservers(currentExtraAction); returnMessage =
		 * currentExtraAction.isLegal(this.currentPlayer, this.match);
		 * notifyObservers(returnMessage); while (returnMessage.isVisited()) {
		 * 
		 * } extraActions = this.match.doAction(currentExtraAction);
		 * poolExtraActions.poll(); for (ExtraAction e : extraActions) { if (!(e
		 * instanceof NullAction)) poolExtraActions.add(e); }
		 * 
		 * }
		 */
	}

	private void performAction() {
		
		try {
			ExtraAction[] poolExtraAction = match.doAction(currentAction);
			ArrayList<ExtraAction> extraActions = new ArrayList<>();
			for (ExtraAction a : poolExtraAction) {
				if (!(a instanceof NullAction))
					extraActions.add(a);
			}
			if (extraActions.size() == 0) {
				setChanged();
				notifyObservers(new CompletedActionMessage(currentPlayer.getId()));
				nextPlayer();
			}
		} catch (NotExecutableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RequirementNotMetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientPropsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VaticanRoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// creare un nuovo array di extra action senza NullAction da notificare
		// all'utente
		// se l'array depurato è vuoto chiama la setNextPlayer, la setNextPlayer
		// ritorna un enum che dice il tipo di round: se è cambiato, significa
		// che è un nuovo round;

		
	}

	//TODO: verificare se è round vatican
	private void newRound() {
		currentPlayer=match.getCurrentPlayer();
		reqPlayerAction();
	}

	private void reqPlayerAction() {
		timerThread = new Thread(this.timer);
		timerThread.start();
		ActionRequest req = new ActionRequest(currentPlayer.getId());
		setChanged();
		notifyObservers(req);
	}

	private void nextPlayer() {
		RoundType oldRoundType = this.roundType;
		this.roundType = match.setNextPlayer();
		currentPlayer = match.getCurrentPlayer();
		if (oldRoundType != this.roundType) {
			// new round
			newRound();
		} else {
			reqPlayerAction();
		}
	}

	/*
	 * public void roundLoop() { boolean isNewRound=false; while(!isNewRound)
	 * {isNewRound=false; currentPlayer = match.getCurrentPlayer();
	 * ActionRequest message = new ActionRequest(currentPlayer.getId());
	 * setChanged(); notifyObservers(message); //asks userHandlers to visit the
	 * message while (!message.isVisited()){wait for the message to be visited}
	 * this.currentAction = message.getChoosenAction(); actionLoop();
	 * isNewRound=match.setNextPlayer(); } }
	 */

	@Override
	public void update(Observable source, Object arg) {
		if (source != match && source != timer && !handlersMap.containsValue(source)) {
			throw new IllegalArgumentException();
		}
		if (source==match) {
			if(arg instanceof String)
			{
			String message = (String) arg;
			// if (message.compareTo("Match Started") == 0) {
			setChanged();
			notifyObservers(message);
			// gameLoop();
			}
			else if(arg==null)
			{
				setChanged();
				notifyObservers(new MatchData(match));
			}
		}
		if (source instanceof UserHandler) {
			if(((UserHandler) source).getPlayerId()==this.currentPlayer.getId()){
				this.state=ActionState.AWAITING_CHOICES;
			if (arg instanceof ExtraAction) {
				ExtraAction action = (ExtraAction) arg;
				this.currentAction=action;
				getActionChoices();
			} else if (arg instanceof Action) {
				this.currentAction=(Action)arg;
				getActionChoices();
			}
			}
		}
		if (source == timer) {
			if (state != ActionState.ACCEPTED) {
				setChanged();
				notifyObservers(new RefusedAction(currentPlayer.getId(), "Timeout expired!"));
				nextPlayer();
				reqPlayerAction();
			}
		}

	}

	private void setupLeaderCards() {

	}

}
