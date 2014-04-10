package server;

import java.util.*;
import java.net.*;

public class ClientList {
	private List<ClientObject> clients;
	
	public void userConnect(String username, Socket userSocket) {
		ClientObject client = new ClientObject(username, userSocket);
		clients.add(client);
	}
	
	public void userDisconnect(String _username)
	{
		for (ClientObject thisClient: clients)
		{
			if (thisClient.getUsername() == _username)
			{
				clients.remove(thisClient);
				return;
			}
		}
	}
	
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
	}
	
	public Socket getUserSocket(String _username)
	{
		for (ClientObject thisClient: clients)
		{
			if (thisClient.getUsername() == _username)
			{
				return thisClient.getSocket();
			}
		}
		return null;
	}
}
