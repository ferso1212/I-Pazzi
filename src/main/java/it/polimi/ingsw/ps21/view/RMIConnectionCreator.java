package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIConnectionCreator extends Remote {
	 
	public abstract RMIConnectionInterface getNewConnection(boolean wantsNewMatch) throws RemoteException;
	
	public abstract void setupConnection(RMIConnectionInterface connection) throws RemoteException;

}
