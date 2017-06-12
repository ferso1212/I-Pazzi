package it.polimi.ingsw.ps21;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;

public class BoardTest {
	
	private int numberOfPlayer;
	private boolean isAdvanced;
	
	@Before
	public void setUp(){
		this.numberOfPlayer = 3;
		this.isAdvanced=true;
	}
	

	@Test
	public void test() {
		assert(checkBoardCreation());
	}


	private boolean checkBoardCreation() {
		try {
			Board testBoard = new Board(this.numberOfPlayer, this.isAdvanced);
			return true;
		} catch (BuildingDeckException e) {
			return false;
		}
		
	}

}
