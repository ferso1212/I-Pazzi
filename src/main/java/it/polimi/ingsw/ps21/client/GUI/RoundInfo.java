package it.polimi.ingsw.ps21.client.GUI;

import javax.swing.JLabel;

public class RoundInfo extends JLabel{
	
	private String matchInfos;
	
	public RoundInfo(String matchInfo){
		
		this.matchInfos = matchInfo;
		this.startRoundInfos();
		
	}
	
	private void startRoundInfos(){
		
		this.setText(matchInfos);
		this.setVisible(true);
		
	}

}
