package it.polimi.ingsw.ps21.view;

import java.util.Observable;
import java.util.Timer;

import it.polimi.ingsw.ps21.controller.TimeoutTask;

public class RoundTimer extends Observable implements Runnable {
	private TimeoutTask timeout;

	public RoundTimer(int timeout) {
		super();
		this.timeout = new TimeoutTask();
		new Timer().schedule(new TimeoutTask(), timeout);
	}

	@Override
	public void run() {
		while(!timeout.isExpired());
				setChanged();
				notifyObservers();
	}

}
