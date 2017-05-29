package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class AdvSingleSpace extends SingleSpace {

	private FamilyMember otherOccupant;

	public AdvSingleSpace(int diceRequirement, ImmProperties instantBonus, FamilyMember occupant, SingleSpaceType type,
			FamilyMember otherOccupant) {
		super(diceRequirement, instantBonus, occupant, type);
		this.otherOccupant = otherOccupant;
	}

	public FamilyMember getOtherOccupant() {
		return otherOccupant;
	}

	// TODO 
	/*@Override
	public void occupy(FamilyMember famMember) throws NotOccupableException{
		
		if (occupant == null) {
			this.occupant = famMember;
		}
		else switch () {
		case false:
		{
			return false;
		}
		case true:
		{
			if (this.otherOccupant==null){
				this.otherOccupant=player;
				return true;
			}
			else return false;
		}	

		default: return false;
		} */
		
	}
