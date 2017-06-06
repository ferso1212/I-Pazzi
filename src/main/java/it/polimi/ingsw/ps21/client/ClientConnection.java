package it.polimi.ingsw.ps21.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ClientConnection extends Remote {
	
	public abstract void send(String string) throws RemoteException;
	
	public abstract String getString()throws RemoteException;


}
