package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleTowerSpace extends Space {

	protected DevelopmentCard card;
	protected FamilyMember occupant;
	protected static final ImmProperties REOCCUPY_TOWER_COST = new ImmProperties(3);

	public SingleTowerSpace() {
		super(0, new ImmProperties(0));
		this.card = null;
		this.occupant=null;
	}
	
	public SingleTowerSpace(int diceRequirement, ImmProperties instantBonus) {
		super(diceRequirement, instantBonus);
		this.card = null;
		this.occupant=null;
	}

	public DevelopmentCard getCard() {
		return card;
	}

	public void setCard(DevelopmentCard card) {
		this.card = card;
	}
	
	public void reset(){
		this.occupant=null;
		this.card=null;
	}

	public FamilyMember getOccupant() {
		return occupant;
	}
	
	public boolean isOccupable(Player player, FamilyMember member) {
		if (occupant == null) {
			return true;
		}
		return false;
	}
	
	public void occupy(Player player, FamilyMember member) throws NotOccupableException{
		if (this.occupant==null){
			this.occupant = member;
			member.setUsed(true);
		} else throw new NotOccupableException();
	}



}
