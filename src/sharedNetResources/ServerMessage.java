package sharedNetResources;

import gameLogic.*;

import java.io.Serializable;

/**
 * Encapsulates the message sent from the
 * server to the client
 * 
 * @author Ian Swift
 *
 */
public class ServerMessage implements Serializable {
	
	private static final long serialVersionUID = 8390697074092621636L;
	private int playerIndex;
	
	private String sender;
	private String message;
	private boolean connect_message;
	private boolean disconnect_message;
	
	private boolean gameMessage;
	private boolean drawFromDeckMessage;
	private boolean insertDiscardMessage;
	private boolean drawFromDiscardMessage;
	private boolean gameover;
	private boolean hitMessage;
	
	private String phaseText;
	private String discardHistory;
	private String phaseHistory;
	private Card c;
	
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
		this.gameMessage = false;
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
		this.gameMessage = false;
	}
	
	public ServerMessage(){

		this.gameMessage = true;
	}
	/**
	 * Getter for this message's sender.
	 * 
	 * @return Username of sender
	 */
	public String getSender()
	{
		return this.sender;
	}
	
	/**
	 * Getter for this message's string message.
	 * 
	 * @return String that represents the message
	 */
	public String getMessage()
	{
		return this.message;
	}
	
	/**
	 * Returns whether or not this message represents a connection.
	 * 
	 * @return True if a connect message
	 */
	public boolean isConnectMessage()
	{
		return this.connect_message;
	}

	/**
	 * Returns whether or not this message represents a disconnection.
	 * 
	 * @return True if is a disconnect message
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
