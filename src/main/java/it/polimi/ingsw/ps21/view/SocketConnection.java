package it.polimi.ingsw.ps21.view;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.client.ActionRequestNetPacket;
import it.polimi.ingsw.ps21.client.ActionResponseNetPacket;
import it.polimi.ingsw.ps21.client.ChosenRulesNetPacket;
import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.CostChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.CostChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.EffectChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.EffectChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.ExtraActionChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.ExtraActionChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.GenericStringNetPacket;
import it.polimi.ingsw.ps21.client.InitNetPacket;
import it.polimi.ingsw.ps21.client.MatchStartedNetPacket;
import it.polimi.ingsw.ps21.client.NameRequestNetPacket;
import it.polimi.ingsw.ps21.client.NameResponseNetPacket;
import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;
import it.polimi.ingsw.ps21.client.PlayerIdNetPacket;
import it.polimi.ingsw.ps21.client.PrivilegesChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.PrivilegesChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.VaticanChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.VaticanChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.ViewUpdateRequestNetPacket;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.RequirementAndCost;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SocketConnection implements Connection{
	private final static Logger LOGGER = Logger.getLogger(SocketConnection.class.getName());
	private Socket socket;
	private String name;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected;
	protected boolean newMatch;
	private int messageCounter;

	public SocketConnection(Socket socket) {
		this.connected=true;
		this.socket = socket;
		this.messageCounter=1;
		try {
			out=new ObjectOutputStream(socket.getOutputStream());
			in= new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			out.reset();
			//in.reset();
			InitNetPacket choice= (InitNetPacket)in.readObject();
			if(choice.isNew()) newMatch=true;
			else newMatch=false;
			//if(chosenRules.getChosenRules()==1) this.isAdvanced=false;
			//else this.isAdvanced=true;
			//this.name=initialInfos.getName();
		} catch (IOException e) {
			connected=false;
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, "Unable to read initial infos from the socket due to ClassNotFoundException", e);
		}
		
	}


	@Override
	public void remoteUpdate(MatchData match) {
		if(!this.socket.isConnected()) return;
		try{
		out.writeObject(new ViewUpdateRequestNetPacket(this.messageCounter, match));
		messageCounter++;
	} catch (IOException e) {
		LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
		return;
	}
		
	}

	private NetPacket requestAndAwaitResponse(NetPacket packetToSend) throws IOException
	{
		try {
			out.writeObject(packetToSend);
			messageCounter++;
			NetPacket receivedPacket= (NetPacket) in.readObject();
			while(receivedPacket.getType()!=packetToSend.getType())
			{
				if(receivedPacket.getType()==PacketType.CHAT_MESSAGE);//TODO: chat protocol
			}
			return receivedPacket;
			
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, "Unable to parse received object due to ClassNotFound Exception", e);
			return null;
		}
	}
	
	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costs) throws DisconnectedException {
		
		try {
			return ((CostChoiceResponseNetPacket)requestAndAwaitResponse(new CostChoiceRequestNetPacket(messageCounter, costs))).getChosen();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request cost choice to the remote client due to IOException", e);
			throw new DisconnectedException();
		}
		
		
	}


	@Override
	public boolean reqVaticanChoice() throws DisconnectedException {

		try {
			return ((VaticanChoiceResponseNetPacket)requestAndAwaitResponse(new VaticanChoiceRequestNetPacket(messageCounter))).supportsVatican();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request vatican choice to the remote client due to IOException", e);
			throw new DisconnectedException();
		}
	}


	@Override

	public ImmProperties[] reqPrivilegesChoice(int number, ImmProperties choices[]) throws DisconnectedException {
		
		try {
			return ((PrivilegesChoiceResponseNetPacket)requestAndAwaitResponse(new PrivilegesChoiceRequestNetPacket(this.messageCounter, number, choices))).getChosenPrivileges();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request privileges choice to the remote client due to IOException", e);
			throw new DisconnectedException();
		}

	}



	@Override
	public void sendMessage(String mess) {
		if(!this.socket.isConnected()) return;
		try {

			out.writeObject(new GenericStringNetPacket(this.messageCounter, mess));
			messageCounter++;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send message to the remote client due to IOException", e);
		}
		
		
	}


	@Override
	public String getName() {
		return this.name;
	}
	

	@Override
	public void matchStarted(){
		try {
			out.writeObject(new MatchStartedNetPacket(this.messageCounter));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send message to the remote client due to IOException", e);
		}
		
	}


	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) throws DisconnectedException{
		try {
			return ((ExtraActionChoiceResponseNetPacket)requestAndAwaitResponse(new ExtraActionChoiceRequestNetPacket(messageCounter, actions))).getChosen();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request action to the remote client due to IOException", e);
			throw new DisconnectedException();
		}
	}


	@Override
	public ActionData reqAction(int id) throws DisconnectedException{
		
		try {
			return ((ActionResponseNetPacket)requestAndAwaitResponse(new ActionRequestNetPacket(messageCounter, id))).getAction();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request action to the remote client due to IOException", e);
			throw new DisconnectedException();
		}
		
	}
	
	@Override
	public void setID(PlayerColor player)
	{
		try {
			out.writeObject(new PlayerIdNetPacket(++this.messageCounter, player));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send ID to the remote client due to IOException", e);
		}
	}


	@Override
	public EffectSet reqEffectChoice(EffectSet[] possibleEffects) throws DisconnectedException{
		
		try {
			int chosen = ((EffectChoiceResponseNetPacket)requestAndAwaitResponse(new EffectChoiceRequestNetPacket(messageCounter, possibleEffects))).getChosen();
			return possibleEffects[chosen];
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request effect choice to the remote client due to IOException", e);
			throw new DisconnectedException();
		}
	}


	
	public String reqName() {
		try {
			String receivedName= ((NameResponseNetPacket)requestAndAwaitResponse(new NameRequestNetPacket(messageCounter))).getName();
			this.name=receivedName;
			return name;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request name to the remote client due to IOException", e);
			return null;
		}
	}
		
	public boolean isConnected(){
		return this.socket.isConnected();
	}


	@Override
	public int reqWorkChoice(DevelopmentCard message) throws DisconnectedException {
	
		try {
			return ((WorkChoiceResponseNetPacket)requestAndAwaitResponse(new WorkChoiceRequestNetPacket(messageCounter, message))).getChosen();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request work choice to the remote client due to IOException", e);
			throw new DisconnectedException();
		}
	}


	
	public boolean wantsNewMatch() {
		
		return this.newMatch;
	}


	
	public boolean reqWantsAdvRules(){
		try {
			return ((RulesChoiceResponseNetPacket)requestAndAwaitResponse(new RulesChoiceRequestNetPacket(messageCounter))).wantsAdvanced();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to request rules choice to the remote client due to IOException", e);
			return false;
		}
		
	}


	@Override
	public void matchEnded(EndData data) {
		try {
			out.writeObject(new MatchEndedNetPacket(++messageCounter, data));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send 'match ended' notification to the remote client due to IOException", e);
		}
		
	}
	

	
}
