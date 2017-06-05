package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface Connection extends Remote {

	
	public abstract void sendMessage(String mess) throws RemoteException;
	
	public abstract String getName() throws RemoteException;
	
	
}
