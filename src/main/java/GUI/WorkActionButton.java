package GUI;


import javax.swing.JButton;

import it.polimi.ingsw.ps21.model.actions.WorkType;

public class WorkActionButton extends JButton{
	
	private WorkType type;
	private boolean singleSpace;
	
	public WorkActionButton (WorkType type, boolean singleSpace){
		this.type = type;
		this.singleSpace = singleSpace;
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.addActionListener(new CardListener());
	}

	public WorkType getType() {
		return type;
	}
	
	

}
