package it.polimi.ingsw.ps21.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketConnection extends Connection{
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
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

}
