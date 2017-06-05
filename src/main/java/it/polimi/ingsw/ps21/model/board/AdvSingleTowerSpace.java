package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class AdvSingleTowerSpace extends SingleTowerSpace {
	
	private FamilyMember otherOccupant;

	public AdvSingleTowerSpace(int diceRequirement, ImmProperties instantBonus) {
		super(diceRequirement, instantBonus);
		this.otherOccupant = null;
	}

	public FamilyMember getOtherOccupant() {
		return otherOccupant;
	}
	
	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if (this.occupant == null) {
			return true;
		} else if (((AdvancedPlayer) player).getAdvMod().canReoccupyPlaces()) {
			if (this.otherOccupant == null) {
				return true;
			} else
				return false;
		} else
			return false;
	}
	
	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException {

		if (this.occupant == null) {
			this.occupant = member;
		} else if ((((AdvancedPlayer) player).getAdvMod().canReoccupyPlaces()) && (this.otherOccupant == null)) {
			this.otherOccupant = member;
		} else
			throw new NotOccupableException();
	}


}
