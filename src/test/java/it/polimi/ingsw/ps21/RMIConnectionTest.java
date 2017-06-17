package it.polimi.ingsw.ps21;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.CLInterface;
import it.polimi.ingsw.ps21.client.RMIClient;
import it.polimi.ingsw.ps21.view.Connection;
import it.polimi.ingsw.ps21.view.RMIConnectionAcceptor;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;

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
			rmiserver = new RMIConnectionAcceptor(connectionsQueue, advConnectionsQueue);
			testRegistry.rebind("RMIConnectionCreator", rmiserver);
			System.out.println("Test setted");
		} catch (RemoteException e) {
			e.printStackTrace();
			assert(false);
		}
	}
	
	@Test
	public void test() {
		assert(checkConnection());
	}

	private boolean checkConnection() {
		CLInterface testui = new CLInterface(1);
		try {
			RMIClient testclient = new RMIClient("testcase", testui, 1, PORT);
			if (connectionsQueue.size()!=1)return false;
			if (testclient.isConnected()==true) return true;
			else return false;
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}