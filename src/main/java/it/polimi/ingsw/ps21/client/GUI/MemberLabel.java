package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class MemberLabel extends JLabel {

	private BufferedImage memberIcon;
	private double scaleFactor;

	public MemberLabel(double scaleFactor) {
		this.scaleFactor = scaleFactor;
		
	}

	

	public void update(PlayerColor player, MembersColor memberColor) {

		try {
			
			this.memberIcon = ImageIO.read(new File((new File("")).getAbsolutePath()
					.concat("/src/images/Lorenzo_Pedine/blue_Player_orange.png")));
			this.setIcon(new ImageIcon(memberIcon.getScaledInstance(resize(200), resize(200), Image.SCALE_SMOOTH)));
			this.setVisible(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void hideMember() {
		this.setVisible(false);
	}
	
	private int resize (int originalSize){
		return (int)(originalSize * scaleFactor);
	}

}
