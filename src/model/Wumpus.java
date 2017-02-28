package model;

/**
 * Model for a game of Wumpus. Apply for both ImageView and TextView.
 *  
 * @author Long Chen
 */
import java.util.Observable;
import java.util.Random;

public class Wumpus extends Observable {
	// Need 10 x 10 grid of holes in a view
	public final static int HOLES = 10;
	public Room[][] board = new Room[HOLES][HOLES];
	private Room[][] dupBoard = new Room[HOLES][HOLES];
	private int wumpus_x, wumpus_y, hunter_x, hunter_y;
	private int trap;
	private boolean isMovable, isRunning, isWin;
	private static Random generator;

	// Constructor
	public Wumpus() {
		for (int i = 0; i < HOLES; i++) {
			for (int j = 0; j < HOLES; j++) {
				board[i][j] = new Room();
				dupBoard[i][j] = new Room();
			}
		}
		generator = new Random();
		setupPit();
		setupWumpus();
		setupHunter();
		setDupBoard();
		isMovable = true;
		isRunning = true;
		isWin = false;
		// types of traps. 0 means no traps
		trap = 0;
		super.setChanged();
		super.notifyObservers();
	}

	// Dup board for position check
	private void setDupBoard() {
		// TODO Auto-generated method stub
		for (int i = 0; i < HOLES; i++) {
			for (int j = 0; j < HOLES; j++) {
				if (board[i][j].getElement() == 'O')
					dupBoard[i][j].setElement(' ');
				else
					dupBoard[i][j].setElement(board[i][j].getElement());
			}
		}
	}

	// Setup for Hunter
	private void setupHunter() {
		// TODO Auto-generated method stub
		hunter_x = generator.nextInt(HOLES);
		hunter_y = generator.nextInt(HOLES);
		while (board[hunter_x][hunter_y].getElement() != ' ') {
			hunter_x = generator.nextInt(HOLES);
			hunter_y = generator.nextInt(HOLES);
		}
		board[hunter_x][hunter_y].setElement('O');
		board[hunter_x][hunter_y].setVisited(true);

	}

