package dk.dma.aisvirtualnet.gui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TranspondersPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public TranspondersPanel() {
		super();
		setBorder(new TitledBorder(null, "Virtual transponders", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

}
