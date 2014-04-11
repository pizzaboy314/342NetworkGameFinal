package client;

import java.io.Serializable;
import java.util.*;

public class ClientMessage implements Serializable {
	private ArrayList<String> destinations;
	private String username;
	private String message;

	public ClientMessage(ArrayList<String> _destinations, String _username,
			String _message) {
		destinations = _destinations;
		username = _username;
		message = _message;
	}

	public ArrayList<String> getDestinations() {
		return destinations;
	}

	public String getSender() {
		return username;
	}

	public String getMessage() {
		return message;
	}
}