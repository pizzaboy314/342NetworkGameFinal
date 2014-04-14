package sharedResources;

import java.io.*;

public class ServerMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8390697074092621636L;
	private String sender;
	private String message;
	
	public ServerMessage(String sender, String message)
	{
		this.sender = sender;
		this.message = message;
	}
	
	public String getSender()
	{
		return this.sender;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	@Override
	public String toString(){
		return message + ", From: " + sender;
	}
}
