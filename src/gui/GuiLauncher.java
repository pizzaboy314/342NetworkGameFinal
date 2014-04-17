package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GuiLauncher extends JFrame implements Runnable{

	public GuiLauncher() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		int choice = JOptionPane.showConfirmDialog(this,
				"Are you a server?", "Launcher",
				JOptionPane.YES_NO_OPTION);
		System.out.println("Option = " + choice);
		if (choice == 0){
			this.add(new ServerPanel());
		} else if (choice == 1){
			System.out.println("sec");
			this.add(new ClientPanel());//TODO make 9001 a variable
		} else{
			System.exit(0);
		}
		this.setPreferredSize(new Dimension(500, 500));
		this.pack();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new GuiLauncher());
	}

	@Override
	public void run() {
		setVisible(true);
			
	}

}
