package dk.dma.aisvirtualnet.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import dk.dma.aisvirtualnet.AisVirtualNet;
import dk.frv.ais.reader.AisReader;

public class SourcesPanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public SourcesPanel() {
		super();
		setAlignmentX(0.0f);
		setBorder(new TitledBorder(null, "Sources", TitledBorder.LEADING, TitledBorder.TOP, null, null));		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		(new Thread(this)).start();
	}
	
	public void loadComponents() {
		removeAll();
		
		
		// Go through all source readers
		for (int i = 0; i < AisVirtualNet.getSourceReader().getReaders().size(); i++) {
			AisReader aisReader = AisVirtualNet.getSourceReader().getReaders().get(i);			
			SingleSourcePanel panel = new SingleSourcePanel(aisReader); 
			add(panel);
		}
		
		SingleSourcePanel panel = new SingleSourcePanel();
		add(panel);
		
		revalidate();
		AisVirtualNet.getMainFrame().repaint();		
	}

	@Override
	public void run() {
		while (true) {
			AisVirtualNet.sleep(2000);
			for (Component component : getComponents()) {
				if (component instanceof SingleSourcePanel) {
					SingleSourcePanel panel = (SingleSourcePanel)component;
					panel.updateStatus();
				}
			}			
		}
		
		
	}
}
