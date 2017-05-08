package it.polimi.ingsw.ps21;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.*;

public class GameServer {
	
	final private int TCP_PORT=12300;
	private int timeout; // Timeout in secondi prima di avviare una partita
	private Queue<Player> pendingPlayer;
	private Queue<Player> pendingAdvancedPlayer;
	private Queue<Socket> pendingSocket;
	
	public int getPortNumber(){
		return TCP_PORT;
		
	}
	
	public void addOnQueue(Player newPlayer, int type){
		
	}

}
