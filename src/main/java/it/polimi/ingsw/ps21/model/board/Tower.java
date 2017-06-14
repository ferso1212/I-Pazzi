package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class Tower {
	
	protected final static int FLOORS_NUM=4;
	private SingleTowerSpace[] tower = new SingleTowerSpace[4];
	
	public Tower(boolean isAdvanced, ImmProperties[] spaceBonuses) {
		int j=0;
		if (isAdvanced == true){
			this.tower = new AdvSingleTowerSpace[FLOORS_NUM];
			for(int i=0; i<FLOORS_NUM; i++){
				tower[i] = new AdvSingleTowerSpace(1 + 2*j, spaceBonuses[j]);
				j++;
			}
		}else {
			this.tower = new SingleTowerSpace [FLOORS_NUM];
			for(int i=0; i<FLOORS_NUM; i++){
				tower[i] = new SingleTowerSpace(1 + 2*j, spaceBonuses[j]);
				j++;
			}
		}
		
	}
	
	public SingleTowerSpace getTowerSpace(int floor) throws IllegalArgumentException{
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

	public SingleTowerSpace[] getTower() {
		return tower;
	}
	
	

}
