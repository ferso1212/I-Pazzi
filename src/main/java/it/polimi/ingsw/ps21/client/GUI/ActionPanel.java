package it.polimi.ingsw.ps21.client.GUI;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps21.controller.MatchData;

public class ActionPanel extends JPanel{
	
	private FamilyMemberPanel memberPanel = new FamilyMemberPanel();
	private JLabel roundInfo;
	
	public ActionPanel (MatchData matchInfo){
		
		this.setLayout(new BorderLayout(0, 0));
		
		this.add(memberPanel, BorderLayout.LINE_START);
	}

}
