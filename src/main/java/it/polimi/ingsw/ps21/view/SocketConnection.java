package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.match.MatchFactory;

public class SocketConnection extends Connection{
	private final static Logger LOGGER = Logger.getLogger(SocketConnection.class.getName());
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private ObjectOutputStream objOut;
	private ObjectInputStream objIn;
	private boolean connected;

	public SocketConnection(String name, Socket socket) {
		super(name);
		this.socket = socket;
		try {
			out=new PrintWriter(socket.getOutputStream(), true);
			in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			connected=false;
		}
		
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
	
	public void sendObject(Object o)
	{
		try {
			objOut.writeObject(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Action reqUserAction()
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
	
	

}
