package sharedResources;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Encapsulates the client's name, socket, and 
 * input/output object streams
 * 
 * @author Ian Swift
 *
 */
public class ClientObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private Socket userSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	/**
	 * Constructor for this class. Takes in the user's name and the socket for
	 * their connection.
	 * 
	 * @param _username
	 * @param _userSocket
	 */
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
	
	/**
	 * Setter for this client's username.
	 * 
	 * @param nm
	 */
	public void setName(String nm){
		username = nm;
	}

	/**
	 * Getter for this client's username.
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Getter for this client's socket.
	 * 
	 * @return
	 */
	public Socket getSocket() {
		return userSocket;
	}
	
	/**
	 * Getter for this client's input stream.
	 * 
	 * @return
	 */
	public ObjectInputStream getObIn(){
		return in;
	}
	
	/**
	 * Getter for this client's output stream.
	 * 
	 * @return
	 */
	public ObjectOutputStream getObOut(){
		return out;
	}
}
