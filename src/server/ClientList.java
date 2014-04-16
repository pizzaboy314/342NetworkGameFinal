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
	
	public void userConnect(String username, ClientObject client) {
		try {
			//1st client
			for (ClientObject cl : clients){
				System.out.println("about to write to " + username);
				client.getObOut().writeObject(new ServerMessage(true, cl.getUsername()));
				System.out.println("done writing to this client");
			}
			for (ClientObject cl : clients){
				//1st  client to new ObStream again
				System.out.println("about to write to another client(" + cl.getUsername() + ")");
				cl.getObOut().writeObject(new ServerMessage(true, username));
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
	
	public ArrayList<Socket> getUserSockets(List<String> usernames)
	{
		ArrayList<Socket> myList = new ArrayList<Socket>();
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
	
	public ArrayList<ObjectOutputStream> getUserOutStreams(List<String> usernames)
	{
		ArrayList<ObjectOutputStream> myList = new ArrayList<ObjectOutputStream>();
		for (ClientObject thisClient: clients)
		{
			for (String username : usernames) {
				if (thisClient.getUsername().equals(username)) {
					myList.add(thisClient.getObOut());
				}
			}
		}
		return myList;
	}
}
