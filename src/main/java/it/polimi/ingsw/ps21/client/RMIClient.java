package it.polimi.ingsw.ps21.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.ps21.controller.CostChoice;
import it.polimi.ingsw.ps21.controller.VaticanChoice;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.Connection;
import it.polimi.ingsw.ps21.view.RMIConnection;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;
import it.polimi.ingsw.ps21.view.RMIConnectionInterface;
import it.polimi.ingsw.ps21.view.RMIMessageBuffer;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface{
	
	private transient Registry serverRegistry;
	private transient RMIConnectionInterface connection = null;
	private transient UserInterface ui;
	public boolean connected = false;
	private String username;
	
	public RMIClient(String username, UserInterface ui, int chosenRules) throws RemoteException, NotBoundException{
		this.username = username;
		this.ui = ui;
		serverRegistry = LocateRegistry.getRegistry("localhost", 5000);
		RMIConnectionCreator connectionService = (RMIConnectionCreator) serverRegistry.lookup("RMIConnectionCreator");
	   	connection = connectionService.getNewConnection(username, chosenRules);
		connection.setClient((RMIClientInterface) this); 
		connected = true;
	}
	
	public void start(){ 
		 if (connected) ui.showInfo("Connected to RMI Server");
		
		
	}
		/*try {
			while(true){
			this.wait(30);
			ui.showInfo("Connection OK");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			ui.showInfo("Awaked from interrupt");
		}*/
	
	public boolean isConnected(){
		return connected;
	}

	@Override
	public void receiveMessage(String string) throws RemoteException {
		ui.showInfo(string);
	}	
	

	@Override
	public int setCost(ArrayList<ImmProperties> costsChoices) throws RemoteException {
		return ui.reqCostChoice(costsChoices);
	}

	@Override
	public boolean vaticanChoice() throws RemoteException {
		return ui.reqVaticanChoice();
	}

	@Override
	public ImmProperties[] reqPrivileges(int number) {
		return ui.reqPrivileges(number);
	
	}

	@Override
	public void setId(PlayerColor id) throws RemoteException {
		ui.setID(id);
	}

	@Override
	public void notifyMatchStarted() throws RemoteException {
		ui.playMatch();
	}

	@Override
	public String sendName() throws RemoteException {
		return this.username;
	}

	/*@Override
	public String getString() throws RemoteException {
		return "Not implemented yet";
	}*/

}
