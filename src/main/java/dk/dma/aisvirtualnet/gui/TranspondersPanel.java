package dk.dma.aisvirtualnet.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import dk.dma.aisvirtualnet.AisVirtualNet;
import dk.dma.aisvirtualnet.transponder.Transponder;

public class TranspondersPanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;

	public TranspondersPanel() {
		super();
		setBorder(new TitledBorder(null, "Virtual transponders", TitledBorder.LEADING, TitledBorder.TOP, null, null));		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		(new Thread(this)).start();
	}
	
	public void loadComponents() {
		removeAll();
		
		// Go thorugh all transponders
		for (int i = 0; i < AisVirtualNet.getTransponders().size(); i++) {
			Transponder trans = AisVirtualNet.getTransponders().get(i);
			SingleTransponderPanel panel = new SingleTransponderPanel(trans);
			add(panel);
		}
		
		SingleTransponderPanel panel = new SingleTransponderPanel();
		add(panel);
		
		revalidate();
		AisVirtualNet.getMainFrame().repaint();		
	}
 
	
	@Override
	public void run() {
		while (true) {
			AisVirtualNet.sleep(2000);
			for (Component component : getComponents()) {
				if (component instanceof SingleTransponderPanel) {
					SingleTransponderPanel panel = (SingleTransponderPanel)component;
					panel.updateStatus();
				}
			}			
		}
		
		
	}


}
