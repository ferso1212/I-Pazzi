package it.polimi.ingsw.ps21;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps21.client.GUI.GUInterface;
import it.polimi.ingsw.ps21.controller.LeaderChoice;
import it.polimi.ingsw.ps21.controller.TileChoice;
import it.polimi.ingsw.ps21.model.actions.Action;
import it.polimi.ingsw.ps21.model.actions.LeaderChoiceAction;
import it.polimi.ingsw.ps21.model.actions.NotExecutableException;
import it.polimi.ingsw.ps21.model.actions.TileChoiceAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.board.NotOccupableException;
import it.polimi.ingsw.ps21.model.match.AdvancedMatch;
import it.polimi.ingsw.ps21.model.match.BuildingDeckException;
import it.polimi.ingsw.ps21.model.match.InvalidIDException;
import it.polimi.ingsw.ps21.model.match.Match;
import it.polimi.ingsw.ps21.model.match.RoundType;
import it.polimi.ingsw.ps21.model.match.SimpleMatch;
import it.polimi.ingsw.ps21.model.match.VaticanRoundException;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.InsufficientPropsException;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.RequirementNotMetException;
import it.polimi.ingsw.ps21.view.MatchData;


public class TestGUInterface {

	GUInterface testeGuInterface = new GUInterface();
	private PlayerColor validPlayers[] = { PlayerColor.BLUE, PlayerColor.RED, PlayerColor.GREEN };
	private AdvancedMatch testedAdvancedMatch;
	private final static Logger LOGGER = Logger.getLogger(TestGUInterface.class.getName());

	@Before
	public void setUp() {
		try {
			testedAdvancedMatch = new AdvancedMatch(validPlayers);
			// Setting default leaderCard
			while (testedAdvancedMatch.getRound() != RoundType.INITIAL_ROUND) {
				Player player = testedAdvancedMatch.getCurrentPlayer();
				if (testedAdvancedMatch.getRound() == RoundType.LEADER_ROUND) {
					LeaderChoice mess = new LeaderChoice(testedAdvancedMatch.getLeaderPossibilities(), player.getId(), 0);
					Action leaderAction = new LeaderChoiceAction(player.getId(), mess, 0);
					mess.setChosenCard(0);
					mess.setVisited();
					try {
						testedAdvancedMatch.doAction(leaderAction);
					} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException
							| VaticanRoundException e) {
						LOGGER.log(Level.WARNING, "Error executing test leader choice");
						fail("Error starting advancedMatch");
					}
				}
				if (testedAdvancedMatch.getRound() == RoundType.TILE_CHOICE) {
					TileChoice mess2 = new TileChoice(player.getId(), testedAdvancedMatch.getPossibleTiles(), 0);
					Action tileAction = new TileChoiceAction(player.getId(), mess2, 0);
					mess2.setChosen(0);
					mess2.setVisited();
					try {
						testedAdvancedMatch.doAction(tileAction);
					} catch (NotExecutableException | RequirementNotMetException | InsufficientPropsException
							| VaticanRoundException e) {
						LOGGER.log(Level.WARNING, "Error executing test tile choice");
						fail("Error starting advancedMatch");
					}

				}
				testedAdvancedMatch.setNextPlayer();
			}
		} catch (InvalidIDException | BuildingDeckException e) {
			fail("Error creating test match");
		}
		testeGuInterface.setID(PlayerColor.BLUE);
		testeGuInterface.setRules(true);
		ArrayList<Player> players = new ArrayList<>(testedAdvancedMatch.getPlayers());
		Player player1 = players.get(0);
		Player player2 = players.get(1);
		
		FamilyMember member1 = player1.getFamily().getMember(MembersColor.BLACK);
		member1.increaseValue(5);
		FamilyMember member2 = player2.getFamily().getMember(MembersColor.ORANGE);
		member2.increaseValue(5);
		
		((AdvancedPlayer)player2).getAdvMod().setReoccupyPlaces(true);
		
		try {
			testedAdvancedMatch.getBoard().getSingleWorkSpace(WorkType.HARVEST).occupy(player1, member1);
			testedAdvancedMatch.getBoard().getSingleWorkSpace(WorkType.HARVEST).occupy(player2, member2);
		} catch (NotOccupableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//@Test
	public void showInterface() {

		testeGuInterface.updateView(new MatchData(testedAdvancedMatch));
		testeGuInterface.makeAction(1);
		testeGuInterface.updateView(new MatchData(testedAdvancedMatch));
		testeGuInterface.makeAction(1);
		assert (true);

	}

}
