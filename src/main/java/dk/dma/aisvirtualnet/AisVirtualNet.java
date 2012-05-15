package dk.dma.aisvirtualnet;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
		Frame mainFrame = new Frame();
		mainFrame.setVisible(true);
	}

}
