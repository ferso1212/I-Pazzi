package it.polimi.ingsw.ps21.client.GUI;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.polimi.ingsw.ps21.controller.FamilyMemberData;
import it.polimi.ingsw.ps21.model.actions.WorkType;

public class WorkActionButton extends JButton{
	
	private WorkType type;
	private boolean singleSpace;
	
	public WorkActionButton (WorkType type, boolean singleSpace){
		this.type = type;
		this.singleSpace = singleSpace;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
