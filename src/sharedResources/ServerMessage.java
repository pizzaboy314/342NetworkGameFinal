package sharedResources;

import java.io.Serializable;

public class ServerMessage implements Serializable {
	
	private static final long serialVersionUID = 8390697074092621636L;
	private String sender;
	private String message;
	private boolean connect_message;
	private boolean disconnect_message;
	
	/**
	 * Constructor for this class. Takes in a sender and its associated message.
	 * 
	 * @param sender
	 * @param message
	 */
	public ServerMessage(String sender, String message)
	{
		this.connect_message = false;
		this.disconnect_message = false;
		this.sender = sender;
		this.message = message;
	}

	/**
	 * Constructor for this class. Takes in a boolean that indicates whether or
	 * not this is a connection or a disconnection and the username of the
	 * sender.
	 * 
	 * @param connect
	 * @param username
	 */
	public ServerMessage(boolean connect, String username)
	{
		if (connect)
		{
			this.connect_message = true;
			this.disconnect_message = false;
		}
		else
		{
			this.disconnect_message = true;
			this.connect_message = false;
		}
		this.sender = username;
	}
	
	/**
	 * Getter for this message's sender.
	 * 
	 * @return
	 */
	public String getSender()
	{
		return this.sender;
	}
	
	/**
	 * Getter for this message's string message.
	 * 
	 * @return
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	/**
	 * Returns whether or not this message represents a connection.
	 * 
	 * @return
	 */
	public boolean isConnectMessage()
	{
		return this.connect_message;
	}

	/**
	 * Returns whether or not this message represents a disconnection.
	 * 
	 * @return
	 */
	public boolean isDisconnectMessage()
	{
		return this.disconnect_message;
	}

	/**
	 * Override for the toString method, for getting a formatted string.
	 */
	@Override
	public String toString(){
		return message + ", From: " + sender;
	}
}
