package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ExcommunicationLabel extends JLabel{
	
	private Image excomImage;
	
	public ExcommunicationLabel (String path, double scaleFactor){
		try {
			
			int width = (int)(330 *scaleFactor);
			int height = (int)(710 * scaleFactor);			
			this.excomImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/").concat(path)));
			this.setIcon(new ImageIcon(excomImage.getScaledInstance(width, height, Image.SCALE_DEFAULT)));
			this.setToolTipText("<html><body><div><img src=\"file:"+path+"\"></div><div></div></body></html> ");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
