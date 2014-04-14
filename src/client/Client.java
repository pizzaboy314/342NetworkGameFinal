package client;

import gui.ClientPanel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import sharedResources.ClientMessage;
import sharedResources.ServerMessage;

public class Client {
	private Socket client;
	private String username;

	private ClientPanel panel;
	public Client(int port)
	{
		panel = new ClientPanel();
		try {
			client = new Socket("localhost", port);
			Listener L = new Listener();
			L.start();
			Scanner sc = new Scanner(System.in);
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			System.out.print("Username: ");
			username = sc.nextLine();
			out.writeObject(new ClientMessage(null, username, ""));
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
		catch (ConnectException e){
			System.err.println("Unable to connect, exiting");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private class Listener implements Runnable{
		private Thread t;
		
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

	public ClientPanel getPanel() {
		return panel;
	}
}
