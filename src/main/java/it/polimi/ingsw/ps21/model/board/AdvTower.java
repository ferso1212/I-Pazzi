package it.polimi.ingsw.ps21.model.board;

public class AdvTower {
	
	public final static int FLOORS_NUM=4;
	private AdvSingleTowerSpace[] tower;
	
	public AdvTower(AdvSingleTowerSpace[] tower) {
		super();
		this.tower = tower;
	}
	
	public AdvSingleTowerSpace getTowerSpace(int floor) throws IllegalArgumentException{
		if ( (0 < floor) && (floor < FLOORS_NUM + 1) ){
			return tower[floor - 1];			
		} else throw new IllegalArgumentException();
	}
	
	public boolean isOccupied()
	{
		for(int i=1; i<FLOORS_NUM + 1; i++)
		{
			if(tower[i].getOccupant() != null) return true;
		}
		return false;
	}
	
	public AdvSingleTowerSpace[] getTower() {
		return tower;
	}
	

}
