package it.polimi.ingsw.ps21.model.excommunications;

import it.polimi.ingsw.ps21.model.player.Player;

public class VenturePointsExcommunication extends Excommunication{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282803470444970984L;

	public VenturePointsExcommunication(int id, int period) {
		super(id, period);
	}

	@Override
	public void activate(Player player) {
		player.getModifiers().getFinalMods().setNoVentureCardsFinalVictoryPointsBonus(true);
		
	}

	@Override
	public String toString() {
		return "At the end of the game, you don't get victory points from the number from permanent effects of Venture cards";
	}
	
	

}
