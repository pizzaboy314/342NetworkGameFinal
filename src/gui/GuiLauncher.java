package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GuiLauncher extends JFrame implements Runnable{

	public GuiLauncher() {
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
