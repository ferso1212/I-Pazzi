package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;

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
	
	public boolean isOccupied(Player player, FamilyMember member)
	{
		for(int i=1; i<FLOORS_NUM + 1; i++)
		{
			if(!(tower[i].isOccupable(player, member))) return false;
		}
		return true;
	}

}
