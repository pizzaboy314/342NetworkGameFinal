package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import sharedResources.ClientMessage;
import sharedResources.ServerMessage;

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
				Listener L = new Listener(S);
				L.start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
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
					break;
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					break;
				}
			}
		}
	}
	
	public static void main(String args[])
	{
		Server myServer = new Server(9002);
	}
}
