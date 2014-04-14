package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerPanel extends JPanel{
	private ClientPanel cPanel;
	private JPanel infoPanel;

	public ServerPanel() {
		int port  = 9001;
		cPanel = new ClientPanel(port);
		infoPanel = new JPanel();
		JLabel temp = new JLabel();
		temp.setText("Connection Info will be here");
		infoPanel.add(temp);
		infoPanel.setLayout(new FlowLayout());
		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(cPanel, BorderLayout.CENTER);
	}

}
