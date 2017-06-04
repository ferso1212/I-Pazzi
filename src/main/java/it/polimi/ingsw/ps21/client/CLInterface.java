package it.polimi.ingsw.ps21.client;

import it.polimi.ingsw.ps21.controller.MatchData;

public class CLInterface {
	
	public MatchData match;
	
	public CLInterface() {
		// TODO Auto-generated constructor stub
	}
	
	private void show(MatchData match) {
		System.out.print("Stato della partita:\n "
					+ "Era: " + match.getEra() + "\tRound: " + match.getRound()
					+ "Tabellone: ");
	}

	public boolean isEnded() {
		return match.getEra() == 3;
	}

}
