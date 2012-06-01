package dk.dma.aisvirtualnet.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import dk.dma.aisvirtualnet.AisVirtualNet;

public class MainFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private SourcesPanel sourcesPanel;
	private TranspondersPanel transponderPanel;
	private JButton btnExit;

	public MainFrame() {
		super();
		setSize(new Dimension(500, 535));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("AisVirtualNet");
		setLocationRelativeTo(null);
		setVisible(true);
		
		sourcesPanel = new SourcesPanel();
		sourcesPanel.setAlignmentY(0.0f);
		transponderPanel = new TranspondersPanel();		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(transponderPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(sourcesPanel, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(btnExit))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(sourcesPanel, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(transponderPanel, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExit)
					.addContainerGap())
		);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		getContentPane().setLayout(groupLayout);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExit) {
			AisVirtualNet.closeApp();			
		}
	}
	
	public void loadComponents() {
		sourcesPanel.loadComponents();
		transponderPanel.loadComponents();
	}
	
}
