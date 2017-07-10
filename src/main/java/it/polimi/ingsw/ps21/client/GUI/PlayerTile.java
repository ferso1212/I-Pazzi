package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import it.polimi.ingsw.ps21.controller.NotAdvancedPlayerException;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.deck.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.excommunications.Excommunication;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.view.PlayerData;

public class PlayerTile extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayerBoardPanel playerBoardPanel;
	private JPanel charactersAndVentures = new JPanel();
	private JLabel characters = new JLabel();
	private JLabel ventures = new JLabel();
	private JLabel excomm1 = new JLabel();
	private JLabel excomm2 = new JLabel();
	private JLabel excomm3 = new JLabel();
	private JLabel leader1 = new JLabel();
	private JLabel leader2 = new JLabel();
	private JLabel leader3 = new JLabel();
	private JLabel leader4 = new JLabel();
	private final static Logger LOGGER = Logger.getLogger(PlayerTile.class.getSimpleName());
	private boolean isAdvanced;
	private PlayerColor playerId;

	// passare carte character e venture del giocatore
	public PlayerTile(PlayerData infoPlayer, int preferredHeight, boolean advanced) {
		isAdvanced = advanced;
		playerId = infoPlayer.getId();				

				try {

					BufferedImage blueimage = ImageIO.read(new File(
							new File("").getAbsolutePath().concat("/src/images/DevelopmentCards/back_blue.jpg")));
					characters.setIcon(new ImageIcon(
							blueimage.getScaledInstance(-1, (int) (preferredHeight / 2), Image.SCALE_SMOOTH)));
					BufferedImage purpleimage = ImageIO.read(new File(
							new File("").getAbsolutePath().concat("/src/images/DevelopmentCards/back_purple.jpg")));
					ventures.setIcon(new ImageIcon(
							purpleimage.getScaledInstance(-1, (int) (preferredHeight / 2), Image.SCALE_SMOOTH)));

					BufferedImage excomm1Image = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/ExcommunicationAndTile/excomm_back_1.png")));
					excomm1.setIcon(new ImageIcon(
							excomm1Image.getScaledInstance(-1, (int) (preferredHeight/ 3), Image.SCALE_SMOOTH)));
					BufferedImage excomm2Image = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/ExcommunicationAndTile/excomm_back_2.png")));
					excomm2.setIcon(new ImageIcon(
							excomm2Image.getScaledInstance(-1, (int) (preferredHeight / 3), Image.SCALE_SMOOTH)));
					BufferedImage excomm3Image = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/ExcommunicationAndTile/excomm_back_3.png")));
					excomm3.setIcon(new ImageIcon(
							excomm3Image.getScaledInstance(-1, (int) (preferredHeight/ 3), Image.SCALE_SMOOTH)));

					playerBoardPanel = new PlayerBoardPanel(
							(new File("")).getAbsolutePath()
									.concat("/src/images/Lorenzo_Punchboard_FRONT_compressed/punchboard_f_c_03_bis.jpg"),
							infoPlayer, preferredHeight);

					if (isAdvanced) {
						BufferedImage leaderBackImage = ImageIO.read(new File(new File("").getAbsolutePath()
								.concat("/src/images/Lorenzo_Leaders_compressed/leaders_b_c_00.jpg")));
						leader1.setIcon(new ImageIcon(
								leaderBackImage.getScaledInstance(-1, (int) (preferredHeight / 4), Image.SCALE_SMOOTH)));
						leader2.setIcon(new ImageIcon(
								leaderBackImage.getScaledInstance(-1, (int) (preferredHeight / 4), Image.SCALE_SMOOTH)));
						leader3.setIcon(new ImageIcon(
								leaderBackImage.getScaledInstance(-1, (int) (preferredHeight / 4), Image.SCALE_SMOOTH)));
						leader4.setIcon(new ImageIcon(
								leaderBackImage.getScaledInstance(-1, (int) (preferredHeight / 4), Image.SCALE_SMOOTH)));
					}

					setLeftComponent(playerBoardPanel);
					
					setDividerLocation((int)((double)14*(double)preferredHeight/(double)8.5));
					

				} catch (IOException e) {
					e.printStackTrace();
					characters.setBackground(new Color(0, 127, 255));
					characters.setOpaque(true);
					characters.setText("CHARACTERS");
					ventures.setBackground(new Color(244, 0, 161));
					ventures.setOpaque(true);
					ventures.setText("VENTURES");
					excomm1.setBackground(new Color(245, 117, 37));
					excomm1.setOpaque(true);
					excomm1.setText("EXCOMMUNICATION 1");
					excomm2.setBackground(new Color(187, 112, 42));
					excomm2.setOpaque(true);
					excomm2.setText("EXCOMMUNICATION 2");
					excomm3.setBackground(new Color(106, 17, 30));
					excomm3.setOpaque(true);
					excomm3.setText("EXCOMMUNICATION 3");
					setDividerLocation(((getWidth() - ventures.getWidth() - excomm1.getWidth())));

				}

				JPanel temp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));

				charactersAndVentures.setLayout(new GridLayout(2, 1));
				charactersAndVentures.add(characters);
				charactersAndVentures.add(ventures);
				charactersAndVentures.setVisible(true);

				JPanel excommPanel = new JPanel(new GridLayout(3, 1));
				excommPanel.add(excomm1);
				excommPanel.add(excomm2);
				excommPanel.add(excomm3);

				if (isAdvanced) {

					JPanel playerLeaders = new JPanel(new GridLayout(4, 1));
					playerLeaders.add(leader1);
					playerLeaders.add(leader2);
					playerLeaders.add(leader3);
					playerLeaders.add(leader4);

					temp.add(playerLeaders);
				}

				temp.add(charactersAndVentures);
				temp.add(excommPanel);

				setRightComponent(temp);

				setVisible(true);


	}

	public void update(PlayerData playerData) {

		StringBuilder characterHTML = new StringBuilder();
		characterHTML.append("<html><body><div>");
		for (DevelopmentCard c : playerData.getCards().get(DevelopmentCardType.CHARACTER)) {
			characterHTML.append(
					"<img src=\"file:src/images/DevelopmentCards/" + c.getName().replaceAll(" ", "") + ".png\">");
		}
		characterHTML.append("</div></body></html>");
		characters.setToolTipText(characterHTML.toString());

		StringBuilder ventureHTML = new StringBuilder();
		ventureHTML.append("<html><body><div>");
		for (DevelopmentCard c : playerData.getCards().get(DevelopmentCardType.VENTURE)) {
			ventureHTML.append(
					"<img src=\"file:src/images/DevelopmentCards/" + c.getName().replaceAll(" ", "") + ".png\">");
		}
		ventureHTML.append("</div></body></html>");
		ventures.setToolTipText(ventureHTML.toString());

		for (Excommunication e : playerData.getExcommunications()) {
			try {
				if (e.getPeriod() == 1) {
					BufferedImage excomm1Image = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/ExcommunicationAndTile/" + e.getId() + ".png")));
					excomm1.setIcon(new ImageIcon(
							excomm1Image.getScaledInstance(-1, (int) (getHeight() / 3), Image.SCALE_SMOOTH)));
				}
				if (e.getPeriod() == 2) {
					BufferedImage excomm2Image = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/ExcommunicationAndTile/" + e.getId() + ".png")));
					excomm2.setIcon(new ImageIcon(
							excomm2Image.getScaledInstance(-1, (int) (getHeight() / 3), Image.SCALE_SMOOTH)));
				}
				if (e.getPeriod() == 3) {
					BufferedImage excomm3Image = ImageIO.read(new File(new File("").getAbsolutePath()
							.concat("/src/images/ExcommunicationAndTile/" + e.getId() + ".png")));
					excomm3.setIcon(new ImageIcon(
							excomm3Image.getScaledInstance(-1, (int) (getHeight() / 3), Image.SCALE_SMOOTH)));
				}
			} catch (IOException i) {
				if (e.getPeriod() == 1) {
					excomm1.setToolTipText(e.toString());
				}
				if (e.getPeriod() == 2) {
					excomm2.setToolTipText(e.toString());
				}
				if (e.getPeriod() == 3) {
					excomm3.setToolTipText(e.toString());
				}
			}
		}

		if (isAdvanced) {
			try {
				try {
					BufferedImage leader1Image = ImageIO.read(
							new File(new File("").getAbsolutePath().concat("/src/images/Lorenzo_Leaders_compressed/"
									+ playerData.getLeaders()[0].getName().replaceAll(" ", "") + ".jpg")));
					leader1.setIcon(new ImageIcon(
							leader1Image.getScaledInstance(-1, (int) (getHeight() / 4), Image.SCALE_SMOOTH)));
					leader1.setToolTipText("<html><body><div><img width='200' height='308' src=\"file:src/images/Lorenzo_Leaders_compressed/" + playerData.getLeaders()[0].getName().replaceAll(" ", "") + ".jpg\"></div></body></html>");

					BufferedImage leader2Image = ImageIO.read(
							new File(new File("").getAbsolutePath().concat("/src/images/Lorenzo_Leaders_compressed/"
									+ playerData.getLeaders()[1].getName().replaceAll(" ", "") + ".jpg")));
					leader2.setIcon(new ImageIcon(
							leader2Image.getScaledInstance(-1, (int) (getHeight() / 4), Image.SCALE_SMOOTH)));
					leader2.setToolTipText("<html><body><div><img width='200' height='308' src=\"file:src/images/Lorenzo_Leaders_compressed/" + playerData.getLeaders()[1].getName().replaceAll(" ", "") + ".jpg\"></div></body></html>");

					BufferedImage leader3Image = ImageIO.read(
							new File(new File("").getAbsolutePath().concat("/src/images/Lorenzo_Leaders_compressed/"
									+ playerData.getLeaders()[2].getName().replaceAll(" ", "") + ".jpg")));
					leader3.setIcon(new ImageIcon(
							leader3Image.getScaledInstance(-1, (int) (getHeight() / 4), Image.SCALE_SMOOTH)));
					leader3.setToolTipText("<html><body><div><img width='200' height='308' src=\"file:src/images/Lorenzo_Leaders_compressed/" + playerData.getLeaders()[2].getName().replaceAll(" ", "") + ".jpg\"></div></body></html>");

					BufferedImage leader4Image = ImageIO.read(
							new File(new File("").getAbsolutePath().concat("/src/images/Lorenzo_Leaders_compressed/"
									+ playerData.getLeaders()[3].getName().replaceAll(" ", "") + ".jpg")));
					leader4.setIcon(new ImageIcon(
							leader4Image.getScaledInstance(-1, (int) (getHeight() / 4), Image.SCALE_SMOOTH)));
					leader4.setToolTipText("<html><body><div><img width='200' height='308' src=\"file:src/images/Lorenzo_Leaders_compressed/" + playerData.getLeaders()[3].getName().replaceAll(" ", "") + ".jpg\"></div></body></html>");

				} catch (IOException i) {
					leader1.setToolTipText(playerData.getLeaders()[0].toString());
					leader2.setToolTipText(playerData.getLeaders()[1].toString());
					leader3.setToolTipText(playerData.getLeaders()[2].toString());
					leader4.setToolTipText(playerData.getLeaders()[3].toString());
				}
			} catch (NotAdvancedPlayerException p) {
				LOGGER.log(Level.WARNING, "This player is not an AdvancedPlayer!", p);
			}
		}

		playerBoardPanel.updateProperties(playerData);
	}

	public PlayerColor getPlayerId() {
		return this.playerId;
	}

}
