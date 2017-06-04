package it.polimi.ingsw.ps21.view;

import java.net.Socket;

public class SocketConnection extends Connection{
	private Socket socket;
	

	public SocketConnection(String name, Socket socket) {
		super(name);
		this.socket = socket;
	}
	
	

}
