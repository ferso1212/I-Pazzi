package it.polimi.ingsw.ps21.model;

public class MultipleSpace extends Space{
	
	protected Player[] occupants;
	protected int diceMalus;
	
	public MultipleSpace(){
		super();
	}

	
	
	
	public Player[] getOccupants() {
		return occupants;
	}

	public boolean occupy(Player player){
		Match match = new Match();
		for (int i=0; i < match.getPlayers().length; i++) {
			if (occupants[i] == null){
				occupants[i] = player;
				return true;
			} 
		} 
		return false;
	}

	public int getDiceMalus() {
		return diceMalus;
	}
	
	 
}
