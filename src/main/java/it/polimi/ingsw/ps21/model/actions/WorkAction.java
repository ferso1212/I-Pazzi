package it.polimi.ingsw.ps21.model.actions;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.board.SingleSpace;
import it.polimi.ingsw.ps21.model.board.Space;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.Effect;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;

public class WorkAction extends Action {
	
	private Space space;
	private FamilyMember famMember;
	
	public WorkAction(Match m, Player p, Space space, FamilyMember famMember) {
		super(m, p);
		this.space = space;
		this.famMember = famMember;
	}

	@Override
	public boolean isLegal() {
		if(space instanceof SingleSpace){
			if ((famMember.getValue() >= space.getDiceRequirement()) && (space.isOccupable(famMember) && (!famMember.isUsed()))){
				return true;
			}
		}
		else if((space instanceof MultipleProductionSpace)||(space instanceof MultipleHarvestSpace)){
			if (famMember.getValue() - 3 >= space.getRequirement()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean execute() {
		boolean controlOccupation = space.occupy(this.player);
		boolean controlEffect = false;
		ArrayList<DevelopmentCard> cardToActivate = null;
		switch (space.getType()) {
		case HARVEST:
		{
			if(space instanceof SingleHarvestSpace){
				cardToActivate = player.getWorkCards(famMember.getValue(), WorkType.HARVEST);
			}
			else if (space instanceof MultipleHarvestSpace){
				cardToActivate = player.getWorkCards(famMember.getValue() - 3, WorkType.HARVEST);
			}
			break;}
		case PRODUCTION:
		{
			if(space instanceof SingleProductionSpace){
				cardToActivate = player.getWorkCards(famMember.getValue(), WorkType.PRODUCTION);
			}
			else if (space instanceof MultipleProductionSpace){
				cardToActivate = player.getWorkCards(famMember.getValue() - 3, WorkType.PRODUCTION);
			}
			break;
		}
		case TOWER:
		{
			return false;
		}
		case COUNCIL:
		{
			return false;
		}
		default: cardToActivate = null;
		}
		int i;
		if (cardToActivate.size()==0){
			for(i=0; i<cardToActivate.size(); i++){
				Effect[] effetti = cardToActivate.get(i).getEffect();
				if ((effetti[0]==null) && (effetti[1]==null)){ //caso in cui non ci sono effetti permanenti
					controlEffect=true;
				}
				else if ((effetti[0]==null)){
					controlEffect=effetti[1].activate(player);
				}
				else if ((effetti[1]==null)){
					controlEffect=effetti[0].activate(player);
				}
				else if ((effetti[0]!=null) && (effetti[1]!=null)){
					controlEffect=true;//implementazione della scelta dell'effetto
				}
			}
		}
		else controlEffect=true;
		if(controlOccupation && controlEffect){
			return true;
		}
		return false;
		
	}
	
	
	
			

}