	// Setup For Wumpus
	private void setupWumpus() {
		// TODO Auto-generated method stub
		wumpus_x = generator.nextInt(HOLES);
		wumpus_y = generator.nextInt(HOLES);
		while (board[wumpus_x][wumpus_y].getElement() == 'P') {
			wumpus_x = generator.nextInt(HOLES);
			wumpus_y = generator.nextInt(HOLES);
		}

		board[wumpus_x][wumpus_y].setElement('W');
		for (int i = -2; i < 3; i++) {
			for (int j = -2; j < 3; j++) {
				if ((!(i != 0 && j != 0) || (Math.abs(i) == 1 && Math.abs(j) == 1))
						&& board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].getElement() != 'P') {
					if (board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].getElement() == 'S') {
						board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].setElement('G');
					} else
						board[(i + wumpus_x + HOLES) % HOLES][(j + wumpus_y + HOLES) % HOLES].setElement('B');
				}
			}
		}
		board[wumpus_x][wumpus_y].setElement('W');
	}

	// Setup for Pits
	private void setupPit() {
		// TODO Auto-generated method stub
		int n = 4;
		while (n > 0) {
			int x = generator.nextInt(HOLES);
			int y = generator.nextInt(HOLES);

			while (board[x][y].getElement() == 'P') {
				x = generator.nextInt(HOLES);
				y = generator.nextInt(HOLES);
			}
			board[x][y].setElement('P');
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (!(i != 0 && j != 0)
							&& board[(i + x + HOLES) % HOLES][(j + y + HOLES) % HOLES].getElement() != 'P')
						board[(i + x + HOLES) % HOLES][(j + y + HOLES) % HOLES].setElement('S');
				}
			}
			n--;
		}
	}

	// return dupBoard
	public Room[][] getDupBoard() {
		return dupBoard;
	}

	// set hunter is/isnt able to move anymore
	public void setMovable(boolean m) {
		isMovable = m;
	}

	// set game to keep running or not
	public void setIsRunning(boolean b) {
		isRunning = b;
	}

	// set if wins
	public void setIsWin(boolean b) {
		isWin = b;
	}

	// check if hunter is able to move or not
	public boolean getMovable() {
		return isMovable;
	}

	// return if game is still running or not
	public boolean isRunning() {
		return isRunning;
	}

	// return if wins or not
	public boolean isWin() {
		return isWin;
	}

	// return trap type
	public int getTrap() {
		return trap;
	}

	// return size
	public int getHoles() {
		return HOLES;
	}

	// return board
	public Room[][] getBoard() {
		return board;
	}

	// get x of Hunter
	public int getHunterX() {
		return hunter_x;
	}

	// get y of Hunter
	public int getHunterY() {
		return hunter_y;
	}

	// get x of Wumpus
	public int getWumpusX() {
		return wumpus_x;
	}

	// get y of Wumpus
	public int getWumpusY() {
		return wumpus_y;
	}

	// shoot arrow check if wins, wrap-around supported
	public void moveArrow(int i, int j) {
		// TODO Auto-generated method stub

		int temp_x = getHunterX(), temp_y = getHunterY();
		temp_x = (i + temp_x + HOLES) % HOLES;
		temp_y = (j + temp_y + HOLES) % HOLES;

		do {

			// check if wumpus is hit
			if (temp_x == getWumpusX() && temp_y == getWumpusY()) {
				this.setIsRunning(false);
				this.setIsWin(true);
				this.setMovable(false);
				setChanged();
				notifyObservers();
				return;
			}
			// or hunter will be died
			if (temp_x == getHunterX() && temp_y == getHunterY()) {
				this.setIsRunning(false);
				this.setIsWin(false);
				this.setMovable(false);
				setChanged();
				notifyObservers();
				return;
			}
			temp_x = (i + temp_x + HOLES) % HOLES;
			temp_y = (j + temp_y + HOLES) % HOLES;

		} while (true);
	}

	// function for moving hunter
	public void movePlayer(int x, int y) {

		int i = moveCheck(x, y);
		if (i == -1) {
			setMovable(false);
			trap = -1;
		}
		if (i == -2) {
			setMovable(false);
			trap = -2;
		}
		trap = i;

		board[hunter_x][hunter_y].setVisited(true);
		board[hunter_x][hunter_y].setElement(dupBoard[hunter_x][hunter_y].getElement());
		hunter_x = (hunter_x + x + HOLES) % HOLES;
		hunter_y = (hunter_y + y + HOLES) % HOLES;
		board[hunter_x][hunter_y].setElement('O');
		board[hunter_x][hunter_y].setVisited(true);
		setChanged();
		notifyObservers();
	}

	// moveHunter helper method
	private int moveCheck(int x, int y) {
		// TODO Auto-generated method stub
		if (board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].getElement() == 'P')
			return -1;
		if (board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].getElement() == 'W')
			return -2;
		if (board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].getElement() == 'S')
			return 1;
		if (board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].getElement() == 'G')
			return 2;
		if (board[(hunter_x + x + HOLES) % HOLES][(hunter_y + y + HOLES) % HOLES].getElement() == 'B')
			return 3;
		return 0;
	}

	// toString method, visited supported
	public String toString() {
		String str = "";
		for (int i = 0; i < HOLES; i++) {
			for (int j = 0; j < HOLES; j++) {
				if (board[j][i].isVisited())
					str += "[" + board[j][i].getElement() + "]" + " ";
				else
					str += "[" + "X" + "]" + " ";
			}
			str += "\n\n";
		}
		return str;
	}

	// toString method, show all
	public String toShowAllString() {
		String str = "";
		for (int i = 0; i < HOLES; i++) {
			for (int j = 0; j < HOLES; j++) {
				str += "[" + board[j][i].getElement() + "]" + " ";
			}
			str += "\n\n";

		}
		return str;
	}

}