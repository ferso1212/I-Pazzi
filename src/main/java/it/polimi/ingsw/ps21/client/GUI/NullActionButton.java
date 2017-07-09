package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class NullActionButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullActionButton(PlayerColor playerId) {
		super();
		BufferedImage image;
		try {
			image = ImageIO.read(new File(new File("").getAbsolutePath().concat(
					"/src/images/Lorenzo_Pedine/" + playerId.toString().toLowerCase() + "_Player_noaction.png")));
			this.setIcon(new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			this.setText("Null Action");
		}
		this.setToolTipText("Null Action");

		this.setPreferredSize(new Dimension(100, 100));

	}
}
