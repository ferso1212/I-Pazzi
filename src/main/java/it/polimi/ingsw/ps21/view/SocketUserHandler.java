package it.polimi.ingsw.ps21.view;

import java.net.Socket;

public class SocketUserHandler extends UserHandler implements Runnable{
	private Socket socket;

	public SocketUserHandler(Socket socket) {
		super();
		this.socket = socket;
	}
	
	
}
