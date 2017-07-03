package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ExcommunicationLabel extends JLabel{
	
	private transient Image excomImage;
	private static final transient Logger LOGGER = Logger.getLogger(ExcommunicationLabel.class.getName());
	
	public ExcommunicationLabel (int number, double scaleFactor){
		try {
			
			int width = (int)(330 *scaleFactor);
			int height = (int)(710 * scaleFactor);			
			this.excomImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/ExcommunicationAndTile/").concat(Integer.toString(number)).concat(".png")));
			this.setIcon(new ImageIcon(excomImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
			this.setToolTipText("<html><body><div><img src=\"file:src/images/ExcommunicationAndTile/"+number+".png\"></div><div></div></body></html> ");
			
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Unable to construct excommunication label due to IOException", e);

		}
	}
	

}
