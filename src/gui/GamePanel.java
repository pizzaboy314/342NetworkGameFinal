package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;

/**
 * The game panel to be added to a frame, handles the input and the output
 * 
 * @author Shanon Mathai
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
	private ClientPanel clPanel;

	public GamePanel(ClientPanel cl) {
		clPanel = cl;
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.green.darker().darker());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
