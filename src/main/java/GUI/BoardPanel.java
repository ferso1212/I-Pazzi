package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage boardImage; 
	
	public BoardPanel(String boardPath){
		super(true);  //crea un JPanel con doubleBuffered true
		try {
			setImage(ImageIO.read(new File(boardPath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImage(BufferedImage img){
		this.boardImage = img;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.boardImage, 0, 0, null);
	}

}
