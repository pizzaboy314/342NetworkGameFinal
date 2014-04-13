package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClientPanel extends JPanel implements ListSelectionListener, ActionListener {
	private DefaultListModel<String> listModel;
	private JTextField input, output;

	public ClientPanel() {
		setLayout(new BorderLayout());
		listModel = new DefaultListModel<String>();
		listModel.addElement("stuff");
		listModel.addElement("stuff2");
		JList<String> list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(10);
		JScrollPane listWindow = new JScrollPane(list);
		listWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new BorderLayout());
		ioPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		input = new JTextField();
		input.addActionListener(this);
		output = new JTextField();
		output.setBackground(Color.white);
		output.setEditable(false);
		ioPanel.add(output, BorderLayout.CENTER);
		ioPanel.add(input, BorderLayout.SOUTH);
		add(ioPanel, BorderLayout.CENTER);
		add(listWindow, BorderLayout.EAST);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		//Selection events
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == input){
			output.setText(input.getText());
			input.setText("");
		}
		
	}

}
