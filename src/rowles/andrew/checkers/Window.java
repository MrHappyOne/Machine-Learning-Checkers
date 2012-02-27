package rowles.andrew.checkers;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import rowles.andrew.checkers.Canvas;
import rowles.andrew.checkers.game.Piece;

@SuppressWarnings("serial")
public class Window extends JFrame {

	public JFrame frame;
	public Canvas canvas;

	JTextField input;

	public Window(double version) {
		canvas = new Canvas();
		frame = new JFrame();
		frame.setTitle("Checkers " + version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(canvas);
		frame.setSize(800, 600);
		frame.setVisible(true);

		input = new JTextField(35);
		input.setBounds(70, 700, 100, 20);
		input.setVisible(true);
		frame.add(input);

	}

	public void updateBoard(ArrayList<Piece> board) {
		canvas.board = board;
	}

}
