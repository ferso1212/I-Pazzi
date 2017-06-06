package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.CouncilChoice;
import it.polimi.ingsw.ps21.controller.EffectChoice;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class UserHandler extends Observable implements Visitor, Runnable, Observer {
	private PlayerColor playerId;
	private Connection connection;
	private String name;
	

	public UserHandler(PlayerColor playerId, Connection connection) {
		super();
		this.playerId = playerId;
		this.connection=connection;
		try {
			this.name=this.connection.getName();
			this.connection.sendMessage(this.name + "'s UserHandler created.");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public PlayerColor getPlayerId() {
		return playerId;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Match && arg == null)	{
			Match matchState = (Match) o;
			MatchData newState;
			BoardData board = new BoardData(matchState.getBoard(), matchState.getDices()[0], matchState.getDices()[1], matchState.getDices()[2]);
		}	
	}
	
	

}
