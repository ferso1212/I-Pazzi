package it.polimi.ingsw.ps21.view;

import java.util.Observable;
import java.util.Timer;

import it.polimi.ingsw.ps21.controller.ActionTimeoutTask;


public class ActionTimer extends Observable implements Runnable {
	private ActionTimeoutTask timeoutTask;

	public ActionTimer(int timeout) {
		super();
		this.timeoutTask = new ActionTimeoutTask();
		new Timer().schedule(this.timeoutTask, (long)timeout);
	}

	@Override
	public void run() {
		while(!timeoutTask.isExpired());
				setChanged();
				notifyObservers();
	}

}
