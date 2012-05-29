package dk.dma.aisvirtualnet.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import dk.dma.aisvirtualnet.AisVirtualNet;
import dk.frv.ais.reader.AisReader;
import dk.frv.ais.reader.AisSerialReader;
import dk.frv.ais.reader.AisTcpReader;

public class SourcesPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public SourcesPanel() {
		super();
		setAlignmentX(0.0f);
		setBorder(new TitledBorder(null, "Sources", TitledBorder.LEADING, TitledBorder.TOP, null, null));		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
	}
	
	public void loadComponents() {
		removeAll();
		
		
		// Go through all source readers
		for (int i = 0; i < AisVirtualNet.getSourceReader().getReaders().size(); i++) {
			AisReader aisReader = AisVirtualNet.getSourceReader().getReaders().get(i);
			
			String type = null;
			String hostname = "N/A";
			String port = null;
			if (aisReader instanceof AisTcpReader) {
				AisTcpReader aisTcpReader = (AisTcpReader)aisReader;
				type =  "TCP";
				hostname = aisTcpReader.getHostname();
				port = Integer.toString(aisTcpReader.getPort());
			}
			else if (aisReader instanceof AisSerialReader) {
				AisSerialReader aisSerialReader = (AisSerialReader)aisReader;
				type = "SERIAL";
				port = aisSerialReader.getPortName();
			}
			
			if (type == null) {
				continue;
			}
			SingleSourcePanel panel = new SingleSourcePanel(i, type, hostname, port); 
			add(panel);
		}
		
		SingleSourcePanel panel = new SingleSourcePanel();
		add(panel);
		
		revalidate();
		AisVirtualNet.getMainFrame().repaint();		
	}
}
