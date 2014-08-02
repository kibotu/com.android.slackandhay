package playground.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import playground.controller.Controller;


public class MenuView extends JMenuBar{

	private static final long serialVersionUID = 5168330532718835387L;

	private final Controller controller;

	public MenuView(final Controller controller) {
		this.controller = controller;

		addFileMenu();
		addAddMenu();
		addViewMenu();
	}

	public void addFileMenu() {
		final JMenu menu= new JMenu("File");
		for(final Controller.FileCommand command: Controller.FileCommand.values()) {
			final JMenuItem item = new JMenuItem(command.name().replaceAll("_", " "));
			item.setActionCommand(command.name());
			item.addActionListener(controller);
			menu.add(item);
		}
		add(menu);
	}

	public void addAddMenu() {
		final JMenu menu = new JMenu("Add");
		for(final Controller.AddCommand command: Controller.AddCommand.values()) {
			final JMenuItem item = new JMenuItem(command.name().replaceAll("_", " "));
			item.setActionCommand(command.name());
			item.addActionListener(controller);
			menu.add(item);
		}
		add(menu);
	}
	
	public void addViewMenu() {
		final JMenu menu = new JMenu("View");
		for(final Controller.ViewCommand command: Controller.ViewCommand.values()) {
			final JMenuItem item = new JMenuItem(command.name().replaceAll("_", " "));
			item.setActionCommand(command.name());
			item.addActionListener(controller);
			menu.add(item);
		}
		add(menu);
	}
}