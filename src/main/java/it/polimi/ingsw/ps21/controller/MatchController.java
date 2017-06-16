package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.DevelopmentAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.MarketAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.PlayLeaderCard;
import it.polimi.ingsw.ps21.model.actions.WorkAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.Space;
import it.polimi.ingsw.ps21.model.board.WorkSpace;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.match.VaticanRoundException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.RoundTimer;
import it.polimi.ingsw.ps21.view.TimeoutExpiredMessage;
import it.polimi.ingsw.ps21.view.UserHandler;

public class MatchController extends Observable implements Observer {
	private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());
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
		// this.timer = new RoundTimer(MatchFactory.instance().makeTimeoutRound());
		this.timer = new RoundTimer(600000);
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
			if (extraActions.isEmpty()) {
				setChanged();
				notifyObservers(new CompletedActionMessage(currentPlayer.getId()));
				nextPlayer();
			}
		} catch (NotExecutableException e) {
			LOGGER.log(Level.INFO , "Action not executable", e);
		} catch (RequirementNotMetException e) {
			LOGGER.log(Level.WARNING , "Player doesn't met the requirements for this action", e);
		} catch (InsufficientPropsException e) {
			LOGGER.log(Level.INFO , "Player doesn't have enough properties to execute this action", e);
		} catch (VaticanRoundException e) {
			LOGGER.log(Level.SEVERE, "Match is in Vatican State, so cannot execute this type of action", e);
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
			} else if (arg instanceof ActionData) {
				parseAction((ActionData)arg);
				getActionChoices();
				
			}
			}
		}
		if (source == timer && state != ActionState.ACCEPTED) {
				setChanged();
				notifyObservers(new TimeoutExpiredMessage(currentPlayer.getId()));
				nextPlayer();
				reqPlayerAction();
			}

	}

	private void parseAction(ActionData data) {
		Action parsedAction;
		Player currentPlayer = match.getCurrentPlayer();
		FamilyMember chosenMember = currentPlayer.getFamily().getMember(data.getFamilyMember());
		chosenMember.increaseValue(data.getServants());
		currentPlayer.getProperties().getProperty(PropertiesId.SERVANTS).payValue(data.getServants());
		switch (data.getType()) {
		case COUNCIL:
		{
			parsedAction = new CouncilAction(currentPlayer.getId(), chosenMember);
		}
			break;
		case HARVEST:
		{
			WorkSpace workSpace;
			if (data.getSpace()== 1) workSpace = match.getBoard().getSingleWorkSpace(WorkType.HARVEST);
			else workSpace = match.getBoard().getMultipleWorkSpace(WorkType.HARVEST);
			parsedAction = new WorkAction(currentPlayer.getId(), workSpace, currentPlayer.getFamily().getMember(data.getFamilyMember()));
		}
			
			break;
		case MARKET:
		{
			// TODO remove councilChoice parsedAction = new MarketAction(currentPlayer.getId(), match.getBoard().getMarketSpace(data.getSpace()), chosenMember, councilChoice)
			parsedAction = new NullAction(currentPlayer.getId());
		}
			break;
		case NULL:
			parsedAction = new NullAction(currentPlayer.getId());
			break;
		case PLAY_LEADERCARD:
		{
			// todo parsedAction = new PlayLeaderCard(playerId, cardToPlay);
			parsedAction = new NullAction(currentPlayer.getId());
		}
			break;
		case PRODUCTION:
		{
			WorkSpace workSpace;
			if (data.getSpace()== 1) workSpace = match.getBoard().getSingleWorkSpace(WorkType.HARVEST);
			else workSpace = match.getBoard().getMultipleWorkSpace(WorkType.HARVEST);
			parsedAction = new WorkAction(currentPlayer.getId(), workSpace, currentPlayer.getFamily().getMember(data.getFamilyMember()));
		}
			break;
		case TAKE_CARD:
		{
			parsedAction = new DevelopmentAction(currentPlayer.getId(), chosenMember, data.getTower(), data.getSpace());
		}
			break;
		default:
			parsedAction = new NullAction(currentPlayer.getId());
			break;
		}
		this.currentAction = parsedAction;
	}

	private void setupLeaderCards() {

	}

}
