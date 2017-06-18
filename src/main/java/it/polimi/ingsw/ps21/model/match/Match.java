package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;


/**
 * Class that represent all information about match and communicates with Controller
 * 
 * @author gullit
 *
 */
public class Match extends Observable {
	
	protected EnumMap<PlayerColor, Player> players;
	protected ArrayList<Player> order;
	protected ArrayList<ExtraAction> extraActions;
	protected Board board;
	protected int orangeDice;
	protected int blackDice;
	protected int whiteDice;
	protected int period; 
	protected RoundType round;
	private boolean ended = false;
	private int currentPlayer;
	
	public Match(PlayerColor...colors )throws InvalidIDException, BuildingDeckException{
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
		setChanged();
	}
	
	public Match(Match previousMatch){
		this.board = previousMatch.board;
		this.blackDice = previousMatch.blackDice;
		this.orangeDice = previousMatch.orangeDice;
		this.whiteDice = previousMatch.whiteDice;
		this.players = previousMatch.players;
		this.order = previousMatch.order;
		this.period = previousMatch.period;
		this.round = previousMatch.round;
		this.ended = previousMatch.ended;
	}
	
	

	public void throwDices(){
		Random generator = new Random();
		orangeDice = (int) generator.nextInt(5) + 1;
		blackDice = (int) generator.nextInt(5) + 1;
		whiteDice = (int) generator.nextInt(5) + 1;
	}

	
	public Board getBoard() {
		return board;
	}
	
	/**
	 * This method is needed by Action.isLegal()
	 * @return First Player in order without removing it
	 */
	public int getPeriod(){
		return period;
	}
	
	public RoundType getRound(){
		return round;
	}
	
	public Player getCurrentPlayer(){
		return order.get(currentPlayer);
	}
	
	public ExtraAction[] doAction(Action action) throws NotExecutableException, RequirementNotMetException, InsufficientPropsException, VaticanRoundException{
		if (!(round == RoundType.VATICAN_ROUND)) {
			ExtraAction[] extraActionPool;
		extraActionPool = action.activate(order.get(currentPlayer),this);
		setChanged();
		notifyObservers();
		return extraActionPool;
		} else throw new VaticanRoundException();
	}
	
	/**
	 * @return TRUE if no actions are left in the queue (all players have performed all their placement action in the round)
	 */
	public RoundType setNextPlayer(){
		currentPlayer++;
		if (currentPlayer == order.size()) nextRound();
		return round;
	}

	public Match getCopy() throws CloneNotSupportedException{
		Match copied = new Match(this);
		return copied;
	}
	
	public int[] getDices() {
		int returnValues[] = new int[3];
		returnValues[0] = orangeDice;
		returnValues[1] = blackDice;
		returnValues[2] = whiteDice;
		return returnValues;
	 }
	
	public int getNumberPlayers(){
		return this.players.size();
	}


	public List<ExtraAction> getExtraActions() {
		return extraActions;
	}
	
	public void vaticanLoop(){
		// TODO Check this method
		Queue<Player> supportedPlayers = new ArrayDeque<>();
		Map<Player, Boolean> supportChoices = new HashMap<Player, Boolean>();
		for(Player p: players.values())
		{	
		if (board.getExcommunicationRequirement(period) > p.getProperties().getProperty(PropertiesId.FAITHPOINTS).getValue())	
			{/*setta scomunica*/}
		else supportedPlayers.add(p);
		}
		notifyObservers();
		// TODO Set nextPlayer
		supportedPlayers.poll();
		if (supportedPlayers.isEmpty()){
		for (Player p: players.values()){
			if (!(supportChoices.containsKey(p))) return;
			}
		nextRound();
		}
	}
	
	public boolean isEnded(){
		return ended;
	}

	private void endMatch() {
		// TODO Check implementation
		Map<Player, Integer> militaryBonus = calculateMilitaryWinner();
		Map<Player, Integer> playerPositions = calculateWinner(militaryBonus); 	
		ended = true;
		setChanged();
		notifyObservers();
	}

	private Map<Player, Integer> calculateMilitaryWinner(){
		Map<Player, Integer> result = new HashMap<>();
		//calculate 1 and second value
		int values[] = new int[players.size()];
		for (int i=0; i<values.length; i++){
			values[i]=0;
		}
		for (Player p: players.values()){
			for (int j=0; j<values.length; j++)
			 if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() >= values[j]) 
				 {
				 	values[j] = p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue();
				  }
		}
		int numWinners = 0;
		for(Player p:players.values()){
			for (int j=0; j<values.length;j++)
			if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() == values[j]) 
			{
				if(j==0){
					result.put(p, 1);
					numWinners ++;
				}
				else if ( j== 1 && numWinners<2) result.put(p, 2);
					else result.put(p, j+1);
			}
		}
		return result;
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
	
	public PlayerColor[] getOrderQueue()
	{
		Player[] players= order.toArray(new Player[0]);
		PlayerColor[] output= new PlayerColor[players.length];
		for (int i=0; i<players.length; i++)
		{
			output[i]= players[i].getId();
		}
		return output;
	}
	
	public Collection<Player> getPlayers()
	{
		return players.values();
	}
	
	public int getOrangeDice() {
		return orangeDice;
	}

	public int getWhiteDice() {
		return whiteDice;
	}

	public int getBlackDice() {
		return blackDice;
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
		currentPlayer = 1;
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

	
}
	
