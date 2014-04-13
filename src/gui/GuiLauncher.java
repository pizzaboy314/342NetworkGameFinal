package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import server.Server;
import client.Client;

public class GuiLauncher extends JFrame implements Runnable{

	public GuiLauncher() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		int choice = JOptionPane.showConfirmDialog(this,
				"Are you a server?", "Launcher",
				JOptionPane.YES_NO_OPTION);
		if (choice == 0){
			System.out.println("first");
			//Server myServer = new Server(9001);
			//TODO
		}else{
			System.out.println("sec");
			this.add(new ClientPanel());
			//Client myClient = new Client(9001);
			//TODO
		}
		this.setSize(300, 300);
		this.pack();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new GuiLauncher());
	}

	@Override
	public void run() {
		setVisible(true);
			
	}

}
