package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIConnectionCreator extends Remote {
	
	public abstract RMIConnection getNewConnection(String connectionName);

}
