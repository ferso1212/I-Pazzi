package it.polimi.ingsw.ps21.view;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RMIConnectionAcceptor {

	Registry registry;
	RMIMessageBuffer output; // It is saved in input for the client
	RMIMessageBuffer input;
	ConcurrentLinkedQueue<Connection> connectionsQueue;
	Map<String, RMIConnection> connectedRMI;
	
	public RMIConnectionAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue) throws RemoteException {
		this.connectionsQueue = connectionsQueue;
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		
		output = (RMIMessageBuffer) UnicastRemoteObject.exportObject(new RMIMessageBuffer(), 0);
		input = (RMIMessageBuffer) UnicastRemoteObject.exportObject(new RMIMessageBuffer(), 0);
		registry = LocateRegistry.getRegistry();
		registry.rebind("ServerInput", input);
		registry.rebind("ServerOutput", output);
	}


	@Override
	public void run() {
		while (true){
		output.write("Connected to server RMI");
		output.write("Insert your nickname please:");
		String inputName = input.nextLine();
		RMIConnection newConnection;
		while (!(connectedRMI.containsKey(inputName))){
			while(inputName.compareTo("")!=0)
				inputName = input.nextLine();
		}
			newConnection = new RMIConnection(inputName);
			connectedRMI.put(inputName, newConnection);
			connectionsQueue.add(newConnection);
		}
		
	}
}

