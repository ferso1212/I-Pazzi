package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.SingleSpaceType;

public class AdvSingleSpace extends SingleSpace {

	private Player otherOccupant;

	public AdvSingleSpace(int diceRequirement, ImmProperties instantBonus, Player occupant, SingleSpaceType type,
			Player otherOccupant) {
		super(diceRequirement, instantBonus, occupant, type);
		this.otherOccupant = otherOccupant;
	}

	public Player getOtherOccupant() {
		return otherOccupant;
	}

	@Override
	public boolean occupy(Player player) {
		
		if (occupant == null) {
			this.occupant = player;
			return true;
		}
		else switch (player.getAdvModifier()) {
		case false:
		{
			return false;
		}
		case true:
		{
			if (otherOccupant==null){
				this.otherOccupant=player;
				return true;
			}
			else return false;
		}	

		default: return false;
		}
		
	}
