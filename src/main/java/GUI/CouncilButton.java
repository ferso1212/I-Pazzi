package GUI;

import javax.swing.JButton;

public class CouncilButton extends JButton {

	public CouncilButton() {

		this.setToolTipText("Proprietà");
		this.addActionListener(new CardListener());
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);

	}

}
