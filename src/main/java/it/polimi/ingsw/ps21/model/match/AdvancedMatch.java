package it.polimi.ingsw.ps21.model.match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.VaticanAction;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.deck.LeaderDeck;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.EndData;

public class AdvancedMatch extends Match {

	private EnumMap<PlayerColor, AdvancedPlayer> players;
	private ArrayList<AdvancedPlayer> order;
	private ArrayList<ExtraAction> extraActions;
	private ArrayList<ArrayList<LeaderCard>> possibleChoices;
	private LeaderDeck leaderCards;
	private ArrayList<PersonalBonusTile> possibleAdvTiles;
	
	public AdvancedMatch(AdvancedMatch advancedMatch) {
		this.blackDice = advancedMatch.blackDice;
		this.orangeDice = advancedMatch.orangeDice;
		this.whiteDice = advancedMatch.whiteDice;
		this.period = advancedMatch.period;
		this.round = advancedMatch.round;
		this.board = advancedMatch.board;
		this.currentPlayer = advancedMatch.currentPlayer;
		this.ended = advancedMatch.ended;
		this.extraActions = advancedMatch.extraActions;
		this.players = advancedMatch.players;
		this.order = advancedMatch.order;
	}
	
	public AdvancedMatch(PlayerColor...colors) throws InvalidIDException, BuildingDeckException{
		super();
		if (colors.length>4) throw new InvalidIDException();
		MatchFactory builder = MatchFactory.instance();
		ImmProperties[] initialProperties = builder.makeInitialProperties();
		players = new EnumMap<>(PlayerColor.class);
		order = new ArrayList<>();
		board = new Board(colors.length, true);
		board.getDeck().shuffle();
		this.leaderCards=builder.makeLeaderDeck();
		this.leaderCards.shuffle();
		extraActions = new ArrayList<>();
		PersonalBonusTile[] tempTiles = builder.makeAdvancedTiles();
		this.possibleAdvTiles = new ArrayList<>();
		for (PersonalBonusTile t: tempTiles){
			possibleAdvTiles.add(t);
		}
		for (int i=0; i<colors.length; i++){
			players.put(colors[i], new AdvancedPlayer(colors[i], new PlayerProperties(0)));
			players.get(colors[i]).getProperties().increaseProperties(initialProperties[i]);
			order.add(players.get(colors[i]));
		}
		currentPlayer=0;
		period = 0;
		round = RoundType.LEADER_ROUND;
		setupLeaderChoices();
		notifyObservers("Leader Cards shuffle");
	}
	
	private void setupLeaderChoices() {
		this.possibleChoices = new ArrayList<>();
			for (int i=0; i<order.size(); i++){
				ArrayList<LeaderCard> cards = new ArrayList<>();
					for (int j=0; j<4; j++) cards.add(leaderCards.getCard());
				possibleChoices.add(i, cards);
		}
	}

	@Override
	public Player getCurrentPlayer(){
		return order.get(currentPlayer);
	}
	
	@Override
	public Collection<Player> getPlayers()
	{
		ArrayList<Player> result = new ArrayList<>();
		for (AdvancedPlayer p: players.values()){
			result.add(p);
		}
		return result;
	}
	
