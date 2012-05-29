package dk.dma.aisvirtualnet.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import dk.dma.aisvirtualnet.AisVirtualNet;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SingleSourcePanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JTextField hostTextField;
	private JTextField portTextField;
	private int id;
	private JButton btnAddDelete;
	private JComboBox typeComboBox;
	
	public SingleSourcePanel() {
		this(-1, "TCP", "", "");
	}
	
	public SingleSourcePanel(int i, String type, String hostname, String port) {
		super();
		setAlignmentY(0.0f);
		setAlignmentX(0.0f);
		this.id = i;
		
		boolean create = (i < 0);
		
		JLabel lblType = new JLabel("Type");
		
		JLabel lblHost = new JLabel("Host");
		
		hostTextField = new JTextField();
		hostTextField.setColumns(10);
		hostTextField.setText(hostname);
		hostTextField.setEnabled(create);
		
		JLabel lblPort = new JLabel("Port");
		
		portTextField = new JTextField();		
		portTextField.setColumns(10);
		portTextField.setText(port);
		portTextField.setEnabled(create);
		
		
		btnAddDelete = new JButton(create ? "Add" : "Delete");
		btnAddDelete.addActionListener(this);
		
		JLabel lblS = new JLabel();
		ImageIcon statusIcon = new ImageIcon(AisVirtualNet.class.getResource("/images/status/ERROR.png"));
		lblS.setIcon(statusIcon);
		lblS.setVisible(!create);
		
		typeComboBox = new JComboBox();
		typeComboBox.setModel(new DefaultComboBoxModel(new String[] {"TCP", "SERIAL"}));
		typeComboBox.setSelectedIndex(type.equals("TCP") ? 0 : 1);
		typeComboBox.setEnabled(create);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblType)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHost)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(hostTextField, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(lblPort)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(portTextField, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblS, GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
							.addGap(59))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnAddDelete)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(hostTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHost)
						.addComponent(portTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPort)
						.addComponent(lblS)
						.addComponent(btnAddDelete))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddDelete) {
			if (id >= 0) {
				AisVirtualNet.getSourceReader().removeReader(id);
				this.setVisible(false);
			} else {
				AisVirtualNet.addReader((String)typeComboBox.getSelectedItem(), hostTextField.getText(), portTextField.getText());
			}
		}
		
	}
}
