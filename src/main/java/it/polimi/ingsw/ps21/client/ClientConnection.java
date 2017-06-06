package it.polimi.ingsw.ps21.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ClientConnection {
	
	public abstract void send(String string) ;
	
	public abstract String getString();


}
