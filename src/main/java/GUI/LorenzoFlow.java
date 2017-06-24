package GUI;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;

import java.awt.BorderLayout;
import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import it.polimi.ingsw.ps21.model.actions.WorkType;

import java.awt.Component;

public class LorenzoFlow {

	private JFrame mainWindow;
	private BoardPanel boardPanel;
	private JPanel rightPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LorenzoFlow window = new LorenzoFlow();
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
	public LorenzoFlow() {
		initialize(3, false);
	}
	
	private class MyListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().getClass().equals(DevelopmentCardLabel.class)){
				
				DevelopmentActionPanel actionPanel = new DevelopmentActionPanel((DevelopmentCardLabel)event.getSource());
				rightPanel.add(actionPanel, 1);
				rightPanel.setVisible(true);
				
			}else if (event.getSource() instanceof WorkActionButton){
				
				}
			}
		
		

	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize(int numberOfPlayer, boolean isAdvanced) {
		mainWindow = new JFrame();
		mainWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		mainWindow.setBounds(100, 100, 450, 300);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		
		//setting BoardPanel
		BoardPanel boardPanel = new BoardPanel((new File("")).getAbsolutePath().concat("/src/images/board.gif"));
		boardPanel.setLayout(null);
		mainWindow.getContentPane().add(boardPanel);
		
		// development card
		for (int i = 0; i < 4; i++) {
			DevelopmentCardLabel developmentCard = new DevelopmentCardLabel("src/images/LorenzoCards_compressed_png/devcards_f_en_c_"+(i+1)+".png");
			developmentCard.addActionListener(new MyListener());
			boardPanel.add(developmentCard).setBounds(80, 85 + 118*i, 70, 100);
			}
		
		//excommunication
		for (int i = 0; i<3 ; i++) {
			ExcommunicationLabel excomCard = new ExcommunicationLabel("src/images/Lorenzo_Punchboard_CUT_compressed/excomm_"+(i+1)+"_"+(i+1)+".png");
			boardPanel.add(excomCard).setBounds(165 + 50*i , 595, 50, 100);
			}
		
		//work space
		WorkActionButton singleHarvest = new WorkActionButton(WorkType.HARVEST, true);
		boardPanel.add(singleHarvest).setBounds(75 , 790, 50, 60);
		singleHarvest.addActionListener(new MyListener());
		
		WorkActionButton singleProduction = new WorkActionButton(WorkType.PRODUCTION, true);
		boardPanel.add(singleProduction).setBounds(75 , 865, 50, 60);
		singleProduction.addActionListener(new MyListener());
		
		WorkActionButton multipleHarvest = new WorkActionButton(WorkType.HARVEST, false);
		boardPanel.add(multipleHarvest).setBounds(145 , 790, 145, 60);
		multipleHarvest.addActionListener(new MyListener());
		
		WorkActionButton multipleProduction = new WorkActionButton(WorkType.PRODUCTION, false);
		boardPanel.add(multipleProduction).setBounds(145 , 865, 145, 60);
		multipleProduction.addActionListener(new MyListener());
		
		//market space
		MarketButton firstMarket = new MarketButton(1);
		boardPanel.add(firstMarket).setBounds(393, 775, 54, 60);
		firstMarket.addActionListener(new MyListener());
		
		MarketButton secondMarket = new MarketButton(2);
		boardPanel.add(secondMarket).setBounds(453, 775, 54, 60);
		secondMarket.addActionListener(new MyListener());
		
		MarketButton thirdMarket = new MarketButton(3);
		boardPanel.add(thirdMarket).setBounds(512, 790, 54, 60);
		thirdMarket.addActionListener(new MyListener());
		
		MarketButton fourthMarket = new MarketButton(4);
		boardPanel.add(fourthMarket).setBounds(558, 830, 54, 60);
		fourthMarket.addActionListener(new MyListener());
		
		//council space
		CouncilButton councilButton = new CouncilButton();
		boardPanel.add(councilButton).setBounds(348, 550, 178, 78);
		councilButton.addActionListener(new MyListener());
		
		//left general panel setting with grid layout 2 rows 1 column
		rightPanel = new JPanel(); 
		mainWindow.getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		//on the top of the left panel there is a split panel with personal board and personal bonus tile
		JSplitPane splitPane = new JSplitPane();
		rightPanel.add(splitPane);
		
		//setting prsonal bonus tile
		JLabel personalBonusTile = new JLabel();
		personalBonusTile.setHorizontalAlignment(SwingConstants.LEFT);
		personalBonusTile.setIcon(new ImageIcon("C:\\Users\\darix\\I-Pazzi\\src\\images\\Lorenzo_Punchboard_CUT_compressed\\personalbonustile_2.png"));
		splitPane.setLeftComponent(personalBonusTile);
		
		//setting a tabbedPane in the right space of splitPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		
		for (int i = 1; i <= numberOfPlayer; i++) {
			PlayerTile playerTile = new PlayerTile();
			tabbedPane.addTab("Player " + i + " Tile", null, playerTile, null);
		}
		
		//setting a panel sith borderLayout in the splitPane
		JPanel actionPanel = new JPanel();
		rightPanel.add(actionPanel);
		actionPanel.setLayout(new BorderLayout(0, 0));
		
		RoundInfo roundInfo = new RoundInfo("Informazioni sul round");
		actionPanel.add(roundInfo, BorderLayout.PAGE_START);
		
		actionPanel.add(new FamilyMemberPanel(), BorderLayout.LINE_START);
		
		
		
		
		

		
		
	}
}
