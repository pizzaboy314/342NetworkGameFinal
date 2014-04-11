package server;

import java.util.*;
import java.net.*;

import client.ClientObject;

public class ClientList {
	private List<ClientObject> clients;

	public void userConnect(String username, Socket userSocket) {
		ClientObject client = new ClientObject(username, userSocket);
		clients.add(client);
	}

	public void userDisconnect(String _username) {
		for (ClientObject thisClient : clients) {
			if (thisClient.getUsername() == _username) {
				clients.remove(thisClient);
				return;
			}
		}
	}

	public void userDisconnect(Socket _userSocket) {
		for (ClientObject thisClient : clients) {
			if (thisClient.getSocket() == _userSocket) {
				clients.remove(thisClient);
				return;
			}
		}
	}

	public List<Socket> getUserSockets(List<String> usernames) {
		List<Socket> myList = new ArrayList<Socket>();
		for (ClientObject thisClient : clients) {
			for (String username : usernames)
				if (thisClient.getUsername() == username) {
					myList.add(thisClient.getSocket());
				}
		}
		return myList;
	}
}
