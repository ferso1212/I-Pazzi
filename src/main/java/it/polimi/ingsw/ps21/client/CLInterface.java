package it.polimi.ingsw.ps21.client;

import java.io.InputStream;
import java.util.ArrayList;
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
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CLInterface implements UserInterface {
	
	public MatchData match;
	public Scanner userInput;
	
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
	}

	public boolean isEnded() {
		return false;
		//return match.getEra() == 3;
	}

	@Override
	public void showInfo(String info) {
		System.out.println(info);
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

}
