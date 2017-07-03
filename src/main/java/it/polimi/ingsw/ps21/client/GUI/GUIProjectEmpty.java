package it.polimi.ingsw.ps21.client.GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import javax.swing.JSplitPane;
import javax.swing.JOptionPane;

import it.polimi.ingsw.ps21.client.UserInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.FamilyMemberData;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUIProjectEmpty implements UserInterface {

	private static final Logger LOGGER = Logger.getLogger(GUIProjectEmpty.class.getSimpleName());
	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private JPanel rightPanel;
	private ActionPanel actionPanel;
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane;
	private PlayerTile[] playerTiles;
	private double scaleFactor;
	private int numberOfPlayers;
	private int updateCounter = 0;
	private boolean isAdvanced;
	private int playerTile;
	private CouncilButton councilButton;
	private WorkActionButton singleHarvest;
	private WorkActionButton singleProduction;
	private WorkActionButton multipleHarvest;
	private WorkActionButton multipleProduction;
	private DevelopmentCardButton[][] developmentCards;
	private MarketButton[] marketButtons;
	private MemberLabel[][] familyMembersOnBoard;
	private PlayerColor playerID;
	private Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	private Semaphore waitingActions;
	private ActionData chosenAction = null;
	private int actionId;

	/**
	 * Create the application.
	 */
	public GUIProjectEmpty() {
		this.developmentCards = new DevelopmentCardButton[4][4];
		this.familyMembersOnBoard = new MemberLabel[4][4];

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException
				| UnsupportedLookAndFeelException e) {

		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainWindow = new JFrame();
				waitingActions = new Semaphore(0);
				mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainWindow.setContentPane(new BackgroundPanel());
				mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
				mainWindow.setVisible(true);
			}
		});
	}

	private class TowerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof DevelopmentCardButton) {
				DevelopmentCardButton source = (DevelopmentCardButton) e.getSource();
				DevelopmentCardType type = source.getTower();
				int space = source.getFloor();
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {
				if (waitingActions.availablePermits() == 0) {
					chosenAction = new ActionData(ActionType.TAKE_CARD, color, servants, type, space,
							actionId);
					waitingActions.release();
					}
				}
			}

		}

	}

	private class WorkListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(singleHarvest)) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {
					
					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.HARVEST, color, servants, DevelopmentCardType.TERRITORY, 0,
								actionId);
						waitingActions.release();
					}
				}
			} else if (e.getSource().equals(singleProduction)) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {
					
					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.PRODUCTION, color, servants, DevelopmentCardType.BUILDING,
								0, actionId);
						waitingActions.release();
					}
				}
			} else if (e.getSource().equals(multipleHarvest)) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {
					
					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.HARVEST, color, servants, DevelopmentCardType.TERRITORY, 1,
								servants);
						waitingActions.release();
					}
				}
			} else if (e.getSource().equals(multipleProduction)) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {
					
					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.PRODUCTION, color, servants, DevelopmentCardType.BUILDING,
								1, actionId);
						waitingActions.release();
					}
				}
			}

		}

	}

	private class CouncilListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(councilButton)) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color != null) {
					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.COUNCIL, color, servants, null, 0, actionId);
						waitingActions.release();
					}
				} else {
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				}
			}

		}

	}

	private class MarketListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < marketButtons.length; i++) {
				if (e.getSource().equals(marketButtons[i])) {
					int space = i;
					int servants = actionPanel.getChosenServants();
					MembersColor color = actionPanel.getChosenColor();
					if (color != null) {
						if (waitingActions.availablePermits() == 0) {
							chosenAction = new ActionData(ActionType.MARKET, color, servants, null, space, actionId);
							waitingActions.release();
						}
					} else
						JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
								"Invalid action", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

	}

	private void firstUpdate(MatchData matchInfo) {

		this.numberOfPlayers = matchInfo.getPlayers().length;


				JPanel contentPanel = new JPanel(new GridLayout(0,2));
				mainWindow.setContentPane(contentPanel);
				boardPanel = new BoardPanel((new File("")).getAbsolutePath().concat("/src/images/board2.jpg"),
						matchInfo.getBlackDice(), matchInfo.getWhiteDice(), matchInfo.getOrangeDice());
				boardPanel.setLayout(null);
				mainWindow.getContentPane().add(boardPanel);
				scaleFactor = boardPanel.getScaleFactor();

				for (int j = 0; j < 4; j++) {
					for (int i = 0; i < 4; i++) {
						developmentCards[i][j] = new DevelopmentCardButton(boardPanel.getScaleFactor(), i, j);
						developmentCards[i][j].addActionListener(new TowerListener());
						boardPanel.add(developmentCards[i][j]).setBounds(resize(615) + j * resize(970),
								resize(3050) - resize(820) * i, resize(470), resize(720));
					}
				}
				
				for (int j = 0; j < 4; j++) {
					for (int i = 0; i < 4; i++) {
						familyMembersOnBoard[i][j] = new MemberLabel(this.scaleFactor);
						boardPanel.add(familyMembersOnBoard[i][j]).setBounds(1190 + j * resize(955), 3585 - i * resize(885), resize(200), resize(200));
					}
				}

				// left general panel setting with grid layout 2 rows 1
				// column
				rightPanel = new JPanel();
				rightPanel.setOpaque(false);
				mainWindow.getContentPane().add(rightPanel);
				rightPanel.setLayout(new GridLayout(2, 0, 0, 0));

				// on the top of the left panel there is a split panel with
				// personal
				// board and personal bonus tile
				splitPane = new JSplitPane();
				rightPanel.add(splitPane);

				// setting a tabbedPane in the right space of splitPane
				tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				splitPane.setRightComponent(tabbedPane);

				// setting a panel with borderLayout in the splitPane
				actionPanel = new ActionPanel(matchInfo, playerID);
				rightPanel.add(actionPanel);

				mainWindow.setVisible(true);

				placeDevelopmentCards(matchInfo.getBoard().getCards());
				placeExcommunications(matchInfo.getBoard().getExcommunications());
				placeBoardFamilyMembers(matchInfo.getBoard().getTowerSpaces());
				setSpaces();
				setUpRightPanel(matchInfo.getPlayers());
				// mainWindow.pack();
			

	}

	private int resize(int originalSize) {
		return (int) (originalSize * this.scaleFactor);
	}

	private void placeDevelopmentCards(DevelopmentCard[][] cards) {
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (cards[i][j] != null) {
					this.developmentCards[i][j].update(cards[i][j].getName(), cards[i][j].toString());
				} else
					this.developmentCards[i][j].hideButton();
			}
		}
	}
	
	private void placeBoardFamilyMembers(FamilyMemberData[][] members) {
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (members[i][j].getOwnerId() != null) {
					this.familyMembersOnBoard[i][j].update(members[i][j].getOwnerId(), members[i][j].getColor());
				} else
					this.familyMembersOnBoard[i][j].hideMember();
			}
		}
	}

	private void placeExcommunications(Excommunication[] excommunications) {
		for (int i = 0; i < 3; i++) {
			ExcommunicationLabel excomCard = new ExcommunicationLabel(excommunications[i].getId(),
					boardPanel.getScaleFactor());
			boardPanel.add(excomCard).setBounds(resize(1180) + resize(380) * i, resize(4100), resize(400), resize(715));
		}
	}

	private void setSpaces() {
		// work space

		singleHarvest = new WorkActionButton(WorkType.PRODUCTION, true);
		boardPanel.add(singleProduction).setBounds(resize(545), resize(5485), resize(415), resize(415));
		singleHarvest.addActionListener(new WorkListener());

		singleProduction = new WorkActionButton(WorkType.HARVEST, true);
		boardPanel.add(singleHarvest).setBounds(resize(545), resize(6020), resize(415), resize(415));
		singleProduction.addActionListener(new WorkListener());

		if (numberOfPlayers > 2) {

			multipleHarvest = new WorkActionButton(WorkType.PRODUCTION, false);
			boardPanel.add(multipleProduction).setBounds(resize(1100), resize(5485), resize(900), resize(415));
			multipleHarvest.addActionListener(new WorkListener());

			multipleProduction = new WorkActionButton(WorkType.HARVEST, false);
			boardPanel.add(multipleHarvest).setBounds(resize(1100), resize(6020), resize(900), resize(415));
			multipleProduction.addActionListener(new WorkListener());
		}

		// market space
		if (numberOfPlayers < 4) {
			this.marketButtons = new MarketButton[2];
			marketButtons[0] = new MarketButton(1);
			boardPanel.add(marketButtons[0]).setBounds(resize(2860), resize(5380), resize(415), resize(415));
			marketButtons[0].addActionListener(new MarketListener());

			marketButtons[1] = new MarketButton(2);
			boardPanel.add(marketButtons[1]).setBounds(resize(3300), resize(5380), resize(415), resize(415));
			marketButtons[1].addActionListener(new MarketListener());
		}

		else {
			this.marketButtons = new MarketButton[4];

			marketButtons[0] = new MarketButton(1);
			boardPanel.add(marketButtons[0]).setBounds(resize(2860), resize(5380), resize(415), resize(415));
			marketButtons[0].addActionListener(new MarketListener());

			marketButtons[1] = new MarketButton(2);
			boardPanel.add(marketButtons[1]).setBounds(resize(3300), resize(5380), resize(415), resize(415));
			marketButtons[1].addActionListener(new MarketListener());

			marketButtons[2] = new MarketButton(3);
			boardPanel.add(marketButtons[2]).setBounds(resize(3730), resize(5500), resize(415), resize(415));
			marketButtons[2].addActionListener(new MarketListener());

			marketButtons[3] = new MarketButton(4);
			boardPanel.add(marketButtons[3]).setBounds(resize(4050), resize(5800), resize(415), resize(415));
			marketButtons[3].addActionListener(new MarketListener());
		}

		// council space
		councilButton = new CouncilButton();
		boardPanel.add(councilButton).setBounds(resize(2515), resize(3780), resize(1280), resize(510));
		councilButton.addActionListener(new CouncilListener());
	}

	private void setUpRightPanel(PlayerData[] playersInfo) {

		// setting prsonal bonus tile
		this.playerTiles = new PlayerTile[numberOfPlayers];
		TilePanel personalBonusTile = new TilePanel(Integer.toString(this.playerTile));
		splitPane.setLeftComponent(personalBonusTile);
		splitPane.setDividerLocation((int) (personalBonusTile.getTileImage().getWidth()
				* (screenDimension.getHeight() / 2) / personalBonusTile.getTileImage().getHeight()));

		for (int i = 0; i < numberOfPlayers; i++) {
			playerTiles[i] = new PlayerTile(playersInfo[i]);
			if (playersInfo[i].getId() == playerID)
				tabbedPane.insertTab("Your Tile", null, playerTiles[i], null, i);
			else
				tabbedPane.insertTab("Player " + playersInfo[i].getId(), null, playerTiles[i], null, i);

		}

	}

	private void update(MatchData matchInfo) {
		updateRightPanel(matchInfo.getPlayers());
		updateSpaces();
		placeDevelopmentCards(matchInfo.getBoard().getCards());
		placeExcommunications(matchInfo.getBoard().getExcommunications());
		placeBoardFamilyMembers(matchInfo.getBoard().getTowerSpaces());

	}

	private void updateRightPanel(PlayerData[] players) {
		for (int i = 0; i < playerTiles.length; i++) {
			PlayerTile currentPlayerTile = playerTiles[i];
			for (int j = 0; j < players.length; j++) {
				if (currentPlayerTile.getPlayerId() == players[j].getId()) {
					currentPlayerTile.updateCharactersAndVentures(players[j]);
				}
			}
		}

	}

	private void updateSpaces() {

	}

	@Override
	public void playMatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateView(MatchData match) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if (updateCounter == 0)
					firstUpdate(match);
				else
					update(match);
				updateCounter++;
				
			}
		});
	}

	@Override
	public ActionData makeAction(int id) {
		JOptionPane.showMessageDialog(mainWindow, "It's your turn!");
		this.actionId = id;
		try {
			waitingActions.acquire();
			if (chosenAction != null) {
				return chosenAction;
			}
			else return new ActionData(ActionType.NULL, MembersColor.NEUTRAL, 0, null, 0, id);
		} catch (InterruptedException e) {
			return new ActionData(ActionType.NULL, MembersColor.NEUTRAL, 0, null, 0, id);
		}
	}

	@Override
	public void showInfo(String message) {
		JOptionPane.showMessageDialog(mainWindow, "Message from Server: " + message, "Server Message",
				JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public boolean reqVaticanChoice() {
		boolean choice;
		Object choices[] = {"Yes", "No"};
		int chosen =  JOptionPane.showOptionDialog(mainWindow, "Do you want to support the church?", "Vatican Choice", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[1]);
		if (chosen == 0) return true;
		else return false;
	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {
		Object choices[] = new Object[effectChoice.length];
		for (int i = 0; i < effectChoice.length; i++) {
			choices[i] = effectChoice[i].toString();
		}
		String chosenEffect = (String) JOptionPane.showInputDialog(mainWindow, "Which effect do you want to activate?",
				"Choose Effect", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenEffect.compareTo((String) choices[j]) == 0) {
				break;
			}
		}
		return j;
	}

	@Override
	public int chooseLeaderCard(LeaderCard[] possibleChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void showMessage(AcceptedAction mess) {
		JOptionPane.showMessageDialog(mainWindow, mess, "Accepted Action", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void showMessage(RefusedAction mess) {
		JOptionPane.showMessageDialog(mainWindow, mess, "Refused Action", JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void setID(PlayerColor id) {
		this.playerID = id;
	}

	@Override
	public String nextInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void matchEnded(EndData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int reqWorkChoice(DevelopmentCard workCard) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String reqName() {
		String name = JOptionPane.showInputDialog(mainWindow, "Insert your name", JOptionPane.PLAIN_MESSAGE);
		return name;
	}

	@Override
	public boolean reqIfWantsAdvancedRules() {
		Object choices[] = { "Advanced Rules", "Standard Rules" };
		int chosenMethod = JOptionPane.showOptionDialog(mainWindow, "Which rules do you want to use?", "Choose Rules",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);
		if (chosenMethod == 0)
			this.isAdvanced = true;
		else {
			this.isAdvanced = false;
			this.playerTile = 0;
		}
		return this.isAdvanced;
	}

	@Override
	public int chooseTile(PersonalBonusTile[] possibilities) {
		Object choices[] = new Object[possibilities.length];
		for (int i = 0; i < possibilities.length; i++) {
			choices[i] = possibilities[i].toString();
		}
		String chosenTile = (String) JOptionPane.showInputDialog(mainWindow, "Which tile do you want to use?",
				"Choose Tile", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenTile.compareTo((String) choices[j]) == 0) {
				this.playerTile = possibilities[j].getID();
				break;
			}
		}
		return j;
	}

	@Override
	public void setRules(boolean isAdvanced) {
		this.isAdvanced = isAdvanced;
	}
	
	

}
