package sharedResources;

import java.io.*;

public class ServerMessage implements Serializable {
	
	private static final long serialVersionUID = 8390697074092621636L;
	private String sender;
	private String message;
	private boolean connect_message;
	private boolean disconnect_message;
	
	
	public ServerMessage(String sender, String message)
	{
		this.connect_message = false;
		this.disconnect_message = false;
		this.sender = sender;
		this.message = message;
	}
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
	
	public String getSender()
	{
		return this.sender;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public boolean isConnectMessage()
	{
		return this.connect_message;
	}
	public boolean isDisconnectMessage()
	{
		return this.disconnect_message;
	}
	@Override
	public String toString(){
		return message + ", From: " + sender;
	}
}
