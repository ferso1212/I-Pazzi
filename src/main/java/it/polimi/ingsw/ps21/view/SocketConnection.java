package it.polimi.ingsw.ps21.view;

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
	private PrintWriter out;
	private BufferedReader in;
	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;
	private boolean connected;
	private String name;
	private int messageCounter;

	public SocketConnection(String name, Socket socket) {
		
		this.socket = socket;
		this.messageCounter=0;
		try {
			out=new PrintWriter(socket.getOutputStream(), true);
			in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			connected=false;
		}
		this.name=name;
	}


	@Override
	public void sendMessage(String mess) {
		out.println(mess);
		
	}
	
	public String getMessage()
	{
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public void sendAndWaitResponse(Object o)
	{
		try {
			objOut.writeObject(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public String getName() {
		return this.name;
	}




	@Override
	public void remoteUpdate(MatchData match, BoardData board, PlayerData[] players) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int reqChoice(ArrayList<ImmProperties> costs) {
		out.println(messageCounter + "_costChoice");
		try {
			objOut.writeObject(costs);
			return Integer.parseInt(in.readLine());
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to send choice request to the remote client due to IOException", e);
			return 1;
		}
	}


	@Override
	public boolean setVaticanChoice() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ImmProperties[] reqPrivileges(int number) {
		out.println("privilegesChoice");
		out.println(number);
	}


	@Override
	public void setID(PlayerColor player) {
		// TODO Auto-generated method stub
		
	}
	
	/*public Action reqUserAction()
	{
		//TODO request action
		try {
			return (Action)objIn.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	

}
