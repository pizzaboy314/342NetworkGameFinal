package server;

import java.net.*;
import java.io.*;
import java.util.*;
import client.*;

public class Server {
	
	private ServerSocket serverSocket;
	private ClientList clientList;
	
	public Server (int port)
	{
		try {
			serverSocket = new ServerSocket(port);
			while(true)
			{
				Socket S = serverSocket.accept();
				Listener L = new Listener(S);
				L.run();
			}
		}
		catch (IOException e)
		{
		}
	}
	
	private class Listener implements Runnable{
		private Thread t;
		private Socket mySocket;
		private ObjectInputStream in;
		private String username;
		
		public Listener(Socket mySocket)
		{
			this.mySocket = mySocket;
			try {
				in = new ObjectInputStream(mySocket.getInputStream());
			}
			catch (IOException e)
			{
			}
		}
		
		public void run()
		{
			try{
				ClientMessage myMessage =(ClientMessage)in.readObject();
				this.username = myMessage.getSender();
			}
			catch (EOFException e)
			{
			}
			catch (Exception ex)
			{
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
				}
				catch (Exception ex)
				{
				}
			}
		}
	}
	
	public static void main(String args[])
	{
		Server myServer = new Server(9001);
	}
}
