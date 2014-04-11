package sharedResources;

import java.io.Serializable;
import java.util.*;

public class ClientMessage extends ServerMessage {
	private ArrayList<String> destinations;

	public ClientMessage(ArrayList<String> _destinations, String _username,
			String _message) {
		super(_username, _message);
		destinations = _destinations;
	}

	public ArrayList<String> getDestinations() {
		return destinations;
	}
}