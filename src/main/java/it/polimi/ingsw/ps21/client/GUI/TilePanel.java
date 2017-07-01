package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TilePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient BufferedImage tileImage;

	public TilePanel(String tileNumber) {
		super(true); // crea un JPanel con doubleBuffered true
		try {
			setImage(ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/ExcommunicationAndTile/Tile").concat(tileNumber).concat(".png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setImage(BufferedImage img) {
		this.tileImage = img;
	}

	@Override
 	public void paintComponent(Graphics g){
 		super.paintComponent(g);
 		try {
			g.drawImage(resizeImage(tileImage, (int)(this.tileImage.getWidth() * this.getHeight() / this.tileImage.getHeight()), this.getHeight(), BufferedImage.TYPE_INT_RGB), 0, 0, null);
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
 	
 	public BufferedImage getTileImage(){
 		return this.tileImage;
 	}

}
