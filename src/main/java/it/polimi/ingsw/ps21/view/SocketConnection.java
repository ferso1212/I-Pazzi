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

import it.polimi.ingsw.ps21.client.ClientConnection;
import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;
import it.polimi.ingsw.ps21.controller.BoardData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.match.MatchFactory;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SocketConnection implements Connection{
	private final static Logger LOGGER = Logger.getLogger(SocketConnection.class.getName());
	private Socket socket;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean connected;
	private String name;
	private int messageCounter;

	public SocketConnection(String name, Socket socket) {
		
		this.socket = socket;
		this.messageCounter=0;
		try {
			out=new ObjectOutputStream(socket.getOutputStream());
			in= new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			connected=false;
		}
		this.name=name;
	}


	

	@Override
	public String getName() {
		return this.name;
	}




	@Override
	public void remoteUpdate(MatchData match, BoardData board, PlayerData[] players) {
		try{
		out.writeObject(new NetPacket(PacketType.GENERIC_STRING, match, this.messageCounter, board, players));
		messageCounter++;
	} catch (IOException e) {
		LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
	}
		
	}

	private Object requestAndAwaitResponse(PacketType requestType, Object o)
	{
		NetPacket output=new NetPacket(requestType, o, this.messageCounter);
		try {
			out.writeObject(output);
			messageCounter++;
			NetPacket receivedPacket= (NetPacket) in.readObject();
			while(receivedPacket.getType()!=requestType)
			{
				if(receivedPacket.getType()==PacketType.CHAT_MESSAGE);//TODO: chat protocol
			}
			return receivedPacket.getObject();
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
			return 0;
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, "Unable to parse received object due to ClassNotFound Exception", e);
			return 0;
		}
	}
	
	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costs) {
		return (int)requestAndAwaitResponse(PacketType.COST_CHOICE, costs);
		
	}


	@Override
	public boolean reqVaticanChoice() {
		return (boolean)requestAndAwaitResponse(PacketType.VATICAN_CHOICE, null);
	}


	@Override
	public ImmProperties[] reqPrivilegesChoice(int number) {
		return (ImmProperties[])requestAndAwaitResponse(PacketType.PRIVILEGES_CHOICE, number);
	}



	@Override
	public void sendMessage(String mess) {
		try {
			out.writeObject(new NetPacket(PacketType.GENERIC_STRING, mess, this.messageCounter));
			messageCounter++;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
		}
		
		
	}




	@Override
	public void setID(PlayerColor player) {
		requestAndAwaitResponse(PacketType.ID, player);
		
	}
	
		

}
