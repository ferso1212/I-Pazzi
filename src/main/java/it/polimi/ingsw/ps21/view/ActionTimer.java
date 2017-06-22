package it.polimi.ingsw.ps21.view;

import java.util.Observable;
import java.util.Timer;

import it.polimi.ingsw.ps21.controller.ActionTimeoutTask;


public class ActionTimer extends Observable implements Runnable {
	private ActionTimeoutTask timeoutTask;
	private int timeout;
	private Timer timer;
	

	public ActionTimer(int timeout) {
		super();
		this.timeoutTask = new ActionTimeoutTask();
		this.timeout=timeout;
		timer = new Timer();
		
	}

	@Override
	public void run() {
		this.timeoutTask.reset();
		timer.schedule(this.timeoutTask, (long)timeout);
		while(!timeoutTask.isExpired());
		
				setChanged();
				notifyObservers();
	}
	
	public void restart()
	{
		this.timeoutTask.reset();
		
	}

}
