package it.polimi.ingsw.ps21.client;

import it.polimi.ingsw.ps21.controller.MatchData;

public class CLInterface {
	
	
	
	private void show(MatchData match) {
		System.out.print("Stato della partita:\n "
					+ "Era: " + match.getEra() + "\tRound: " + match.getRound()
					+ "Tabellone: ");
	}

}
