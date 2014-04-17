package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Launches the JFrame and the panels used for either
 * the client or the server
 * @author Shanon Mathai
 *
 */
@SuppressWarnings("serial")
public class GuiLauncher extends JFrame implements Runnable{

	/**
	 * Asks the user whether or not they will be the
	 * server or will they be a client and initializes accordingly
	 */
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

	/**
	 * Launches this program
	 * 
	 * @param args Command line arguments (unused)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new GuiLauncher());
	}

	/**
	 * Runs this program as its own thread separate from the main thread
	 */
	@Override
	public void run() {
		setVisible(true);
			
	}

}
