package it.polimi.ingsw.ps21.client;

public abstract interface ClientConnection {
	
	public abstract void receiveMessage(String string) ;
	
	public abstract String getString();


}
