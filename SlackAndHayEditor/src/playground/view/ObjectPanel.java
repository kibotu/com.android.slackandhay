package playground.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import playground.Config;
import playground.controller.ChangeEvent;
import playground.controller.ChangeEventListener;
import playground.controller.Controller;
import playground.model.Model;

public class ObjectPanel extends JPanel implements ChangeEventListener{

	private static final long serialVersionUID = -3886041854333463443L;

	final private JList list;
	final private DefaultListModel listmodel;

	public ObjectPanel(final Controller controller) {
		final Dimension panelsize = new Dimension(Config.WINDOW_WIDTH/4, Config.WINDOW_HEIGHT/2);
		setPreferredSize(panelsize);
		setBackground(new Color(0xFFFFFF));
		listmodel = new DefaultListModel();
		list = new JList(listmodel);
		list.setMinimumSize(panelsize);
		list.setBorder(new LineBorder(Color.red, 0));
		list.setFixedCellWidth(panelsize.width-20);
		list.setVisibleRowCount(18);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		final JScrollPane scroll = new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(new EmptyBorder(0,0,0,0));
		scroll.setSize(panelsize);
		scroll.setAutoscrolls(true);
		add(scroll);
		list.addMouseListener(controller);
	}

	@Override
	public void changeEventReceived(final ChangeEvent event) {
		addModel();
		list.repaint();
	}

	private void addModel() {
		listmodel.clear();
		for(final Drawable drawable: Model.getInstance().getDrawables()) {
			listmodel.addElement(drawable);
		}
	}

	public JList getList() {
		return list;
	}
}
