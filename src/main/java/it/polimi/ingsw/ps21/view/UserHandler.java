package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class UserHandler implements Visitor, Runnable{
	private PlayerColor playerId;
	private Connection connection;
	private String name;
	

	public UserHandler(PlayerColor playerId, Connection connection) {
		super();
		this.playerId = playerId;
		this.connection=connection;
		this.name=this.connection.name;
	}

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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
