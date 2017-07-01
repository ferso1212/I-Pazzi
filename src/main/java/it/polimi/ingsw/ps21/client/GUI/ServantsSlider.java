package it.polimi.ingsw.ps21.client.GUI;

import javax.swing.JSlider;

import it.polimi.ingsw.ps21.controller.PlayerData;
import it.polimi.ingsw.ps21.model.properties.PropertiesId;

public class ServantsSlider extends JSlider{
	
	public ServantsSlider (){
		super(JSlider.HORIZONTAL, 0, 1, 0);
		this.setMinorTickSpacing(1);
		this.setMajorTickSpacing(1);
		this.setPaintLabels(true);
		this.setPaintTicks(true);
	}
	
	public void updateSlider (PlayerData playerInfo){
		this.setMaximum(playerInfo.getPropertyValue(PropertiesId.SERVANTS));
	}

}
