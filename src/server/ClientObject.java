package server;
import java.net.*;

public class ClientObject {
	private String username;
	private Socket userSocket;
	
	public ClientObject(String _username, Socket _userSocket)
	{
		username = username;
		userSocket = userSocket;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public Socket getSocket()
	{
		return userSocket;
	}
}
