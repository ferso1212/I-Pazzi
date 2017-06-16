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
import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.CostChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.CostChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.ExtraActionChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.ExtraActionChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.GenericStringNetPacket;
import it.polimi.ingsw.ps21.client.MatchStartedNetPacket;
import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;
import it.polimi.ingsw.ps21.client.PlayerIdNetPacket;
import it.polimi.ingsw.ps21.client.PrivilegesChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.PrivilegesChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.StartInfoNetPacket;
import it.polimi.ingsw.ps21.client.VaticanChoiceRequestNetPacket;
import it.polimi.ingsw.ps21.client.VaticanChoiceResponseNetPacket;
import it.polimi.ingsw.ps21.client.ViewUpdateRequestNetPacket;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SocketConnection implements Connection{
	private final static Logger LOGGER = Logger.getLogger(SocketConnection.class.getName());
	private Socket socket;
	private String name;
	private boolean isAdvanced;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected;
	
	private int messageCounter;

	public SocketConnection(Socket socket) {
		
		this.socket = socket;
		this.messageCounter=1;
		try {
			out=new ObjectOutputStream(socket.getOutputStream());
			in= new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			out.reset();
			//in.reset();
			StartInfoNetPacket initialInfos= (StartInfoNetPacket)in.readObject();
			if(initialInfos.getChosenRules()==1) this.isAdvanced=false;
			else this.isAdvanced=true;
			this.name=initialInfos.getName();
		} catch (IOException e) {
			connected=false;
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, "Unable to read initial infos from the socket due to ClassNotFoundException", e);
		}
		
	}


	@Override
	public void remoteUpdate(MatchData match) {
		try{
		out.writeObject(new ViewUpdateRequestNetPacket(this.messageCounter, match));
		messageCounter++;
	} catch (IOException e) {
		LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
	}
		
	}

	private NetPacket requestAndAwaitResponse(NetPacket packetToSend)
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
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
			return null;
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, "Unable to parse received object due to ClassNotFound Exception", e);
			return null;
		}
	}
	
	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costs) {
		return ((CostChoiceResponseNetPacket)requestAndAwaitResponse(new CostChoiceRequestNetPacket(messageCounter, costs))).getChosen();
		
	}


	@Override
	public boolean reqVaticanChoice() {
		return ((VaticanChoiceResponseNetPacket)requestAndAwaitResponse(new VaticanChoiceRequestNetPacket(messageCounter))).supportsVatican();
	}


	@Override

	public ImmProperties[] reqPrivilegesChoice(int number) {
		return ((PrivilegesChoiceResponseNetPacket)requestAndAwaitResponse(new PrivilegesChoiceRequestNetPacket(this.messageCounter, number))).getChosenPrivileges();

	}



	@Override
	public void sendMessage(String mess) {
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
	
	public boolean isAdvanced() {
		return this.isAdvanced;
	}


	@Override
	public void matchStarted() {
		try {
			out.writeObject(new MatchStartedNetPacket(this.messageCounter));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send message to the remote client due to IOException", e);
		}
		
	}


	@Override
	public int reqExtraActionChoice(ActionData[] actions) {
		return ((ExtraActionChoiceResponseNetPacket)requestAndAwaitResponse(new ExtraActionChoiceRequestNetPacket(messageCounter, actions))).getChosen();
	}


	@Override
	public ActionData reqAction(){
		return ((ActionResponseNetPacket)requestAndAwaitResponse(new ActionRequestNetPacket(messageCounter))).getAction();
		
	}
	
	@Override
	public void setID(PlayerColor player)
	{
		try {
			out.writeObject(new PlayerIdNetPacket(this.messageCounter, player));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send ID to the remote client due to IOException", e);
		}
	}
		

}
