package sharedResources;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

public class ClientObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private Socket userSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ClientObject(String _username, Socket _userSocket) {
		username = _username;
		userSocket = _userSocket;
		try{
			in = new ObjectInputStream(_userSocket.getInputStream());
			out = new ObjectOutputStream(_userSocket.getOutputStream());
		} catch(Exception ex){
			System.err.println("Unable to create the input/out stream(s)");
		}
	}
	
	public void setName(String nm){
		username = nm;
	}

	public String getUsername() {
		return username;
	}

	public Socket getSocket() {
		return userSocket;
	}
	
	public ObjectInputStream getObIn(){
		return in;
	}
	
	public ObjectOutputStream getObOut(){
		return out;
	}
}
