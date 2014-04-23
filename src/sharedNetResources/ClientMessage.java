package sharedNetResources;

import java.util.ArrayList;

/**
 * Encapsulate the data need to message other clients
 * when sending information to the server
 * 
 * @author Ian Swift
 *
 */
public class ClientMessage extends ServerMessage{

	private static final long serialVersionUID = 1824338017583746179L;
	private ArrayList<String> destinations;
	
	/**
	 * Constructor for this class. Takes in a list of network locations, the
	 * origin client's username, and the message being sent.
	 * 
	 * @param _destinations
	 * @param _username
	 * @param _message
	 */
	public ClientMessage(ArrayList<String> _destinations, String _username, String _message)
	{
		super(_username, _message);
		destinations = _destinations;
	}
	
	/**
	 * Getter for this message's network destinations.
	 * 
	 * @return Get the list of usernames
	 */
	public ArrayList<String> getDestinations()
	{
		return destinations;
	}
}