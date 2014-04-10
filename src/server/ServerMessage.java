package server;

public class ServerMessage {
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
}
