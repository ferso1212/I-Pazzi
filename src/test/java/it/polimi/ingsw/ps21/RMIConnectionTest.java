package it.polimi.ingsw.ps21;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.CLInterface;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.view.Connection;
import it.polimi.ingsw.ps21.view.RMIConnectionAcceptor;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;
import it.polimi.ingsw.ps21.view.UserHandler;

public class RMIConnectionTest {

	public final int PORT = 5000;
	Registry testRegistry;
	RMIConnectionCreator rmiserver;
	RMIClient rmiclient;
	ConcurrentLinkedQueue<Connection> connectionsQueue;
	ConcurrentLinkedQueue<Connection> advConnectionsQueue;
	@Before
	public void setUpClass(){
		try {
			// System.setSecurityManager(new SecurityManager());
			testRegistry = LocateRegistry.createRegistry(PORT);
			connectionsQueue =  new ConcurrentLinkedQueue<>();
			advConnectionsQueue =  new ConcurrentLinkedQueue<Connection>();
			ArrayList<String> names = new ArrayList<>();
			ConcurrentHashMap<String, UserHandler> userHandlers = new ConcurrentHashMap<>();
			rmiserver = new RMIConnectionAcceptor(connectionsQueue, advConnectionsQueue, names, userHandlers, new Semaphore(0), new Semaphore(0));
			testRegistry.rebind("RMIConnectionCreator", rmiserver);
			System.out.println("Test setted");
		} catch (RemoteException e) {
			e.printStackTrace();
			assert(false);
		}
	}
	

	private boolean checkConnection() {
		CLInterface testui = new CLInterface();
		try {
			RMIClient testclient = new RMIClient(testui, "127.0.0.1", 5000, true);
			if (connectionsQueue.size()!=1 && advConnectionsQueue.size()!=1) return false;
			if (testclient.isConnected()==true) return true;
			else return false;
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
