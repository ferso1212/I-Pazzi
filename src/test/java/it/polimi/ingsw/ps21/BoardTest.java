package it.polimi.ingsw.ps21;

import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.Board;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.board.Tower;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class BoardTest {

	private final static Logger LOGGER = Logger.getLogger(BoardTest.class.getSimpleName());
	private int numberOfPlayer;
	private Board advancedTest;
	private Board simpleTest;
	private Player simplePlayer;
	private Player advancedPlayer;

	@Before
	public void setUp() {
		this.numberOfPlayer = 4;
		simplePlayer = new Player(PlayerColor.BLUE, new PlayerProperties(0));
		advancedPlayer = new AdvancedPlayer(PlayerColor.BLUE, new PlayerProperties(0));
		try {
			advancedTest = new Board(numberOfPlayer, true);
			advancedTest.newSetBoard(1);
			simpleTest = new Board(numberOfPlayer, false);
			simpleTest.newSetBoard(1);
		} catch (BuildingDeckException e) {

		}
	}

	@Test
	public void test() {
		assert (checkBoardCreation());
	}

	@Test
	public void testSpaces() {
		assert (checkTowerSpace());
		assert (checkCouncil());
		assert(checkMarketSpace());
		assert(checkWorkSpaces());
	}

	private boolean checkMarketSpace() {
		for (MembersColor color: MembersColor.values()){
			FamilyMember testMember = simplePlayer.getFamily().getMember(color);
			testMember.increaseValue(1);
			try{ 
			if (!simpleTest.getMarketPlaces()[0].isOccupable(simplePlayer, testMember)) return false;
			else {
				simpleTest.getMarketPlaces()[0].occupy(simplePlayer, testMember);
				testMember.setUsed(false);
				simpleTest.getMarketPlaces()[0].reset();
			}
			if (!simpleTest.getMarketPlaces()[1].isOccupable(simplePlayer, testMember)) return false;
			else {
				simpleTest.getMarketPlaces()[1].occupy(simplePlayer, testMember);
				testMember.setUsed(false);
				simpleTest.getMarketPlaces()[1].reset();
			}
			if (!simpleTest.getMarketPlaces()[2].isOccupable(simplePlayer, testMember)) return false;
			else {
				simpleTest.getMarketPlaces()[2].occupy(simplePlayer, testMember);
				testMember.setUsed(false);
				simpleTest.getMarketPlaces()[2].reset();
			}
			if (!simpleTest.getMarketPlaces()[3].isOccupable(simplePlayer, testMember)) return false;
			else {
				simpleTest.getMarketPlaces()[3].occupy(simplePlayer, testMember);
				testMember.setUsed(false);
				simpleTest.getMarketPlaces()[3].reset();
			}
			} catch(NotOccupableException e){
				return false;
			}
			
			testMember = advancedPlayer.getFamily().getMember(color);
			testMember.increaseValue(1);
			try{ 
			if (!advancedTest.getMarketPlaces()[0].isOccupable(advancedPlayer, testMember)) return false;
			else {
				advancedTest.getMarketPlaces()[0].occupy(advancedPlayer, testMember);
				testMember.setUsed(false);
				advancedTest.getMarketPlaces()[0].reset();
			}
			if (!advancedTest.getMarketPlaces()[1].isOccupable(advancedPlayer, testMember)) return false;
			else {
				advancedTest.getMarketPlaces()[1].occupy(advancedPlayer, testMember);
				testMember.setUsed(false);
				advancedTest.getMarketPlaces()[1].reset();
			}
			if (!advancedTest.getMarketPlaces()[2].isOccupable(advancedPlayer, testMember)) return false;
			else {
				advancedTest.getMarketPlaces()[2].occupy(advancedPlayer, testMember);
				testMember.setUsed(false);
				advancedTest.getMarketPlaces()[2].reset();
			}
			if (!advancedTest.getMarketPlaces()[3].isOccupable(simplePlayer, testMember)) return false;
			else {
				advancedTest.getMarketPlaces()[3].occupy(simplePlayer, testMember);
				testMember.setUsed(false);
				advancedTest.getMarketPlaces()[3].reset();
			}
			} catch(NotOccupableException e){
				return false;
			}
		}
		return true;
	}

	private boolean checkCouncil() {
		for (MembersColor color: MembersColor.values()){
		FamilyMember testMember = simplePlayer.getFamily().getMember(color);
		if (!simpleTest.getCouncilPalace().isOccupable(simplePlayer, testMember))
		{
			if (color != MembersColor.NEUTRAL) return false;
		} else
			try {
				simpleTest.getCouncilPalace().occupy(simplePlayer, testMember);
			} catch (NotOccupableException e) {
				return false;
			}
		testMember = advancedPlayer.getFamily().getMember(color);
		if (!advancedTest.getCouncilPalace().isOccupable(advancedPlayer, testMember))
		{
			if (color != MembersColor.NEUTRAL) return false;
		} else
			try {
				advancedTest.getCouncilPalace().occupy(advancedPlayer, testMember);
			} catch (NotOccupableException e) {
				return false;
			}
		}
		return true;
	}

	private boolean checkTowerSpace() {
		for (MembersColor color : MembersColor.values()) {
			for (Tower t : simpleTest.getTowers().values()) {
				for (int i = 0; i < t.FLOORS_NUM; i++) {
					try {
						FamilyMember testMember = simplePlayer.getFamily().getMember(color);
						testMember.setUsed(false);
						testMember.increaseValue(7);
						if (!t.getTowerSpace(i).isOccupable(simplePlayer, testMember))
							return false;
						t.getTowerSpace(i).occupy(simplePlayer, testMember);
						t.getTowerSpace(i).reset();
					} catch (IllegalArgumentException | NotOccupableException e) {
						return false;
					}
				}
			}
			for (Tower t : advancedTest.getTowers().values()) {
				for (int i = 0; i < Tower.FLOORS_NUM; i++) {
					try {
						FamilyMember testMember = advancedPlayer.getFamily().getMember(color);
						testMember.setUsed(false);
						testMember.increaseValue(7);
						if (!t.getTowerSpace(i).isOccupable(advancedPlayer, testMember))
							return false;
						t.getTowerSpace(i).occupy(advancedPlayer, testMember);
						t.getTowerSpace(i).reset();
					} catch (IllegalArgumentException | NotOccupableException e){
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean checkBoardCreation() {
		try {
			Board testBoard = new Board(this.numberOfPlayer, true);
			testBoard.newSetBoard(1);
			testBoard.newSetBoard(2);
			testBoard = new Board(numberOfPlayer, false);
			testBoard.newSetBoard(1);
			testBoard.newSetBoard(2);
			return true;
		} catch (BuildingDeckException e) {
			return false;
		}

	}
	
	private boolean checkWorkSpaces()
	{
		FamilyMember mem= advancedPlayer.getFamily().getMember(MembersColor.BLACK);
		mem.increaseValue(10);
		try {
			if(advancedTest.getSingleWorkSpace(WorkType.HARVEST).isOccupable(advancedPlayer, mem)){
			advancedTest.getSingleWorkSpace(WorkType.HARVEST).occupy(advancedPlayer, mem);}
			
			mem.setUsed(false);
			if(advancedTest.getSingleWorkSpace(WorkType.PRODUCTION).isOccupable(advancedPlayer, mem)){
				advancedTest.getSingleWorkSpace(WorkType.PRODUCTION).occupy(advancedPlayer, mem);}

		} catch (NotOccupableException e) {
			return false;
		}
		return true;
	}

}
