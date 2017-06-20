package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
	private ArrayList<Connection> connection;
	private ConcurrentHashMap<RMIConnectionInterface, RMIConnection> interfaceMap;

	public RMIConnectionAcceptor(ConcurrentLinkedQueue<Connection> connectionsQueue,
			ConcurrentLinkedQueue<Connection> advConnectionsQueue, ArrayList<String> names,
			ConcurrentHashMap<String, UserHandler> playingUsers) throws RemoteException {
		this.connectionsQueue = connectionsQueue;
		this.advConnectionsQueue = advConnectionsQueue;
		this.playingUsers = playingUsers;
		this.usedNames = names;
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
			RMIConnection newConnection = new RMIConnection(wantsNewConnection);
			RMIConnectionInterface rmiinterface = (RMIConnectionInterface) newConnection;
			interfaceMap.put(rmiinterface, newConnection);
			return rmiinterface;
		} else
			return null;
	}

	public boolean rejoinConnection(RMIConnectionInterface newConnection, String oldName) {
		synchronized (playingUsers) {
			if (playingUsers.containsKey(oldName) && interfaceMap.containsKey(newConnection)) {
				playingUsers.get(oldName).setConnection(interfaceMap.get(newConnection));
				interfaceMap.remove(newConnection);
				return true;
			} else
				return false;
		}
	}

	public synchronized void setupConnection(RMIConnectionInterface connection) throws RemoteException {
		if (interfaceMap.containsKey(connection)) {
			RMIConnection unsettedConnection = interfaceMap.get(connection);
			try {
				if (unsettedConnection.wantsNewMatch()) {
					String name = unsettedConnection.reqName();
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
					interfaceMap.remove(connection);
				} else {
					String name = unsettedConnection.reqName();
					boolean usedName;
					do {
						synchronized (playingUsers) {
							usedName = playingUsers.containsKey(name);
						}
						if (!usedName)
							name = unsettedConnection.reqName();
					} while (!usedName);
					playingUsers.get(name).setConnection(unsettedConnection);
					interfaceMap.remove(connection);
				}
			} catch (RemoteException e) {
				LOGGER.log(Level.SEVERE, "Error in new connection setup, removing unsetted connection", e);
				interfaceMap.remove(connection);
			}

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
