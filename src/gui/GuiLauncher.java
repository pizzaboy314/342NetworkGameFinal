package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GuiLauncher extends JFrame implements Runnable{

	public GuiLauncher() {
		int choice = JOptionPane.showConfirmDialog(this,
				"Are you a server?", "Start Up",
				JOptionPane.YES_NO_OPTION);
		if (choice == 0){
			System.out.println("first");
			//TODO
		}else{
			System.out.println("sec");
			//TODO
		}
		this.setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
