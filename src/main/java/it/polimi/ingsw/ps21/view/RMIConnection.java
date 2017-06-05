package it.polimi.ingsw.ps21.view;

public class RMIConnection implements Connection, Runnable {

	private String name;
	public RMIConnection(String inputName) {
		name = inputName;
	}

	@Override
	public void run() {
		
		
	}

	@Override
	public void sendMessage(String mess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
