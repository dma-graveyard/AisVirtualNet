package dk.dma.aisvirtualnet.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import dk.dma.aisvirtualnet.AisVirtualNet;
import dk.dma.aisvirtualnet.transponder.Transponder;

public class SingleTransponderPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private Transponder transponder;
	private JTextField mmsiTextField;
	private JTextField portTextField;
	private JLabel lblStatus;
	private JLabel lblOwmMsgInterval;
	private JLabel lblPort;
	private JLabel lblMmsi;
	private JButton btnAddDelete;
	
	public SingleTransponderPanel() {
		this(null);
	}
	
	public SingleTransponderPanel(Transponder transponder) {
		super();
		this.transponder = transponder;
		
		String mmsi = "";
		String port = "";
		String omi = "5"; 
				
		if (transponder != null) {
			mmsi = Long.toString(transponder.getMmsi());
			port = Integer.toString(transponder.getTcpPort());
			omi = Integer.toString(transponder.getForceOwnInterval());
		}				
		boolean create = (transponder == null);
		
		lblMmsi = new JLabel("MMSI");
		
		mmsiTextField = new JTextField();
		mmsiTextField.setColumns(10);
		mmsiTextField.setText(mmsi);
		
		lblPort = new JLabel("Port");
		
		portTextField = new JTextField();
		portTextField.setColumns(10);
		portTextField.setText(port);
		
		lblOwmMsgInterval = new JLabel("Owm msg interval");
		
		JComboBox owmMsgIntervalcomboBox = new JComboBox();
		owmMsgIntervalcomboBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "5", "10"}));
		owmMsgIntervalcomboBox.setSelectedItem(omi);
		
		lblStatus = new JLabel();
		ImageIcon statusIcon = new ImageIcon(AisVirtualNet.class.getResource("/images/status/UNKNOWN.png"));
		lblStatus.setIcon(statusIcon);
		lblStatus.setVisible(!create);
		
		btnAddDelete = new JButton(create ? "Add" : "Delete");
		btnAddDelete.addActionListener(this);		
		
		setAlignmentY(0.0f);
		setAlignmentX(0.0f);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMmsi)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mmsiTextField, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPort)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(portTextField, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblOwmMsgInterval)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(owmMsgIntervalcomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblStatus)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(btnAddDelete)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMmsi)
						.addComponent(mmsiTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPort)
						.addComponent(portTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOwmMsgInterval)
						.addComponent(owmMsgIntervalcomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddDelete)
						.addComponent(lblStatus))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void updateStatus() {
		if (transponder == null) return;
		if (transponder.isConnected()) {
			lblStatus.setIcon(new ImageIcon(AisVirtualNet.class.getResource("/images/status/OK.png")));
		} else {
			lblStatus.setIcon(new ImageIcon(AisVirtualNet.class.getResource("/images/status/PARTIAL.png")));
		}		
	}
	
	


}
