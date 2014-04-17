package server;

import gui.ServerPanel;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JOptionPane;

import sharedResources.*;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private ClientList clientList;
	private ServerPanel panel;
	
	public Server (ServerPanel pn)
	{
		panel = pn;
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
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(panel, "Unable to connet client to self");
			e.printStackTrace();
			System.exit(1);
		}
		this.start();
	}
	
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
	
	private class ClientHandler implements Runnable{
		private Thread t;
		private ClientObject clObj;
		private String username;
		
		public ClientHandler(Socket mySocket)
		{
			clObj = new ClientObject("", mySocket);
		}
		
		public void start()
		{
			t = new Thread(this);
			t.start();
		}
		
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
					for (ObjectOutputStream s: destinations)
					{
						s.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
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
