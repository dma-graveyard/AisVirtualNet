package dk.dma.aisvirtualnet;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dk.dma.aisvirtualnet.gui.MainFrame;

public class AisVirtualNet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
        
        // Create and set up the main window        
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
