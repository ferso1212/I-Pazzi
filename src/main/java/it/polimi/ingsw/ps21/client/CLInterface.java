package it.polimi.ingsw.ps21.client;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.FamilyMemberData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ActionData;

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
		if (chosenRules == 1)
			advancedMatch = false;
		else
			advancedMatch = true;
		System.out.println("Waiting for match starting...");
	}

	@Override
	public void updateView(MatchData match) {
		this.matchInfo = match;
		this.boardInfo = match.getBoard();
		showMatchInfos();
		System.out.println("----------\tPlayers' Infos\t----------");
		for (PlayerData p : match.getPlayers()) {
			System.out.println();
			if (p.getId() == this.playerID) {
				this.playerInfo = p;
				showPlayerInfos();
			} else {
				showOtherPlayer(p);
			}
			System.out.println();
		}
	}

	private void showMatchInfos() {
		System.out.println("------------\tMatch Status\t------------");
		System.out.print("Period: " + matchInfo.getPeriod() + "\tRound: " + matchInfo.getRound());
		System.out.println("\nBoard:\nDices: Black = " + matchInfo.getBlackDice() + " White = "
				+ matchInfo.getWhiteDice() + " Orange = " + matchInfo.getOrangeDice());
		DevelopmentCard[][] cards = boardInfo.getCards();
		for (int i = 0; i < 4; i++) {
			System.out.println("\nTower " + i + 1 + "");
			for (int j = 0; j < 4; j++) {
				System.out.println("\nFloor " + j + ":");
				System.out.println(" Card: " + cards[i][j].toString());
				System.out.println("Family Member: " + (boardInfo.getTowerSpaces()[i][j]).toString());
			}
			System.out.println(";");
		}
	}

	private void showPlayerInfos() {
		System.out.println("---------\t YOUR INFO \t-------");
		System.out.println("COLOR: " + this.playerID);
		System.out.print("PROPERTIES: ");
		for (PropertiesId id : PropertiesId.values()) {
			System.out.print(id + " = " + this.playerInfo.getProperties().get(id) + ", ");
		}
		System.out.println(";");
		System.out.println("-----------\tPICKED CARDS\t-----------");
		for (DevelopmentCardType t : DevelopmentCardType.values()) {
			if (this.playerInfo.getCards().containsKey(t))
				;
			{
				System.out.println("-" + t + ":");
				ArrayList<DevelopmentCard> typeCards = this.playerInfo.getCards().get(t);
				for (DevelopmentCard c : typeCards) {
					System.out.print(c + ";\t");
				}
			}
		}
		System.out.println("----\t TILE BONUSES \t-----");
		System.out.println("HARVEST:\tDice Requirement =" + this.playerInfo.getTileHarvDiceReq() + ";\t" + "Bonus = "
				+ this.playerInfo.getTileHarvBonus() + ";");
		System.out.println("PRODUCTION:\tDice Requirement = " + this.playerInfo.getTileProdDiceReq() + ";\t " + "Bonus = "
				+ this.playerInfo.getTileProdBonus() + ";");

	}

	public void showOtherPlayer(PlayerData player) {
		System.out.println("COLOR: " + player.getId());
		System.out.print("PROPERTIES: ");
		for (PropertiesId id : PropertiesId.values()) {
			System.out.print(id + " = " + player.getProperties().get(id) + ", ");
		}
		System.out.println(";");
		System.out.println("---------\tPICKED CARDS\t-------");
		for (DevelopmentCardType t : DevelopmentCardType.values()) {
			if (player.getCards().containsKey(t)) {
				System.out.println("-" + t + ":");
				ArrayList<DevelopmentCard> typeCards = player.getCards().get(t);
				for (DevelopmentCard c : typeCards) {
					System.out.print(c.toString() + ";\t");
				}
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
		System.out.println(
				"Do you want to receive an excommunication(1) or not(2)? If you reject excommunication you will loose all your faith points");
		int userChoice = userInput.nextInt();
		while (userChoice != 1 && userChoice != 2) {
			System.out.println("God is watching you, please insert a valid choice!");
			userChoice = userInput.nextInt();
		}
		if (userChoice == 1)
			return false;
		else
			return true;

	}

	@Override
	public ImmProperties[] reqPrivileges(int number) { // TODO need possible
														// choices of privileges
		int avaiablePrivileges = number;
		ArrayList<ImmProperties> choices = new ArrayList<>();
		System.out.println("You have to choose " + number + " council privileges to take, you can choose between: ");

		while (avaiablePrivileges != 0) {

		}
		return choices.toArray(new ImmProperties[0]);
	}

	@Override
	public void setID(PlayerColor id) {
		if (this.playerID == null)
			this.playerID = id;

	}

	@Override
	public void matchEnded() {
		this.matchEnded = true;

	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {

		if (costChoices.size() == 1) {
			System.out.println("You have to pay this cost: " + costChoices.get(0).toString());
			return 0;
		} else {
			System.out.println("You have to choose one of these costs: ");
			for (int i = 0; i < costChoices.size(); i++) {
				System.out.println((i + 1) + " - " + costChoices.get(i).toString() + ";");
			}
			System.out.println("Which one do you want to pay?");
			int choosen = userInput.nextInt();
			while (choosen < 1 || choosen > costChoices.size()) {
				System.out.println("Invalid choice, please insert another choice:");
			}
			return choosen - 1;
		}

	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {

		if (effectChoice.length == 1) {
			System.out.println("You're activating this effect: " + effectChoice[0]);
			return 0;
		} else {
			System.out.println("Wich one of these effects do you want to activate?");
			for (int i = 0; i < effectChoice.length; i++) {
				System.out.println((i + 1) + "): " + effectChoice[i]);
			}
			int choosen = userInput.nextInt();
			while (choosen < 1 || choosen > effectChoice.length) {
				System.out.println("Idiot, this choice is invalid! Please insert another choice: ");
				choosen = userInput.nextInt();
			}
			return choosen - 1;
		}
	}

	@Override
	public int chooseLeaderCard(LeaderCard[] possibleChoices) {
		System.out.println("Please pick one of these Leader Cards: ");
		for (int i = 0; i < possibleChoices.length; i++) {
			System.out.println((i + 1) + "): " + possibleChoices[i]);
		}
		int choosen = userInput.nextInt();
		while (choosen < 1 || choosen > possibleChoices.length) {
			System.out.println("Ahahah you're a very funny jerk, please insert a valid choice...");
			choosen = userInput.nextInt();
		}
		return choosen - 1;
	}

	@Override
	public ActionData makeAction() {
		// TODO define ActionData and how to parse it
		System.out.println("It's your turn: which action do you want to do?");
		System.out.println("1)-Place a family member in a Tower Space;\n2)-Place a family member in Council palace\n"
				+ "3)-Place a family member in a Work Space\n" + "4)-Place a family memeber in a Market Space");

		if (advancedMatch) System.out.println("5)-Activate a Leader Card");
		int actionChoice = userInput.nextInt();
		while ( actionChoice!=1 && actionChoice!=2 && actionChoice != 3 && actionChoice != 4 && (!(advancedMatch) || actionChoice!=5 )){

			System.out.println("Invalid action, please insert a valid choice: ");
		}
		ActionType type;
		MembersColor familyMember;
		int servants;
		DevelopmentCardType tower;
		int space;
		switch (actionChoice) {
		case 1: // TODO Development Action setting
		{
			type = ActionType.TAKE_CARD;
			familyMember = chooseColor();
			servants = chooseServants();
			System.out.println("Which type of card do you want to pick?");
			int cardType = 1;
			System.out.println("1) Territory Card;");
			System.out.println("2) Building Card;");
			System.out.println("3) Character Card;");
			System.out.println("4) Venture Card;");
			cardType = userInput.nextInt();
			while (cardType < 1 || cardType > 4) {
				System.out.println("Invalid choice, please insert another choice:");
				cardType = userInput.nextInt();
			}
			switch (cardType) {
			case 1:
				tower = DevelopmentCardType.TERRITORY;
				break;
			case 2:
				tower = DevelopmentCardType.BUILDING;
				break;
			case 3:
				tower = DevelopmentCardType.CHARACTER;
				break;
			case 4:
				tower = DevelopmentCardType.VENTURE;
				break;
			default: // This part of code should never be reached
				tower = DevelopmentCardType.VENTURE;
			}
			// TODO choose floor;
			ArrayList<Integer> possibleSpaces = new ArrayList<>();
			for (int k=0; k < matchInfo.getBoard().getTowerSpaces()[cardType].length; k++){
				if (!matchInfo.getBoard().getTowerSpaces()[cardType][k].exists()){
					System.out.println( (k+1) + 
								"Card: " + matchInfo.getBoard().getCards()[cardType][k] +
								"Instant bonus: " + matchInfo.getBoard().getTowerBonuses()[cardType][k]);
					possibleSpaces.add(k+1);
				}
			}
			space = userInput.nextInt();
			while (!possibleSpaces.contains(space)){
				System.out.println("Invalid choice, please choose another space:");
				space = userInput.nextInt();
			}
			space--;

		}
			break;
		case 2: {
			type = ActionType.COUNCIL;
			familyMember = chooseColor();
			servants = 0;
			tower = null;
			space = 0;

		}
			break;
		case 3: {
			System.out.println("Do you want to use Harvest (1) area or Produciton (2) area?");
			int workChoice = userInput.nextInt();
			while (workChoice != 1 && workChoice != 2) {
				System.out.println("Invalid choice, please insert another choice");
				workChoice = userInput.nextInt();
			}
			if (workChoice == 1) {
				type = ActionType.HARVEST;
				tower = DevelopmentCardType.BUILDING;
			} else {
				type = ActionType.PRODUCTION;
				tower = DevelopmentCardType.TERRITORY;
			}
			familyMember = chooseColor();
			servants = chooseServants();
			System.out.println("Do you want to use Single space(1)  or Multiple space (2)?");
			int spaceChoice = userInput.nextInt();
			while (spaceChoice != 1 && spaceChoice != 2) {
				System.out.println("Invalid choice, please insert another choice");
				spaceChoice = userInput.nextInt();
			}
			space = spaceChoice;
		}
			break;
		case 4: {
			type = ActionType.MARKET;
			familyMember = chooseColor();
			servants = chooseServants();
			System.out.println("Which market space do you want to use?");
			for (int i = 0; i < boardInfo.getMarket().length; i++) {
				if (boardInfo.getMarket()[i] != null) {
					System.out.println((i + 1) + ") Bonus: " + boardInfo.getMarketBonuses()[i] + " - Privleges: "
							+ boardInfo.getMarketPrivileges()[i]);
				}
			}
			int marketChoice = userInput.nextInt();
			while (marketChoice < 1 || marketChoice > boardInfo.getMarket().length) {
				System.out.println("Invalid market choice, please insert another choice");
				marketChoice = userInput.nextInt();
			}
			space = marketChoice-1;
			tower = DevelopmentCardType.BUILDING;

		}
			break;
		// case 5: // TODO Leader Action setting
		// break;
		default:
			type = ActionType.NULL;
			familyMember = MembersColor.NEUTRAL;
			servants = 0;
			tower = null;
			space = 0;
			break;
		}
		return new ActionData(type, familyMember, servants, tower, space);
	}

	// TODO make this private after test
	public int chooseServants() {
		int choice = 0;
		System.out.println("How many servants do you want to use to increment dice value?");
		choice = userInput.nextInt();
		while (choice < 0 || choice > playerInfo.getPropertyValue(PropertiesId.SERVANTS)) {
			System.out.println("You can't add this number of servants");
			choice = userInput.nextInt();
		}
		return choice;
	}

	// TODO make this private after testing
	public MembersColor chooseColor() {
		System.out.println("Which family member do you want to use?");
		System.out.println("Available Family Member:");
		ArrayList<MembersColor> availableColors = new ArrayList<>();
		for (MembersColor c : MembersColor.values()) {
			if (!(playerInfo.getFamilyMember(c).isUsed()))
				availableColors.add(c);
		}
		for (int i = 0; i < availableColors.size(); i++) {
			System.out.println((i + 1) + ") " + availableColors.get(i));
		}
		int choice = userInput.nextInt();
		while (choice < 1 || choice > availableColors.size()) {
			System.out.println("Invalid choice, please insert another choice:");
			choice = userInput.nextInt();
		}
		return availableColors.get(choice);
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
		} else {
			System.out.println("You can execute other extra actions. Choose between:");
			for (int i = 0; i < actions.length; i++)
				System.out.println((i + 1) + actions[i].toString() + ";");
			System.out.println("Please, insert your choice: ");
			int userChoice = userInput.nextInt();
			while (userChoice < 1 || userChoice > actions.length) {
				System.out.println("Your choice is invalid, please insert another one...");
				userChoice = userInput.nextInt();
			}
			return userChoice;
		}
	}

}
