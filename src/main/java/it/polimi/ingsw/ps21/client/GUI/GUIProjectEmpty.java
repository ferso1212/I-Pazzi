package it.polimi.ingsw.ps21.client.GUI;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.RepaintManager;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import it.polimi.ingsw.ps21.client.UserInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.ActionType;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

import java.awt.Component;
import java.awt.Dimension;

public class GUIProjectEmpty implements UserInterface {
	
	private static final Logger LOGGER = Logger.getLogger(GUIProjectEmpty.class.getSimpleName());
	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private JPanel rightPanel;
	private JPanel actionPanel;
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane;
	private JButton okButton;
	private double scaleFactor;
	private int numberOfPlayers;
	private int updateCounter = 0;
	private boolean isAdvanced;
	private int playerTile;
	private PlayerColor playerID;
	private Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	private Semaphore waitingActions;
	private DevelopmentCardButton[][] developmentButtons;
	private WorkActionButton singleHarvestButton;
	private WorkActionButton singleProductionButton;
	private WorkActionButton multipleProductionButton;
	private WorkActionButton multipleHarvestButton;
	private CouncilButton councilButton;
	private MarketButton[] marketButtons;
	/**
	 * Create the application.
	 */
	public GUIProjectEmpty() {
		this.developmentButtons = new DevelopmentCardButton[4][4];
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainWindow = new JFrame("Lorenzo Il Magnifico");
				mainWindow.setVisible(true);
				waitingActions = new Semaphore(1);
			}
		});
	}

	private class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(okButton)){
			if (waitingActions.availablePermits()==0){
				waitingActions.release();
				}
			}
				
		}

	}

	private void firstUpdate(MatchData matchInfo) {

		this.numberOfPlayers = matchInfo.getPlayers().length;

		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		boardPanel = new BoardPanel((new File("")).getAbsolutePath().concat("/src/images/board2.jpg"),
				matchInfo.getBlackDice(), matchInfo.getWhiteDice(), matchInfo.getOrangeDice());
		boardPanel.setLayout(null);
		mainWindow.getContentPane().add(boardPanel);

		// left general panel setting with grid layout 2 rows 1 column
		rightPanel = new JPanel();
		mainWindow.getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(2, 0, 0, 0));

		// on the top of the left panel there is a split panel with personal
		// board and personal bonus tile
		splitPane = new JSplitPane();
		rightPanel.add(splitPane);

		// setting a tabbedPane in the right space of splitPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		// setting a panel with borderLayout in the splitPane
		actionPanel = new JPanel();
		rightPanel.add(actionPanel);
		actionPanel.setLayout(new BorderLayout(0, 0));

		this.scaleFactor = boardPanel.getScaleFactor();
		this.mainWindow.setVisible(true);
		
		this.developmentButtons = new DevelopmentCardButton[4][4];
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				{
					developmentButtons[i][j] = new DevelopmentCardButton(boardPanel.getScaleFactor());
					developmentButtons[i][j].addActionListener(new MyListener());
					boardPanel.add(developmentButtons[i][j]).setBounds(resize(615) + j * resize(970),
							resize(580) + resize(820) * i, resize(470), resize(720));
				}
			}
		}

		mainWindow.pack();

		update(matchInfo);
	}

	private int resize(int originalSize) {
		return (int) (originalSize * this.scaleFactor);
	}

	private void placeDevelopmentCards(DevelopmentCard[][] developmentCards) {
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (developmentCards[i][j] != null) {
					developmentButtons[i][j].update(developmentCards[i][j].getName(), developmentCards[i][j].toString());
				}
				else developmentButtons[i][j].reset();
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
		singleHarvestButton = new WorkActionButton(WorkType.HARVEST, true);
		boardPanel.add(singleHarvestButton).setBounds(resize(545), resize(5485), resize(415), resize(415));
		singleHarvestButton.addActionListener(new MyListener());

		singleProductionButton = new WorkActionButton(WorkType.PRODUCTION, true);
		boardPanel.add(singleProductionButton).setBounds(resize(545), resize(6020), resize(415), resize(415));
		singleProductionButton.addActionListener(new MyListener());

		multipleHarvestButton = new WorkActionButton(WorkType.HARVEST, false);
		boardPanel.add(multipleHarvestButton).setBounds(resize(1100), resize(5485), resize(900), resize(415));
		multipleHarvestButton.addActionListener(new MyListener());

		multipleProductionButton = new WorkActionButton(WorkType.PRODUCTION, false);
		boardPanel.add(multipleProductionButton).setBounds(resize(1100), resize(6020), resize(900), resize(415));
		multipleProductionButton.addActionListener(new MyListener());

		if (numberOfPlayers<4) {
			this.marketButtons = new MarketButton[2];
		// market space
		marketButtons[0] = new MarketButton(1);
		boardPanel.add(marketButtons[0]).setBounds(resize(2860), resize(5380), resize(415), resize(415));
		marketButtons[0].addActionListener(new MyListener());

		marketButtons[1] = new MarketButton(2);
		boardPanel.add(marketButtons[1]).setBounds(resize(3300), resize(5380), resize(415), resize(415));
		marketButtons[1].addActionListener(new MyListener());
		}
		else {	
			this.marketButtons = new MarketButton[4];
			marketButtons[0] = new MarketButton(1);
			boardPanel.add(marketButtons[0]).setBounds(resize(2860), resize(5380), resize(415), resize(415));
			marketButtons[0].addActionListener(new MyListener());
			marketButtons[1] = new MarketButton(2);
			boardPanel.add(marketButtons[1]).setBounds(resize(3300), resize(5380), resize(415), resize(415));
			marketButtons[1].addActionListener(new MyListener());
					
			marketButtons[2] = new MarketButton(3);
			boardPanel.add(marketButtons[2]).setBounds(resize(3730), resize(5500), resize(415), resize(415));
			marketButtons[2].addActionListener(new MyListener());

			marketButtons[4] = new MarketButton(4);
			boardPanel.add(marketButtons[4]).setBounds(resize(4050), resize(5800), resize(415), resize(415));
			marketButtons[4].addActionListener(new MyListener());
		}
		// council space
		councilButton = new CouncilButton();
		boardPanel.add(councilButton).setBounds(resize(2515), resize(3780), resize(1280), resize(510));
		councilButton.addActionListener(new MyListener());
	}

	private void setUpRightPanel(PlayerData[] playersInfo) {

		// setting prsonal bonus tile
		TilePanel personalBonusTile = new TilePanel(Integer.toString(this.playerTile));
		splitPane.setLeftComponent(personalBonusTile);
		splitPane.setDividerLocation((int) (personalBonusTile.getTileImage().getWidth() * (screenDimension.getHeight() / 2) / personalBonusTile.getTileImage().getHeight()));

		// setting a tabbedPane in the right space of splitPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		for (int i = 0; i < this.numberOfPlayers; i++) {
			PlayerTile playerTile = new PlayerTile(playersInfo[i]);
			tabbedPane.addTab("Player " + i + " Tile", null, playerTile, null);
			
		}
		

	}

	private void setDownRightPanel() {
		// setting a panel with borderLayout in the splitPane

		RoundInfo roundInfo = new RoundInfo("Informazioni sul round");
		actionPanel.add(roundInfo, BorderLayout.PAGE_START);
		actionPanel.add(new FamilyMemberPanel(), BorderLayout.LINE_START);
		okButton = new JButton("Send Action");
		okButton.addActionListener(new MyListener());
		actionPanel.add(okButton);
		actionPanel.setVisible(true);
		//mainWindow.pack();
	}

	private void update(MatchData matchInfo) {
		setUpRightPanel(matchInfo.getPlayers());
		setSpaces();
		placeDevelopmentCards(matchInfo.getBoard().getCards());
		placeExcommunications(matchInfo.getBoard().getExcommunications());
		mainWindow.pack();
	}

	@Override
	public void playMatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateView(MatchData match) {
		if (this.updateCounter == 0)
			firstUpdate(match);
		else
			update(match);
		this.updateCounter++;
	}

	@Override
	public ActionData makeAction(int id) {
		setDownRightPanel();
		waitingActions.drainPermits();
		try {
			waitingActions.acquire();
			return new ActionData(ActionType.NULL, MembersColor.NEUTRAL, 0, null, 0, id);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int reqCostChoice(ArrayList<ImmProperties> costChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int reqEffectChoice(EffectSet[] effectChoice) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int chooseLeaderCard(LeaderCard[] possibleChoices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void showMessage(AcceptedAction mess) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showMessage(RefusedAction mess) {
		// TODO Auto-generated method stub

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
