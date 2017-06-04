package it.polimi.ingsw.ps21.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ElapsedTimeCounterTest {
	private ElapsedTimeCounter counter;

	@Before
	public void setup()
	{
		counter=new ElapsedTimeCounter();
	}
	
	@Test
	public void test() {
		
	  
	}
	
	private boolean checkRunner()
	{
		counter.start();
		counter.startCounter();
		
	}

}
