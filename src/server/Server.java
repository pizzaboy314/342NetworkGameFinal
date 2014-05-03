package server;

import gui.ServerPanel;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import sharedNetResources.*;

/**
 * Establishes the server for the clients
 * 
 * @author Ian Swift
 */
public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private ClientList clientList;
	private ServerPanel panel;
	private int numplayers;
	
	/**
	 * Establishes the threads to handle the clients
	 * 
	 * @param pn Sever panel to send output to
	 */
	public Server (ServerPanel pn)
	{
		panel = pn;
		numplayers = 0;
		clientList = new ClientList();
		try {
			serverSocket = new ServerSocket(0);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(panel, "Unrecoverablable establishing a server socket");
			e.printStackTrace();
			System.exit(1);
		}
		 try {
			 String hostName = InetAddress.getLocalHost().getHostAddress();
			 int port = serverSocket.getLocalPort();
			 panel.setInfo(hostName, port);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(panel, "Unable to connet client to self");
			e.printStackTrace();
			System.exit(1);
		}
		this.start();
	}
	
	/**
	 * Runs the thread to accept new connections
	 */
	@Override
	public void run(){
		try {
			while (true) {
				Socket S;
				S = serverSocket.accept();
				ClientHandler ch = new ClientHandler(S);
				ch.start();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "Unrecoverablable error in input stream");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Handles the connect with the client
	 * 
	 * @author Ian Swift
	 *
	 */
	private class ClientHandler implements Runnable{
		private Thread t;
		private ClientObject clObj;
		private String username;
		
		/**
		 * Create a ClientObject that establishes the 
		 * in/out streams
		 * 
		 * @param mySocket Socket to the client connection
		 */
		public ClientHandler(Socket mySocket)
		{
			clObj = new ClientObject("", mySocket, ++numplayers);
		}
		
		/**
		 * Start this thread
		 */
		public void start()
		{
			t = new Thread(this);
			t.start();
		}
		
		/**
		 * Allow this dedicated thread to handle input from this client
		 */
		public void run()
		{
			try{
				ClientMessage myMessage =(ClientMessage)clObj.getObIn().readObject();
				this.username = myMessage.getSender();
				clObj.setName(username);
				System.out.println("Server gets: " + myMessage.toString());
				clientList.userConnect(username, clObj);
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(panel, "Unrecoverablable error in input stream");
				e.printStackTrace();
				System.exit(1);
			}
			while(true)
			{ 	
				try{
					ClientMessage myMessage =(ClientMessage)clObj.getObIn().readObject();
					System.out.println("Server gets: " + myMessage.toString());
					List<ObjectOutputStream> destinations = clientList.getUserOutStreams(myMessage.getDestinations());
					
					if (myMessage.isDrawFromDeckMessage()){
						System.out.println("Read as a draw message");
						for (ObjectOutputStream s: destinations) {
							s.writeObject(new ServerMessage(this.username,
									myMessage.isDrawFromDeckMessage(),
									myMessage.isDrawFromDiscardMessage(),
									myMessage.isGameoverMessage(),
									true));
							//s.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
						}
					} else if (myMessage.isInsertDiscardMessage()){
						System.out.println("Read as a insert discard message");
						System.out.println(myMessage.getCard().getColor() + " " + myMessage.getCard().getValue());
						List<String> nms = new ArrayList<String>();
						destinations = clientList.getUserOutStreams(null);
						for (ObjectOutputStream s: destinations) {
							s.writeObject(new ServerMessage(this.username,
									myMessage.isDrawFromDeckMessage(),
									myMessage.isDrawFromDiscardMessage(),
									myMessage.isGameoverMessage(),
									true));
							//s.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
						}
					} else {
						System.out.println("Read as other message");
						for (ObjectOutputStream s: destinations) {
							s.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
							//s.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
						}
					}
				}
				catch (SocketException e)
				{
					System.out.println("Disconnecting user: " + username);
					clientList.userDisconnect(username);
					break;
				}
				catch (Exception ex)
				{
					System.err.println("Some other error in client handler (run)");
					JOptionPane.showMessageDialog(panel, "Unrecoverablable error in input stream");
					ex.printStackTrace();
					System.exit(1);
					break;
				}
			}
		}
	}
}
