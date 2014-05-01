package gui;

import gameLogic.ClientInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Launches the JFrame and the panels used for either
 * the client or the server and adds a menu bar
 * @author Shanon Mathai
 *
 */
@SuppressWarnings("serial")
public class MainLauncher extends JFrame implements Runnable, ActionListener{

	private JMenuItem help;
	private JMenuItem about;
	private String helpStr = "Select the people you want to\n communicate with by selecting them\n"
			+ " with Ctrl+click, or Shift+click";
	private String aboutStr = "342 Chat Client, By:\nIan Swift\n"
			+ "Shanon Mathai\nNikhil Vellala\nAdrian Campos\nBryan Spahr";
	/**
	 * Asks the user whether or not they will be the
	 * server or will they be a client and initializes accordingly
	 * and adds teh menu bar
	 */
	public MainLauncher() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenu infoMenu = new JMenu("Menu");
		help = infoMenu.add("Help");
		about = infoMenu.add("About");
		JMenuBar mn = new JMenuBar();
		mn.add(infoMenu);
		this.setJMenuBar(mn);
		help.addActionListener(this);
		about.addActionListener(this);
		int choice = JOptionPane.showConfirmDialog(this,
				"Are you a server?", "Launcher",
				JOptionPane.YES_NO_OPTION);
		System.out.println("Option = " + choice);
		this.setLayout(new BorderLayout());
		if (choice == 0){
			ServerPanel clp = new ServerPanel();
			this.add(clp, BorderLayout.CENTER);//TODO make 9001 a variable
			this.add(new ClientInterface(), BorderLayout.WEST);//TODO make 9001 a variable
		} else if (choice == 1){
			System.out.println("sec");
			ClientPanel clp = new ClientPanel();
			this.add(clp, BorderLayout.CENTER);//TODO make 9001 a variable
			this.add(new ClientInterface(), BorderLayout.WEST);//TODO make 9001 a variable
		} else{
			System.exit(0);
		}
		//this.setPreferredSize(new Dimension(800, 500));
		this.pack();
	}

	/**
	 * Launches this program
	 * 
	 * @param args Command line arguments (unused)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new MainLauncher());
	}

	/**
	 * Runs this program as its own thread separate from the main thread
	 */
	@Override
	public void run() {
		setVisible(true);
			
	}

	/**
	 * Handle menu events
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.about){
			JOptionPane.showMessageDialog(this, aboutStr);
		} else if (e.getSource() == this.help){
			JOptionPane.showMessageDialog(this, helpStr);
		}
	}

}
