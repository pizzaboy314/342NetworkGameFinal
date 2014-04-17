package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.Client;

/**
 * The client panel to be added to a frame, handles the input and the output
 * 
 * @author Shanon Mathai
 *
 */
@SuppressWarnings("serial")
public class ClientPanel extends JPanel implements ListSelectionListener, ActionListener {
	private DefaultListModel<String> clListModel, outputModel;//list to add string
	private Client clSocket;
	private JList<String> cList, oList;//for display count
	private JTextField input;
	private JScrollPane listWindow, outputWindow;
	private JScrollBar vertScrollBar;
	private String myName, ip, port;

	public ClientPanel() {
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
	public ClientPanel(int pt) {//for server
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
	
	/**
	 * Initializes the variables to use for the connection
	 */
	private void init(){
		setLayout(new BorderLayout());
		clSocket.sendName(myName);
		clListModel = new DefaultListModel<String>();
		outputModel = new DefaultListModel<String>();
		cList = new JList<String>(clListModel);
		cList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		cList.setVisibleRowCount(10);
		oList = new JList<String>(outputModel);
		oList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		oList.setVisibleRowCount(10);
		listWindow = new JScrollPane(cList);
		listWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		outputWindow = new JScrollPane(oList);
		outputWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new BorderLayout());
		ioPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		input = new JTextField();
		input.addActionListener(this);
		vertScrollBar = outputWindow.getVerticalScrollBar();
		
		ioPanel.add(outputWindow, BorderLayout.CENTER);
		ioPanel.add(input, BorderLayout.SOUTH);
		add(ioPanel, BorderLayout.CENTER);
		add(listWindow, BorderLayout.EAST);
	}
	
	/** Display the formated message 
	 * 
	 * @param from Sender of the message
	 * @param message Message to be sent
	 */
	public synchronized void printMessage(String from, String message){
		outputModel.addElement("["+ from + "]: " + message);
		vertScrollBar.setValue( vertScrollBar.getMaximum() );
	}
	
	/**
	 * Adds the user to the side panel
	 * @param nm Name of new user
	 */
	public synchronized void addUser(String nm){
		clListModel.addElement(nm);
	}

	/**
	 * Removes the user from the side panel
	 * @param nm Name of the user to remove
	 */
	public synchronized void rmUser(String nm) {
		clListModel.removeElement(nm);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Selection events
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == input){
			printMessage("ME", input.getText());
			for (String nm: cList.getSelectedValuesList())
				clSocket.sendMessage(input.getText(), nm);
			input.setText("");
		}
		
	}
}
