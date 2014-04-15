package server;

import gui.ClientPanel;
import gui.ServerPanel;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import sharedResources.ClientMessage;
import sharedResources.ServerMessage;

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
			// TODO Auto-generated catch block
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
					System.out.println("Server gets: " + myMessage.toString());
					List<Socket> destinations = clientList.getUserSockets(myMessage.getDestinations());
					for (Socket S: destinations)
					{
						ObjectOutputStream out = new ObjectOutputStream(S.getOutputStream());
						out.writeObject(new ServerMessage(this.username, myMessage.getMessage()));
					}
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
