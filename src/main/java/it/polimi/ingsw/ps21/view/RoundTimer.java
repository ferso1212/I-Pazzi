package it.polimi.ingsw.ps21.view;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoundTimer extends Observable implements Runnable {
	private final int timeout;
	private static final Logger LOGGER = Logger.getLogger(RoundTimer.class.getName());

	public RoundTimer(int timeout) {
		super();
		this.timeout = timeout;
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				this.wait((long) timeout);
				setChanged();
				notifyObservers();
			}

		} catch (InterruptedException e) {
			LOGGER.log(Level.INFO, "Interrupted exception", e);
		}
	}

}
