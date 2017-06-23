package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.CouncilAction;
import it.polimi.ingsw.ps21.model.actions.DevelopmentAction;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.MarketAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.NullAction;
import it.polimi.ingsw.ps21.model.actions.VaticanAction;
import it.polimi.ingsw.ps21.model.actions.WorkAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.WorkSpace;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.match.VaticanRoundException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.ActionTimer;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;
import it.polimi.ingsw.ps21.view.ExtraActionRequest;
import it.polimi.ingsw.ps21.view.TimeoutExpiredMessage;
import it.polimi.ingsw.ps21.view.UserHandler;

/**
 * Controller class of the MVC pattern. Separates Model (Match) from View
 * (UserHandler). Observes all the UserHandlers and is observed by all of them.
 * The controller observes the match, too.
 * 
 * @author fabri
 * @author daniele
 */
public class MatchController extends Observable implements Observer {
	private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());
	private EnumMap<PlayerColor, UserHandler> handlersMap;
	private Player currentPlayer;
	private Match match;
	private boolean matchEnded = false;
	private Action currentAction;
	private ArrayList<ExtraAction> currentExtraActions;
	private int actionCounter;
	private int actionTimeout;

	private static enum ActionState {
		ACCEPTED, REFUSED, AWAITING_CHOICES, TIMEOUT_EXPIRED,
	}

	private ActionState state;
	private RoundType roundType;
	//private ActionTimer timer;
	//private Thread timerThread;
	private Timer timer;
	

	/**
	 * Constructs the controller. The controller adds himself as observer of the
	 * match and of all the UserHandlers. Each UserHandler is also registered as
	 * observer of the controller. Once the object is created, the match starts.
	 * 
	 * @param match
	 *            the model to observe
	 * @param handlers
	 *            the UserHandlers the controller will communicate with.
	 */
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
		//this.timer = new ActionTimer(5000);
		//this.timer = new ActionTimer(MatchFactory.instance().makeTimeoutRound());
		
		//this.timer.addObserver(this);
		//timerThread = new Thread(this.timer);
		this.currentExtraActions = new ArrayList<>();
		this.actionCounter = 0;
		this.timer=new Timer();
		this.actionTimeout=MatchFactory.instance().makeTimeoutRound();
		startMatch();
	}

	/**
	 * Starts a new match. This method performs 2 actions: notifies the
	 * UserHandlers with the datas of the new match and starts a new round.
	 */
	public void startMatch() {
		setChanged();
		MatchData matchinfo = new MatchData(match);
		notifyObservers(matchinfo);
		this.roundType = match.getRound();
		newRound();
	}

	/**
	 * Checks if an action requires the user to choice some parameters in order
	 * to be performed and, if so, requests that choice to the user. This is
	 * achieved by calling the action.update() method and checking its return
	 * value:
	 * <li>if action.update() returns a RefusedAction message, the action is
	 * discarded and another action is requested to the player;
	 * <li>if action.update() returns a AcceptedAction message, the action is
	 * executed and the player is notified;
	 * <li>otherwise, the action needs some parameters (user choices) in order
	 * to be performed, so the choice is requested to the player.
	 */
	private void getActionChoices() {

		Message returnMessage = currentAction.update(this.currentPlayer, this.match);
		if (returnMessage instanceof RefusedAction) {
			state = ActionState.REFUSED;
			setChanged();
			notifyObservers((RefusedAction) returnMessage);
			reqPlayerAction();
		} else if (returnMessage instanceof AcceptedAction) {
			state = ActionState.ACCEPTED;
			setChanged();
			notifyObservers((AcceptedAction) returnMessage);
			performAction();
		} else {
			setChanged();
			notifyObservers(returnMessage);
		} // request choice to the user or
			// notify that the action has been
		// accepted or refused
	}

	/**
	 * Checks if an extra action requires the user to choice some parameters in
	 * order to be performed and, if so, requests that choice to the user. This
	 * is achieved by calling the action.update() method and checking its return
	 * value:
	 * <li>if action.update() returns a RefusedAction message, the action is
	 * discarded and another action is requested to the player;
	 * <li>if action.update() returns a AcceptedAction message, the action is
	 * executed and the player is notified;
	 * <li>otherwise, the action needs some parameters (user choices) in order
	 * to be performed, so the choice is requested to the player.
	 */
	private void getExtraActionChoices() {
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
		} // accepted or refused
		if (state == ActionState.ACCEPTED)
			performAction();
		else {
			reqExtraAction();
		}
	}

	/**
	 * Performs the action that is being handled by the controller, stores the
	 * eventual extra actions generated and activates the first of them.
	 */
	private void performAction() {

		try {
			ExtraAction[] poolExtraAction = match.doAction(currentAction);
			
			currentExtraActions = new ArrayList<>();
			for (ExtraAction a : poolExtraAction) {
				if (!(a instanceof NullAction))
					currentExtraActions.add(a);
			}
			if (currentExtraActions.isEmpty()) {
				setChanged();
				notifyObservers(new CompletedActionMessage(currentPlayer.getId()));
				nextPlayer();
			} else {
				reqExtraAction();
			}
		} catch (NotExecutableException e) {
			LOGGER.log(Level.INFO, "Action not executable", e);
			notifyObservers(new RefusedAction(currentPlayer.getId(), "Action not executable"));
			nextPlayer();
		} catch (RequirementNotMetException e) {
			LOGGER.log(Level.WARNING, "Player doesn't met the requirements for this action", e);
			notifyObservers(
					new RefusedAction(currentPlayer.getId(), "Player doesn't met the requirements for this action"));
			nextPlayer();
		} catch (InsufficientPropsException e) {
			LOGGER.log(Level.INFO, "Player doesn't have enough properties to execute this action", e);
			notifyObservers(new RefusedAction(currentPlayer.getId(),
					"Player doesn't have enough properties to execute this action"));
			nextPlayer();
		} catch (VaticanRoundException e) {
			LOGGER.log(Level.SEVERE, "Match is in Vatican State, so cannot execute this type of action", e);
			notifyObservers(new RefusedAction(currentPlayer.getId(),
					"Match is in Vatican State, so cannot execute this type of action"));
			nextPlayer();
		}
	}

	/*
	 * private void performExtraAction() {
	 * 
	 * try { ExtraAction[] poolExtraAction = match.doAction(currentAction); for
	 * (ExtraAction a : poolExtraAction) { if (!(a instanceof NullAction))
	 * currentExtraActions.add(a); } if (currentExtraActions.isEmpty()) {
	 * setChanged(); notifyObservers(new
	 * CompletedActionMessage(currentPlayer.getId())); nextPlayer(); } else {
	 * reqExtraAction(); } } catch (NotExecutableException e) {
	 * LOGGER.log(Level.INFO , "Action not executable", e); notifyObservers(new
	 * RefusedAction(currentPlayer.getId(), "Action not executable"));
	 * nextPlayer(); } catch (RequirementNotMetException e) {
	 * LOGGER.log(Level.WARNING ,
	 * "Player doesn't met the requirements for this action", e);
	 * notifyObservers(new RefusedAction(currentPlayer.getId(),
	 * "Player doesn't met the requirements for this action")); nextPlayer(); }
	 * catch (InsufficientPropsException e) { LOGGER.log(Level.INFO ,
	 * "Player doesn't have enough properties to execute this action", e);
	 * notifyObservers(new RefusedAction(currentPlayer.getId(),
	 * "Player doesn't have enough properties to execute this action"));
	 * nextPlayer(); } catch (VaticanRoundException e) {
	 * LOGGER.log(Level.SEVERE,
	 * "Match is in Vatican State, so cannot execute this type of action", e);
	 * notifyObservers(new RefusedAction(currentPlayer.getId(),
	 * "Match is in Vatican State, so cannot execute this type of action"));
	 * nextPlayer(); } }
	 */

	/**
	 * Notifies the UserHandler with the available extra actions
	 * 
	 */
	private void reqExtraAction() {
		ArrayList<ExtraActionData> extraDatas = new ArrayList<>();
		for (ExtraAction a : currentExtraActions)
			{extraDatas.add(a.getData());
			}
		for(ExtraActionData ad: extraDatas)
		{
			ad.setActionId(actionCounter);
		}
		if (extraDatas.isEmpty()) {

			notifyObservers(new Message(this.currentPlayer.getId()));
			nextPlayer();
		}

		else {
			setChanged();
			notifyObservers(
					new ExtraActionRequest(this.currentPlayer.getId(), extraDatas.toArray(new ExtraActionData[0])));
		}
	}

	/**
	 * Starts a new round
	 */
	// TODO: verificare se è round vatican
	private void newRound() {
		currentPlayer = match.getCurrentPlayer();
		reqPlayerAction();
	}

	/**
	 * Requests a new action to the current player. If the current stage is
	 * Vatican Report, the requested action will be a Vatican Action.
	 */
	private void reqPlayerAction() {
		if (roundType != RoundType.VATICAN_ROUND) {
			//timerThread = new Thread(this.timer);
			//timerThread.start();
			this.timer=new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					setChanged();
					notifyObservers(new TimeoutExpiredMessage(currentPlayer.getId()));
					nextPlayer();
					
				}}, actionTimeout);
			ActionRequest req = new ActionRequest(currentPlayer.getId(), ++this.actionCounter);
			setChanged();
			notifyObservers(req);
		} else {
			this.currentAction = new VaticanAction(currentPlayer.getId());
		}
	}

	/**
	 * Clears the extra actions queue and moves the game to the next player. If
	 * there are no moves left in the current round, a new round is started. In
	 * every case, a new action is requested to the next player.
	 */
	private void nextPlayer() {
		this.currentExtraActions.clear();
		this.timer.cancel();
		this.timer.purge();
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
		if (source != match && !handlersMap.containsValue(source)) {
			throw new IllegalArgumentException();
		}
		if (source == match) {
			if (arg instanceof String) {
				String message = (String) arg;
				// if (message.compareTo("Match Started") == 0) {
				setChanged();
				notifyObservers(message);
				// gameLoop();
			} else if (arg == null) {
				setChanged();
				notifyObservers(new MatchData(match));
				setChanged();
				
			}
			else if (arg instanceof EndData)
			{
				this.timer.cancel();
				this.timer.purge();
				setChanged();
				notifyObservers((EndData)arg);
			}
		}
		if (source instanceof UserHandler) {
			if (((UserHandler) source).getPlayerId() == this.currentPlayer.getId()) {
				this.state = ActionState.AWAITING_CHOICES;
				if (arg instanceof ExtraActionData) {
					ExtraActionData action = (ExtraActionData) arg;
					if(action.getActionId()!=this.actionCounter) return;
					int i = 0;
					for (i = 0; i < currentExtraActions.size(); i++) {
						if (currentExtraActions.get(i).getData() == action)
							break;
					}
					if (i < currentExtraActions.size()) // significa che è stata
														// trovata
					{
						this.currentAction = currentExtraActions.get(i);
						this.currentExtraActions.remove(i);
						getActionChoices();
					} else {
						setChanged();
						notifyObservers(
								new RefusedAction(currentPlayer.getId(), "Your chosen extra action doesn't exist"));
						reqExtraAction();

					}
				} else if (arg instanceof ActionData) {
					ActionData action = (ActionData) arg;
					if (action.getId() == this.actionCounter) {
						parseAction(action);
						getActionChoices();
					}
				} else if (arg instanceof ExecutedChoice) {
					if (((ExecutedChoice) arg).getDest() == this.currentPlayer.getId())
						getActionChoices();
				} else if (arg instanceof String) {
					String s = (String) arg;
					if ("playerDisconnected".equals(s)) {
						nextPlayer();
					}
				}
			}
		}
		/*if (source == timer && state != ActionState.ACCEPTED) {
			setChanged();
			notifyObservers(new TimeoutExpiredMessage(currentPlayer.getId()));
			nextPlayer();
		}*/

	}

	private void parseAction(ActionData data) {
		Action parsedAction;
		Player currentPlayer = match.getCurrentPlayer();
		FamilyMember chosenMember = currentPlayer.getFamily().getMember(data.getFamilyMember());
		chosenMember.increaseValue(data.getServants());
		currentPlayer.getProperties().getProperty(PropertiesId.SERVANTS).payValue(data.getServants());
		switch (data.getType()) {
		case COUNCIL:
			parsedAction = new CouncilAction(currentPlayer.getId(), chosenMember);
			break;
		case HARVEST: {
			WorkSpace workSpace;
			if (data.getSpace() == 1)
				workSpace = match.getBoard().getSingleWorkSpace(WorkType.HARVEST);
			else
				workSpace = match.getBoard().getMultipleWorkSpace(WorkType.HARVEST);
			parsedAction = new WorkAction(currentPlayer.getId(), workSpace,
					currentPlayer.getFamily().getMember(data.getFamilyMember()));
		}

			break;
		case MARKET:
			parsedAction = new MarketAction(currentPlayer.getId(), match.getBoard().getMarketSpace(data.getSpace()),
					chosenMember);
			break;
		case NULL:
			parsedAction = new NullAction(currentPlayer.getId());
			break;
		case PLAY_LEADERCARD:
			// todo parsedAction = new PlayLeaderCard(playerId, cardToPlay);
			parsedAction = new NullAction(currentPlayer.getId());
			break;
		case PRODUCTION: {
			WorkSpace workSpace;
			if (data.getSpace() == 1)
				workSpace = match.getBoard().getSingleWorkSpace(WorkType.PRODUCTION);
			else
				workSpace = match.getBoard().getMultipleWorkSpace(WorkType.PRODUCTION);
			parsedAction = new WorkAction(currentPlayer.getId(), workSpace,
					currentPlayer.getFamily().getMember(data.getFamilyMember()));
		}
			break;
		case TAKE_CARD: {
			parsedAction = new DevelopmentAction(currentPlayer.getId(), chosenMember, data.getTower(), data.getSpace());
		}
			break;
		default:
			LOGGER.log(Level.INFO, "Invalid ActionData type, generated a default new Action");
			parsedAction = new NullAction(currentPlayer.getId());
			break;
		}
		this.currentAction = parsedAction;
	}

	private void setupLeaderCards() {

	}

}
