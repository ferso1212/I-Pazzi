package it.polimi.ingsw.ps21.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.model.properties.PropertiesSet;

public class CLInterface implements UserInterface {
	
	public MatchData match;
	public Scanner userInput;
	private PlayerColor playerID = null;
	private boolean matchEnded = false;
	private EnumMap<DevelopmentCardType, ArrayList<DevelopmentCard>> playerCards = new EnumMap<>(DevelopmentCardType.class);
	private EnumMap<PropertiesId, Integer> playerProperties = new EnumMap<>(PropertiesId.class);
	private int tileProdiceReq;
	private ImmProperties tileProdBonus;
	private int tileHarvDiceReq;
	private ImmProperties tileHarvBonus;
	
	public CLInterface() {
		userInput = new Scanner(System.in);
	}
	
	@Override
	public void updateView(MatchData match, BoardData board, PlayerData players[]) {
		System.out.print("Match State:\n "
					+ "Period: " + match.getEra() + "\tRound: " + match.getRound());
		System.out.println("\nBoard:\nDices: Black = " + board.getBlackDice() + " White = " + board.getWhiteDice() + " Orange = " + board.getOrangeDice());
		DevelopmentCard[][] cards = board.getCards();
		for (int i= 0; i<4 ; i++){
			System.out.println("\nTower " + i+1 + "");
			for (int j=0; j<4; j++){
				System.out.println("\nFloor " + j + ":");
				System.out.println(" Card: " + cards[i][j].toString() + "; ");
				if (j<3) System.out.println(", ");
				System.out.println("Family Member: " + board.getTowerSpaces()[i][j]);
			}
			System.out.println(";");
		}
		System.out.println("----------\tPlayers' Infos\t----------");
		for (PlayerData p: players){
			System.out.println();
			if (p.getId() == this.playerID){
				this.playerCards = p.getCards();
				this.playerProperties = p.getProperties();
				this.tileHarvBonus = p.getTileHarvBonus();
				this.tileHarvDiceReq = p.getTileHarvDiceReq();
				this.tileProdBonus = p.getTileProdBonus();
				this.tileProdiceReq = p.getTileProdDiceReq();
				showPlayerInfos();
			}
			else{
				showOtherPlayer(p);
			}
			System.out.println();
		}
	}

	private void showPlayerInfos() {
		System.out.println("---------\t YOUR INFO \t-------");
		System.out.println("COLOR: " + this.playerID);
		System.out.println("PROPERTIES:");
		for (PropertiesId id: PropertiesId.values()){
			if (this.playerProperties.containsKey(id))
				System.out.print(id + " = " + playerProperties.get(id) + ";\t");
		}
		System.out.println("PICKED CARDS:"); 
		for (DevelopmentCardType t: DevelopmentCardType.values()){
			System.out.println("-" + t + ":");
			ArrayList<DevelopmentCard> typeCards = playerCards.get(t);
			for (DevelopmentCard c: typeCards){
				System.out.print("c" + ";\t");
			}
		}
		System.out.println("----\t TILE BONUSES \t-----");
		System.out.println("HARVEST: Dice Requirement = " + this.tileHarvDiceReq + "; " + "Bonus = "+ this.tileHarvBonus + ";");
		System.out.println("PRODUCTION: Dice Requirement = " + this.tileProdiceReq + "; " + "Bonus = "+ this.tileProdBonus + ";");
		
	}
	
	public void showOtherPlayer(PlayerData player){
		System.out.println("COLOR: " + player.getId());
		System.out.println("COLOR: " + this.playerID);
		System.out.println("PROPERTIES:");
		for (PropertiesId id: PropertiesId.values()){
			if (this.playerProperties.containsKey(id))
				System.out.print(id + " = " + playerProperties.get(id) + ";\t");
		}
		System.out.println("PICKED CARDS:"); 
		for (DevelopmentCardType t: DevelopmentCardType.values()){
			System.out.println("-" + t + ":");
			ArrayList<DevelopmentCard> typeCards = playerCards.get(t);
			for (DevelopmentCard c: typeCards){
				System.out.print("c" + ";\t");
			}
		}
	}

	public boolean isEnded() {
		return matchEnded;
		}

	@Override
	public void showInfo(String info) {
		System.out.println("Message from server: " + info);
	}



	@Override
	public void reqChoice(CostChoice choice) {
		if (choice.getChoices().size()==1)
			{System.out.println("You have to pay this cost: " + choice.getChoices().get(0).toString());
			choice.setChosen(0);
			}
		else {
			System.out.println("You have to choose one of these costs: ");
			ArrayList<ImmProperties> costs =  choice.getChoices();
			int i=0;
			for (ImmProperties c: costs){
				System.out.println((i+1) + " - " + c.toString() + ";");
			}
			System.out.println("Which one do you want to pay?");
			int choosen = userInput.nextInt();
			while(choosen < 1 || choosen > costs.size()){
				System.out.println("Invalid choice, please insert another choice:");
			}
			choice.setChosen(choosen);
		}
	}

	@Override
	public void reqChoice(CouncilChoice choice) {		
		
	}

	@Override
	public void reqChoice(EffectChoice choice) {

	}

	@Override
	public void showMessage(AcceptedAction mess) {
		System.out.println(mess.getMessage());
	}

	@Override
	public void showMessage(RefusedAction mess) {
		System.out.println("Your action has been refused: " + mess.getMessage());		
	}

	@Override
	public String nextInput() {
		return userInput.nextLine();
	}

	@Override
	public boolean reqVaticanChoice() {
		System.out.println("You have to take a choice, bastard:\n ");
		System.out.println("Do you want to receive an excommunication(1) or not(2)? If you reject excommunication you will loose all your faith points");
		int userChoice = userInput.nextInt();
		while(userChoice != 1 && userChoice != 2){
			System.out.println("Invalid choice, please insert another choice");
			userChoice = userInput.nextInt();
		}
		if (userChoice == 1) return false;
		else return true ;
		
	}

	@Override
	public ImmProperties[] reqPrivileges(int number) { // TODO need possible choices of privileges
		int avaiablePrivileges = number;
		ArrayList<ImmProperties> choices = new ArrayList<>();
		System.out.println("You have to choose " + number + " council privileges to take, you can choose between: ");
		
		while (avaiablePrivileges != 0){
		
			
		}
		return choices.toArray(new ImmProperties[0]);
	}

	@Override
	public void setID(PlayerColor id) {
		if (this.playerID == null)  this.playerID = id;
		
	}

	@Override
	public void matchEnded() {
		this.matchEnded = true;
		
	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void chooseLeaderCard(LeaderCard[] possibleChoices) {
		// TODO Auto-generated method stub
		
	}

}
