package it.polimi.ingsw.ps21.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public interface RMIClientInterface extends Remote {

	public void receiveMessage(String message) throws RemoteException;
	
	public int setCost(ArrayList<ImmProperties> costs) throws RemoteException;
	
	public boolean vaticanChoice()throws RemoteException;
	
	public ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues) throws RemoteException;
	
	public void setId(PlayerColor id) throws RemoteException;
	
	public void notifyMatchStarted() throws RemoteException;
	
	public String sendName() throws RemoteException;
	
	public ActionData actionRequest(int id)throws RemoteException;

	public int reqExtraActionChoice(ExtraActionData[] actions) throws RemoteException;

	public void updateMatch(MatchData match) throws RemoteException;

	public int reqEffectChoice(EffectSet[] possibleEffects) throws RemoteException;

	public int reqWorkChoice(DevelopmentCard workCard) throws RemoteException;

	public String reqName() throws RemoteException;
	
	public boolean reqRules() throws RemoteException;

	public void matchEnded(EndData data) throws RemoteException;

	public int reqLeaderChoice(LeaderCard[] choices) throws RemoteException;
}
