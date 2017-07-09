package it.polimi.ingsw.ps21.client;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.NotAdvancedPlayerException;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.BoardData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;
import it.polimi.ingsw.ps21.view.FamilyMemberData;
import it.polimi.ingsw.ps21.view.MatchData;
import it.polimi.ingsw.ps21.view.PlayerData;

/**
 * This interface show information about the match in text form.
 *  Player can interact with match using keyboard.
 * 
 * @author gullit
 *
 */
public class CLInterface implements UserInterface {
	private static final Logger LOGGER = Logger.getLogger(CLInterface.class.getName());
	private Scanner userInput;
	private PlayerColor playerID = null;
	private PlayerData playerInfo;
	private BoardData boardInfo;
	private MatchData matchInfo;
	private boolean matchEnded = false;
	private boolean advancedMatch;
	private int charcatersPrinted = 0;

	public CLInterface() {
		userInput = new Scanner(System.in);

	}

	private void printOutput(String output) {
		System.out.println(output);
		charcatersPrinted++;

	}

	@Override
	public void updateView(MatchData match) {
		refreshOutput();
		this.matchInfo = match;
		this.boardInfo = match.getBoard();
		showMatchInfos();
		printOutput("----------\tPlayers' Infos\t----------");
		for (PlayerData p : match.getPlayers()) {
			printOutput("");
			if (p.getId() == this.playerID) {
				this.playerInfo = p;
				showPlayerInfos();
			} else {
				showOtherPlayer(p);
			}
			printOutput("");
		}
	}

	private void refreshOutput() {
		while (charcatersPrinted > 0) {
			// TODO this doesn't works with eclipse console
			// System.out.print("\b");
			System.out.println();
			charcatersPrinted--;
		}

	}

	private void showMatchInfos() {
		printOutput("------------\tMatch Status\t------------");
		printOutput("Period: " + matchInfo.getPeriod() + "\tRound: " + matchInfo.getRound());
		printOutput("\nBoard:\nDices:\tBlack = " + matchInfo.getBlackDice() + "\tWhite = " + matchInfo.getWhiteDice()
				+ "\tOrange = " + matchInfo.getOrangeDice());
		DevelopmentCard[][] cards = boardInfo.getCards();
		for (int i = 0; i < 4; i++) {
			printOutput("\nTower " + (i + 1) + "\n------------------------------------------------------------");
			for (int j = 0; j < 4; j++) {
				printOutput("\nFloor " + (j + 1) + ":");
				printOutput("Floor Requirement: " + matchInfo.getBoard().getTowerRequirements()[j][i] + ":");
				DevelopmentCard card = cards[j][i];
				if (card != null)
					printOutput(card.toString());
				else
					printOutput("Empty Floor");
				printOutput("Family Member: " + (boardInfo.getTowerSpaces()[j][i]).toString());
				printOutput("------------------------------------------------------------");
			}
			printOutput("===================================================================");
		}
		printOutput("--------------------\t COUNCIL PALACE \t ---------------------");
		StringBuilder council = new StringBuilder("Occupants: ");
		for (FamilyMemberData f: matchInfo.getBoard().getCouncilOccupants()){
			council.append("Player " + f.getOwnerId() + " with member " + f.getColor() );
		}
		printOutput(council.toString());
		printOutput("===============================================================");
		printOutput("----------------\t HARVEST SPACE \t---------------------");
		if (matchInfo.getBoard().getSingleHarvestSpace()!=null) printOutput("Single space: " +  matchInfo.getBoard().getSingleHarvestSpace().toString());
		else printOutput("Single space: FREE"); 
		if (advancedMatch && matchInfo.getBoard().getSingleHarvestSpaceOtherOccupant() != null)
		printOutput("\tOther occupant " +  matchInfo.getBoard().getSingleHarvestSpaceOtherOccupant().toString());
		StringBuilder multipleSpace = new StringBuilder("Multiple space: ");
		for (FamilyMemberData f: matchInfo.getBoard().getMultipleHarvestSpace()){
			multipleSpace.append("Player " + f.getOwnerId() + " with member " + f.getColor());
		}
		printOutput(multipleSpace.toString());
		printOutput("Dice Malus: 3");
		printOutput("===============================================================");
		printOutput("----------------\t PRODUCTION SPACE \t---------------------");
		if (matchInfo.getBoard().getSingleProductionSpace()!=null) printOutput("Single space: " +  matchInfo.getBoard().getSingleProductionSpace().toString());
		else printOutput("Single space: FREE"); 
		if (advancedMatch && matchInfo.getBoard().getSingleProductionSpaceOtherOccupant() != null) printOutput("\tOther occupant " + matchInfo.getBoard().getSingleProductionSpaceOtherOccupant().toString());
		 multipleSpace = new StringBuilder("Multiple space: ");
		for (FamilyMemberData f: matchInfo.getBoard().getMultipleProductionSpace()){
			multipleSpace.append("Player " + f.getOwnerId() + " with member " + f.getColor());
		}
		printOutput(multipleSpace.toString());
		printOutput("Dice Malus: 3");
		printOutput("====================================================================");
		printOutput("--------------------\t MARKET SPACES \t-----------------------------");
		StringBuilder market = new StringBuilder();
		for(int i=0; i<matchInfo.getBoard().getMarketBonuses().length; i++){
			if (matchInfo.getBoard().getMarket()[i].getFirstOccupant() !=null) {
				market.append((i+1) + ") " + matchInfo.getBoard().getMarket()[i].getFirstOccupant().toString());
				if (advancedMatch && matchInfo.getBoard().getMarket()[i].getSecondOccupant()!=null) market.append("\n\tOther Occupant: " +  matchInfo.getBoard().getMarket()[i].getSecondOccupant().toString());
			}
			else market.append((i+1) + ") FREE");
			market.append("\nBonus: " +  matchInfo.getBoard( ).getMarketBonuses()[i].toString() + "\tPrivileges: " + matchInfo.getBoard().getMarketPrivileges()[i]);
			market.append("\n");
		}
		printOutput(market.toString());
		printOutput("====================================================================");
	}

