package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
	private GamePanel cPanel;
	//private Server sv;
	private JPanel infoPanel;
	private String ipAddress;
	private int portNumber;
	private JTextField connectionInfoPanel;

	/**
	 * Establishes the server
	 */
	public ServerPanel() {
		new Server(this);
	}
	
	/**
	 * Creates the GUI and creates an
	 * area to show the server info
	 * 
	 * @param port Port to use
	 */
	private void createGUI(int port){
		cPanel = new GamePanel(port);
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
	
	/**
	 * Updates the GUI for the server information
	 * @param ip IP for connection
	 * @param port Port to be used
	 */
	public void setInfo(String ip, int port){
		createGUI(port);
		ipAddress = ip;
		portNumber = port;
		connectionInfoPanel.setText("Connection: " + ipAddress + ", Port: " + portNumber);
	}

}
