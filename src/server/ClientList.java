package server;

import java.util.*;
import java.io.IOException;
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
			for (ClientObject cl : clients){
				client.getObOut().writeObject(new ServerMessage(true, cl.getUsername()));
			}
			for (ClientObject cl : clients){
				cl.getObOut().writeObject(new ServerMessage(true, username));
			}
			
		} catch (IOException e) {
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
					thisClient.getObOut().writeObject(new ServerMessage(false, _username));
				}
				catch (Exception ex)
				{
					System.err.println("Error in client disconnect");
				}
			}
		}
	}
	
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
		if (usernames == null || usernames.isEmpty()){
			for (ClientObject thisClient: clients)
			{
				myList.add(thisClient.getObOut());
			} 
			return myList;
		}
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
