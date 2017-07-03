package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.controller.MatchData;

public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(BoardPanel.class.getName());
	private static final long serialVersionUID = 1L;
	private transient BufferedImage boardImage;
	private JLabel orangeDiceLabel;
	private JLabel blackDiceLabel;
	private JLabel whiteDiceLabel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private double scaleFactor;

	public BoardPanel(String boardPath, int blackDice, int whiteDice, int orangeDice) {
		super(true); // crea un JPanel con doubleBuffered true
		try {
			setImage(ImageIO.read(new File(boardPath)));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to set panel image due to IOException", e);
		}
		setDiceLabel(blackDice, whiteDice, orangeDice);
		this.setOpaque(false);
	}

	public void setImage(BufferedImage img) {
		this.boardImage = img;
		this.scaleFactor = screenSize.getHeight() / this.boardImage.getHeight();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			g.drawImage(resizeImage(boardImage,
					(int) (this.boardImage.getWidth() * screenSize.getHeight() / this.boardImage.getHeight()),
					this.getHeight(), BufferedImage.TYPE_INT_RGB), 0, 0, null);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to draw panel image due to IOException", e);
		}
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) throws IOException {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}
	
	private int resize(int originalSize){
		return (int)(originalSize * this.scaleFactor);
	}

	private void setDiceLabel(int blackDice, int whiteDice, int orangeDice){
		
		this.setLayout(null);
		
		orangeDiceLabel = new JLabel(Integer.toString(orangeDice));
		orangeDiceLabel.setBackground(Color.WHITE);
		orangeDiceLabel.setOpaque(true);
		orangeDiceLabel.setHorizontalAlignment(JLabel.CENTER);
		whiteDiceLabel = new JLabel(Integer.toString(whiteDice));
		whiteDiceLabel.setBackground(Color.WHITE);
		whiteDiceLabel.setOpaque(true);
		whiteDiceLabel.setHorizontalAlignment(JLabel.CENTER);
		blackDiceLabel = new JLabel(Integer.toString(blackDice));
		blackDiceLabel.setBackground(Color.WHITE);
		blackDiceLabel.setOpaque(true);
		blackDiceLabel.setHorizontalAlignment(JLabel.CENTER);

		
		this.add(blackDiceLabel).setBounds(resize(2910), resize(6200), resize(130), resize(130));
		this.add(whiteDiceLabel).setBounds(resize(3365), resize(6200), resize(130), resize(130));
		this.add(orangeDiceLabel).setBounds(resize(3810), resize(6200), resize(130), resize(130));
	}
}