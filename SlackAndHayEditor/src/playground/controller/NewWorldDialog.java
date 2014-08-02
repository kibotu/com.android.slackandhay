package playground.controller;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import playground.Config;
import playground.model.Model;
import playground.view.Grid;

public class NewWorldDialog extends JDialog{
	
	private static final long serialVersionUID = 7523384134520888016L;

	public NewWorldDialog(String name, int width, int height, RootPaneContainer context) {
		super(new JFrame(), true);
		setTitle(name);
//		setBounds(((JApplet)context).getWidth()/2-width/2+((JApplet)context).getLocationOnScreen().x,((JApplet) context).getHeight()/2-height/2+((JApplet)context).getLocationOnScreen().y, width, height);
		setBounds(context.getRootPane().getWidth()/2-width/2+context.getRootPane().getLocationOnScreen().x,context.getRootPane().getHeight()/2-height/2+context.getRootPane().getLocationOnScreen().y, width, height);
		setLayout(null);
		setResizable(false);

		final JTextField wWidth = new JTextField(0);
		final JTextField wHeight = new JTextField(0);
		
		addElementWithLabel(wWidth, "Width:", 15, 20, 50, 18);
		addElementWithLabel(wHeight, "Height:", 15, 45, 50, 18);
		JButton create = new JButton("Create");
		addElement(create, 140, 19, 80,45);
		
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Scanner scanner = new Scanner(wWidth.getText());
				
				int worldWidth = 0;
				int worldHeight = 0;
				
				if(scanner.hasNextInt()) {
					worldWidth = scanner.nextInt();
				} else {
					// todo alert
					return;
				}
				
				scanner = new Scanner(wHeight.getText());
				if(scanner.hasNextInt()) {
					worldHeight = scanner.nextInt();
				} else {
					// todo alert
					return;
				}
				Model.getInstance().reset();
				Model.getInstance().getDrawables().add(new Grid(worldWidth, worldHeight,Config.GRID_SCALE_GAME));
				setVisible(false);
			}
		});
		
		setVisible(true);
	}
	
	public void addElementWithLabel(JComponent component, String name, int x, int y, int width, int height) {
		// create label
		JLabel label = new JLabel(name);
		// get insects
		Insets insets = getInsets();
		// set bounds 
		label.setBounds(x + insets.left, y + insets.top,width, height);
		component.setBounds(x + label.getWidth() + insets.left, y + insets.top, width, height);
		// add to stage
		add(label);
		add(component);
	}
	
	public void addElement(JComponent component, int x, int y, int width, int height) {
		Insets insets = getInsets();
		component.setBounds(x + insets.left, y + insets.top, width, height);
		add(component);
	}
}
