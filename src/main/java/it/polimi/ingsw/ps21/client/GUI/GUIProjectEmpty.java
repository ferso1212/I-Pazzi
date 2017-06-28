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
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;

import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import it.polimi.ingsw.ps21.client.UserInterface;
import it.polimi.ingsw.ps21.controller.AcceptedAction;
import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.RefusedAction;
import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;
import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.EndData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

import java.awt.Component;
import java.awt.Dimension;

public class GUIProjectEmpty implements UserInterface{

	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private JPanel rightPanel;
	private JPanel actionPanel;
	private JTabbedPane tabbedPane;
	private JSplitPane splitPane;
	private double scaleFactor;
	private int numberOfPlayers;
	private Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIProjectEmpty window = new GUIProjectEmpty();
					window.mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIProjectEmpty() {
		
		initialize(3, false);
		placeDevelopmentCards();
		placeExcommunications();
		setSpaces();
		setUpRightPanel();
		setDownRightPanel();
	}

	private class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private void initialize(int numberOfPlayer, boolean isAdvanced) {

		this.numberOfPlayers = numberOfPlayer;

		mainWindow = new JFrame();
		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));

		boardPanel = new BoardPanel((new File("")).getAbsolutePath().concat("/src/images/board2.jpg"));
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

		mainWindow.pack();

		this.scaleFactor = boardPanel.getScaleFactor();
	}

	private int resize(int originalSize) {
		return (int) (originalSize * this.scaleFactor);
	}

	private void placeDevelopmentCards() {
		for (int i = 0; i < 4; i++) {
			DevelopmentCardLabel developmentCard = new DevelopmentCardLabel("Commercial Hub", boardPanel.getScaleFactor());
			developmentCard.addActionListener(new MyListener());
			boardPanel.add(developmentCard).setBounds(resize(615), resize(580) + resize(820) * i, resize(470), resize(720));
		}
	}

	private void placeExcommunications() {
		for (int i = 0; i < 3; i++) {
			ExcommunicationLabel excomCard = new ExcommunicationLabel("1",	boardPanel.getScaleFactor());
			boardPanel.add(excomCard).setBounds(resize(1180) + resize(380) * i, resize(4100), resize(400), resize(715));
		}
	}

	private void setSpaces() {
		// work space
		WorkActionButton singleHarvest = new WorkActionButton(WorkType.HARVEST, true);
		boardPanel.add(singleHarvest).setBounds(resize(545), resize(5485), resize(415), resize(415));
		singleHarvest.addActionListener(new MyListener());

		WorkActionButton singleProduction = new WorkActionButton(WorkType.PRODUCTION, true);
		boardPanel.add(singleProduction).setBounds(resize(545), resize(6020), resize(415), resize(415));
		singleProduction.addActionListener(new MyListener());

		WorkActionButton multipleHarvest = new WorkActionButton(WorkType.HARVEST, false);
		boardPanel.add(multipleHarvest).setBounds(resize(1100), resize(5485), resize(900), resize(415));
		multipleHarvest.addActionListener(new MyListener());

		WorkActionButton multipleProduction = new WorkActionButton(WorkType.PRODUCTION, false);
		boardPanel.add(multipleProduction).setBounds(resize(1100), resize(6020), resize(900), resize(415));
		multipleProduction.addActionListener(new MyListener());

		// market space
		MarketButton firstMarket = new MarketButton(1);
		boardPanel.add(firstMarket).setBounds(resize(2860), resize(5380), resize(415), resize(415));
		firstMarket.addActionListener(new MyListener());

		MarketButton secondMarket = new MarketButton(2);
		boardPanel.add(secondMarket).setBounds(resize(3300), resize(5380), resize(415), resize(415));
		secondMarket.addActionListener(new MyListener());

		MarketButton thirdMarket = new MarketButton(3);
		boardPanel.add(thirdMarket).setBounds(resize(3730), resize(5500), resize(415), resize(415));
		thirdMarket.addActionListener(new MyListener());

		MarketButton fourthMarket = new MarketButton(4);
		boardPanel.add(fourthMarket).setBounds(resize(4050), resize(5800), resize(415), resize(415));
		fourthMarket.addActionListener(new MyListener());

		// council space
		CouncilButton councilButton = new CouncilButton();
		boardPanel.add(councilButton).setBounds(resize(2515), resize(3780), resize(1280), resize(510));
		councilButton.addActionListener(new MyListener());
	}

	private void setUpRightPanel() {

		// setting prsonal bonus tile
		TilePanel personalBonusTile = new TilePanel("0");
		splitPane.setLeftComponent(personalBonusTile);
		splitPane.setDividerLocation((int)(personalBonusTile.getTileImage().getWidth() * (screenDimension.getHeight() / 2) / personalBonusTile.getTileImage().getHeight()));
		
		// setting a tabbedPane in the right space of splitPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		for (int i = 1; i <= this.numberOfPlayers; i++) {
			PlayerTile playerTile = new PlayerTile();
			tabbedPane.addTab("Player " + i + " Tile", null, playerTile, null);
		}

	}

	private void setDownRightPanel() {
		// setting a panel with borderLayout in the splitPane

		RoundInfo roundInfo = new RoundInfo("Informazioni sul round");
		actionPanel.add(roundInfo, BorderLayout.PAGE_START);

		actionPanel.add(new FamilyMemberPanel(), BorderLayout.LINE_START);
	}


	@Override
	public void playMatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView(MatchData match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionData makeAction(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showInfo(String name) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reqIfWantsAdvancedRules() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int chooseTile(PersonalBonusTile[] possibilities) {
		// TODO Auto-generated method stub
		return 0;
	}
}
