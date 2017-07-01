package it.polimi.ingsw.ps21.client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		this.setLayout(new BorderLayout(0, 0));
		
		roundInfo = new JLabel("This is the " + matchInfo.getRound() + " of the " + matchInfo.getPeriod() + "° period.");
		this.add(roundInfo, BorderLayout.PAGE_START);
		this.add(memberPanel, BorderLayout.LINE_START);
		servantsSlider = new ServantsSlider();
		for(PlayerData p : matchInfo.getPlayers()){
			if (p.getId() == this.playerId)
				servantsSlider.updateSlider(p);
		}
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		temp.add(new JLabel("Servants to add:"));
		temp.add(servantsSlider);
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
