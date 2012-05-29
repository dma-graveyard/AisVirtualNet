package dk.dma.aisvirtualnet;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import dk.dma.aisvirtualnet.gui.MainFrame;
import dk.dma.aisvirtualnet.network.AisNetwork;
import dk.dma.aisvirtualnet.source.SourceReader;
import dk.dma.aisvirtualnet.transponder.Transponder;


public class AisVirtualNet {
	
	private static Logger LOG;
	private static MainFrame mainFrame;
	private static AisNetwork aisNetwork = new AisNetwork();
	private static SourceReader sourceReader = new SourceReader();
	private static List<Transponder> transponders = new ArrayList<Transponder>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DOMConfigurator.configure("log4j.xml");
		LOG = Logger.getLogger(AisVirtualNet.class);
		LOG.info("Starting AisVirtualNet");
		
		// Create and show GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	
	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainFrame = new MainFrame();
	}
	
	public static MainFrame getMainFrame() {
		return mainFrame;
	}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			LOG.error("Sleep failed: " + e.getMessage());
		}
	}
	
	public static AisNetwork getAisNetwork() {
		return aisNetwork;
	}
	
	public static SourceReader getSourceReader() {
		return sourceReader;
	}
	
	public static List<Transponder> getTransponders() {
		return transponders;
	}

}
