package GUI;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ExcommunicationLabel extends JLabel{
	
	private Image excomImage;
	private static int width = 50;
	private static int height = 100;
	
	public ExcommunicationLabel (String path){
		try {
			
			this.excomImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/").concat(path)));
			this.setIcon(new ImageIcon(excomImage.getScaledInstance(ExcommunicationLabel.width, ExcommunicationLabel.height, Image.SCALE_DEFAULT)));
			this.setToolTipText("<html><body><div><img src=\"file:"+path+"\"></div><div></div></body></html> ");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
