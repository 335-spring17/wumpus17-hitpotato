package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.*;

/**
 * ImageView for a game of Wumpus.
 * 
 * @author Long Chen
 */
public class ImageView extends JPanel implements Observer {

	private Wumpus model;

	private Image hunter, wumpus, pit, blood, slime, goop, ground;
	// Need this to help compute where to draw the player
	public static final int TILE_SIZE = 50;

	// constructor
	public ImageView(Wumpus game) {
		// TODO Auto-generated constructor stub
		this.model = game;
		setBackground(Color.BLACK);
		try {
			hunter = ImageIO.read(new File("images/TheHunter.png"));
			wumpus = ImageIO.read(new File("images/Wumpus.png"));
			pit = ImageIO.read(new File("images/SlimePit.png"));
			blood = ImageIO.read(new File("images/Blood.png"));
			slime = ImageIO.read(new File("images/Slime.png"));
			goop = ImageIO.read(new File("images/Goop.png"));
			ground = ImageIO.read(new File("images/Ground.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		repaint();

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public void paintComponent(Graphics g) {
		// Need to call this method to avoid incorrect drawing of this JPanel.
		super.paintComponent(g);

		// TODO 6: Cast g to a Graphics2D Object
		Graphics2D g2 = (Graphics2D) g;
		// TODO 7: Draw the background image reference. The tile image is 50 by 50 pixels.
		Room[][] board = model.getBoard();
		for (int r = 0; r < model.getHoles(); r++) {
			for (int c = 0; c < model.getHoles(); c++) {
				if (board[r][c].isVisited() == true) {
					if (board[r][c].getElement() == ' ')
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
					else if (board[r][c].getElement() == 'B') {
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
						g2.drawImage(blood, r * TILE_SIZE, c * TILE_SIZE, null);
					} else if (board[r][c].getElement() == 'P') {
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
						g2.drawImage(pit, r * TILE_SIZE, c * TILE_SIZE, null);
					} else if (board[r][c].getElement() == 'S') {
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
						g2.drawImage(slime, r * TILE_SIZE, c * TILE_SIZE, null);
					} else if (board[r][c].getElement() == 'G') {
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
						g2.drawImage(goop, r * TILE_SIZE, c * TILE_SIZE, null);
					} else if (board[r][c].getElement() == 'O') {
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
						if (model.getDupBoard()[r][c].getElement() == 'S')
							g2.drawImage(slime, r * TILE_SIZE, c * TILE_SIZE, null);
						if (model.getDupBoard()[r][c].getElement() == 'B')
							g2.drawImage(blood, r * TILE_SIZE, c * TILE_SIZE, null);
						if (model.getDupBoard()[r][c].getElement() == 'G')
							g2.drawImage(goop, r * TILE_SIZE, c * TILE_SIZE, null);
						if (model.getDupBoard()[r][c].getElement() == 'P')
							g2.drawImage(pit, r * TILE_SIZE, c * TILE_SIZE, null);
						if (model.getDupBoard()[r][c].getElement() == 'W')
							g2.drawImage(wumpus, r * TILE_SIZE, c * TILE_SIZE, null);
						g2.drawImage(hunter, r * TILE_SIZE, c * TILE_SIZE, null);
					} else {
						g2.drawImage(ground, r * TILE_SIZE, c * TILE_SIZE, null);
						g2.drawImage(wumpus, r * TILE_SIZE, c * TILE_SIZE, null);
					}
				}
			}
		}
		
		// Instruction messages
		g2.setColor(Color.WHITE);
		g2.drawString("Move Up:W, Down:S, Left:A, Right:D", 10, 520);
		g2.drawString("Shoot Up:I, Down:K, Left:J, Right:L", 10, 540);
		
		// Status message
		if (!model.isRunning() && model.isWin()){
			g2.drawString("You killed Wumpus!", 10, 560);
			for (int r = 0; r < model.getHoles(); r++) {
				for (int c = 0; c < model.getHoles(); c++)
					board[r][c].setVisited(true);
			}
			repaint();
		}
		if (model.getTrap() == -1){
			g2.drawString("You walked into Pit!", 10, 560);
			for (int r = 0; r < model.getHoles(); r++) {
				for (int c = 0; c < model.getHoles(); c++)
					board[r][c].setVisited(true);
			}
			repaint();
		}
		if (model.getTrap() == -2){
			g2.drawString("You walked into Wumpus!", 10, 560);
			for (int r = 0; r < model.getHoles(); r++) {
				for (int c = 0; c < model.getHoles(); c++)
					board[r][c].setVisited(true);
			}
			repaint();
		}
		if (model.getTrap() == 1)
			g2.drawString("You walked onto Slime!", 10, 560);
		if (model.getTrap() == 2)
			g2.drawString("You walked onto Goop!", 10, 560);
		if (model.getTrap() == 3)
			g2.drawString("You walked onto Blood!", 10, 560);
		if (!model.isRunning() && !model.isWin()){
			g2.drawString("You killed yourself!", 10, 560);
			for (int r = 0; r < model.getHoles(); r++) {
				for (int c = 0; c < model.getHoles(); c++)
					board[r][c].setVisited(true);
			}
			repaint();
		}
	}
}
