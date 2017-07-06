package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class FamilyMemberButton extends JButton {


	public FamilyMemberButton(PlayerColor id, MembersColor color) {
		super();
		BufferedImage image;
		try {
			image = ImageIO.read(new File(new File("").getAbsolutePath()
					.concat("/src/images/Lorenzo_Pedine/"
							+ id.toString().toLowerCase() + "_Player_"
							+ color.toString().toLowerCase() + ".png")));
			this.setIcon(
					new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			this.setText("Member " + color);
		}
		this.setToolTipText("Member " + color );
		
		
		this.setPreferredSize(new Dimension(100, 100));
		
	}
	
}
