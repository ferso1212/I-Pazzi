package it.polimi.ingsw.ps21.client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.StyleContext.SmallAttributeSet;

import it.polimi.ingsw.ps21.model.player.MembersColor;

public class FamilyMemberPanel extends JPanel{
	
	private JButton orangeMember;
	private JButton whiteMember;
	private JButton blackMember;
	private JButton neutralMember;
	private MembersColor chosenMember = null;
	
	public FamilyMemberPanel(){
		
		this.orangeMember = new FamilyMemberButton(new Color(255, 153, 0));
		this.orangeMember.addActionListener(new FamilyListener());
		this.whiteMember = new FamilyMemberButton(Color.WHITE);
		this.whiteMember.addActionListener(new FamilyListener());
		this.blackMember = new FamilyMemberButton(Color.BLACK);
		this.blackMember.addActionListener(new FamilyListener());
		this.neutralMember = new FamilyMemberButton(new Color(240, 220, 130));
		this.neutralMember.addActionListener(new FamilyListener());

		this.setLayout(new GridLayout(1, 4));
		startFamilyMemberPanel();

	}
	
	
	private class FamilyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(orangeMember)){
				chosenMember = MembersColor.ORANGE;
			}
			else if (e.getSource().equals(whiteMember)){
				chosenMember = MembersColor.WHITE;
			}
			else if (e.getSource().equals(blackMember)){
				chosenMember = MembersColor.BLACK;
			}
			else if (e.getSource().equals(neutralMember)){
				chosenMember = MembersColor.NEUTRAL;
			}
		}
		
	}
	
	private void startFamilyMemberPanel(){
		
		this.add(orangeMember);
		this.add(whiteMember);
		this.add(blackMember);
		this.add(neutralMember);
		this.setVisible(true);
	}
	
	public MembersColor getChosenColor(){
		return this.chosenMember;
	}

}
