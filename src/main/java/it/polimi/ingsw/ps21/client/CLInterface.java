package it.polimi.ingsw.ps21.client;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.FamilyMemberData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.CouncilActionData;
import it.polimi.ingsw.ps21.view.MarketActionData;
import it.polimi.ingsw.ps21.view.WorkActionData;

public class CLInterface implements UserInterface {
	
	private Scanner userInput;
	private PlayerColor playerID = null;
	private PlayerData playerInfo;
	private BoardData boardInfo;
	private MatchData matchInfo;
	private boolean matchEnded = false;
	private boolean advancedMatch;
	private boolean matchStarted = false;
	
	public CLInterface(int chosenRules) {
		userInput = new Scanner(System.in);
		if (chosenRules == 1) advancedMatch = true;
		else advancedMatch = false;
		System.out.println("Waiting for match starting...");
	}
	
	@Override
	public void updateView(MatchData match, BoardData board, PlayerData players[]) {
		this.matchInfo = match;
		this.boardInfo = board;
		showMatchInfos();
		System.out.println("----------\tPlayers' Infos\t----------");
		for (PlayerData p: players){
			System.out.println();
			if (p.getId() == this.playerID){
				this.playerInfo = p;
				showPlayerInfos();
			}
			else{
				showOtherPlayer(p);
			}
			System.out.println();
		}
	}

	private void showMatchInfos() {
		System.out.println("------------\tMatch Status\t------------");
		System.out.print("Period: " + matchInfo.getEra() + "\tRound: " + matchInfo.getRound());
		System.out.println("\nBoard:\nDices: Black = " + boardInfo.getBlackDice() + " White = " + boardInfo.getWhiteDice() + " Orange = " + boardInfo.getOrangeDice());
	DevelopmentCard[][] cards = boardInfo.getCards();
	for (int i= 0; i<4 ; i++){
		System.out.println("\nTower " + i+1 + "");
		for (int j=0; j<4; j++){
			System.out.println("\nFloor " + j + ":");
			System.out.println(" Card: " + cards[i][j].toString() + "; ");
			if (j<3) System.out.println(", ");
			System.out.println("Family Member: " + boardInfo.getTowerSpaces()[i][j]);
		}
		System.out.println(";");
	}
	}

	private void showPlayerInfos() {
		System.out.println("---------\t YOUR INFO \t-------");
		System.out.println("COLOR: " + this.playerID);
		System.out.println("PROPERTIES:");
		for (PropertiesId id: PropertiesId.values()){
			if (this.playerInfo.getCards().containsKey(id))
				System.out.print(id + " = " + this.playerInfo.getProperties().get(id) + ";\t");
		}
		System.out.println("PICKED CARDS:"); 
		for (DevelopmentCardType t: DevelopmentCardType.values()){
			System.out.println("-" + t + ":");
			ArrayList<DevelopmentCard> typeCards = this.playerInfo.getCards().get(t);
			for (DevelopmentCard c: typeCards){
				System.out.print(c + ";\t");
			}
		}
		System.out.println("----\t TILE BONUSES \t-----");
		System.out.println("HARVEST: Dice Requirement = " + this.playerInfo.getTileHarvDiceReq() + "; " + "Bonus = "+ this.playerInfo.getTileHarvBonus() + ";");
		System.out.println("PRODUCTION: Dice Requirement = " + this.playerInfo.getTileProdDiceReq() + "; " + "Bonus = "+ this.playerInfo.getTileProdBonus() + ";");
		
	}
	
