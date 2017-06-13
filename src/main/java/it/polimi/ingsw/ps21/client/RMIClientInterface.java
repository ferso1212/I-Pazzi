package it.polimi.ingsw.ps21.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;

public interface RMIClientInterface extends Remote {

	public void receiveMessage(String message) throws RemoteException;
	
	public int setCost(ArrayList<ImmProperties> costs) throws RemoteException;
	
	public boolean vaticanChoice()throws RemoteException;
	
	public ImmProperties[] reqPrivileges(int number) throws RemoteException;
	
	public void setId(PlayerColor id) throws RemoteException;
	
	public void notifyMatchStarted() throws RemoteException;
	
	public String sendName() throws RemoteException;
	
	public ActionData[] actionRequest()throws RemoteException;

	public int reqExtraActionChoice(ActionData[] object) throws RemoteException;
	
}
