package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.StyleContext.SmallAttributeSet;

public class FamilyMemberPanel extends JPanel{
	
	private JButton orangeMember;
	private JButton whiteMember;
	private JButton blackMember;
	private JButton neutralMember;	
	
	public FamilyMemberPanel(){
		
		this.orangeMember = new FamilyMemberButton(new Color(255, 153, 0));
		this.whiteMember = new FamilyMemberButton(Color.WHITE);
		this.blackMember = new FamilyMemberButton(Color.BLACK);
		this.neutralMember = new FamilyMemberButton(new Color(240, 220, 130));
		this.setLayout(new GridLayout(4, 1));
		startFamilyMemberPanel();

	}
	
	private void startFamilyMemberPanel(){
		
		this.add(orangeMember);
		this.add(whiteMember);
		this.add(blackMember);
		this.add(neutralMember);
		this.setVisible(true);
	}

}
