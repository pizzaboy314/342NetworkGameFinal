package client;

import gui.ClientPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sharedNetResources.*;

/**
 * Handles the client side of the
 * communication
 * 
 * @author Ian Swift
 */
public class Client{
	private static int id;
	private int ourID;
	private Socket serverInput;
	private String username;
	private ObjectOutputStream out;
	private ClientPanel panel;
	
	/**
	 * Establish a connect to the IP and
	 * port provided. Also receives the panel to
	 * handle the GUI events 
	 * 
	 * @param ip IP in string form
	 * @param port Port number as an int
	 * @param pn ClientPanel to use
	 */
	public Client(String ip, int port, ClientPanel pn)
	{
		panel = pn;
		ourID = ++id;
		try {
			serverInput = new Socket(ip, port);
			ServerHandler sh = new ServerHandler();
			out = new ObjectOutputStream(serverInput.getOutputStream());
			sh.start();
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(pn, "Client unable to connect, exiting");
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Assumes local host for this connection
	 * 
	 * @param port Port number to use
	 * @param pn ClientPanel to use
	 */
	public Client(int port, ClientPanel pn)//for server
	{
		panel = pn;
		try {
			serverInput = new Socket("localhost", port);
			ServerHandler sh = new ServerHandler();
			out = new ObjectOutputStream(serverInput.getOutputStream());
			sh.start();
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(pn, "Unable to connect, exiting");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private class ServerHandler implements Runnable{
		private Thread t;
		/**
		 * Actual code to run
		 */
		public void run()
		{
			ObjectInputStream in = null;
			try{
				in = new ObjectInputStream(serverInput.getInputStream());
				while(true)
				{
					try {
						System.out.println(username + " about to recieve");
						ServerMessage messageObject = (ServerMessage)in.readObject();
						System.out.println(username + " received a message");
						if (messageObject.isConnectMessage()){
							panel.addUser(messageObject.getSender());
						}else if (messageObject.isDisconnectMessage()){
							panel.rmUser(messageObject.getSender());
						}else if (messageObject.isDrawFromDeckMessage()){
							//TODO receive drawn card
							System.out.println("asdsaRRRRRRRRRRRRR");
							panel.getCLI().sendCard(messageObject.getCard());
						} else {
							panel.printMessage(messageObject.getSender(), messageObject.getMessage());
						}
						System.out.print(messageObject.getSender() + ": " + messageObject.getMessage() + "\n");
					}
					catch (Exception ex){
						JOptionPane.showMessageDialog(panel, "Server shutdown, closing appliation");
						System.exit(0);
						ex.printStackTrace();
						return;
					}
				}
			}
			catch (Exception ex)
			{
				if (in != null){
					try {
						in.close();
					} catch (Exception e) {
						System.err.println("Error while trying to close input stream");
						e.printStackTrace();
					}
				}
				System.err.println("Error while reading input stream");
				ex.printStackTrace();
				JOptionPane.showMessageDialog(panel, "Unexpectedly lost connection");
				System.exit(0);
			}
		}
		
		/**
		 * Starts the server handler
		 */
		public void start()
		{
			t = new Thread(this, "listen");
			t.start();
		}
	}
	
	/**
	 * Send and set the name of this client
	 * @param nm Name of the client
	 */
	public void sendName(String nm){
		username = nm;
		try {
			out.writeObject(new ClientMessage(null, username, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the ObjectOutputStream to use with this client
	 * @return The stream
	 */
	public ObjectOutputStream getObjOutStream(){
		return out;
	}
	
	/**
	 * Sends a standard message
	 * 
	 * @param str Message to send
	 * @param toPerson The sender
	 */
	public void sendMessage(String str, String toPerson){
		ArrayList<String> recipients = new ArrayList<String>();
		if (toPerson != null && !toPerson.equals(""))
			recipients.add(toPerson);
		try {
			out.writeObject(new ClientMessage(recipients, username, str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Sends a standard message
	 * 
	 * @param str Message to send
	 * @param toPerson The sender
	 */
	public void sendGMessage(String str, String toPerson){
		ArrayList<String> recipients = new ArrayList<String>();
		if (toPerson != null && !toPerson.equals(""))
			recipients.add(toPerson);
		try {
			out.writeObject(new ClientMessage(this.username,
					true,false, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getID() {
		// TODO Auto-generated method stub
		System.out.println(ourID);
		return ourID;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return username;
	}
}
