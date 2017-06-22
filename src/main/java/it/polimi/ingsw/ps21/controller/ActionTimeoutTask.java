package it.polimi.ingsw.ps21.controller;

import java.util.TimerTask;

import it.polimi.ingsw.ps21.view.UserHandler;

public class ActionTimeoutTask extends TimerTask{

		private boolean timeoutExpired;
	
		
		public ActionTimeoutTask(){
			
			this.timeoutExpired=false;
		}

		@Override
		public void run() {
			  timeoutExpired= true;
			 System.out.println("\nAction timeout expired." );
			}
		
		public boolean isExpired()
		{
			return timeoutExpired;
		}
		
		public void reset()
		{
			this.timeoutExpired=false;
		}
	
}
