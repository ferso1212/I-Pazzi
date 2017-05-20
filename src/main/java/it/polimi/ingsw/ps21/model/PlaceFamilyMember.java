package it.polimi.ingsw.ps21.model;

public class PlaceFamilyMember extends Action {

	protected FamilyMember famMember;
	protected Space space;
	
	
	public PlaceFamilyMember(FamilyMember f, Space s){
		this.famMember=f;
		this.space=s;
	}
	
	@Override
	public boolean isLegal() {
		if(famMember.getValue() >= space.getRequirement()){
			return true;
		}
		return false;
	}


}
