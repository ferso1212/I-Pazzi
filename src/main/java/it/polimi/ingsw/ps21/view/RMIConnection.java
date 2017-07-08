package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.client.RMIClientInterface;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
/**
 * Implementation of a connection between server and client using RMI protocol
 * This class communicates directly with RMIClientInterface.
 */
public class RMIConnection extends UnicastRemoteObject implements RMIConnectionInterface, Connection{
	
	private static final long serialVersionUID = 8434915145131336717L;

	private final static Logger LOGGER = Logger.getLogger(RMIConnection.class.getName());

	private String name;
	private transient RMIClientInterface client;
	private boolean newMatch;
	int id;
	
	public RMIConnection(boolean wantsNewConnection, int id) throws RemoteException{
		this.newMatch=wantsNewConnection;
		this.id=id;
		
	}


	@Override
	public void sendMessage(String mess) {
			try {
				client.receiveMessage(mess);
			} catch (RemoteException e) {
				LOGGER.log(Level.WARNING, "Error calling remote Method receiveMessage", e);			
			}
	}
	

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setClient(RMIClientInterface client) {
		this.client = client;
		sendMessage("Connected");
	}

	@Override
	public void receiveMessage(String mess) throws RemoteException {
		System.out.println(mess);
	}

	@Override
	public void disconnect() throws RemoteException {
		client=null;
	}


	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costs) throws DisconnectedException {
		try {
			return client.setCost(costs);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method setCosts()", e);
			throw new DisconnectedException();
		}
	}


	@Override
	public boolean reqVaticanChoice() throws DisconnectedException{
		try {
			return client.vaticanChoice();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method vaticanChoice", e);
			throw new DisconnectedException();
		}
	}


	@Override
	public ImmProperties[] reqPrivilegesChoice(int number, ImmProperties[] privilegesValues) throws DisconnectedException {
		try {
			return client.reqPrivileges(number, privilegesValues);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reqPrivileges", e);
			throw new DisconnectedException(); 
		}
	}


	@Override
	public void setID(PlayerColor player) {
		try {
			client.setId(player);
		} catch (RemoteException e) {
			LOGGER.log(Level.SEVERE, "Error setting id on remote conneciton", e);
		}
	}


	@Override
	public void matchStarted() {
		try {
			client.notifyMatchStarted();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error notifing match started on remote client", e);
		}
		
	}


	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) throws DisconnectedException{
		try {
			return client.reqExtraActionChoice(actions);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method for ExtraActionChoice, return default value", e);
			throw new DisconnectedException();
		}
	}


	@Override
	public ActionData reqAction(int id) throws DisconnectedException {
		try {
			return client.actionRequest(id);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method actionRequest", e);
			throw new DisconnectedException();
		}
	}


	@Override
	public void remoteUpdate(MatchData match) throws DisconnectedException{
		try 
		{ 
			client.updateMatch(match);
		
		} catch (RemoteException e){
			LOGGER.log(Level.SEVERE, "Error updating remote client infos", e );
			throw new DisconnectedException();
		}
		
	}


	@Override
	public EffectSet reqEffectChoice(EffectSet[] possibleEffects) throws DisconnectedException {
		EffectSet chosen;
		try {
			chosen = possibleEffects[client.reqEffectChoice(possibleEffects)];
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reqEffectChoice", e);
			throw new DisconnectedException();
		}
		return chosen;
	}


	@Override
	public int reqWorkChoice(DevelopmentCard workCard) throws DisconnectedException{
		try {
			return client.reqWorkChoice(workCard);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reWorkChoice on client", e);
			throw new DisconnectedException();
		}
		
		
		
	}


	public boolean wantsNewMatch() {
		return this.newMatch;
	}


	
	public String reqName() throws RemoteException {
		this.name = client.reqName();
		return name;
	}


	public boolean reqWantsAdvRules() throws RemoteException {
		return client.reqRules();
	}


	@Override
	public int getId() throws RemoteException {
		return this.id;
	}


	@Override
	public void matchEnded(EndData data) {
		try {
			client.matchEnded(data);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error sending information on ended Match", e);
		}

	}


	@Override
	public String name() throws RemoteException {
		return this.getName();
	}


	@Override
	public int reqLeaderCardChoice(LeaderCard[] choices) {
		try {
			return client.reqLeaderChoice(choices);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error requesting leader card choice on client, returning default value", e);
			return 0;
		}
	}


	@Override
	public int reqPersonalTileChoice(PersonalBonusTile[] choices) {
		try {
			return client.reqPersonalTileChoice(choices);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error requesting tile choice on client, returning default value", e);
			return 0;
		}
	}


	@Override
	public void setRules(boolean isAdvanced) {
		try {
			client.setRules(isAdvanced);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error setting rules of match on client side", e);
		}
	}


	@Override
	public boolean isConnected() {
		try {
			return client.testConnection();
		} catch (RemoteException e) {
			return false;
		}
	}


	@Override
	public int reqCardChoice(DevelopmentCard[] possibleChoices) throws DisconnectedException {
		try {
			return client.reqCardChoice(possibleChoices);
		} catch (RemoteException e) {
			throw new DisconnectedException();
		}
	}


	@Override
	public int chooseNumberOfServants(int max) {
		try {
			return client.reqNumberOfServants(max);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reqNumberOfServants, returning default message", e);
			return 0;
		}
		
	}
	
	

}
