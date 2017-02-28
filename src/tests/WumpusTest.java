package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import model.Room;
import model.Wumpus;

/**
 * JUnit test for Wumpus.
 * 
 * @author Long Chen
 */
public class WumpusTest {

	Wumpus test = new Wumpus();
	Random generator = new Random();
	@Test
	public void test() {
		Room[][] dup = test.getDupBoard();
		int w_x = test.getWumpusX();
		int w_y = test.getWumpusY();
		int h_x = test.getHunterX();
		int h_y = test.getHunterY();
		System.out.println(test.getHoles());
		System.out.println(test.toString());
		System.out.println(test.toShowAllString());
		int k = 0;
		while(test.isRunning() && k < 8){
			int n = generator.nextInt(2);
			if(n == 1)
				test.movePlayer(1, 0);
			else
				test.movePlayer(0, 1);
			k++;
		}
		test.moveArrow(0, 1);
		k = 0;
		while(test.isRunning() && k < 100){
			int n = generator.nextInt(2);
			if(n == 1)
				test.movePlayer(1, 0);
			else
				test.movePlayer(0, 1);
			k++;
		}
		assertEquals(false,test.isRunning());
		assertEquals(false,test.isWin());
		test.moveArrow(1, 0);
		
		
	}

}
