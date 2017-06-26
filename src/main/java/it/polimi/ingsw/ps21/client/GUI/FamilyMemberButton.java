package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

public class FamilyMemberButton extends JButton {

	private Color color;

	public FamilyMemberButton(Color color) {
		super();
		this.color = color;
		this.setPreferredSize(new Dimension(100, 70));
		this.isVisible();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int radius = 0;
		int nXPosition = radius;
		int nYPosition = radius;
		int nWidth = this.getWidth() - radius * 2;
		int nHeight = this.getHeight() - radius * 2;

		g.setColor(color);
		g.drawOval(0, 0, nWidth, nHeight);
		g.fillOval(0, 0, nWidth, nHeight);

	}

}
