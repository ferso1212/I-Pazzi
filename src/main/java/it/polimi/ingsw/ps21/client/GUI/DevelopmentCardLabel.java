package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class DevelopmentCardLabel extends JButton{
	
	private Image cardImage;
	private String path;
	
	public DevelopmentCardLabel(String path, double scaleFactor){
		this.path = path;
		try {
			int width = (int)(470 *scaleFactor);
			int height = (int)(754 * scaleFactor);
			this.cardImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/").concat(path)));
			this.setIcon(new ImageIcon(cardImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
			this.setToolTipText("<html><body><div><img src=\"file:"+path+"\"></div></div></body></html> ");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Image getCardImage() {
		return cardImage;
	}
	
	public Container getFather(){
		return this.getParent();
	}
	
	public String getPath(){
		return this.path;
	}

}
