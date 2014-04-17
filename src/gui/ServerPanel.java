package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.Server;

/**
 * Creates a GUI for the server and embeds a client for the 
 * server themselves to use
 * 
 * @author Shanon Mathai
 *
 */
@SuppressWarnings("serial")
public class ServerPanel extends JPanel{
	private ClientPanel cPanel;
	//private Server sv;
	private JPanel infoPanel;
	private String ipAddress;
	private int portNumber;
	private JTextField connectionInfoPanel;

	/**
	 * Establish the connection
	 * @param p
	 */
	public ServerPanel() {
		new Server(this);
	}
	
	private void createGUI(int port){
		cPanel = new ClientPanel(port);
		infoPanel = new JPanel();
		connectionInfoPanel = new JTextField();
		connectionInfoPanel.setText("Connecting");
		connectionInfoPanel.setEditable(false);
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
		connectionInfoPanel.setText("Connection: " + ipAddress + ", Port: " + portNumber);
	}

}
