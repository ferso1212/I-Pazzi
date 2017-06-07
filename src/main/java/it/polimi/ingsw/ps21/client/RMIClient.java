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
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.Connection;
import it.polimi.ingsw.ps21.view.RMIConnection;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;
import it.polimi.ingsw.ps21.view.RMIConnectionInterface;
import it.polimi.ingsw.ps21.view.RMIMessageBuffer;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface{
	
	private Registry serverRegistry;
	private RMIConnectionInterface connection = null;
	private RMIMessageBuffer input;
	private RMIMessageBuffer output;
	private UserInterface ui;
	public boolean connected = false;
	
	public RMIClient(String username, UserInterface ui, int chosenRules) throws RemoteException, NotBoundException{
		this.ui = ui;
		serverRegistry = LocateRegistry.getRegistry("localhost", 5000);
		RMIConnectionCreator connectionService = (RMIConnectionCreator) serverRegistry.lookup("RMIConnectionCreator");
		connection = connectionService.getNewConnection(username, chosenRules);
		connection.setClient((RMIClientInterface) this); 
		connected = true;
		
	}
	
	public void start(){ 
		System.out.println("Actually RMIConnection is not fully implemented, write the message you want to send to server: (IF you want to end connection write END)");
		String message = ui.nextInput();
		while(message.compareTo("END")!=0){
			try {
				connection.receiveMessage(message);
				message = ui.nextInput();
			} catch (RemoteException e) {
				ui.showInfo("Network Error");
				message = "END";
			}
		}
		
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
	
	public boolean matchEnded(){
		return true;
	}

	@Override
	public void receiveMessage(String string) throws RemoteException {
		System.out.println(string);
		//ui.showInfo(string);
	}	
	

	@Override
	public void setCost(ImmProperties[] costschoices) throws RemoteException {
		return;
	}

	/*@Override
	public String getString() throws RemoteException {
		return "Not implemented yet";
	}*/

}
