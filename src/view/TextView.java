package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import model.Wumpus;
/**
 * TextView for a game of Wumpus. 
 *  
 * @author Long Chen
 */
public class TextView extends JPanel implements Observer {
	private Wumpus model;
	private JTextArea textView, message, status;

	// constructor
	public TextView(Wumpus game) {
		// TODO Auto-generated constructor stub
		this.model = game;
		setBackground(Color.YELLOW);
		textView = new JTextArea(model.toString());
		message = new JTextArea("Move Up:W, Down:S, Left:A, Right:D\nShoot Up:I, Down:K, Left:J, Right:L");
		status = new JTextArea("");
		textView.setSize(500, 500);
		textView.setFont(new Font("Courier", Font.BOLD, 20));
		message.setFont(new Font("Courier", Font.BOLD, 20));
		status.setFont(new Font("Courier", Font.BOLD, 20));
		this.add(textView);
		this.add(message);
		this.add(status);
		repaint();
	}

	// update TextArea
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
			updateTextArea();
	}

	// method for check status and message 
	private void updateTextArea() {
		// TODO Auto-generated method stub
		textView.setText(model.toString());
		status.setText("");
		
		if (!model.isRunning() && !model.isWin()){
			textView.setText(model.toShowAllString());
			status.setText("You killed yourself! Game over!");
		}
		if (!model.isRunning() && model.isWin()){
			textView.setText(model.toShowAllString());
			status.setText("You killed Wumpus! Game over!");
		}
		if (model.getTrap() == -1){
			textView.setText(model.toShowAllString());
			status.setText("You walked into Pit!");
		}
		if (model.getTrap() == -2){
			textView.setText(model.toShowAllString());
			status.setText("You walked into Wumpus!");
		}
		if (model.getTrap() == 1)
			status.setText("You walked into Slime!");
		if (model.getTrap() == 2)
			status.setText("You walked into Goop!");
		if (model.getTrap() == 3)
			status.setText("You walked into Blood!");
		
	}

}
