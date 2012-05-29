package dk.dma.aisvirtualnet.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class MainFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private SourcesPanel sourcesPanel;
	private TranspondersPanel transponderPanel;
	private JButton btnExit;
	private JButton btnApply;

	public MainFrame() {
		super();
		setSize(new Dimension(500, 600));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("AisVirtualNet");
		setLocationRelativeTo(null);
		setVisible(true);
		
		sourcesPanel = new SourcesPanel();
		transponderPanel = new TranspondersPanel();		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnApply = new JButton("Apply");
		btnApply.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(transponderPanel, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
						.addComponent(sourcesPanel, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnApply)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExit)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(sourcesPanel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(transponderPanel, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnExit)
						.addComponent(btnApply))
					.addContainerGap())
		);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		getContentPane().setLayout(groupLayout);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExit) {
			System.exit(0);
		}
		else if (e.getSource() == btnApply) {
			// TODO Apply changes
		}
		
	}
}
