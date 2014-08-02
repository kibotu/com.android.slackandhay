package playground;

import java.awt.Container;

import javax.swing.JFrame;

import playground.controller.Controller;

public class MainFrame {

	public static void main(String [] args) {
		final JFrame frame = new JFrame("Smiley");
		final Container container = frame.getContentPane();
		container.add((new Controller(frame)).getView());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}