package sharedResources;

import java.util.*;

public class ClientMessage extends ServerMessage{

	private static final long serialVersionUID = 1824338017583746179L;
	private ArrayList<String> destinations;
	
	public ClientMessage(ArrayList<String> _destinations, String _username, String _message)
	{
		super(_username, _message);
		destinations = _destinations;
	}
	
	public ArrayList<String> getDestinations()
	{
		return destinations;
	}
}