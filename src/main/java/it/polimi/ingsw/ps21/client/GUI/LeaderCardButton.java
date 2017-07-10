package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class LeaderCardButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LeaderCardButton.class.getSimpleName());
	private transient BufferedImage leaderImage;
	private double scaleFactor;
	
	public LeaderCardButton(){
		
	}
	
	public void setButton(int leaderPanelHeight){
		try {
			BufferedImage backLeaderImage  = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/Lorenzo_Leaders_compressed/leaders_b_c_00.jpg")));
			this.scaleFactor = (double)(leaderPanelHeight) / (double)(backLeaderImage.getHeight());
			this.setIcon(new ImageIcon(backLeaderImage.getScaledInstance((int)(backLeaderImage.getWidth()*scaleFactor), (int)(backLeaderImage.getHeight()*scaleFactor), Image.SCALE_SMOOTH)));
			this.setSize(new Dimension((int)(backLeaderImage.getWidth()*scaleFactor), (int)(backLeaderImage.getHeight()*scaleFactor)));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to find Leader back Image.", e);
		}
		
		this.setVisible(true);
	}
	
	public void update (String name, String description){
		try {
			this.leaderImage = ImageIO.read(new File((new File("")).getAbsolutePath().concat("/src/images/Lorenzo_Leaders_compressed/").concat(name.replace(" ","")).concat(".jpg")));
			this.setIcon(new ImageIcon(this.leaderImage.getScaledInstance((int)(this.leaderImage.getWidth()*scaleFactor), (int)(this.leaderImage.getHeight()*scaleFactor), Image.SCALE_SMOOTH)));
			this.setToolTipText(description);
		} catch (IOException e) {
			this.setToolTipText(description);
		}
		this.setVisible(true);
	}
	
}
