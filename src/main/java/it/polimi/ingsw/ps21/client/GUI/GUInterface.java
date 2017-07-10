package it.polimi.ingsw.ps21.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSplitPane;
import javax.swing.JOptionPane;

import it.polimi.ingsw.ps21.client.UserInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.BoardData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;
import it.polimi.ingsw.ps21.view.FamilyMemberData;
import it.polimi.ingsw.ps21.view.MatchData;
import it.polimi.ingsw.ps21.view.PlayerData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class GUInterface implements UserInterface {

	private static final Logger LOGGER = Logger.getLogger(GUInterface.class.getSimpleName());
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
	private MultipleSpacePanel councilPanel;
	private WorkActionButton singleHarvest;
	private WorkActionButton singleProduction;
	private MultipleSpacePanel multipleHarvest;
	private MultipleSpacePanel multipleProduction;
	private DevelopmentCardButton[][] developmentCards;
	private MarketButton[] marketButtons;
	private JLabel[][] familyMembersOnBoard;
	private PlayerColor playerID;
	private Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	private Semaphore waitingActions;
	private ActionData chosenAction = null;
	private int actionId;
	private int playerPosition;

	/**
	 * Create the application.
	 */
	public GUInterface() {
		this.developmentCards = new DevelopmentCardButton[4][4];
		this.familyMembersOnBoard = new JLabel[4][4];

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
						chosenAction = new ActionData(ActionType.TAKE_CARD, color, servants, type, space, actionId);
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
						chosenAction = new ActionData(ActionType.HARVEST, color, servants,
								DevelopmentCardType.TERRITORY, 1, actionId);
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
						chosenAction = new ActionData(ActionType.PRODUCTION, color, servants,
								DevelopmentCardType.BUILDING, 1, actionId);
						waitingActions.release();
					}
				}
			} else if (e.getSource().equals(multipleHarvest.getSpaceButton())) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {

					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.HARVEST, color, servants,
								DevelopmentCardType.TERRITORY, 2, actionId);
						waitingActions.release();
					}
				}
			} else if (e.getSource().equals(multipleProduction.getSpaceButton())) {
				int servants = actionPanel.getChosenServants();
				MembersColor color = actionPanel.getChosenColor();
				if (color == null)
					JOptionPane.showMessageDialog(mainWindow, "You have to choose before a Family Member ",
							"Invalid action", JOptionPane.WARNING_MESSAGE);
				else {

					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.PRODUCTION, color, servants,
								DevelopmentCardType.BUILDING, 2, actionId);
						waitingActions.release();
					}
				}
			}

		}

	}

	private class CouncilListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(councilPanel.getSpaceButton())) {
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

	private class LeaderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LeaderCardButton[] possibleLeaders = actionPanel.getLeaderPanel().getLeadersButtons();
			for (int i = 0; i < possibleLeaders.length; i++) {
				if (e.getSource().equals(possibleLeaders[i])) {
					if (waitingActions.availablePermits() == 0) {
						chosenAction = new ActionData(ActionType.PLAY_LEADERCARD, null, 0, null, i, actionId);
						waitingActions.release();
					}
				}
			}
		}

	}

	private class NullListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(actionPanel.getNullButton())) {
				if (waitingActions.availablePermits() == 0) {
					chosenAction = new ActionData(ActionType.NULL, null, 0, null, 0, actionId);
					waitingActions.release();
				}
			}

		}
	}

	private void firstUpdate(MatchData matchInfo) {

		this.numberOfPlayers = matchInfo.getPlayers().length;
		JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
		contentPanel.setBackground(new Color(239, 220, 134));
		mainWindow.setContentPane(contentPanel);
		boardPanel = new BoardPanel((new File("")).getAbsolutePath().concat("/src/images/board3.jpg"),
				mainWindow.getRootPane().getHeight(), matchInfo.getBlackDice(), matchInfo.getWhiteDice(),
				matchInfo.getOrangeDice());
		boardPanel.setLayout(null);
		mainWindow.getContentPane().add(boardPanel);
		scaleFactor = boardPanel.getScaleFactor();
		// contentPanel.setDividerLocation(boardPanel.getPreferredSize().getWidth());
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				developmentCards[i][j] = new DevelopmentCardButton(boardPanel.getScaleFactor(), i, j);
				developmentCards[i][j].addActionListener(new TowerListener());
				boardPanel.add(developmentCards[i][j]).setBounds(resize(180) + j * resize(955),
						resize(2900) - resize(885) * i, resize(470), resize(720));
			}
		}

		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				familyMembersOnBoard[i][j] = new JLabel();
				boardPanel.add(familyMembersOnBoard[i][j]).setBounds(resize(775) + j * resize(970),
						resize(3195) - i * resize(915), resize(200), resize(200));
				familyMembersOnBoard[i][j].setVisible(false);
			}
		}

		// left general panel setting with grid layout 2 rows 1
		// column
		rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setPreferredSize(
				new Dimension(mainWindow.getRootPane().getWidth() - (int) boardPanel.getPreferredSize().getWidth() - 10,
						(int) boardPanel.getPreferredSize().getHeight()));
		rightPanel.setLayout(new GridLayout(2, 0, 0, 0));

		// on the top of the left panel there is a split panel with
		// personal
		// board and personal bonus tile
		splitPane = new JSplitPane();
		splitPane.setOpaque(false);
		rightPanel.add(splitPane);

		// setting a tabbedPane in the right space of splitPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setOpaque(false);
		splitPane.setRightComponent(tabbedPane);

		// setting a panel with borderLayout in the splitPane
		if (this.isAdvanced) {
			actionPanel = new ActionPanel(matchInfo, playerID, mainWindow.getRootPane().getSize(), new NullListener(),
					new LeaderListener());
		} else {
			actionPanel = new ActionPanel(matchInfo, playerID, mainWindow.getRootPane().getSize(), new NullListener());
		}
		rightPanel.add(actionPanel);

		mainWindow.getContentPane().add(rightPanel);
		// mainWindow.pack();
		setSpaces();
		setUpRightPanel(matchInfo.getPlayers());
		setActionPanel();
		mainWindow.setVisible(true);

	}

	private int resize(int originalSize) {
		return (int) (originalSize * this.scaleFactor);
	}

	private void setActionPanel() {
		actionPanel.setActionPanel();
	}

	private void placeDevelopmentCards(DevelopmentCard[][] cards) {
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (cards[i][j] != null) {

					this.developmentCards[i][j].update(cards[i][j].getName(),
							CardDescFormatter.format(cards[i][j].toString()));

				} else
					this.developmentCards[i][j].hideButton();
			}
		}
	}

	private void placeBoardFamilyMembers(FamilyMemberData[][] members) {
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (members[i][j].getOwnerId() != null) {
					try {
						BufferedImage image = ImageIO.read(new File(new File("").getAbsolutePath()
								.concat("/src/images/Lorenzo_Pedine/"
										+ members[i][j].getOwnerId().toString().toLowerCase() + "_Player_"
										+ members[i][j].getColor().toString().toLowerCase() + ".png")));
						familyMembersOnBoard[i][j].setIcon(
								new ImageIcon(image.getScaledInstance(resize(200), resize(200), Image.SCALE_SMOOTH)));
					} catch (IOException e) {
						LOGGER.log(Level.SEVERE, "Unable to place family members due to IOException", e);
					}
					familyMembersOnBoard[i][j].setVisible(true);
				} else
					this.familyMembersOnBoard[i][j].setVisible(false);

			}
		}
	}

	private void placeExcommunications(Excommunication[] excommunications) {
		for (int i = 0; i < 3; i++) {
			ExcommunicationLabel excomCard = new ExcommunicationLabel(excommunications[i].getId(),
					boardPanel.getScaleFactor());
			if (i != 1) {
				boardPanel.add(excomCard).setBounds(resize(770) + resize(360) * i, resize(4100), resize(400),
						resize(715));
			} else {
				boardPanel.add(excomCard).setBounds(resize(770) + resize(360) * i, resize(4100) + resize(90),
						resize(400), resize(715));
			}
		}
	}

	private void setSpaces() {
		// work space

		singleProduction = new WorkActionButton(WorkType.PRODUCTION, true);
		boardPanel.add(singleProduction).setBounds(resize(100), resize(5485), resize(415), resize(415));
		singleProduction.addActionListener(new WorkListener());

		singleHarvest = new WorkActionButton(WorkType.HARVEST, true);
		boardPanel.add(singleHarvest).setBounds(resize(100), resize(6020), resize(415), resize(415));
		singleHarvest.addActionListener(new WorkListener());

		if (numberOfPlayers > 2) {

			multipleProduction = new MultipleSpacePanel(new WorkListener(), this.scaleFactor);
			boardPanel.add(multipleProduction).setBounds(resize(733), resize(5599), resize(822), resize(278));

			multipleHarvest = new MultipleSpacePanel(new WorkListener(), this.scaleFactor);
			boardPanel.add(multipleHarvest).setBounds(resize(733), resize(6174), resize(822), resize(278));
		} else {
			JLabel harvestSpaceCover = new JLabel();
			JLabel productionSpaceCover = new JLabel();

			try {
				BufferedImage harvestCover = ImageIO.read(new File(
						(new File("")).getAbsolutePath().concat("/src/images/Space_Covers/HarvestMultipleCover.png")));
				harvestSpaceCover.setIcon(new ImageIcon(harvestCover.getScaledInstance(resize(harvestCover.getWidth()),
						resize(harvestCover.getHeight()), Image.SCALE_DEFAULT)));
				boardPanel.add(harvestSpaceCover).setBounds(resize(545), resize(6007), resize(harvestCover.getWidth()),
						resize(harvestCover.getHeight()));

				BufferedImage productionCover = ImageIO.read(new File((new File("")).getAbsolutePath()
						.concat("/src/images/Space_Covers/ProductionMultipleCover.png")));
				productionSpaceCover
						.setIcon(new ImageIcon(productionCover.getScaledInstance(resize(productionCover.getWidth()),
								resize(productionCover.getHeight()), Image.SCALE_DEFAULT)));
				boardPanel.add(productionSpaceCover).setBounds(resize(552), resize(5446),
						resize(productionCover.getWidth()), resize(productionCover.getHeight()));

			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Unable to find MultipleWorkSpaces covers.", e);
			}

		}

		// market space
		if (numberOfPlayers < 4) {
			this.marketButtons = new MarketButton[2];
			marketButtons[0] = new MarketButton(1);
			boardPanel.add(marketButtons[0]).setBounds(resize(2410), resize(5380), resize(415), resize(415));
			marketButtons[0].addActionListener(new MarketListener());

			marketButtons[1] = new MarketButton(2);
			boardPanel.add(marketButtons[1]).setBounds(resize(2850), resize(5380), resize(415), resize(415));
			marketButtons[1].addActionListener(new MarketListener());

			JLabel firstMarketCover = new JLabel();
			JLabel secondMarketCover = new JLabel();
			try {
				BufferedImage firstCover = ImageIO.read(new File((new File("")).getAbsolutePath()
						.concat("/src/images/Space_Covers/Military_CoinsCoverMarket.png")));
				firstMarketCover.setIcon(new ImageIcon(firstCover.getScaledInstance(resize(firstCover.getWidth()),
						resize(firstCover.getHeight()), Image.SCALE_DEFAULT)));
				boardPanel.add(firstMarketCover).setBounds(resize(3215), resize(5440), resize(firstCover.getWidth()),
						resize(firstCover.getHeight()));

				BufferedImage secondCover = ImageIO.read(new File(
						(new File("")).getAbsolutePath().concat("/src/images/Space_Covers/PrivilegeMarketCover.png")));
				secondMarketCover.setIcon(new ImageIcon(secondCover.getScaledInstance(resize(firstCover.getWidth()),
						resize(firstCover.getHeight()), Image.SCALE_DEFAULT)));
				boardPanel.add(secondMarketCover).setBounds(resize(3541), resize(5760), resize(firstCover.getWidth()),
						resize(firstCover.getHeight()));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Unable to find MarketSpaces covers.", e);
			}
		}

		else {
			this.marketButtons = new MarketButton[4];

			marketButtons[0] = new MarketButton(1);
			boardPanel.add(marketButtons[0]).setBounds(resize(2410), resize(5380), resize(415), resize(415));
			marketButtons[0].addActionListener(new MarketListener());

			marketButtons[1] = new MarketButton(2);
			boardPanel.add(marketButtons[1]).setBounds(resize(2850), resize(5380), resize(415), resize(415));
			marketButtons[1].addActionListener(new MarketListener());

			marketButtons[2] = new MarketButton(3);
			boardPanel.add(marketButtons[2]).setBounds(resize(3280), resize(5500), resize(415), resize(415));
			marketButtons[2].addActionListener(new MarketListener());

			marketButtons[3] = new MarketButton(4);
			boardPanel.add(marketButtons[3]).setBounds(resize(3600), resize(5800), resize(415), resize(415));
			marketButtons[3].addActionListener(new MarketListener());
		}

		// council space
		councilPanel = new MultipleSpacePanel(new CouncilListener(), this.scaleFactor);
		boardPanel.add(councilPanel).setBounds(resize(2200), resize(3800), resize(1050), resize(350));
	}

	private void setUpRightPanel(PlayerData[] playersInfo) {

		// setting personal bonus tile
		this.playerTiles = new PlayerTile[numberOfPlayers];
		TilePanel personalBonusTile = new TilePanel(Integer.toString(this.playerTile));
		splitPane.setLeftComponent(personalBonusTile);
		splitPane.setDividerLocation((int) (personalBonusTile.getTileImage().getWidth()
				* (screenDimension.getHeight() / 2) / personalBonusTile.getTileImage().getHeight()));

		for (int i = 0; i < numberOfPlayers; i++) {
			playerTiles[i] = new PlayerTile(playersInfo[i], (int) (((double) mainWindow.getRootPane().getHeight() / 2)
					- (0.5 / 9) * ((double) mainWindow.getRootPane().getHeight() / 2)), this.isAdvanced);
			if (playersInfo[i].getId() == playerID) {
				tabbedPane.insertTab("Your Tile", null, playerTiles[i], null, i);
				this.playerPosition = i;
			}

			else
				tabbedPane.insertTab("Player " + playersInfo[i].getId(), null, playerTiles[i], null, i);

		}

	}

	private void update(MatchData matchInfo) {
		updateRightPanel(matchInfo.getPlayers());
		updateSpaces(matchInfo.getBoard());
		placeDevelopmentCards(matchInfo.getBoard().getCards());
		placeExcommunications(matchInfo.getBoard().getExcommunications());
		placeBoardFamilyMembers(matchInfo.getBoard().getTowerSpaces());
		actionPanel.updateActionPanel(matchInfo);
		boardPanel.updateDiceLabels(matchInfo.getBlackDice(), matchInfo.getWhiteDice(), matchInfo.getOrangeDice());
		tabbedPane.setSelectedIndex(playerPosition);
	}

	private void updateRightPanel(PlayerData[] players) {
		for (int i = 0; i < playerTiles.length; i++) {
			PlayerTile currentPlayerTile = playerTiles[i];
			for (int j = 0; j < players.length; j++) {
				if (currentPlayerTile.getPlayerId() == players[j].getId()) {
					currentPlayerTile.update(players[j]);
				}
			}
		}

	}

	private void updateSpaces(BoardData boardInfo) {
		councilPanel.update(boardInfo.getCouncilOccupants());
		singleHarvest.update(boardInfo.getSingleHarvestSpace());
		singleProduction.update(boardInfo.getSingleProductionSpace());
		int i = 0;
		for (MarketButton m : this.marketButtons) {
			m.update(boardInfo.getMarket()[i].getFirstOccupant());
			i++;
		}
		if (this.numberOfPlayers > 2) {
			multipleHarvest.update(boardInfo.getMultipleHarvestSpace());
			multipleProduction.update(boardInfo.getMultipleProductionSpace());
		}

		if (this.isAdvanced) {
			if (boardInfo.getSingleProductionSpaceOtherOccupant().getOwnerId() != null) {
				JLabel secondProductionOccupant = new JLabel();
				BufferedImage secondPawnProduction;
				try {
					secondPawnProduction = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/Lorenzo_Pedine/"
									+ boardInfo.getSingleProductionSpaceOtherOccupant().getOwnerId().toString()
											.toLowerCase()
									+ "_Player_" + boardInfo.getSingleProductionSpaceOtherOccupant().getColor()
											.toString().toLowerCase()
									+ ".png")));
					secondProductionOccupant.setIcon(new ImageIcon(secondPawnProduction.getScaledInstance(-1,
							singleProduction.getHeight() / 2, Image.SCALE_SMOOTH)));
					boardPanel.add(secondProductionOccupant).setBounds(resize(515), resize(5692), resize(200),
							resize(200));
					secondProductionOccupant.setOpaque(true);
					secondProductionOccupant.setVisible(true);
				} catch (IOException e) {
					secondProductionOccupant.setToolTipText(
							boardInfo.getSingleProductionSpaceOtherOccupant().getColor().toString() + "member of "
									+ boardInfo.getSingleProductionSpaceOtherOccupant().getOwnerId().toString()
									+ " player");
				}

			}
			if (boardInfo.getSingleHarvestSpaceOtherOccupant().getOwnerId() != null) {
				JLabel secondHarvestOccupant = new JLabel();
				BufferedImage secondPawnHarvest;
				try {
					secondPawnHarvest = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/Lorenzo_Pedine/"
									+ boardInfo.getSingleHarvestSpaceOtherOccupant().getOwnerId().toString()
											.toLowerCase()
									+ "_Player_"
									+ boardInfo.getSingleHarvestSpaceOtherOccupant().getColor().toString().toLowerCase()
									+ ".png")));
					secondHarvestOccupant.setIcon(new ImageIcon(secondPawnHarvest.getScaledInstance(-1,
							singleHarvest.getHeight() / 2, Image.SCALE_SMOOTH)));
					boardPanel.add(secondHarvestOccupant).setBounds(resize(515), resize(6227), resize(200),
							resize(200));
				} catch (IOException e) {
					secondHarvestOccupant.setToolTipText(
							boardInfo.getSingleHarvestSpaceOtherOccupant().getColor().toString() + "member of "
									+ boardInfo.getSingleHarvestSpaceOtherOccupant().getOwnerId().toString()
									+ " player");
				}

			}

			if (boardInfo.getMarket()[0].getSecondOccupant().getOwnerId() != null) {
				JLabel secondMarketOccupant = new JLabel();
				BufferedImage secondPawnMarket;
				try {
					secondPawnMarket = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/Lorenzo_Pedine/"
									+ boardInfo.getMarket()[0].getSecondOccupant().getOwnerId().toString().toLowerCase()
									+ "_Player_"
									+ boardInfo.getMarket()[0].getSecondOccupant().getColor().toString().toLowerCase()
									+ ".png")));
					secondMarketOccupant.setIcon(new ImageIcon(secondPawnMarket.getScaledInstance(-1,
							marketButtons[0].getHeight() / 2, Image.SCALE_SMOOTH)));
					boardPanel.add(secondMarketOccupant).setBounds(resize(2410), resize(5180), resize(200),
							resize(200));
				} catch (IOException e) {
					secondMarketOccupant.setToolTipText(
							boardInfo.getMarket()[0].getSecondOccupant().getColor().toString() + "member of "
									+ boardInfo.getMarket()[0].getSecondOccupant().getOwnerId().toString() + " player");
				}
			}

			if (boardInfo.getMarket()[1].getSecondOccupant().getOwnerId() != null) {
				JLabel secondMarketOccupant = new JLabel();
				BufferedImage secondPawnMarket;
				try {
					secondPawnMarket = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/Lorenzo_Pedine/"
									+ boardInfo.getMarket()[1].getSecondOccupant().getOwnerId().toString().toLowerCase()
									+ "_Player_"
									+ boardInfo.getMarket()[1].getSecondOccupant().getColor().toString().toLowerCase()
									+ ".png")));
					secondMarketOccupant.setIcon(new ImageIcon(secondPawnMarket.getScaledInstance(-1,
							marketButtons[1].getHeight() / 2, Image.SCALE_SMOOTH)));
					boardPanel.add(secondMarketOccupant).setBounds(resize(2850), resize(5180), resize(200),
							resize(200));
				} catch (IOException e) {
					secondMarketOccupant.setToolTipText(
							boardInfo.getMarket()[1].getSecondOccupant().getColor().toString() + "member of "
									+ boardInfo.getMarket()[1].getSecondOccupant().getOwnerId().toString() + " player");
				}
			}
			if (this.numberOfPlayers == 4) {
				if (boardInfo.getMarket()[2].getSecondOccupant().getOwnerId() != null) {
					JLabel secondMarketOccupant = new JLabel();
					BufferedImage secondPawnMarket;
					try {
						secondPawnMarket = ImageIO.read(new File(new File("").getAbsolutePath()
								.concat("/src/images/Lorenzo_Pedine/"
										+ boardInfo.getMarket()[2].getSecondOccupant().getOwnerId().toString()
												.toLowerCase()
										+ "_Player_" + boardInfo.getMarket()[2].getSecondOccupant().getColor()
												.toString().toLowerCase()
										+ ".png")));
						secondMarketOccupant.setIcon(new ImageIcon(secondPawnMarket.getScaledInstance(-1,
								marketButtons[2].getHeight() / 2, Image.SCALE_SMOOTH)));
						boardPanel.add(secondMarketOccupant).setBounds(resize(3695), resize(5500), resize(200),
								resize(200));
					} catch (IOException e) {
						secondMarketOccupant.setToolTipText(
								boardInfo.getMarket()[2].getSecondOccupant().getColor().toString() + "member of "
										+ boardInfo.getMarket()[2].getSecondOccupant().getOwnerId().toString()
										+ " player");
					}
				}

				if (boardInfo.getMarket()[3].getSecondOccupant().getOwnerId() != null) {
					JLabel secondMarketOccupant = new JLabel();
					BufferedImage secondPawnMarket;
					try {
						secondPawnMarket = ImageIO.read(new File(new File("").getAbsolutePath()
								.concat("/src/images/Lorenzo_Pedine/"
										+ boardInfo.getMarket()[3].getSecondOccupant().getOwnerId().toString()
												.toLowerCase()
										+ "_Player_" + boardInfo.getMarket()[3].getSecondOccupant().getColor()
												.toString().toLowerCase()
										+ ".png")));
						secondMarketOccupant.setIcon(new ImageIcon(secondPawnMarket.getScaledInstance(-1,
								marketButtons[3].getHeight() / 2, Image.SCALE_SMOOTH)));
						boardPanel.add(secondMarketOccupant).setBounds(resize(4015), resize(5800), resize(200),
								resize(200));
					} catch (IOException e) {
						secondMarketOccupant.setToolTipText(
								boardInfo.getMarket()[3].getSecondOccupant().getColor().toString() + "member of "
										+ boardInfo.getMarket()[3].getSecondOccupant().getOwnerId().toString()
										+ " player");
					}
				}

			}
		}
	}

	@Override
	public void playMatch() {
		// TODO implement this method

	}

	@Override
	public void updateView(MatchData match) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (updateCounter == 0)
					firstUpdate(match);

				update(match);
				updateCounter++;

			}
		});
	}

	@Override
	public ActionData makeAction(int id) {
		JOptionPane.showMessageDialog(mainWindow, "It's your turn!");
		waitingActions.drainPermits();
		this.actionId = id;
		try {
			waitingActions.acquire();
			if (chosenAction != null) {
				return chosenAction;
			} else
				return new ActionData(ActionType.NULL, MembersColor.NEUTRAL, 0, null, 0, id);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
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
		Object choices[] = { "Yes", "No" };
		int chosen = JOptionPane.showOptionDialog(mainWindow, "Do you want to support the church?", "Vatican Choice",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[1]);
		if (chosen == 0)
			return true;
		else
			return false;
	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {
		Object choices[] = new Object[costChoices.size()];
		for (int i = 0; i < costChoices.size(); i++) {
			choices[i] = costChoices.get(i).toString();
		}
		String chosenCost = (String) JOptionPane.showInputDialog(mainWindow, "Which cost do you want to pay?",
				"Choose Cost", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		if (chosenCost == null) {
			JOptionPane.showMessageDialog(mainWindow, "Since you are lazy, we have made the choice for you!");
			return 0;
		}
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenCost.compareTo((String) choices[j]) == 0) {
				break;
			}
		}
		return j;
	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {
		Object choices[] = new Object[effectChoice.length];
		for (int i = 0; i < effectChoice.length; i++) {
			choices[i] = effectChoice[i].toString();
		}
		String chosenEffect = (String) JOptionPane.showInputDialog(mainWindow, "Which effect do you want to activate?",
				"Choose Effect", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		if (chosenEffect == null) {
			JOptionPane.showMessageDialog(mainWindow, "Since you are lazy, we have made the choice for you!");
			return 0;
		}
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
		Object choices[] = new Object[possibleChoices.length];
		for (int i = 0; i < possibleChoices.length; i++) {
			choices[i] = possibleChoices[i].toString();
		}
		String chosenLeader = (String) JOptionPane.showInputDialog(mainWindow, "Which leader card do you want to pick?",
				"Pick a Leader", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		if (chosenLeader == null) {
			JOptionPane.showMessageDialog(mainWindow, "Since you are lazy, we have made the choice for you!");
			return 0;
		}
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenLeader.compareTo((String) choices[j]) == 0) {
				break;
			}
		}
		return j;
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
	public ImmProperties[] reqPrivileges(int number, ImmProperties[] privilegesValues) {
		int defaultValue = 0;
		ImmProperties[] output = new ImmProperties[number];
		// Object choices[] = new Object[privilegesValues.length];
		// for (int i = 0; i < privilegesValues.length; i++) {
		// choices[i] = privilegesValues[i].toString();
		// }
		ArrayList<String> choices = new ArrayList<String>();
		for (int i = 0; i < privilegesValues.length; i++) {
			choices.add(privilegesValues[i].toString());
		}
		for (int i = 0; i < number; i++) {
			String chosenPrivilege = (String) JOptionPane.showInputDialog(mainWindow,
					"Which privilege do you want to take?", "Choose a privilege", JOptionPane.PLAIN_MESSAGE, null,
					choices.toArray(new String[0]), choices.get(0));
			if (chosenPrivilege == null) {
				JOptionPane.showMessageDialog(mainWindow, "Since you are lazy, we have made the choice for you!");
				output[i] = privilegesValues[defaultValue];
				defaultValue++;
			} else {
				int j;
				for (j = 0; j < privilegesValues.length; j++) {
					if (chosenPrivilege.compareTo((String) privilegesValues[j].toString()) == 0) {
						break;
					}
				}
				output[i] = privilegesValues[j];
				choices.remove(privilegesValues[j].toString());
			}
		}
		return output;
	}

	@Override
	public void matchEnded(EndData data) {
		Map<PlayerColor, Integer> result = data.getPlayersFinalPoints();
		if (data.getWinner() == playerID) {
			JOptionPane.showMessageDialog(mainWindow, "CONGRATULATIONS: You won!!!!!", "Match ended",
					JOptionPane.INFORMATION_MESSAGE);
		} else

		{
			JOptionPane.showMessageDialog(mainWindow, "Player " + data.getWinner() + " won the match!", "Match ended",
					JOptionPane.INFORMATION_MESSAGE);
		}
		StringBuilder s = new StringBuilder();
		for (Map.Entry<PlayerColor, Integer> player : result.entrySet()) {
			if (player.getKey().equals(playerID))
				s.append("\nYou have totalized " + player.getValue() + " final points;");
			else
				s.append("\nPlayer " + player.getKey() + " has totalized " + player.getValue() + " final points;");
		}
		JOptionPane.showMessageDialog(mainWindow, s.toString(), "Match results", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);

	}

	@Override
	public int reqExtraActionChoice(ExtraActionData[] actions) {
		int choice = 0;
		Object choices[] = new Object[actions.length];
		for (int i = 0; i < actions.length; i++) {
			choices[i] = actions[i].getType() + ": " + actions[i].getDescription();
		}
		String chosenExtraAction = (String) JOptionPane.showInputDialog(mainWindow,
				"Which extra action do you want to do?", "Choose an extra action", JOptionPane.PLAIN_MESSAGE, null,
				choices, choices[0]);
		if (chosenExtraAction == null) {
			JOptionPane.showMessageDialog(mainWindow, "Since you are lazy, we have made the choice for you!");
			return 0;
		}
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenExtraAction.compareTo((String) choices[j]) == 0) {
				choice = j;
				break;
			}
		}
		return choice;
	}

	@Override
	public int reqWorkChoice(DevelopmentCard workCard, boolean costs) {
		int effectsNum = workCard.getPossibleEffects().length;
		if (costs) {
			Object choices[] = new Object[effectsNum + 1];
			choices[0] = "Don't activate this effect.";
			for (int i = 0; i < effectsNum; i++) {
				choices[i + 1] = workCard.getPossibleEffects()[i].toString();
			}
			String chosenEffect = (String) JOptionPane.showInputDialog(mainWindow,
					"Which of the effects of this card should be activated in the current work action?",
					"Choose effect to activate", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
			if (chosenEffect == null) {
				JOptionPane.showMessageDialog(mainWindow, "Effect not activated.");
				return 0;
			}
			int j;
			for (j = 0; j < choices.length; j++) {
				if (chosenEffect.compareTo((String) choices[j]) == 0) {
					return j;
				}
				
			}
			return 0;
		}else{
			Object choices[] = new Object[effectsNum];
			for (int i = 0; i < effectsNum; i++) {
				choices[i] = workCard.getPossibleEffects()[i].toString();
			}
			String chosenEffect = (String) JOptionPane.showInputDialog(mainWindow,
					"Which of the effects of this card should be activated in the current work action?",
					"Choose effect to activate", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
			if (chosenEffect == null) {
				return 1;
			}
			int j;
			for (j = 0; j < choices.length; j++) {
				if (chosenEffect.compareTo((String) choices[j]) == 0) {
					return j+1;
				}
				
			}
			return 0;
			
		}
	}

	@Override
	public String reqName() {
		String name = JOptionPane.showInputDialog(mainWindow, "Insert your name", JOptionPane.PLAIN_MESSAGE);
		if (name == null) {
			JOptionPane.showMessageDialog(mainWindow, "Match Aborted!");
			System.exit(0);
		}
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
		if (chosenTile == null) {
			JOptionPane.showMessageDialog(mainWindow, "Since you are lazy, we have made the choice for you!");
			return 0;
		}
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

	@Override
	public int reqCardChoice(DevelopmentCard[] possibleChoices) {
		int numberOfChoices = possibleChoices.length;
		Object choices[] = new Object[numberOfChoices];
		for (int i = 0; i < numberOfChoices; i++) {
			choices[i] = possibleChoices[i].toString();
		}
		String chosenCard = (String) JOptionPane.showInputDialog(mainWindow,
				"Which Development Card do you want to take with extra action?", "Choose Development Card",
				JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenCard.compareTo((String) choices[j]) == 0) {
				break;
			}
		}
		return j;
	}

	@Override
	public int reqNumberOfServants(int max) {
		int possibleServants = max;
		Object choices[] = new Object[max + 1];
		for (int i = 0; i <= possibleServants; i++) {
			choices[i] = Integer.toString(i);
		}
		String chosenNumberOfServants = (String) JOptionPane.showInputDialog(mainWindow,
				"How many servants do you want to use to increment the value of the action?",
				"Choose number of servants", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
		int j;
		j = Integer.parseInt(chosenNumberOfServants);
		return j;
	}

	@Override
	public int reqLorenzoIlMagnificoChoice(LeaderCard[] possibilities) {
		int numberOfChoices = possibilities.length;
		Object choices[] = new Object[numberOfChoices];
		for (int i = 0; i < numberOfChoices; i++) {
			choices[i] = possibilities[i].getName().concat(" : " + possibilities[i].getEffect().getDesc());
		}
		String chosenLeaderCard = (String) JOptionPane.showInputDialog(mainWindow,
				"Wich Leader Card effect do you want to copy?", "Choose Leader Card", JOptionPane.PLAIN_MESSAGE, null,
				choices, choices[0]);
		int j;
		for (j = 0; j < choices.length; j++) {
			if (chosenLeaderCard.compareTo((String) choices[j]) == 0) {
				break;
			}
		}
		return j;
	}

}
