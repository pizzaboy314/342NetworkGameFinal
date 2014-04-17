package server;

import java.util.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

import sharedResources.*;

/**
 * Allows specific operations to be 
 * done on each ClientObject
 * 
 * @author Ian Swift
 */
public class ClientList {
	private List<ClientObject> clients;
	
	/**
	 * Creates the clients list to be used
	 */
	public ClientList()
	{
		clients = new ArrayList<ClientObject>();
	}
	
	/**
	 * Adds the new user to the list and
	 * tells the other client that the new
	 * user is connected
	 * @param username
	 * @param client
	 */
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
	
	/**
	 * Removes the new user from the list and
	 * tells the other clients that the
	 * user has disconnected
	 * 
	 * @param _username
	 */
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
	
	/**
	 * Get the sockets to the connection
	 * @param usernames Usernames to get connection for
	 * @return List of sockets
	 */
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
	
	/**
	 * Get the ObjectOutputStreams for the specificed users
	 * @param usernames Users whose streams are requested
	 * @return List of ObjectOutputStreams for the specified users
	 */
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
