package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;

public class UserHandler implements Visitor{

	@Override
	public void visit(VaticanChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CostChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CouncilChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EffectChoice choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WorkMessage message) {
		
	}

	@Override
	public void visit(AcceptedAction message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RefusedAction message) {
		// TODO Auto-generated method stub
		
	}
	
	

}
