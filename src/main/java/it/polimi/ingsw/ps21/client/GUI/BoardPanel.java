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
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage boardImage;
	private JLabel orangeDice;
	private JLabel blackDice;
	private JLabel whiteDice;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private double scaleFactor;

	public BoardPanel(String boardPath) {
		super(true); // crea un JPanel con doubleBuffered true
		try {
			setImage(ImageIO.read(new File(boardPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDiceLabel();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private void setDiceLabel(){
		
		this.setLayout(null);
		
		orangeDice = new JLabel("5");
		orangeDice.setBackground(Color.WHITE);
		orangeDice.setOpaque(true);
		orangeDice.setHorizontalAlignment(JLabel.CENTER);
		whiteDice = new JLabel("5");
		whiteDice.setBackground(Color.WHITE);
		whiteDice.setOpaque(true);
		whiteDice.setHorizontalAlignment(JLabel.CENTER);
		blackDice = new JLabel("5");
		blackDice.setBackground(Color.WHITE);
		blackDice.setOpaque(true);
		blackDice.setHorizontalAlignment(JLabel.CENTER);

		
		this.add(orangeDice).setBounds(resize(2910), resize(6200), resize(130), resize(130));
		this.add(whiteDice).setBounds(resize(3365), resize(6200), resize(130), resize(130));
		this.add(blackDice).setBounds(resize(3810), resize(6200), resize(130), resize(130));
	}
}