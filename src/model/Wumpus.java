package model;

/**
 * Model for a game of whack a mole.  At a specific interval, 
 * this model will tell all listeners of the new Point to place
 * an image. That view will allow userMissed and userHit messages 
 * to be sent back here depending on where the mouse was clicked.
 * This model also decides what to score. It is +1 for each hit and -1
 * for each miss. Yes, you can have a negative score.
 *  
 * @author Rick Mercer
 */
import java.util.Observable;
import java.util.Random;

public class Wumpus extends Observable {
	// Need 10 x 10 grid of buttons in a view
	public final static int HOLES = 10;
	private Room [][] board = new Room[HOLES][HOLES];
	private int wumpus_x, wumpus_y, hunter_x, hunter_y;
	private static Random generator;

	/**
	 * 
	 */
	public Wumpus() {
		for(int i = 0; i < HOLES; i++){
			for(int j = 0; j < HOLES; j++)
				board[i][j] = new Room();
		}
		generator = new Random();
		setupPit();
		setupWumpus();
		setupHunter();
		super.setChanged();
	    super.notifyObservers();
	}
	
	public int getHoles(){
		return HOLES;
	}

	private void setupHunter() {
		// TODO Auto-generated method stub
		hunter_x = generator.nextInt(HOLES);
		hunter_y = generator.nextInt(HOLES);
		while(board[wumpus_x][wumpus_y].getElement() == ' '){
			hunter_x = generator.nextInt(HOLES);
			hunter_y = generator.nextInt(HOLES);
		}
			board[hunter_x][hunter_y].setElement('O');
		
	}

	private void setupWumpus() {
		// TODO Auto-generated method stub
		wumpus_x = generator.nextInt(HOLES);
		wumpus_y = generator.nextInt(HOLES);
		while(board[wumpus_x][wumpus_y].getElement() == 'P' ){
			wumpus_x = generator.nextInt(HOLES);
			wumpus_y = generator.nextInt(HOLES);
		}
		
		board[wumpus_x][wumpus_y].setElement('W');
		for(int i = -2; i < 3; i++){
			for(int j = -2; j < 3; j++){
				if((!(i != 0 && j != 0) || (Math.abs(i) == 1 && Math.abs(j) == 1))  && board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].getElement() != 'P'){
					if(board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].getElement() == 'S'){
						board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].setElement('G');
					}
					else
						board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].setElement('B');
				}
			}
		}
		board[wumpus_x][wumpus_y].setElement('W');
	}

	private void setupPit() {
		// TODO Auto-generated method stub
		int n = 4;
		while(n > 0){
			int x = generator.nextInt(HOLES);
			int y = generator.nextInt(HOLES);
			
			while(board[x][y].getElement() == 'P' ){
				x = generator.nextInt(HOLES);
				y = generator.nextInt(HOLES);
			}
			board[x][y].setElement('P');
			for(int i = -1; i < 2; i++){
				for(int j = -1; j < 2; j++){
					if(!(i != 0 && j != 0) && board[(i + x + HOLES) % HOLES][(j + y + HOLES) % HOLES].getElement() != 'P')
						board[(i + x + HOLES) % HOLES][(j + y + HOLES) % HOLES].setElement('S');
				}
			}
			n--;
		}
	}
	public Room[][] getBoard(){
		return board;
	}
	public void movePlayer(int x, int y){
		board[hunter_x][hunter_y].setElement(' ');		
		int i = moveCheck(x,y);
		hunter_x += x;
		hunter_y += y;
		board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].setElement('O');	
	}
	private int moveCheck(int x, int y) {
		// TODO Auto-generated method stub
		if(board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].getElement() != ' ')
			return -1;
		return 0;
	}
	
	public int getHunterX(){
		return hunter_x;
	}
	public int getHunterY(){
		return hunter_y;
	}

	public String toString(){
		String str = "";
		for(int i = 0; i < HOLES; i++){
			for(int j = 0; j < HOLES; j++)
				str += board[i][j].getElement() + " ";
			str += "\n";
		}
		return str;
	}
	
}