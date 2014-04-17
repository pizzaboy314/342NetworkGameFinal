package server;

import gui.ClientPanel;
import gui.ServerPanel;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import client.Client;
import sharedResources.*;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private ClientList clientList;
	private ServerPanel panel;
	
	public Server (int port, ServerPanel pn)
	{
		panel = pn;
		clientList = new ClientList();
		try {
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		 try {
			 String hostName = InetAddress.getLocalHost().getHostAddress();
			 panel.setInfo(hostName, 9001);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (IOException e) {
			e.printStackTrace();
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
			catch (EOFException e)
			{
				e.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
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
					clientList.userDisconnect(username);
					break;
				}
				catch (EOFException e)
				{
					System.err.println("EOF reached");
					e.printStackTrace();
					break;
				}
				catch (Exception ex)
				{
					System.err.println("Some other error in client handler (run)");
					ex.printStackTrace();
					break;
				}
			}
		}
	}
	
	public static void main(String args[])
	{
		Server myServer = new Server(9001, null);//TODO remove
	}
}
