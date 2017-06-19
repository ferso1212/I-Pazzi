package it.polimi.ingsw.ps21.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.ps21.view.Connection;
import it.polimi.ingsw.ps21.view.UserHandler;

public class ConnectionsAcceptor {
	protected final static int TIMEOUT=60; //the seconds that the server will wait once 2 players joined
	protected final static int MAX_PLAYERS_NUM=4;
	//The match is created if the max number of players is reached or if the timeout expires.
	
	protected ConcurrentLinkedQueue<Connection> stdConnections;
	protected ConcurrentLinkedQueue<Connection> advConnections;
	protected boolean acceptingConnections;
	protected ArrayList<String> names;
	protected ConcurrentHashMap<String, UserHandler> playingUsers;
	
	public ConnectionsAcceptor(ConcurrentLinkedQueue<Connection> stdConnections, ConcurrentLinkedQueue<Connection> advConnections, ArrayList<String> names, ConcurrentHashMap<String, UserHandler> playingUsers)
	{
		this.acceptingConnections=true;
		this.stdConnections=stdConnections;
		this.advConnections=advConnections;
		this.names=names;
		this.playingUsers=playingUsers;
	}
	
}
