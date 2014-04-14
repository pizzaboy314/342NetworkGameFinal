package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import server.ClientList;
import sharedResources.ClientMessage;
import sharedResources.ServerMessage;

public class Client{
	private Socket serverInput;
	private String username;
	private ObjectOutputStream out;

	public Client(int port)
	{
		try {
			serverInput = new Socket("localhost", port);
			ServerHandler sh = new ServerHandler();
			sh.start();
			Scanner sc = new Scanner(System.in);
			out = new ObjectOutputStream(serverInput.getOutputStream());
			System.out.print("Username: ");
			username = sc.nextLine();
			out.writeObject(new ClientMessage(null, username, ""));
			/*
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
			*/
		}
		catch (ConnectException e){
			System.err.println("Unable to connect, exiting");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private class ServerHandler implements Runnable{
		private Thread t;
		
		public void run()
		{
			try{
				ObjectInputStream in = new ObjectInputStream(serverInput.getInputStream());
				while(true)
				{
					ServerMessage messageObject = (ServerMessage)in.readObject();
					System.out.print(messageObject.getSender() + ": " + messageObject.getMessage() + "\n");
				}
			}
			catch (Exception ex)
			{

				ex.printStackTrace();
			}
		}
		public void start()
		{
			t = new Thread(this, "listen");
			t.start();
		}
	}
	public static void main(String args[])
	{
		Client myClient = new Client(9002);
	}
	
	public void sendMessage(String str, String toPerson){
		ArrayList<String> recipients = new ArrayList<String>();
		recipients.add(toPerson);
		try {
			out.writeObject(new ClientMessage(recipients, username, str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