	@Override
	public void throwDices(){
		super.throwDices();
		for (AdvancedPlayer p: players.values()){
			p.getFamily().getMember(MembersColor.ORANGE).setValue(orangeDice);
			p.getFamily().getMember(MembersColor.BLACK).setValue(blackDice);
			p.getFamily().getMember(MembersColor.WHITE).setValue(whiteDice);
		}
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
	public void nextRound() {
	if (round == RoundType.LEADER_ROUND){
			if(!switchLeaderChoice()){
				round = RoundType.TILE_CHOICE;
				notifyObservers("Player Tile Choices");
				return;
			}
			else return;
		}
	else if (round == RoundType.TILE_CHOICE){
		round = RoundType.INITIAL_ROUND;
		period=1;
	}
	else if (round == RoundType.INITIAL_ROUND) round = RoundType.FINAL_ROUND;
	else if (round == RoundType.FINAL_ROUND) round = RoundType.VATICAN_ROUND;
	else if (round == RoundType.VATICAN_ROUND){ 
		if (period <3) {
			round = RoundType.INITIAL_ROUND;
			period++;
		}
		else {
			endMatch();
			return;
		}
	}
	currentPlayer = 1;
	Queue<FamilyMember> temp = board.getCouncilPalace().getOccupants();
	ArrayList<AdvancedPlayer> oldOrder = new ArrayList<>();
	for (int i=0; i< players.values().size(); i++){
		oldOrder.add(order.get(i));
	}
	ArrayList<AdvancedPlayer> newOrder = new ArrayList<>();
	for (FamilyMember f: temp){
		AdvancedPlayer player = players.get(f.getOwnerId());
		if (order.contains(player));
		else {	
			newOrder.add(player);
			oldOrder.remove(player);
		}
	}
	for (AdvancedPlayer p: oldOrder){
		newOrder.add(p);
	}
	order = new ArrayList<>();
	for ( int j = 0 ; j < newOrder.size(); j++) order.add(newOrder.get(j));
	if (round != RoundType.VATICAN_ROUND){
		for (int i=0; i<3; i++)
			for ( int j =0 ; j< newOrder.size(); j++) order.add(newOrder.get(j));
		board.newSetBoard(period);
		for (AdvancedPlayer p: players.values()){
			p.getFamily().roundReset();
		}
		throwDices();
	}
	if (round!= RoundType.LEADER_ROUND && round != RoundType.TILE_CHOICE)
	{
		setChanged();
		notifyObservers();
	}
	}
	
	private boolean switchLeaderChoice() {
		ArrayList<LeaderCard>leftTemp =  possibleChoices.get(0);
		ArrayList<LeaderCard> rightTemp = possibleChoices.get(1);
		int i;
		for(i=0; i<order.size()-1; i++){
			rightTemp = possibleChoices.get(i+1);
			possibleChoices.set(i+1, leftTemp);
			leftTemp = rightTemp;
		}
		possibleChoices.set(0, leftTemp);
		currentPlayer=0;
		if (getLeaderPossibilities().isEmpty()) return false;
		return true;
	}

	private Map<PlayerColor, Integer> calculateWinner(Map<Player, Integer> militaryBonus) {
		Map<PlayerColor, Integer> result = new EnumMap<>(PlayerColor.class);
		for(AdvancedPlayer p:players.values()){
			result.put(p.getId(),p.getFinalVictoryPoints(board.getTrackBonuses(), board.getCardBonus(), militaryBonus.get(p)));
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
		AdvancedPlayer winners[] = new AdvancedPlayer[players.values().size()];
		int firstnumber = 0;
		int max=0;
		int secondValue=0;
		int secondNumber=0;
		for (AdvancedPlayer p: players.values()){
			if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() >= max){
				if(p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() == max)
				{
					firstnumber++;
				}
				else {
					secondNumber = firstnumber;
					firstnumber=1;
				}
				
				max = p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue();
				if (winners[0] == null)
					winners[0]=p;
				else {
					AdvancedPlayer temp1 = winners[0];
					winners[0] = p;
					int i=1;
					for (i=1; i<winners.length -1 ; i++){
						if (winners[i] == null) {
							winners[i]=temp1;
							temp1 = null;
						}
						else {AdvancedPlayer temp2 = winners[i];
						winners[i] = temp1;
						temp1 = temp2;}
					}
					if (temp1!= null) winners[i] = temp1;
				}
			}
			else 
				if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() >= secondValue){
					if (p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue() == secondValue)
					secondNumber++;
					else secondNumber=1;
					secondValue = p.getProperties().getProperty(PropertiesId.MILITARYPOINTS).getValue();
					if (winners[firstnumber] == null)
						winners[firstnumber]=p;
					else {
						AdvancedPlayer temp1 = winners[firstnumber];
						winners[firstnumber] = p;
						int i=firstnumber+1;
						for (i=firstnumber+1; i<winners.length -1 ; i++){
							if (winners[i] == null) {
								winners[i]=temp1;
								temp1 = null;
							}
							else {AdvancedPlayer temp2 = winners[i];
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
		for (int i=0; i< winners.length; i++){
			if (i <= firstnumber -1) result.put(winners[i], 1);
			else  if (i < firstnumber + secondNumber){
				if (firstnumber == 1){
					result.put(winners[i], 2);
				}
				else result.put(winners[i],3);
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
	public AdvancedMatch getCopy() throws CloneNotSupportedException {
		AdvancedMatch copied = new AdvancedMatch(this);
		return copied;
	}

	@Override
	public RoundType setNextPlayer() {
			currentPlayer++;
			if (currentPlayer == order.size()) nextRound();
			return round;
	}

	@Override
	public ExtraAction[] doAction(Action action) throws NotExecutableException, RequirementNotMetException,
			InsufficientPropsException, VaticanRoundException {
		if (!(round == RoundType.VATICAN_ROUND)){
			if ((!(action instanceof VaticanAction))){
				ExtraAction[] extraActionPool;
				extraActionPool = action.activate(order.get(currentPlayer),this);
				if (round != RoundType.LEADER_ROUND && round != RoundType.TILE_CHOICE)	{
						setChanged();
						notifyObservers();
					}
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
	
	public ArrayList<LeaderCard> getLeaderPossibilities(){
		return this.possibleChoices.get(currentPlayer);
	}
	
	public ArrayList<PersonalBonusTile> getPossibleTiles(){
		return this.possibleAdvTiles;
	}
	
}