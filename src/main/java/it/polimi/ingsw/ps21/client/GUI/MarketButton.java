package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.polimi.ingsw.ps21.view.FamilyMemberData;

public class MarketButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MarketButton.class.getSimpleName());
	private int spaceNumber;
	
	public MarketButton(int spaceNumber){
		
		this.spaceNumber = spaceNumber;
		this.setToolTipText("Proprietà");
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(true);
		
	}

	public int getSpaceNumber() {
		return spaceNumber;
	}
	
	public void update (FamilyMemberData occupant){
		if (occupant.getOwnerId() == null){
			this.setIcon(null);
		} else {
			try {
				BufferedImage memberIcon = ImageIO.read(new File(new File("").getAbsolutePath()
						.concat("/src/images/Lorenzo_Pedine/" + occupant.getOwnerId().toString().toLowerCase() + "_Player_"
								+ occupant.getColor().toString().toLowerCase() + ".png")));
				this.setIcon(
						new ImageIcon(memberIcon.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				this.setToolTipText("Player: " + occupant.getOwnerId().toString().toLowerCase() + "family member color :"
								+ occupant.getColor().toString().toLowerCase());
			}
		}
	}
	
	

}
