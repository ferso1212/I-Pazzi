package it.polimi.ingsw.ps21.view;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.RMIClient;

public abstract interface Connection extends Remote {

	
	public abstract void sendMessage(String mess) throws RemoteException;
	
	public abstract String getName() throws RemoteException;
	
	public abstract void setClient(ClientConnection client) throws RemoteException;

	
}
