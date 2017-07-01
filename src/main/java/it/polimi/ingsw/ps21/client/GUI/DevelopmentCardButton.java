package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class DevelopmentCardButton extends JButton{
	
	private static final Logger LOGGER = Logger.getLogger(DevelopmentCardButton.class.getSimpleName());
	private transient Image cardImage;
	private String path;
	
	public DevelopmentCardButton(String name, String description, double scaleFactor){
		this.path = path;
		try {
			int width = (int)(470 *scaleFactor);
			int height = (int)(754 * scaleFactor);
			this.cardImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/DevelopmentCards/").concat(name.replace(" ","")).concat(".png")));
			this.setIcon(new ImageIcon(cardImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
			this.setToolTipText("<html><body><div><img src=\"file:src/images/DevelopmentCards/"+name.replace(" ", "")+".png\"></div><div>"+ description + "</div></body></html> ");
			
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Error finding card name." + name, e);
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
