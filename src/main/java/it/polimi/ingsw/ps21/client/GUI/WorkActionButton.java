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

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.view.FamilyMemberData;

public class WorkActionButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(WorkActionButton.class.getSimpleName());
	private WorkType type;
	
	public WorkActionButton (WorkType type, boolean singleSpace){
		this.type = type;
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(true);
	}

	public WorkType getType() {
		return type;
	}
	
	public void update(FamilyMemberData occupant){
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
				LOGGER.log(Level.SEVERE, "Unable to find member icon image.", e);
			}
		}
	}
	
	

}
