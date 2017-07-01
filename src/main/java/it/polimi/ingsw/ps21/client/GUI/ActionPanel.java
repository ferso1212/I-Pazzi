package it.polimi.ingsw.ps21.client.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout.Constraints;
import javax.swing.border.Border;

import it.polimi.ingsw.ps21.controller.MatchData;
import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ActionPanel extends JPanel{
	
	private FamilyMemberPanel memberPanel = new FamilyMemberPanel();
	private JLabel roundInfo;
	private ServantsSlider servantsSlider;
	private PlayerColor playerId;
	
	public ActionPanel (MatchData matchInfo, PlayerColor playerId){
		
		this.playerId = playerId;
		this.setLayout(new BorderLayout(5, 2));
		Constraints borderConstraints = new Constraints();
		roundInfo = new JLabel("This is the " + matchInfo.getRound() + " of the " + matchInfo.getPeriod() + "° period.");

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // used to center label roundinfo
		centerPanel.add(roundInfo);
		this.add(centerPanel, BorderLayout.PAGE_START);
		// LINE 
		
		//PAGE_END
		servantsSlider = new ServantsSlider();
		for(PlayerData p : matchInfo.getPlayers()){
			if (p.getId() == this.playerId)
				servantsSlider.updateSlider(p);
		}
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		GridBagConstraints constraints= new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		temp.add(memberPanel, constraints);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.EAST;

		temp.add(new JLabel("Servants to add:"));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.CENTER;
		temp.add(servantsSlider, constraints);
		this.add(temp, BorderLayout.PAGE_END);
		this.setVisible(true);
	}
	
	public void updateActionPanel(MatchData matchInfo){
		roundInfo.setText("This is the " + matchInfo.getRound() + " of the " + matchInfo.getPeriod() + "° period.");
		for(PlayerData p : matchInfo.getPlayers()){
			if (p.getId() == this.playerId)
				servantsSlider.updateSlider(p);
		}
	}
	
	public MembersColor getChosenColor(){
		return memberPanel.getChosenColor();
	}
	
	public int getChosenServants(){
		return servantsSlider.getValue();
	}

}
