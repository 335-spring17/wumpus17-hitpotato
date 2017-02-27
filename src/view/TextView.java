package view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Wumpus;

public class TextView extends JPanel implements Observer{

	public TextView(Wumpus game) {
		// TODO Auto-generated constructor stub
		setBackground(Color.WHITE);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
