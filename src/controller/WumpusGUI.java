package controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.*;
import view.*;

/**
 * GUI for Wumpus. Contains key listener.
 * @author Long Chen
 */
public class WumpusGUI extends JFrame {

	public static void main(String[] args) {
		WumpusGUI g = new WumpusGUI();
		g.setVisible(true);
	}

	private JTabbedPane severalPanels;
	private Wumpus theGame;
	private ImageView imageView;
	private TextView textView;
	public static final int width = 522;
	public static final int height = 640;

	// Constructor
	public WumpusGUI() {
		theGame = new Wumpus();
		imageView = new ImageView(theGame);
		textView = new TextView(theGame);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		severalPanels = new JTabbedPane();
		severalPanels.add(imageView, "Image");
		severalPanels.add(textView, "Text");
		add(severalPanels);
		addObservers();
		severalPanels.addKeyListener(new ArrowKeyListener());
		System.out.println(theGame.toString());

	}

	// Added Observer
	private void addObservers() {
		theGame.addObserver(imageView);
		theGame.addObserver(textView);
	}

	// Key Listener
	private class ArrowKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent key) {
			// Keys for Hunter
			if (key.getKeyCode() == KeyEvent.VK_W && theGame.getMovable())
				theGame.movePlayer(0, -1);
			if (key.getKeyCode() == KeyEvent.VK_S && theGame.getMovable())
				theGame.movePlayer(0, 1);
			if (key.getKeyCode() == KeyEvent.VK_A && theGame.getMovable())
				theGame.movePlayer(-1, 0);
			if (key.getKeyCode() == KeyEvent.VK_D && theGame.getMovable())
				theGame.movePlayer(1, 0);
			// Keys for Arrow
			if (key.getKeyCode() == KeyEvent.VK_I && theGame.getMovable())
				theGame.moveArrow(0, -1);
			if (key.getKeyCode() == KeyEvent.VK_K && theGame.getMovable())
				theGame.moveArrow(0, 1);
			if (key.getKeyCode() == KeyEvent.VK_J && theGame.getMovable())
				theGame.moveArrow(-1, 0);
			if (key.getKeyCode() == KeyEvent.VK_L && theGame.getMovable())
				theGame.moveArrow(1, 0);

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