	private void showPlayerInfos() {
		printOutput("---------\t YOUR INFO \t-------");
		printOutput("COLOR: " + this.playerID);
		System.out.print("PROPERTIES: ");
		for (PropertiesId id : PropertiesId.values()) {
			System.out.print(id + " = " + this.playerInfo.getProperties().get(id) + ", ");
		}
		printOutput(";");
		printOutput("-----------\tPICKED CARDS\t-----------");
		for (DevelopmentCardType t : DevelopmentCardType.values()) {
			if (this.playerInfo.getCards().containsKey(t))
			{
				printOutput("\t\t" + t + ":");
				ArrayList<DevelopmentCard> typeCards = this.playerInfo.getCards().get(t);
				for (DevelopmentCard c : typeCards) {
					printOutput(c + ";");
				}
			}
		}
		printOutput("---------\t EXCOMMUNICATION \t-------------");
		for (Excommunication e : this.playerInfo.getExcommunications()) {
			printOutput("-" + e.toString() + ";");
		}
		printOutput("----\t TILE BONUSES \t-----");
		printOutput("HARVEST:\tDice Requirement =" + this.playerInfo.getTileHarvDiceReq() + ";\t" + "Bonus = "
				+ this.playerInfo.getTileHarvBonus() + ";");
		printOutput("PRODUCTION:\tDice Requirement = " + this.playerInfo.getTileProdDiceReq() + ";\t " + "Bonus = "
				+ this.playerInfo.getTileProdBonus() + ";");
		if(advancedMatch) {
			try {
				printOutput("---------\t LEADER CARDS \t-------------");
				int i=1;
				for(LeaderCard c : playerInfo.getLeaders())
				{
					printOutput("\n-------\n" +i + ") "+ c.toString());
					i++;
				}
			} catch (NotAdvancedPlayerException e1) {
				LOGGER.log(Level.SEVERE, "This player is not advanced.", e1);
			}
		}

	}

	public void showOtherPlayer(PlayerData player) {
		printOutput("COLOR: " + player.getId());
		System.out.print("PROPERTIES: ");
		for (PropertiesId id : PropertiesId.values()) {
			System.out.print(id + " = " + player.getProperties().get(id) + ", ");
		}
		printOutput(";");
		printOutput("---------\tPICKED CARDS\t-------");
		for (DevelopmentCardType t : DevelopmentCardType.values()) {
			if (player.getCards().containsKey(t)) {
				printOutput("\t\t" + t + ":");
				ArrayList<DevelopmentCard> typeCards = player.getCards().get(t);
				for (DevelopmentCard c : typeCards) {
					printOutput(c.toString() + ";");
				}
			}
		}
	}

