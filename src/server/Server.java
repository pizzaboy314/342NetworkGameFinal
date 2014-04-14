package server;

import java.net.*;
import java.io.*;
import java.util.*;
import client.*;
import sharedResources.*;

public class Server {
	
	private ServerSocket serverSocket;
	private ClientList clientList;
	
	public Server (int port)
	{
		clientList = new ClientList();
		try {
			serverSocket = new ServerSocket(port);
			while(true)
			{
				Socket S = serverSocket.accept();
				ClientHandler ch = new ClientHandler(S);
				ch.start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class ClientHandler implements Runnable{
		private Thread t;
		private Socket mySocket;
		private ObjectInputStream in;
		private String username;
		
		public ClientHandler(Socket mySocket)
		{
			this.mySocket = mySocket;
			try {
				in = new ObjectInputStream(mySocket.getInputStream());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void start()
		{
			t = new Thread(this);
			t.start();
		}
		
		public void run()
		{
			try{
				ClientMessage myMessage =(ClientMessage)in.readObject();
				this.username = myMessage.getSender();
				System.out.println("Server gets: " + myMessage.toString());
				clientList.userConnect(username, mySocket);
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
					ClientMessage myMessage =(ClientMessage)in.readObject();
					List<Socket> destinations = clientList.getUserSockets(myMessage.getDestinations());
					for (Socket S: destinations)
					{
						ObjectOutputStream out = new ObjectOutputStream(S.getOutputStream());
						out.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
					}
				}
				catch (EOFException e)
				{
					e.printStackTrace();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String args[])
	{
		Server myServer = new Server(9002);
	}
}
