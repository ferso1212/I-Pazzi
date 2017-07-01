package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private String imagePath = (new File("").getAbsolutePath().concat("/src/images/background.jpg"));
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			
			BufferedImage backgorundImage = ImageIO.read(new File(imagePath));
			int scaleH = (int) (backgorundImage.getHeight() /  screenSize.getHeight());
			int scaleW = (int) (backgorundImage.getWidth() /  screenSize.getWidth());
			g.drawImage(resizeImage(backgorundImage,
					(int) screenSize.getWidth(),
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

	
}
