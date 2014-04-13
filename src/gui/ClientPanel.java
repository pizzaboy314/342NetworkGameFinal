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
	private DefaultListModel<String> clListModel, outputModel;//list to add string
	private JList<String> cList, oList;//for display count
	private JTextField input;
	private JScrollPane listWindow, outputWindow;

	public ClientPanel() {
		setLayout(new BorderLayout());
		clListModel = new DefaultListModel<String>();
		outputModel = new DefaultListModel<String>();
		clListModel.addElement("Name 1");
		clListModel.addElement("Name 2");
		cList = new JList<String>(clListModel);
		cList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		cList.setVisibleRowCount(10);
		oList = new JList<String>(outputModel);
		oList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		oList.setVisibleRowCount(10);
		listWindow = new JScrollPane(cList);
		listWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		outputWindow = new JScrollPane(oList);
		outputWindow.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new BorderLayout());
		ioPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		input = new JTextField();
		input.addActionListener(this);
		
		ioPanel.add(outputWindow, BorderLayout.CENTER);
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
			outputModel.addElement(input.getText());
			input.setText("");
		}
		
	}

}
