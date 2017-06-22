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
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SimpleMatch extends Match {
	
	private EnumMap<PlayerColor, Player> players;
	private ArrayList<Player> order;
	private ArrayList<ExtraAction> extraActions;

	
	public SimpleMatch(PlayerColor...colors) throws InvalidIDException, BuildingDeckException {
		super();
		if (colors.length>4) throw new InvalidIDException();
		MatchFactory builder = MatchFactory.instance();
		ImmProperties[] initialProperties = builder.makeInitialProperties();
		players = new EnumMap<>(PlayerColor.class);
		order = new ArrayList<>();
		ArrayList<Player> tempPlayer = new ArrayList<>();
		board = new Board(colors.length, false);
		board.getDeck().shuffle();
		extraActions = new ArrayList<>();
		for (int i=0; i<colors.length; i++){
			players.put(colors[i], new Player(colors[i], new PlayerProperties(0)));
			players.get(colors[i]).getProperties().increaseProperties(initialProperties[i]);
			tempPlayer.add(players.get(colors[i]));
		}
		for (int j=0; j<4; j++)
			for (int i=0; i<tempPlayer.size(); i++)
				order.add(tempPlayer.get(i));
		currentPlayer=0;
		period = 1;
		round = RoundType.INITIAL_ROUND;
		throwDices();
		board.newSetBoard(period);
		setChanged();		// TODO Auto-generated constructor stub
	}
	
	
	public SimpleMatch(SimpleMatch simpleMatch){
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


	public Player getCurrentPlayer(){
		return order.get(currentPlayer);
	}
	
	@Override
	public void throwDices(){
		super.throwDices();
		for (Player p: players.values()){
			p.getFamily().getMember(MembersColor.ORANGE).setValue(orangeDice);
			p.getFamily().getMember(MembersColor.ORANGE).setValue(blackDice);
			p.getFamily().getMember(MembersColor.ORANGE).setValue(whiteDice);
		}
	}
	
	
	
	
	public void nextRound(){
		if (round == RoundType.INITIAL_ROUND) round = RoundType.FINAL_ROUND;
		else if (round == RoundType.FINAL_ROUND) round = RoundType.VATICAN_ROUND;
		else if (round == RoundType.VATICAN_ROUND){ 
			if (period <3) round = RoundType.INITIAL_ROUND;
			else {
				endMatch();
				return;
			}
 		}
		currentPlayer = 0;
		Queue<FamilyMember> temp = board.getCouncilPalace().getOccupants();
		ArrayList<Player> oldOrder = new ArrayList<>();
		for (int i=0; i< players.values().size(); i++){
			oldOrder.add(order.get(i));
		}
		ArrayList<Player> newOrder = new ArrayList<>();
		for (FamilyMember f: temp){
			Player player = players.get(f.getOwnerId());
			if (order.contains(player));
			else {	
				newOrder.add(player);
				oldOrder.remove(player);
			}
		}
		for (Player p: oldOrder){
			newOrder.add(p);
		}
		order = new ArrayList<>();
		for ( int j = 0 ; j < newOrder.size(); j++) order.add(newOrder.get(j));
		if (round != RoundType.VATICAN_ROUND){
			for (int i=0; i<3; i++)
				for ( int j =0 ; j< newOrder.size(); j++) order.add(newOrder.get(j));
			board.newSetBoard(period);
			for (Player p: players.values()){
				p.getFamily().roundReset();
			}
		}
		setChanged();
		notifyObservers();
		
	}

	@Override
	public PlayerColor[] getOrderQueue() {
			Player[] players= order.toArray(new Player[0]);
			PlayerColor[] output= new PlayerColor[players.length];
			for (int i=0; i<players.length; i++)
			{
				output[i]= players[i].getId();
			}
			return output;
	}

	@Override
	public Collection<Player> getPlayers() {
		return players.values();
	}
	
	private Map<Player, Integer> calculateWinner(Map<Player, Integer> militaryBonus) {
		Map<Player, Integer> result = new HashMap<>();
		// Calculate value orders
		int values[] = new int[players.size()];
		for (int i=0; i<values.length; i++){
			values[i]=0;
		}
		for (Player p: players.values()){
			for (int j=0; j<values.length; j++)
			 if (p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p)) >= values[j]) 
				 {
				 	values[j] = p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p));
				  }
		}
		for(Player p:players.values()){
			for (int j=0; j<values.length;j++)
			if (p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p)) == values[j]) 
				result.put(p, j+1);
		}
		return null;
	}
	
	private void endMatch() {
		// TODO Check implementation
		Map<Player, Integer> militaryBonus = calculateMilitaryWinner();
		Map<Player, Integer> playerPositions = calculateWinner(militaryBonus); 	
		ended = true;
		setChanged();
		notifyObservers();
	}

	private Map<Player, Integer> calculateMilitaryWinner() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ExtraAction> getExtraActions() {
			return extraActions;
	}


	@Override
	public int getNumberPlayers(){
		return this.players.size();
	}


	@Override
	public RoundType setNextPlayer() {
		currentPlayer++;
		if (currentPlayer == order.size()) nextRound();
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
		if (action instanceof PlayLeaderCard) throw new NotExecutableException();
		if (!(round == RoundType.VATICAN_ROUND)){
			if ((!(action instanceof VaticanAction))){
				ExtraAction[] extraActionPool;
				extraActionPool = action.activate(order.get(currentPlayer),this);
				setChanged();
				notifyObservers();
				return extraActionPool;
		}
			else throw new VaticanRoundException();
		}
		else
			if (action instanceof VaticanAction){
				ExtraAction []extraActionPool;
				extraActionPool = action.activate(order.get(currentPlayer), this);
				setChanged();
				notifyObservers();
				return extraActionPool;
			}
			else throw new VaticanRoundException();
	}
}

