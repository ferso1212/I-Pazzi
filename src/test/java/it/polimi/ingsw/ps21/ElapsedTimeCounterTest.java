package it.polimi.ingsw.ps21;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.controller.ElapsedTimeCounter;

public class ElapsedTimeCounterTest {
	private ElapsedTimeCounter counter;

	@Before
	public void setup()
	{
		counter=new ElapsedTimeCounter();
		counter.start();
		counter.startCounter();
	}
	
	@Test
	public void test() {
		while(true)
		{
			System.out.println("Elapsed Time = " + counter.getElapsedTime());
		}
	  
	}

}
