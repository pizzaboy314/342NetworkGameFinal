package server;
import java.net.*;

public class ClientObject {
	private String username;
	private Socket userSocket;
	
	public ClientObject(String _username, Socket _userSocket)
	{
		username = _username;
		userSocket = _userSocket;
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
