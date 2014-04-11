package sharedResources;

import java.io.Serializable;

public class ServerMessage implements Serializable{
	protected String sender;
	protected String message;

	public ServerMessage(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public String getSender() {
		return this.sender;
	}

	public String getMessage() {
		return this.message;
	}
}
