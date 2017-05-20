package it.polimi.ingsw.ps21.model;

/**
 * This class is used to allow the player to take a development card from the board
 * and add it to his cards. 
 **/
public class DevelopmentAction extends PlaceFamilyMember {
	
	private SingleTowerSpace space;

	public DevelopmentAction(FamilyMember f, SingleTowerSpace s){
		this.famMember=f;
		this.space=s;
	}
	
	/**Returns a boolean that specifies if player has got the necessary resources to take a specific development card and if the family member has the necessary value to occupies the relative space on the tower. 
	 * 
	 * @return boolean indicating whether the action is legal or not.
	 */
	@Override
	public boolean isLegal() {
		if ((player.checkRequirements(space.getcard().getRequirement())) && (famMember.getValue() >= space.getRequirement())){
			return true;
		}
		return false;
	}
	
	/**Returns a boolean that specifies if the card has been correctly assigned to the player. 
	 * 
	 * @return boolean indicating if the action has taken place correctly.
	 */
	@Override
	public boolean execute() {
		boolean control;
		control = player.addCard(space.getcard());
		
	}
	
	
	

}
