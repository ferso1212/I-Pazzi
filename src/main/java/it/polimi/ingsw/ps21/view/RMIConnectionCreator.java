package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIConnectionCreator extends Remote {
	 
	public abstract RMIConnectionInterface getNewConnection(String connectionName, int chosenRules) throws RemoteException;

}
