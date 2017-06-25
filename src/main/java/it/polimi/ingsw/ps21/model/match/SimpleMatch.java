package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.PlayLeaderCard;
import it.polimi.ingsw.ps21.model.actions.VaticanAction;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.EndData;

public class SimpleMatch extends Match {

	private EnumMap<PlayerColor, Player> players;
	private ArrayList<Player> order;
	private ArrayList<ExtraAction> extraActions;
	private static final int NUM_OF_PERIODS=1;

	public SimpleMatch(PlayerColor... colors) throws InvalidIDException, BuildingDeckException {
		super();
		if (colors.length > 4)
			throw new InvalidIDException();
		MatchFactory builder = MatchFactory.instance();
		ImmProperties[] initialProperties = builder.makeInitialProperties();
		players = new EnumMap<>(PlayerColor.class);
		order = new ArrayList<>();
		ArrayList<Player> tempPlayer = new ArrayList<>();
		board = new Board(colors.length, false);
		board.getDeck().shuffle();
		extraActions = new ArrayList<>();
		for (int i = 0; i < colors.length; i++) {
			players.put(colors[i], new Player(colors[i], new PlayerProperties(0)));
			players.get(colors[i]).getProperties().increaseProperties(initialProperties[i]);
			tempPlayer.add(players.get(colors[i]));
		}
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < tempPlayer.size(); i++)
				order.add(tempPlayer.get(i));
		currentPlayer = 0;
		period = 1;
		round = RoundType.INITIAL_ROUND;
		throwDices();
		board.newSetBoard(period);
		setChanged(); // TODO Auto-generated constructor stub
	}

	public SimpleMatch(SimpleMatch simpleMatch) {
		this.blackDice = simpleMatch.blackDice;
		this.orangeDice = simpleMatch.orangeDice;
		this.whiteDice = simpleMatch.whiteDice;
		this.period = simpleMatch.period;
		this.round = simpleMatch.round;
		this.board = simpleMatch.board;
		this.currentPlayer = simpleMatch.currentPlayer;
		this.ended = simpleMatch.ended;
		this.extraActions = simpleMatch.extraActions;
		this.players = simpleMatch.players;
		this.order = simpleMatch.order;

	}

	public Player getCurrentPlayer() {
		return order.get(currentPlayer);
	}

	@Override
	public void throwDices() {
		super.throwDices();
		for (Player p : players.values()) {
			p.getFamily().getMember(MembersColor.ORANGE).setValue(orangeDice);
			p.getFamily().getMember(MembersColor.WHITE).setValue(blackDice);
			p.getFamily().getMember(MembersColor.BLACK).setValue(whiteDice);
		}
	}

	public void nextRound() {
		if (round == RoundType.INITIAL_ROUND) {
			round = RoundType.FINAL_ROUND;
		} else if (round == RoundType.FINAL_ROUND)
			round = RoundType.VATICAN_ROUND;
		else if (round == RoundType.VATICAN_ROUND) {
			if (period < NUM_OF_PERIODS){
				round = RoundType.INITIAL_ROUND;
			}
			else {
				endMatch();
				return;
			}
		}
		currentPlayer = 0;
		Queue<FamilyMember> temp = board.getCouncilPalace().getOccupants();
		ArrayList<Player> oldOrder = new ArrayList<>();
		for (int i = 0; i < players.values().size(); i++) {
			oldOrder.add(order.get(i));
		}
		ArrayList<Player> newOrder = new ArrayList<>();
		for (FamilyMember f : temp) {
			Player player = players.get(f.getOwnerId());
			if (order.contains(player))
				;
			else {
				newOrder.add(player);
				oldOrder.remove(player);
			}
		}
		for (Player p : oldOrder) {
			newOrder.add(p);
		}
		order = new ArrayList<>();
		for (int j = 0; j < newOrder.size(); j++)
			order.add(newOrder.get(j));
		if (round != RoundType.VATICAN_ROUND) {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < newOrder.size(); j++)
					order.add(newOrder.get(j));
			board.newSetBoard(++period);
			for (Player p : players.values()) {
				p.getFamily().roundReset();
			}
			throwDices();
		}
		setChanged();
		notifyObservers();

	}

	@Override
	public PlayerColor[] getOrderQueue() {
		Player[] players = order.toArray(new Player[0]);
		PlayerColor[] output = new PlayerColor[players.length];
		for (int i = 0; i < players.length; i++) {
			output[i] = players[i].getId();
		}
		return output;
	}

	@Override
	public Collection<Player> getPlayers() {
		return players.values();
	}

	private Map<PlayerColor, Integer> calculateWinner(Map<Player, Integer> militaryBonus) {
		Map<PlayerColor, Integer> result = new HashMap<>();
		for (Player p : players.values()) {
			result.put(p.getId(), p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(),
						militaryBonus.get(p)));
		}
		return result;
	}

	private void endMatch() {
		// TODO Check implementation
		Map<Player, Integer> militaryBonus = calculateMilitaryWinner();
		Map<PlayerColor, Integer> playersFinalPoints = calculateWinner(militaryBonus);
		ended = true;
		this.statistics = new EndData(playersFinalPoints);
		setChanged();
		notifyObservers(new EndData(playersFinalPoints));
	}

	private Map<Player, Integer> calculateMilitaryWinner() {
		Map<Player, Integer> result = new HashMap<>();
		Player winners[] = new Player[players.values().size()];
		int firstnumber = 0; // numero di giocatori con il massimo dei punti militari
		int max=0;
		int secondValue=0;
		int secondNumber = 0; // numero di giocatori in seconda posizione
		for (Player p: players.values()){
			if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() >= max){
				if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() == max){
					firstnumber++;
				}
				else {
					secondNumber = firstnumber;
					firstnumber = 1;
				}
				max = p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue();
				
				if (winners[0] == null)
					winners[0]=p;
				else {
					Player temp1 = winners[0];
					winners[0] = p;
					int i=1;
					for (i=1; i<winners.length -1 ; i++){
						if (winners[i] == null) {
							winners[i]=temp1;
							temp1 = null;
						}
						else {Player temp2 = winners[i];
						winners[i] = temp1;
						temp1 = temp2;}
					}
					if (temp1!= null) winners[i] = temp1;
				}
				
			}
				else {
					if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() >= secondValue){
						if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() == secondValue){
							secondNumber++;
						}
						else secondNumber = 1;
						secondValue = p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue();
						if (winners[firstnumber] == null)
							winners[firstnumber]=p;
						else {
							Player temp1 = winners[firstnumber];
							winners[firstnumber] = p;
							int i=firstnumber+1;
							for (i=firstnumber+1; i<winners.length -1 ; i++){
								if (winners[i] == null) {
									winners[i]=temp1;
									temp1 = null;
								}
								else {Player temp2 = winners[i];
								winners[i] = temp1;
								temp1 = temp2;}
							}
							if (temp1!= null) winners[i] = temp1;
						}
				}
					else {
						int i=0;
						while (winners[i]!= null && i<winners.length){
							i++;
						}
						if (i < winners.length) winners[i] = p;
					}
				}
			}
		for (int i=0; i< winners.length; i++){
			if (i <= firstnumber -1) result.put(winners[i], 1);
			else  if (i < firstnumber + secondNumber){
				if (firstnumber == 1){
					result.put(winners[i], 2);
				}
				else result.put(winners[i], 3);
			}
			else result.put(winners[i], 3);
		}
		
		return result;
	}

	@Override
	public List<ExtraAction> getExtraActions() {
		return extraActions;
	}

	@Override
	public int getNumberPlayers() {
		return this.players.size();
	}

	@Override
	public RoundType setNextPlayer() {
		currentPlayer++;
		if (currentPlayer == order.size())
			nextRound();
		return round;
	}

	@Override
	public SimpleMatch getCopy() throws CloneNotSupportedException {
		SimpleMatch copied = new SimpleMatch(this);
		return copied;
	}

	@Override
	public ExtraAction[] doAction(Action action) throws NotExecutableException, RequirementNotMetException,
			InsufficientPropsException, VaticanRoundException {
		if (action instanceof PlayLeaderCard)
			throw new NotExecutableException();
		if (!(round == RoundType.VATICAN_ROUND)) {
			if ((!(action instanceof VaticanAction))) {
				ExtraAction[] extraActionPool;
				extraActionPool = action.activate(order.get(currentPlayer), this);
				setChanged();
				notifyObservers();
				return extraActionPool;
			} else
				throw new VaticanRoundException();
		} else if (action instanceof VaticanAction) {
			ExtraAction[] extraActionPool;
			extraActionPool = action.activate(order.get(currentPlayer), this);
			setChanged();
			notifyObservers();
			return extraActionPool;
		} else
			throw new VaticanRoundException();
	}
}
