package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.rmi.Remote;

public abstract interface Connection extends Remote {

	
	
	public abstract void sendMessage(String mess);
	
	public abstract String getName();
	
	
}
