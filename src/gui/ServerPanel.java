package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ServerPanel extends JPanel implements ListSelectionListener{
	private DefaultListModel<String> listModel;

	public ServerPanel() {
		setLayout(new FlowLayout());
		listModel = new DefaultListModel<String>();
		listModel.addElement("stuff");
		listModel.addElement("stuff2");
		JList<String> list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(1);
		JScrollPane listWindow = new JScrollPane(list);
		listWindow.setPreferredSize(new Dimension(200, 200));
		add(listWindow, FlowLayout.TRAILING);
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
