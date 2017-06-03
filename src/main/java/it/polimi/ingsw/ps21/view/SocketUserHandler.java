package it.polimi.ingsw.ps21.view;

import java.net.Socket;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class SocketUserHandler extends UserHandler implements Runnable{
	private Socket socket;

	public SocketUserHandler(PlayerColor playerId, Socket socket) {
		super(playerId);
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

	
	
	
}
