package it.polimi.ingsw.ps21.model.board;

public class Tower {
	public final static int FLOORS_NUM=4;
	private SingleTowerSpace[] tower;
	public Tower(SingleTowerSpace[] tower) {
		super();
		this.tower = tower;
	}
	
	public SingleTowerSpace getTowerSpace(int floor) throws IllegalArgumentException{
		if ( (0 < floor) && (floor < FLOORS_NUM + 1) ){
			return tower[floor - 1];			
		} else throw new IllegalArgumentException();
	}

}
