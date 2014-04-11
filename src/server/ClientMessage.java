package server;

import java.util.*;
import java.io.*;

public class ClientMessage implements Serializable {

	private static final long serialVersionUID = 1824338017583746179L;
	private ArrayList<String> destinations;
	private String username;
	private String message;
	
	public ClientMessage(ArrayList<String> _destinations, String _username, String _message)
	{
		destinations = _destinations;
		username = _username;
		message = _message;
	}
	
	public ArrayList<String> getDestinations()
	{
		return destinations;
	}
	
	public String getSender()
	{
		return username;
	}
	
	public String getMessage()
	{
		return message;
	}
}