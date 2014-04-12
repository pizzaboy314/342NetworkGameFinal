package client;

import java.util.*;
import java.io.*;
import java.net.*;

import sharedResources.*;

public class Client {
	private Socket client;
	private String username;
	public Client(int port)
	{
		try{
			client = new Socket("localhost", port);
			Listener L = new Listener();
			L.run();
			Scanner sc = new Scanner(System.in);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			System.out.print("Username: ");
			username = sc.nextLine();
			while(true)
			{
				System.out.print("Message: ");
				String myMessage = sc.nextLine();
				System.out.print("Recipient: ");
				String myRecipient = sc.nextLine();
				ArrayList<String> recipients = new ArrayList<String>();
				recipients.add(myRecipient);
				out.writeObject(new ClientMessage(recipients, username, myMessage));
				
			}
		}
		catch (IOException e){
		}
	}
	
	private class Listener implements Runnable{
		public void run()
		{
			try{
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				while(true)
				{
					ServerMessage messageObject = (ServerMessage)in.readObject();
					System.out.print(messageObject.getSender() + ": " + messageObject.getMessage() + "\n");
				}
			}
			catch (Exception ex)
			{
			}
		}
	}
	public static void main(String args[])
	{
		Client myClient = new Client(9001);
	}
}
