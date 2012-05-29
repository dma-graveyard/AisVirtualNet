package dk.dma.aisvirtualnet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import dk.dma.aisvirtualnet.gui.MainFrame;
import dk.dma.aisvirtualnet.network.AisNetwork;
import dk.dma.aisvirtualnet.source.SourceReader;
import dk.dma.aisvirtualnet.transponder.Transponder;
import dk.frv.ais.reader.AisReader;
import dk.frv.ais.reader.AisSerialReader;
import dk.frv.ais.reader.AisTcpReader;

public class AisVirtualNet {

	private static Logger LOG;
	private static MainFrame mainFrame;
	private static AisNetwork aisNetwork = new AisNetwork();
	private static SourceReader sourceReader = new SourceReader();
	private static List<Transponder> transponders = new ArrayList<Transponder>();
	private static Settings settings = new Settings();

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

		// Load configuration
		String settingsFile = "aisvirtualnet.properties";
		if (args.length > 0) {
			settingsFile = args[0];
		}
		try {
			settings.load(settingsFile);
		} catch (IOException e) {
			LOG.error("Failed to load settings: " + e.getMessage());
			System.exit(-1);
		}

		// Start reading from sources
		sourceReader.setAisNetwork(aisNetwork);
		sourceReader.start();

	}

	public static void closeApp() {
		LOG.info("Closing application");
		System.exit(0);
	}

	private static void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOG.error("Failed to set look and feed: " + e.getMessage());
		}
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		LOG.info("Creating GUI");
		mainFrame = new MainFrame();
		mainFrame.loadComponents();
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
	
	public static Settings getSettings() {
		return settings;
	}

	public static void addReader(String type, String hostname, String port) {
		AisReader reader;		
		if (type.equals("TCP")) {
			AisTcpReader aisTcpReader = new AisTcpReader();
			aisTcpReader.setHostname(hostname);
			aisTcpReader.setPort(Integer.parseInt(port));
			reader = aisTcpReader;
		} else {
			AisSerialReader serialReader = new AisSerialReader();
			serialReader.setPortName(port);
			reader = serialReader;
		}		
		getSourceReader().addReader(reader);
		mainFrame.loadComponents();
	}
	

}
