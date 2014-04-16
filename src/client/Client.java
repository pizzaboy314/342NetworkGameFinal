package client;

import gui.ClientPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

import sharedResources.ClientMessage;
import sharedResources.ServerMessage;

public class Client{
	private Socket serverInput;
	private String username;
	private ObjectOutputStream out;
	private ClientPanel panel;

	public Client(int port, ClientPanel pn)
	{
		panel = pn;
		try {
			serverInput = new Socket("localhost", port);
			ServerHandler sh = new ServerHandler();
			out = new ObjectOutputStream(serverInput.getOutputStream());
			sh.start();
			/*
			Scanner sc = new Scanner(System.in);
			out = new ObjectOutputStream(serverInput.getOutputStream());
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
			ObjectInputStream in = null;
			try{
				in = new ObjectInputStream(serverInput.getInputStream());
				while(true)
				{
					try {
					
						ServerMessage messageObject = (ServerMessage)in.readObject();
						System.out.println(username + " received a message");
						if (messageObject.isConnectMessage() == true){
							panel.addUser(messageObject.getSender());
						}
						System.out.print(messageObject.getSender() + ": " + messageObject.getMessage() + "\n");
					}
					catch (Exception ex){
						
					}
				}
			}
			catch (Exception ex)
			{
				if (in != null){
					try {
						in.close();
					} catch (Exception e) {
						ex.printStackTrace();
					}
				}
				ex.printStackTrace();
			}
		}
		public void start()
		{
			t = new Thread(this, "listen");
			t.start();
		}
	}
	
	public void sendName(String nm){
		username = nm;
		try {
			out.writeObject(new ClientMessage(null, username, ""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
