package server;

import java.util.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import sharedResources.*;

public class ClientList {
	private List<ClientObject> clients;
	
	public ClientList()
	{
		clients = new ArrayList<ClientObject>();
	}
	
	public void userConnect(String username, Socket userSocket) {
		ClientObject client = new ClientObject(username, userSocket);
		try {
			ObjectOutputStream cout = new ObjectOutputStream(userSocket.getOutputStream());
			//1st client
			ObjectOutputStream out;
			for (ClientObject cl : clients){
				System.out.println("about to write to " + username);
				cout.writeObject(new ServerMessage(true, cl.getUsername()));
				cout.flush();
				System.out.println("done writing to this client");
			}
			for (ClientObject cl : clients){
				out = new ObjectOutputStream(cl.getSocket().getOutputStream());
				//1st  client to new ObStream again
				System.out.println("about to write to another client(" + cl.getUsername() + ")");
				out.writeObject(new ServerMessage(true, username));
				System.out.println("done writing to this client(2)");
				//out.flush();
				//out.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clients.add(client);
	}
	
	public void userDisconnect(String _username)
	{
		for (ClientObject thisClient: clients)
		{
			if (thisClient.getUsername().equals(_username))
			{
				clients.remove(thisClient);
				return;
			}
			else
			{
				try{
					ObjectOutputStream out = new ObjectOutputStream(thisClient.getSocket().getOutputStream());
					out.writeObject(new ServerMessage(false, _username));
				}
				catch (Exception ex)
				{
				}
			}
		}
	}
	
	/*
	public void userDisconnect(Socket _userSocket)
	{
		for (ClientObject thisClient: clients)
		{
			if (thisClient.getSocket() == _userSocket)
			{
				clients.remove(thisClient);
				return;
			}
		}
	}*/
	
	public List<Socket> getUserSockets(List<String> usernames)
	{
		List<Socket> myList = new ArrayList<Socket>();
		for (ClientObject thisClient: clients)
		{
			for (String username: usernames)
			if (thisClient.getUsername().equals(username))
			{
				myList.add(thisClient.getSocket());
			}
		}
		return myList;
	}
}
