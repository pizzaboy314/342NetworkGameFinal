package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import server.Server;

public class ServerPanel extends JPanel{
	private ClientPanel cPanel;
	private Server sv;
	private JPanel infoPanel;
	private String ipAddress;
	private int portNumber;
	private JLabel connectionInfoPanel;

	public ServerPanel() {
		sv = new Server(this);
	}
	
	private void createGUI(int port){
		cPanel = new ClientPanel(port);
		infoPanel = new JPanel();
		connectionInfoPanel = new JLabel();
		connectionInfoPanel.setText("Connecting....");
		infoPanel.add(connectionInfoPanel);
		infoPanel.setLayout(new FlowLayout());
		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.NORTH);
		this.add(cPanel, BorderLayout.CENTER);
	}
	
	public void setInfo(String ip, int port){
		createGUI(port);
		ipAddress = ip;
		portNumber = port;
		connectionInfoPanel.setText("Connection: " + ip + ", Port: " + port);
	}

}
