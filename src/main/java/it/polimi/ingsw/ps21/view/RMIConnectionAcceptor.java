package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIConnectionAcceptor extends UnicastRemoteObject implements RMIConnectionCreator, Runnable {

	private static final long serialVersionUID = 3367187689111437262L;
	private final Logger LOGGER = Logger.getLogger(RMIConnectionAcceptor.class.getName());
	transient Registry registry;
	private ConcurrentLinkedQueue<Connection> connectionsQueue;
	private ConcurrentLinkedQueue<Connection> advConnectionsQueue;
	ArrayList<RMIConnection> connections = new ArrayList<>();
	private ArrayList<String> usedNames;
	private ConcurrentHashMap<String, UserHandler> playingUsers;
	private HashMap<Integer, RMIConnection> connectionsMap;
	private int interfaceCounter;

	public RMIConnectionAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue,
			ConcurrentLinkedQueue<Connection> advConnectionsQueue, ArrayList<String> names,
			ConcurrentHashMap<String, UserHandler> playingUsers) throws RemoteException {
		this.connectionsQueue = connectionsQueue;
		this.advConnectionsQueue = advConnectionsQueue;
		this.playingUsers = playingUsers;
		this.usedNames = names;
		this.interfaceCounter=0;
		this.connectionsMap=new HashMap<>();
		
	}

	@Override
	public void run() {
		while (connections.size() < 128)
			;
		System.out.println("Number of connections expired");
	}

	@Override
	public synchronized RMIConnectionInterface getNewConnection(boolean wantsNewConnection) throws RemoteException {
		if (connections.size() < 128) {
			interfaceCounter++;
			RMIConnection newConnection = new RMIConnection(wantsNewConnection, interfaceCounter);
			RMIConnectionInterface rmiinterface = (RMIConnectionInterface) newConnection;
			this.connectionsMap.put(interfaceCounter, newConnection);
			return rmiinterface;
		} else
			return null;
	}

	public boolean rejoinConnection(RMIConnectionInterface newConnection, String oldName) {
		synchronized (playingUsers) {
			if (playingUsers.containsKey(oldName)) {
				try {
					playingUsers.get(oldName).setConnection(connectionsMap.get(newConnection.getId()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			} else
				return false;
		}
	}

	public synchronized void setupConnection(RMIConnectionInterface connection) throws RemoteException {
			int value=connection.getId();
			if(!connectionsMap.containsKey(value)) return;
			try {
				RMIConnection unsettedConnection = this.connectionsMap.get(connection.getId());
				if (unsettedConnection.wantsNewMatch()) {
					String name;
					boolean usedName;
					do {
						name = unsettedConnection.reqName();
						synchronized (usedNames) {
							usedName = usedNames.contains(name);
							if (!usedName)
								usedNames.add(name);
						}
						if (usedName)
							unsettedConnection.sendMessage("Already taken name, please choice another one: ");
					} while (usedName);
					boolean rules = unsettedConnection.reqWantsAdvRules();
					addConnectionToQueue(rules, unsettedConnection);
					
				} else {
					String name;
					boolean usedName;
					do {
						name = unsettedConnection.reqName();
						synchronized (playingUsers) {
							usedName = playingUsers.containsKey(name);
						}							
					} while (!usedName);
					playingUsers.get(name).setConnection(unsettedConnection);
					
				}
			} catch (RemoteException e) {
				LOGGER.log(Level.SEVERE, "Error in new connection setup, removing unsetted connection", e);
				
			}

	}

	private void addConnectionToQueue(boolean advancedRules, RMIConnection newConnection) {
		if (advancedRules == false) {
			synchronized (connectionsQueue) {
				this.connectionsQueue.add(newConnection);
				connections.add(newConnection);
				System.out.println("\n" + newConnection.getName()
						+ "'s inbound connection added to the standard lobby in position " + connectionsQueue.size());
			}
		}

		else {
			synchronized (advConnectionsQueue) {
				this.advConnectionsQueue.add(newConnection);
				connections.add(newConnection);
				System.out.println("\n" + newConnection.getName()
						+ "'s inbound connection added to the advanced lobby in position "
						+ advConnectionsQueue.size());
			}
		}
	}
}
