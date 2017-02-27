package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.*;
import view.*;

/**
 * 
 * @author mercer, Long Chen
 */
public class WumpusGUI extends JFrame {

	public static void main(String[] args) {
		WumpusGUI g = new WumpusGUI();
		g.setVisible(true);
	}

	private JTabbedPane severalPanels;
	private Wumpus theGame = new Wumpus();
	private ImageView imageView;
	private TextView textView;
	public static final int width = 525;
	public static final int height = 565;

	public WumpusGUI() {
		imageView = new ImageView(theGame);
		textView = new TextView(theGame);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		severalPanels = new JTabbedPane();
		severalPanels.add(imageView, "Image");
		severalPanels.add(textView, "Text");
		add(severalPanels, BorderLayout.CENTER);
		addObservers();
		System.out.println(theGame.toString());
		this.addKeyListener(new ArrowKeyListener());
	}

	// Added textView Observer
	private void addObservers() {
		theGame.addObserver(imageView);
		theGame.addObserver(textView);
	}

	private class ArrowKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent key) {
			System.out.println("AAA");
			if (key.getKeyCode() == KeyEvent.VK_W)
				theGame.movePlayer(0, 1);
			if (key.getKeyCode() == KeyEvent.VK_S)
				theGame.movePlayer(0, -1);
			if (key.getKeyCode() == KeyEvent.VK_A)
				theGame.movePlayer(-1, 0);
			if (key.getKeyCode() == KeyEvent.VK_D)
				theGame.movePlayer(1, 0);
			System.out.println(theGame.getHunterX() + " " + theGame.getHunterY());

			repaint();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}
