package it.polimi.ingsw.ps21.view;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.client.RMIClientInterface;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.WorkMessage;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
/**
 * Implementation of a connection between server and client using RMI protocol
 */
public class RMIConnection extends UnicastRemoteObject implements RMIConnectionInterface, Connection{
	
	private static final long serialVersionUID = 8434915145131336717L;

	private final static Logger LOGGER = Logger.getLogger(RMIConnection.class.getName());

	private String name;
	// Unused private Queue<String> input;
	// Unused private Queue<String> output;
	private transient RMIClientInterface client;
	public RMIConnection(String userName) throws RemoteException{
		name = userName;
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
	public int reqCostChoice(ArrayList<ImmProperties> costs) {
		try {
			return client.setCost(costs);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method setCosts()", e);
			return 0;
		}
	}


	@Override
	public boolean reqVaticanChoice() {
		try {
			return client.vaticanChoice();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method vaticanChoice", e);
			return false;
		}
	}


	@Override
	public ImmProperties[] reqPrivilegesChoice(int number) {
		try {
			return client.reqPrivileges(number);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reqPrivileges", e);
			return new ImmProperties[0];
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
	public int reqExtraActionChoice(ExtraActionData[] actions) {
		try {
			return client.reqExtraActionChoice(actions);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method for ExtraActionchoice, return default value");
			return 0;
		}
	}


	@Override
	public ActionData reqAction() {
		try {
			return client.actionRequest();
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method actionRequest");
			return new ActionData(ActionType.NULL, null,0 , null, 0);
		}
	}


	@Override
	public void remoteUpdate(MatchData match) {
		try 
		{ 
			client.updateMatch(match);
		
		} catch (RemoteException e){
			LOGGER.log(Level.SEVERE, "Error updating remote client infos", e );
		}
		
	}


	@Override
	public EffectSet reqEffectChoice(EffectSet[] possibleEffects) {
		EffectSet chosen;
		try {
			chosen = possibleEffects[client.reqEffectChoice(possibleEffects)];
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reqEffectChoice", e);
			chosen = possibleEffects[0]; // valore di ripiego
		}
		return chosen;
	}


	@Override
	public int reqWorkChoice(DevelopmentCard workCard) {
		try {
			return client.reqWorkChoice(workCard);
		} catch (RemoteException e) {
			LOGGER.log(Level.WARNING, "Error calling remote method reWorkChoice on client", e);
			return 0;
		}
		
		
		
	}

}
