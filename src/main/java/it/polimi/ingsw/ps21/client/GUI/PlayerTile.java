package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import it.polimi.ingsw.ps21.controller.PlayerData;

public class PlayerTile extends JSplitPane{
	
	private PlayerBoardPanel playerBoardPanel;
	private JPanel charactersAndVentures = new JPanel();
	private JLabel characters = new JLabel();
	private JLabel ventures = new JLabel();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//passare carte character e venture del giocatore
	public PlayerTile(PlayerData infoPlayer){
		
		playerBoardPanel = new PlayerBoardPanel((new File("")).getAbsolutePath().concat("/src/images/Lorenzo_Punchboard_FRONT_compressed/punchboard_f_c_03.jpg"), infoPlayer);
		this.setLeftComponent(playerBoardPanel);
		this.setDividerLocation((int)(playerBoardPanel.getPlayerBoardImage().getWidth() * (screenSize.getHeight() / 2) / (playerBoardPanel.getPlayerBoardImage().getHeight() )));
		
		characters.setBackground(new Color(0,127,255));
		characters.setOpaque(true);
		characters.setText("CHARACTERS");
		characters.setToolTipText("<html><body><div><img src=\"file:src/images/LorenzoCards_compressed_png/devcards_f_en_c_1.png\"><img src=\"file:src/images/LorenzoCards_compressed_png/devcards_f_en_c_2.png\"></div></body></html> ");
		ventures.setBackground(new Color(244,0,161));
		ventures.setOpaque(true);
		ventures.setText("VENTURES");
		ventures.setToolTipText("<html><body><div><img src=\"file:src/images/LorenzoCards_compressed_png/devcards_f_en_c_1.png\"><img src=\"file:src/images/LorenzoCards_compressed_png/devcards_f_en_c_2.png\"></div></body></html> ");

		charactersAndVentures.setLayout(new GridLayout(2, 1));
		charactersAndVentures.add(characters);
		charactersAndVentures.add(ventures);
		charactersAndVentures.setVisible(true);
		
		this.setRightComponent(charactersAndVentures);
		
		this.setVisible(true);
		
		
	}


}
