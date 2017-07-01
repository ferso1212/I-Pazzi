package it.polimi.ingsw.ps21.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;
import it.polimi.ingsw.ps21.view.RMIConnectionCreator;
import it.polimi.ingsw.ps21.view.RMIConnectionInterface;

/**
 * This class implements the client side of connection using RMI protocol.
 * It communicates directly with RMIConnectionInterface
 * @author gullit
 *
 */
public class RMIClient extends UnicastRemoteObject implements RMIClientInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8508268160293342621L;
	private transient Registry serverRegistry;
	private transient RMIConnectionInterface connection = null;
	private transient UserInterface ui;
	public boolean connected = false;
	private String username;
	private final String SERVER_HOSTNAME = "127.0.0.1"; 
	
	public RMIClient( UserInterface ui, String hostname, int port, boolean newMatch) throws RemoteException, NotBoundException{
		this.ui = ui;
		serverRegistry = LocateRegistry.getRegistry(SERVER_HOSTNAME, port);
		RMIConnectionCreator connectionService = (RMIConnectionCreator) serverRegistry.lookup("RMIConnectionCreator");
	   	connection = connectionService.getNewConnection(newMatch);	
		connection.setClient((RMIClientInterface) this); 
		connected = true;
		try {
			connectionService.setupConnection(connection);
		} catch (RemoteException e) {
			connected = false;
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
	
	public boolean isConnected(){
		return connected;
	}
	
	public void start()
	{
		System.out.println("\nClient ready to receive datas from server.");
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
	public ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues) {
		return ui.reqPrivileges(number, privilegesValues);
	
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

	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) throws RemoteException {
		return ui.reqExtraActionChoice(actions);
		
	}

	@Override
	public ActionData actionRequest(int id) throws RemoteException {
		return ui.makeAction(id);
	}

	@Override
	public void updateMatch(MatchData match) throws RemoteException {
		ui.updateView(match);		
	}

	@Override
	public int reqEffectChoice(EffectSet[] possibleEffects) throws RemoteException {
		return ui.reqEffectChoice(possibleEffects);
	}

	@Override
	public int reqWorkChoice(DevelopmentCard workCard) throws RemoteException {
		return ui.reqWorkChoice(workCard);
	}

	@Override
	public String reqName() throws RemoteException {
		return ui.reqName();
	}

	@Override
	public boolean reqRules() throws RemoteException {
		boolean wantsAdvRules= ui.reqIfWantsAdvancedRules();
		ui.showInfo("\nWaiting for match to start...");
		return wantsAdvRules;
	}

	@Override
	public void matchEnded(EndData data) throws RemoteException {
		connection.disconnect();
		ui.matchEnded(data);
	}

	@Override
	public int reqLeaderChoice(LeaderCard[] choices) throws RemoteException {
		return ui.chooseLeaderCard(choices);
	}

	@Override
	public int reqPersonalTileChoice(PersonalBonusTile[] choices) throws RemoteException {
		return ui.chooseTile(choices);
	}

	@Override
	public void setRules(boolean isAdvanced) throws RemoteException {
		ui.setRules(isAdvanced);
	}

}
