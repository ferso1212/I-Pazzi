package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class PlayerTile extends JSplitPane{
	
	private PlayerBoardPanel playerBoardPanel;
	private JPanel charactersAndVentures = new JPanel();
	private JLabel characters = new JLabel();
	private JLabel ventures = new JLabel();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private PlayerColor playerId;
	
	//passare carte character e venture del giocatore
	public PlayerTile(PlayerData infoPlayer){
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				playerId = infoPlayer.getId();
				
				
				
				try {
					
					BufferedImage blueimage = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/DevelopmentCards/back_blue.jpg")));
					characters.setIcon(
							new ImageIcon(blueimage.getScaledInstance(  -1, (int)(getHeight()/2), Image.SCALE_SMOOTH)));
					BufferedImage purpleimage = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/DevelopmentCards/back_purple.jpg")));
					ventures.setIcon(
							new ImageIcon(purpleimage.getScaledInstance(-1, (int)(getHeight()/2), Image.SCALE_SMOOTH)));
					playerBoardPanel = new PlayerBoardPanel((new File("")).getAbsolutePath().concat("/src/images/Lorenzo_Punchboard_FRONT_compressed/punchboard_f_c_03_bis.jpg"), infoPlayer);
					setLeftComponent(playerBoardPanel);
					setDividerLocation(((getWidth() - ventures.getIcon().getIconWidth())));
				} catch (IOException e) {
					e.printStackTrace();
					characters.setBackground(new Color(0,127,255));
					characters.setOpaque(true);
					characters.setText("CHARACTERS");
					ventures.setBackground(new Color(244,0,161));
					ventures.setOpaque(true);
					ventures.setText("VENTURES");
					setDividerLocation(((getWidth() - ventures.getWidth())));
				}
				
				
				charactersAndVentures.setLayout(new GridLayout(2, 1));
				charactersAndVentures.add(characters);
				charactersAndVentures.add(ventures);
				charactersAndVentures.setVisible(true);
				
				setRightComponent(charactersAndVentures);
				
				setVisible(true);
				
			}
		});
		
		
		
	}
	
	public void updateCharactersAndVentures(PlayerData playerData){
		
		StringBuilder characterHTML = new StringBuilder();
		characterHTML.append("<html><body><div>");
		for (DevelopmentCard c : playerData.getCards().get(DevelopmentCardType.CHARACTER)){
			characterHTML.append("<img src=\"file:src/images/DevelopmentCards/" + c.getName().replaceAll(" ", "") + ".png\">");
			}
		characterHTML.append("</div></body></html>");
		characters.setToolTipText(characterHTML.toString());
		
		StringBuilder ventureHTML = new StringBuilder();
		ventureHTML.append("<html><body><div>");
		for (DevelopmentCard c : playerData.getCards().get(DevelopmentCardType.VENTURE)){
			ventureHTML.append("<img src=\"file:src/images/DevelopmentCards/" + c.getName().replaceAll(" ", "") + ".png\">");
			}
		ventureHTML.append("</div></body></html>");
		ventures.setToolTipText(ventureHTML.toString());
		
		playerBoardPanel.updateProperties(playerData);
	}
	
	public PlayerColor getPlayerId(){
		return this.playerId;
	}


}
