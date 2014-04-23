package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;

/**
 * The game panel to be added to a frame, handles the input and the output
 * 
 * @author Shanon Mathai
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
	private Client clSocket;
	private String myName, ip, port;

	public GamePanel() {
		boolean hasConnection = false;
		while (!hasConnection){
			try{
				promptForConnection();
				clSocket = new Client(ip, Integer.parseInt(port), this);
				hasConnection = true;
			}catch (Exception e){
				JOptionPane.showMessageDialog(this, "Unable to connect, retry");
			}
		}
		promptForName();
		
		init();
	}
	
	/**
	 * Establishes the connection for the panel to use
	 * for input and output
	 * @param pt
	 */
	public GamePanel(int pt) {//for server
		promptForName();
		clSocket = new Client(pt, this);
		init();
	}
	
	/**
	 * Prompt the user for the connection
	 */
	private void promptForConnection(){
		String ipadd = JOptionPane.showInputDialog("Enter IP address: ");
		if (ipadd == null || ipadd.equals("")){
			JOptionPane.showMessageDialog(this, "Exiting");
			System.exit(0);
		}
		ip = ipadd;
		String pt = JOptionPane.showInputDialog("Enter Port number: ");
		if (ipadd == null || pt.equals("")){
			JOptionPane.showMessageDialog(this, "Exiting");
			System.exit(0);
		}
		port = pt;
	}
	
	/**
	 * Prompt the user for their name
	 */
	private void promptForName(){
		String name = JOptionPane.showInputDialog("Enter Username: ");
		if (name == null || name.equals("")){
			JOptionPane.showMessageDialog(this, "Exiting");
			System.exit(0);
		}
		myName = name;
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Initializes the variables to use for the connection
	 */
	private void init(){
		setLayout(new BorderLayout());
		clSocket.sendName(myName);
		this.setPreferredSize(new Dimension(300, 300));
		this.setBackground(Color.GREEN);
	}
	
	/** Display the formated message 
	 * 
	 * @param from Sender of the message
	 * @param message Message to be sent
	 */
	public synchronized void printMessage(String from, String message){
	}
	
	/**
	 * Adds the user to the side panel
	 * @param nm Name of new user
	 */
	public synchronized void addUser(String nm){
	}

	/**
	 * Removes the user from the side panel
	 * @param nm Name of the user to remove
	 */
	public synchronized void rmUser(String nm) {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
