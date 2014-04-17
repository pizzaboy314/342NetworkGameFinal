package client;

import gui.ClientPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

import sharedResources.*;

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
						System.out.println(username + " about to recieve");
						ServerMessage messageObject = (ServerMessage)in.readObject();
						System.out.println(username + " received a message");
						if (messageObject.isConnectMessage()){
							panel.addUser(messageObject.getSender());
						}else if (messageObject.isDisconnectMessage()){
							panel.rmUser(messageObject.getSender());
						}
						System.out.print(messageObject.getSender() + ": " + messageObject.getMessage() + "\n");
					}
					catch (Exception ex){
						ex.printStackTrace();
						return;
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