	public void showOtherPlayer(PlayerData player){
		System.out.println("COLOR: " + player.getId());
		System.out.println("COLOR: " + this.playerID);
		System.out.println("PROPERTIES:");
		for (PropertiesId id: PropertiesId.values()){
			if (player.getCards().containsKey(id))
				System.out.print(id + " = " + player.getProperties().get(id) + ";\t");
		}
		System.out.println("PICKED CARDS:"); 
		for (DevelopmentCardType t: DevelopmentCardType.values()){
			System.out.println("-" + t + ":");
			ArrayList<DevelopmentCard> typeCards = player.getCards().get(t);
			for (DevelopmentCard c: typeCards){
				System.out.print(c.toString() + ";\t");
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
			System.out.println("God is watching you, please insert a valid choice!");
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

		if (costChoices.size()==1)
		{System.out.println("You have to pay this cost: " + costChoices.get(0).toString());
		return 0;
		}
	else {
		System.out.println("You have to choose one of these costs: ");
		for (int i =0; i< costChoices.size(); i++){
			System.out.println((i+1) + " - " + costChoices.get(i).toString() + ";");
		}
		System.out.println("Which one do you want to pay?");
		int choosen = userInput.nextInt();
		while(choosen < 1 || choosen > costChoices.size()){
			System.out.println("Invalid choice, please insert another choice:");
		}
		return choosen-1;
	}

	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {

		
		if (effectChoice.length == 1){
			System.out.println("You're activating this effect: " + effectChoice[0]);
			return 0;
		}
		else {
			System.out.println("Wich one of these effects do you want to activate?");
			for (int i = 0; i< effectChoice.length ; i++){
				System.out.println((i+1) + "): " + effectChoice[i]);
			}
			int choosen = userInput.nextInt();
			while (choosen <1 || choosen > effectChoice.length) {
				System.out.println("Idiot, this choice is invalid! Please insert another choice: ");
				choosen = userInput.nextInt();
			}
			return choosen -1;
		}
	}

	@Override
	public int chooseLeaderCard(LeaderCard[] possibleChoices) {
		System.out.println("Please pick one of these Leader Cards: ");
		for (int i=0; i< possibleChoices.length; i++){
			System.out.println((i+1) + "): " + possibleChoices[i]);
		}
		int choosen = userInput.nextInt();
		while (choosen <1 || choosen > possibleChoices.length){
			System.out.println("Ahahah you're a very funny jerk, please insert a valid choice...");
			choosen = userInput.nextInt();
		}
		return choosen -1;
	}

	@Override
	public ActionData makeAction() {
		// TODO define ActionData and how to parse it
		System.out.println("It's your turn: which action do you want to do?");
		System.out.println("1)-Place a family member in a Towe Space;\n2)-Place a family member in Council palace\n"
				+ "3)-Place a family member in a Work Space\n" + "4)-Place a family memeber in a Market Space");
		if (advancedMatch) System.out.println("5)-Activate a Leader Card");
		int actionChoice = userInput.nextInt();
		while ( actionChoice!=1 && actionChoice!=2 && actionChoice != 3 && actionChoice != 4 && (!(advancedMatch) || actionChoice!=5 )){
			System.out.println("Invalid action, please insert a valid choice: ");
		}
		switch (actionChoice){
		case 1: // TODO Development Action setting
			System.out.println("Which family member do you want to use?");
			System.out.println("Available Family Member:");
			FamilyMemberData members[] = playerInfo.getFamilyMembers();
			int i=0;
			for (FamilyMemberData f: members){
				if (!f.isUsed()) {
					System.out.println((i + 1) +  ") " + f.getColor() + ";");
					i++;
				}
				
			}
			int choice = userInput.nextInt();
			while (choice < 1 || choice >i +1){
				
			}
			
			
			
			
			// TODO return new DevelopmentAction(playerID, tower, floor, famMember)
		case 2: // TODO Council Action setting
			return new CouncilActionData(null, null);
		case 3: // TODO Work Action setting
			return new WorkActionData(null, false);
		case 4: // TODO Market Action setting
			return new MarketActionData(0, null);
		case 5: // TODO Leader Action setting
			return null;
		default: 
			//return NullActionData();
			return null;
		}

	}

	@Override
	public void playMatch() {
		this.matchStarted = true;
		System.out.println("Match started!");
	}

	@Override
	public int reqExtraActionChoice(ActionData[] actions) {
		if (actions.length == 1) {
			System.out.println("You can execute this ExtraAction: " + actions[0].toString());
			return 0;
		}
		else {
			System.out.println("You can execute other extra actions. Choose between:");
			for (int i=0; i<actions.length; i++)
				System.out.println((i+1) + actions[i].toString() + ";");
			System.out.println("Please, insert your choice: ");
			int userChoice = userInput.nextInt();
			while (userChoice <1 || userChoice > actions.length){
				System.out.println("Your choice is invalid, please insert another one...");
				userChoice= userInput.nextInt();
			}
			return userChoice;
		}
	}

}
