package sharedNetResources;

import gameLogic.*;

import java.io.Serializable;

import server.ClientList;

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
	
	protected boolean gameMessage = false;
	protected boolean drawFromDeckMessage = false;
	protected boolean insertDiscardMessage = false;
	protected boolean drawFromDiscardMessage = false;
	protected boolean gameover = false;
	protected boolean hitMessage = false;
	
	protected String phaseText;
	protected String discardHistory;
	protected String phaseHistory;
	protected Card c;
	
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
	
	public ServerMessage(String from, boolean ddec, boolean idis, boolean ddis, boolean fromserver){
		drawFromDeckMessage = ddec;
		insertDiscardMessage = idis;
		if (drawFromDeckMessage && fromserver)
			c = ClientList.deck.drawCard();
		if (insertDiscardMessage && fromserver)
			ClientList.deck.insertCard(c);
		insertDiscardMessage = ddis;
		this.gameMessage = true;
	}
	
	public ServerMessage(String from, boolean idis, Card c, boolean fromserver){
		insertDiscardMessage = idis;
		ClientList.discard.PilePrint();
		this.c = c;
		if (insertDiscardMessage && fromserver){
			ClientList.discard.insertCard(c);
		}
		System.out.println("This is my size" + ClientList.discard.pile.size());
		discardHistory = ClientList.discard.PilePrint();
		System.out.println(discardHistory);
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
	
	public Card getCard(){
		return c;
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
	
	public boolean isDrawFromDeckMessage()
	{
		return this.drawFromDeckMessage;
	}
	
	public boolean isInsertDiscardMessage()
	{
		return this.insertDiscardMessage;
	}
	
	public boolean isDrawFromDiscardMessage()
	{
		return this.drawFromDiscardMessage;
	}
	
	public boolean isGameoverMessage()
	{
		return this.gameover;
	}

	/**
	 * Override for the toString method, for getting a formatted string.
	 */
	@Override
	public String toString(){
		return message + ", From: " + sender;
	}
}