	public boolean isEnded() {
		return matchEnded;
	}

	/**
	 * This method print message from server into a textual box
	 */
	@Override
	public void showInfo(String info) {
		info = "Message from server: " + info;
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < info.length() + 6; i++) {
			temp.append("-");
		}
		printOutput(temp.toString());
		temp = new StringBuilder();
		temp.append("|  ");
		temp.append(info);
		temp.append("  |");
		printOutput(temp.toString());
		temp = new StringBuilder();
		for (int i = 0; i < info.length() + 6; i++) {
			temp.append("-");
		}
		printOutput(temp.toString());
		printOutput("");
		// printOutput("Message from server: " + info);
	}

	@Override
	public void showMessage(AcceptedAction mess) {

		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < mess.getMessage().length() + 6; i++) {
			temp.append("-");
		}
		printOutput(temp.toString());
		temp = new StringBuilder();
		temp.append("|  ");
		temp.append(mess.getMessage());
		temp.append("  |");
		printOutput(temp.toString());
		temp = new StringBuilder();
		for (int i = 0; i < mess.getMessage().length() + 6; i++) {
			temp.append("-");
		}
		printOutput(temp.toString());
		printOutput("");
	}

	@Override
	public void showMessage(RefusedAction mess) {

		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < mess.getMessage().length() + 6; i++) {
			temp.append("-");
		}
		printOutput(temp.toString());
		temp = new StringBuilder();
		temp.append("|  ");
		temp.append(mess.getMessage());
		temp.append("  |");
		printOutput(temp.toString());
		temp = new StringBuilder();
		for (int i = 0; i < mess.getMessage().length() + 6; i++) {
			temp.append("-");
		}
		printOutput(temp.toString());
		printOutput("");
	}

	@Override
	public boolean reqVaticanChoice() {
		printOutput("You have to choose to support the Church:");
		printOutput("Do you want to support the vatican(1) or not(2)?");
		int userChoice = userInput.nextInt();
		while (userChoice != 1 && userChoice != 2) {
			printOutput("Please insert a valid choice!");
			printOutput("Do you want to support the vatican(1) or not(2)?");
			userChoice = userInput.nextInt();
		}
		if (userChoice == 1)
			return true;
		else
			return false;

	}

	@Override
	public ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues) {
		// choices of privileges
		int avaiablePrivileges = number;
		ArrayList<ImmProperties> choices = new ArrayList<>();
		printOutput("You have to choose " + number + " council privileges to take, you can choose between: ");
		while (avaiablePrivileges > 0) {
			for (int i = 0; i < privilegesValues.length; i++) {
				printOutput((i + 1) + ") " + privilegesValues[i].toString());
			}
			int userChoice = userInput.nextInt();
			while ((userChoice < 1) || (userChoice > privilegesValues.length)) {
				printOutput("Invalid Choice! Please inser another choice:");
				userChoice = userInput.nextInt();
			}
			if (!choices.contains(privilegesValues[userChoice - 1])) {
				choices.add(privilegesValues[userChoice - 1]);
				avaiablePrivileges--;
			} else
				printOutput("You have already choose this privilege, please choose another one: ");
		}
		return choices.toArray(new ImmProperties[0]);
	}

	@Override
	public void setID(PlayerColor id) {
		if (this.playerID == null)
			this.playerID = id;

	}

	@Override
	public void matchEnded(EndData data) {
		this.matchEnded = true;
		if (data.getWinner() == playerID)
			printOutput("CONGRATULATIONS: You won!!!!!");
		else
			printOutput("Player " + data.getWinner() + " won the match!");
		Map<PlayerColor, Integer> result = data.getPlayersFinalPoints();
		printOutput("----------\t RESULT \t---------------");
		for (PlayerColor color : result.keySet()) {
			if (color.equals(playerID))
				printOutput("You have totalized " + result.get(color) + " final points;");
			else
				printOutput("Player " + color + " has totalized " + result.get(color) + " final points;");
		}
		printOutput("\nMatch ended. Press ENTER to quit.");
		userInput.nextLine();
		System.exit(0);
	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {
		if (costChoices.isEmpty()) {
			printOutput("No costs to choose.");
			return 0;
		}
		if (costChoices.size() == 1) {
			printOutput("You have to pay this cost: " + costChoices.get(0).toString());
			return 0;
		} else {
			printOutput("You have to choose one of these costs: ");
			for (int i = 0; i < costChoices.size(); i++) {
				printOutput((i + 1) + " - " + costChoices.get(i).toString() + ";");
			}
			printOutput("Which one do you want to pay?");
			int choosen = userInput.nextInt();
			while (choosen < 1 || choosen > costChoices.size()) {
				printOutput("Invalid choice, please insert another choice:");
			}
			return choosen - 1;
		}

	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {

		if (effectChoice.length == 1) {
			printOutput("You're activating this effect: " + effectChoice[0]);
			return 0;
		} else {
			printOutput("Which one of these effects do you want to activate?");
			for (int i = 0; i < effectChoice.length; i++) {
				printOutput((i + 1) + "): " + effectChoice[i]);
				printOutput("Cost" + effectChoice[i].getTotalCost() + ";");
			}
			int choosen = userInput.nextInt();
			while (choosen < 1 || choosen > effectChoice.length) {
				printOutput("Invalid choice, please insert another choice: ");
				choosen = userInput.nextInt();
			}
			return choosen - 1;
		}
	}

	@Override
	public int chooseLeaderCard(LeaderCard[] possibleChoices) {
		printOutput("Please pick one of these Leader Cards: ");
		for (int i = 0; i < possibleChoices.length; i++) {
			printOutput((i + 1) + "): " + possibleChoices[i]);
		}
		int choosen = userInput.nextInt();
		while (choosen < 1 || choosen > possibleChoices.length) {
			printOutput("Invalid choice, please insert a valid choice...");
			choosen = userInput.nextInt();
		}
		return choosen - 1;
	}

	@Override
	public ActionData makeAction(int id) {
		printOutput("It's your turn: which action do you want to do?");
		printOutput(
				"0)-No action;\n1)-Place a family member in a Tower Space;\n2)-Place a family member in Council palace\n"
						+ "3)-Place a family member in a Work Space\n" + "4)-Place a family memeber in a Market Space");

		if (advancedMatch)
			printOutput("5)-Activate a Leader Card\n");
		int actionChoice = userInput.nextInt();
		while (actionChoice != 0 && actionChoice != 1 && actionChoice != 2 && actionChoice != 3 && actionChoice != 4
				&& (!(advancedMatch) || actionChoice != 5)) {

			printOutput("Invalid action, please insert a valid choice: ");
		}
		ActionType type;
		MembersColor familyMember;
		int servants;
		DevelopmentCardType tower;
		int space;
		switch (actionChoice) {
		case 0: {// valori pasuli
			type = ActionType.NULL;
			familyMember = MembersColor.NEUTRAL;
			servants = 0;
			tower = DevelopmentCardType.BUILDING;
			space = 0;
			break;
		}
		case 1: {
			type = ActionType.TAKE_CARD;
			familyMember = chooseColor();
			servants = chooseServants();
			printOutput("Which type of card do you want to pick?");
			int cardType = 1;
			printOutput("1) Territory Card;");
			printOutput("2) Character Card;");
			printOutput("3) Building Card;");
			printOutput("4) Venture Card;");
			cardType = userInput.nextInt();
			while (cardType < 1 || cardType > 4) {
				printOutput("Invalid choice, please insert another choice:");
				cardType = userInput.nextInt();
			}
			switch (cardType) {
			case 1:
				tower = DevelopmentCardType.TERRITORY;
				break;
			case 2:
				tower = DevelopmentCardType.CHARACTER;
				break;
			case 3:
				tower = DevelopmentCardType.BUILDING;
				break;
			case 4:
				tower = DevelopmentCardType.VENTURE;
				break;
			default: // This part of code should never be reached
				tower = DevelopmentCardType.VENTURE;
			}
			// choose floor;
			ArrayList<Integer> possibleSpaces = new ArrayList<>();
			for (int k = 0; k < 4; k++) {
				if (!matchInfo.getBoard().getTowerSpaces()[k][cardType - 1].exists()) {
					printOutput((k + 1) + ") " + matchInfo.getBoard().getCards()[k][cardType - 1] + "\nInstant bonus: "
							+ matchInfo.getBoard().getTowerBonuses()[k][cardType - 1] + "\nFloor dice requirement: "
							+ matchInfo.getBoard().getTowerRequirements()[k][cardType - 1] + "\n-----");
					possibleSpaces.add(k + 1);
				}
			}
			space = userInput.nextInt();
			while (!possibleSpaces.contains(space)) {
				printOutput("Invalid choice, please choose another space:");
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
			printOutput("Do you want to use Harvest (1) area or Production (2) area?");
			int workChoice = userInput.nextInt();
			while (workChoice != 1 && workChoice != 2) {
				printOutput("Invalid choice, please insert another choice");
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
			int spaceChoice;
			if (this.matchInfo.getPlayers().length <= 2)
				spaceChoice = 1;
			else {
				printOutput("Do you want to use Single space(1)  or Multiple space (2)?");
				spaceChoice = userInput.nextInt();
				while (spaceChoice != 1 && spaceChoice != 2) {
					printOutput("Invalid choice, please insert another choice");
					spaceChoice = userInput.nextInt();
				}
			}
			space = spaceChoice;
		}
			break;
		case 4: {
			type = ActionType.MARKET;
			familyMember = chooseColor();
			servants = chooseServants();
			printOutput("Which market space do you want to use?");
			for (int i = 0; i < boardInfo.getMarket().length; i++) {
				if (boardInfo.getMarket()[i] != null) {
					printOutput((i + 1) + ") Bonus: " + boardInfo.getMarketBonuses()[i] + " - Privleges: "
							+ boardInfo.getMarketPrivileges()[i]);
				}
			}
			int marketChoice = userInput.nextInt();
			while (marketChoice < 1 || marketChoice > boardInfo.getMarket().length) {
				printOutput("Invalid market choice, please insert another choice");
				marketChoice = userInput.nextInt();
			}
			space = marketChoice - 1;
			tower = DevelopmentCardType.BUILDING;
		}
			break;
		case 5: {
			type = ActionType.PLAY_LEADERCARD;
			familyMember = null;
			servants = 0;
			tower = null;
			space = chooseLeaderCardToActivate();
			break;
		}
		default:
			type = ActionType.NULL;
			familyMember = MembersColor.NEUTRAL;
			servants = 0;
			tower = null;
			space = 0;
			break;
		}
		return new ActionData(type, familyMember, servants, tower, space, id);
	}

	// TODO make this private after test
	public int chooseServants() {
		int choice = 0;
		printOutput("How many servants do you want to use to increment dice value?");
		choice = userInput.nextInt();
		while (choice < 0 || choice > playerInfo.getPropertyValue(PropertiesId.SERVANTS)) {
			printOutput("You can't add this number of servants");
			choice = userInput.nextInt();
		}
		return choice;
	}

	// TODO make this private after testing
	public MembersColor chooseColor() {
		printOutput("Which family member do you want to use?");
		printOutput("Available Family Member:");
		ArrayList<FamilyMemberData> availableColors = new ArrayList<>();
		for (MembersColor c : MembersColor.values()) {
			if (!(playerInfo.getFamilyMember(c).isUsed()))
				availableColors.add(playerInfo.getFamilyMember(c));
		}
		for (int i = 0; i < availableColors.size(); i++) {
			printOutput((i + 1) + ") " + availableColors.get(i).getColor() + "\tValue = "
					+ availableColors.get(i).getValue());
		}
		int choice = userInput.nextInt();
		while (choice < 1 || choice > availableColors.size()) {
			printOutput("Invalid choice, please insert another choice:");
			choice = userInput.nextInt();
		}
		return availableColors.get(choice - 1).getColor();
	}

	@Override
	public void playMatch() {
		printOutput("Match started!");
	}

	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) {
		if (actions.length == 1) {
			printOutput("You can execute this ExtraAction: " + actions[0].getDescription());
			return 0;
		} else {
			printOutput("You can execute other extra actions. Choose between:");
			for (int i = 0; i < actions.length; i++)
				printOutput((i + 1) + actions[i].getDescription() + ";");
			printOutput("Please, insert your choice: ");
			int userChoice = userInput.nextInt();
			while (userChoice < 1 || userChoice > actions.length) {
				printOutput("Your choice is invalid, please insert another one...");
				userChoice = userInput.nextInt();
			}
			return userChoice - 1;
		}
	}

	@Override
	public int reqWorkChoice(DevelopmentCard workCard, boolean cost) {
		int effectChosen = 0;
		printOutput("Which effect do you want to activate on this card?");
		printOutput("Card: " + workCard.getName());
		printOutput("Possible choices:");
		if (!cost) printOutput("0)Don't activate any effect;");
		EffectSet possibleEffects[] = workCard.getPossibleEffects();
		for (int i = 0; i < possibleEffects.length; i++) {
			printOutput("-" + (i + 1) + ") " + possibleEffects[i].toString());
		}
		effectChosen = userInput.nextInt();
		while (effectChosen < 0 || effectChosen > possibleEffects.length) {
			printOutput("Invalid choice, please insert a valid choice...");
			effectChosen = userInput.nextInt();
		}
		return effectChosen;
	}

	@Override
	public String reqName() {
		printOutput("\nInsert your name: ");
		return userInput.nextLine();
	}

	@Override
	public boolean reqIfWantsAdvancedRules() {
		int chosenRules = 0;
		while (chosenRules != 1 && chosenRules != 2) {
			printOutput("\nWhich rules do you want to use? \n1 Standard \n2 Advanced");
			chosenRules = userInput.nextInt();
		}
		if (chosenRules == 2) {
			this.advancedMatch = true;
			return true;
		} else {
			this.advancedMatch = false;
			return false;
		}
	}

	@Override
	public int chooseTile(PersonalBonusTile[] possibilities) {
		printOutput("Which personal bonus tile do you want to attach to your personal board?");
		for (int i = 0; i < possibilities.length; i++) {
			printOutput((i + 1) + ")-" + possibilities[i].toString());

		}
		int userChoice = userInput.nextInt();
		while (userChoice < 1 || userChoice > possibilities.length) {
			printOutput("Invalid choice! Please insert another choice...");
			userChoice = userInput.nextInt();
		}
		return userChoice - 1;
	}

	public int chooseLeaderCardToActivate() {
		try {
			LeaderCard[] leaders = this.playerInfo.getLeaders();
			printOutput("Choose a leader card to activate: ");
			for (int i = 0; i < leaders.length; i++) {
				printOutput((i + 1) + ")- " + leaders[i].toString());
			}
			int userChoice = userInput.nextInt();
			while (userChoice < 1 || userChoice > leaders.length) {
				printOutput("Invalid choice! Please insert another choice...");
				userChoice = userInput.nextInt();
			}
			return userChoice - 1;
		} catch (NotAdvancedPlayerException e) {
			printOutput("\nThis action is not possible in standard matches.");
			LOGGER.log(Level.WARNING, "\nTried to get leader cards from standard player data", e);
			return 0;
		}

	}

	@Override
	public void setRules(boolean isAdvanced) {
		this.advancedMatch = isAdvanced;

	}

	@Override
	public int reqCardChoice(DevelopmentCard[] possibleChoices) {
		showInfo("You have to choose a card to pick for the action PickAnotherCard");
		printOutput("Which card do you want to pick");
		int i =0; 
		while(i<possibleChoices.length){
			printOutput((i+1) + ") " + possibleChoices[i].toString());
		}
		int choice = userInput.nextInt(); 
		while (choice < 1 || choice > possibleChoices.length){
			printOutput("Invalid choice, please insert another one");
			choice = userInput.nextInt();
		}
		return choice-1;
	}

	@Override
	public int reqNumberOfServants(int max) {
		showInfo("You have to choose a number of servants to use for extra action");
		printOutput("Wich number of servants do you want to use");
		int choice = userInput.nextInt();
		while (choice<0 || choice >max){
			printOutput("You have choosen an invalid number of message");
			choice = userInput.nextInt();
			
		}
		return choice;
	}

	@Override
	public int reqLorenzoIlMagnificoChoice(LeaderCard[] possibilities) {
		showInfo("LORENZO EFFECT : You have to choose which card you want to copy");
		printOutput("Activated Cards of other player");
		for (int i=0; i<possibilities.length; i++){
			printOutput( (i + 1) + ") " + possibilities[i].getName() + ": " + possibilities[i].getEffect().getDesc());
		}
		int choice = userInput.nextInt();
		while (choice<1 || choice >possibilities.length){
			printOutput("You have choosen an invalid card of message, please choose another card");
			choice = userInput.nextInt();
		}
		return choice -1;
		
	}

}
