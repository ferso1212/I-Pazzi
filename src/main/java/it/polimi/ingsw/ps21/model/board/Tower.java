package it.polimi.ingsw.ps21.model.board;

public class Tower {
	
	private SingleTowerSpace[] tower;

	public Tower(SingleTowerSpace[] tower) {
		super();
		this.tower = tower;
	}
	
	public SingleTowerSpace getTowerSpace(int floor) throws Exception{
		if ( (0 < floor) && (floor < 5) ){
			return tower[floor - 1];			
		} else throw new Exception();
	}

}
