import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ClientInterface extends JFrame implements ActionListener {
	// Game items
	static CardPile deck, discardPile;
	static Playerhand john;
	
	ArrayList<String> phaseText = new ArrayList<String>();
	
	// GUI items
	JMenuBar mBar;	
	JButton drawFromDeckButton, drawFromDiscardButton, abtButton;
	public JButton phaseCheck;
	JButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11;
	JTextArea cardsHistory, discardHistory, phaseHistory;
	JLabel handLabel, discardLabel, phaseLabel;
	
	// This creates abtMenu when the abtButton is clicked on
	private static void createAbtMenu() throws IOException {
		// Create About Frame
		JFrame abtFrame = new JFrame("About: Sliding Frame");
		
		JTextArea gameInfo = new JTextArea();
		abtFrame.add(new JScrollPane(gameInfo), BorderLayout.CENTER);
		gameInfo.setEditable(false);
		
		FileReader fr = null;
		try {
			fr = new FileReader("gameRule.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		while ((line = reader.readLine()) != null)
		{
		    if (!line.startsWith(">"))
		    {
		        gameInfo.append(line + "\n");
		    }
		}
			
		abtFrame.pack();
		abtFrame.setVisible(true);
	}
	
	// interface constructor
	public ClientInterface () {
		super (" Client Interface ");
		
		// get content pane and set its layout
	    Container container = getContentPane();
	    container.setLayout (new BorderLayout ());
	     
	    // set up the North panel
	    JPanel gamePanel = new JPanel ();
	    JPanel textPanel = new JPanel ();
	    JPanel discardPanel = new JPanel ();
	    JPanel phasePanel = new JPanel ();
	    gamePanel.setLayout (new GridLayout (4, 2));
	    textPanel.setLayout(new GridLayout (2,2));
	    phasePanel.setLayout(new GridLayout(2,2));
	    container.add(gamePanel, BorderLayout.EAST);
	    container.add(phasePanel, BorderLayout.CENTER);
	    container.add(textPanel, BorderLayout.WEST);
	    container.add(discardPanel, BorderLayout.SOUTH);
	    mBar = new JMenuBar();
	    setJMenuBar(mBar);
	
	    abtButton = new JButton("About");
	    abtButton.setEnabled(true);
	    abtButton.setVisible(true);
	    abtButton.addActionListener(this);
	    
	    // set menu bar
	    mBar.add(abtButton);

	    drawFromDeckButton = new JButton("Draw From Deck");
	    drawFromDeckButton.setEnabled(true);
	    drawFromDeckButton.setVisible(true);
	    drawFromDeckButton.addActionListener(this);
	    gamePanel.add(drawFromDeckButton);
	     
	    drawFromDiscardButton = new JButton("Draw From Discard");
	    drawFromDiscardButton.setEnabled(true);
	    drawFromDiscardButton.setVisible(true);
	    drawFromDiscardButton.addActionListener(this);
	    gamePanel.add(drawFromDiscardButton);

	    phaseCheck = new JButton("I have the phase!");
	    phaseCheck.setEnabled(true);
	    phaseCheck.setVisible(true);
	    phaseCheck.addActionListener(this);
	    gamePanel.add(phaseCheck);
	    
	    cardsHistory = new JTextArea ( 10, 10 );
	    cardsHistory.setEditable(false);
	    
	    discardHistory = new JTextArea( 10, 10 );
	    discardHistory.setEditable(false);
	    
	    phaseHistory = new JTextArea(10, 10);
	    phaseHistory.setEditable(false);
	    
	    button1 = new JButton("1");
	    button1.setEnabled(true);
	    button1.setVisible(true);
	    button1.addActionListener(this);
	    
	    button2 = new JButton("2");
	    button2.setEnabled(true);
	    button2.setVisible(true);
	    button2.addActionListener(this);
	    
	    button3 = new JButton("3");
	    button3.setEnabled(true);
	    button3.setVisible(true);
	    button3.addActionListener(this);
	    
	    button4 = new JButton("4");
	    button4.setEnabled(true);
	    button4.setVisible(true);
	    button4.addActionListener(this);
	    
	    button5 = new JButton("5");
	    button5.setEnabled(true);
	    button5.setVisible(true);
	    button5.addActionListener(this);
	    
	    button6 = new JButton("6");
	    button6.setEnabled(true);
	    button6.setVisible(true);
	    button6.addActionListener(this);
	    
	    button7 = new JButton("7");
	    button7.setEnabled(true);
	    button7.setVisible(true);
	    button7.addActionListener(this);
	    
	    button8 = new JButton("8");
	    button8.setEnabled(true);
	    button8.setVisible(true);
	    button8.addActionListener(this);
	    
	    button9 = new JButton("9");
	    button9.setEnabled(true);
	    button9.setVisible(true);
	    button9.addActionListener(this);
	    
	    button10 = new JButton("10");
	    button10.setEnabled(true);
	    button10.setVisible(true);
	    button10.addActionListener(this);
	    
	    button11 = new JButton("11");
	    button11.setEnabled(true);
	    button11.setVisible(true);
	    button11.addActionListener(this);
	    
	    discardPanel.add(button1);
	    discardPanel.add(button2);
	    discardPanel.add(button3);
	    discardPanel.add(button4);
	    discardPanel.add(button5);
	    discardPanel.add(button6);
	    discardPanel.add(button7);
	    discardPanel.add(button8);
	    discardPanel.add(button9);
	    discardPanel.add(button10);
	    discardPanel.add(button11);
	    
	    handLabel = new JLabel("Your Hand");
	    discardLabel = new JLabel("Discard Pile");
	    textPanel.add(handLabel);
	    textPanel.add(discardLabel);
	    textPanel.add(new JScrollPane(cardsHistory));
	    textPanel.add(new JScrollPane(discardHistory));
	    phaseLabel = new JLabel("Current Phases: ");
	    phasePanel.add(phaseLabel);
	    phasePanel.add(new JScrollPane(phaseHistory));
	    
	    pack();
	    setSize( 900, 500 );
	    setVisible( true );
	}
	
	public boolean useWild(int phase, int value){
		int n = JOptionPane.showConfirmDialog(
			    c,
			    "Do you want to use the WILD card for phase " + phase + " for the value " + value,
			    "Wild Option",
			    JOptionPane.YES_NO_OPTION);
		if(n == JOptionPane.YES_OPTION){
			return true;
		}
		else
			return false;
	}
	
	public static ClientInterface c = new ClientInterface();
	
	public void updatePhaseHistory(){
		phaseHistory.setText(null);
		for(int i = 0; i < phaseText.size(); i++){
			phaseHistory.append(phaseText.get(i));
		}
	}
	
	public static void main(String[] args) {
		c.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		 deck = new CardPile(true);
		 
		 discardPile = new CardPile();
		
		 john = new Playerhand("John", 0);
		
		// give player 'John' hand of 10 cards
		for(int i = 0; i < 10; i++) {
			john.drawCard(deck.drawCard());
		}
		john.printhand();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == drawFromDeckButton) {
			// System.out.println("Selected from Deck");
			john.drawCard(deck.drawCard());
			john.printhand();
		}
		else if(e.getSource() == drawFromDiscardButton) {
			// System.out.println("Selected from Discard");
			if (discardPile.cards_left() < 1) {
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			}
			else {
				john.drawCard(discardPile.drawCard());	// removes a card from discardPile
				discardHistory.setText(null);
				discardPile.PilePrint();
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if(e.getSource() == phaseCheck){
			if(john.phaseCheck())
				System.out.println("We have a phase! " + john.current_phase);
		}
		else if(e.getSource() == abtButton) {
			try {
				createAbtMenu();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/* if user clicks a button, but no card is present in that spot 
		 * catch error & print as messagebox
		 */
		else if (e.getSource() == button1) {
			// System.out.println("Button1 Pressed!");
			if (john.curHandSize() < 1) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(0));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(0);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button2) {
			// System.out.println("Button2 Pressed!");
			if (john.curHandSize() < 2) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(1));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(1);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button3) {
			// System.out.println("Button3 Pressed!");
			if (john.curHandSize() < 3) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(2));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(2);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button4) {
			// System.out.println("Button4 Pressed!");
			if (john.curHandSize() < 4) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(3));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(3);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button5) {
			// System.out.println("Button5 Pressed!");
			if (john.curHandSize() < 5) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(4));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(4);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button6) {
			// System.out.println("Button6 Pressed!");
			if (john.curHandSize() < 6) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(5));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(5);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button7) {
			// System.out.println("Button7 Pressed!");
			if (john.curHandSize() < 7) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(6));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(6);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button8) {
			// System.out.println("Button8 Pressed!");
			if (john.curHandSize() < 8) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(7));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(7);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if (e.getSource() == button9) {
			// System.out.println("Button9 Pressed!");
			if (john.curHandSize() < 9) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(8));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(8);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if(e.getSource() == button10) {
			// System.out.println(Button10 Pressed!");
			if (john.curHandSize() < 10) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(9));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(9);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
		else if(e.getSource() == button11) {
			// System.out.println(Button10 Pressed!");
			if (john.curHandSize() < 11) 
				JOptionPane.showMessageDialog(null,"NOT ENOUGH CARDS!", "alert", JOptionPane.WARNING_MESSAGE);
			else {
				discardPile.insertCard(john.getCard(10));
				discardHistory.setText(null);
				discardPile.PilePrint();
				john.discard(10);
				cardsHistory.setText(null);
				john.printhand();
			}
		}
	}
}
