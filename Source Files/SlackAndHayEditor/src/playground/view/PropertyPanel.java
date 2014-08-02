package playground.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import playground.Config;
import playground.controller.ChangeEvent;
import playground.controller.ChangeEventListener;
import playground.controller.ChangeState;
import playground.controller.Controller;
import playground.model.Model;

public class PropertyPanel extends JPanel implements ChangeEventListener {

	private static final long serialVersionUID = 263553532768803172L;

	private final JTable table;
	private final JScrollPane scroll;
	private final DefaultTableModel tablemodel;

	public PropertyPanel(final Controller controller, final JList list) {
		final Dimension panelsize = new Dimension(Config.WINDOW_WIDTH/4, Config.WINDOW_HEIGHT/2);
		setPreferredSize(panelsize);
		setBackground(new Color(0xFFFFFF));

		tablemodel = new DefaultTableModel();
		tablemodel.addColumn("Property");
		tablemodel.addColumn("Value");

		table = new JTable(tablemodel);
		table.setPreferredSize(panelsize);

		scroll = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBorder(new EmptyBorder(0,0,0,0));
		scroll.setPreferredSize(new Dimension(Config.WINDOW_WIDTH/4, Config.WINDOW_HEIGHT/2-30));
		add(scroll);
		
		tablemodel.addTableModelListener(controller);
	}

	@Override
	public void changeEventReceived(final ChangeEvent event) {
		if(event.getEventName() == ChangeState.OBJECTMANAGER_LIST_CLICKED) {
			if((Integer)event.getSource() < Model.getInstance().getDrawables().size()) {
				final Drawable drawable = Model.getInstance().getDrawables().get((Integer)event.getSource());
				addItems(drawable);
			}
		}
		table.repaint();
	}

	private void addItems(final Drawable drawable) {
		tablemodel.getDataVector().removeAllElements();
		for(final Drawable item: Model.getInstance().getDrawables()) {
			if(item.equals(drawable)) {
				final Vector<Object> v = drawable.toVector();
				for(int i = 0; i < v.size(); i+=2) {
					tablemodel.addRow(v.subList(i,i+2).toArray());
				}
				break;
			}
		}
	}
}
