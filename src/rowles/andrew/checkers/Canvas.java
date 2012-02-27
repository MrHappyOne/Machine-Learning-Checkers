package rowles.andrew.checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JTextField;

import rowles.andrew.checkers.game.Helper;
import rowles.andrew.checkers.game.Logic;
import rowles.andrew.checkers.game.Piece;

@SuppressWarnings("serial")
public class Canvas extends JComponent {

	public ArrayList<Piece> board;

	public int size = 50;

	Logic logic = new Logic();
	Helper Helper = new Helper();

	JTextField input;

	public Canvas() {
		board = new ArrayList<Piece>();
	}

	// TODO check README
	public void drawBoard(Graphics g) {
		int color = 0;
		for (int y = 1; y < 9; y++) { // int y=8; y>0; y--
			if (y == 1 || y == 3 || y == 5 || y == 7)
				color = 0;
			else
				color = 1;

			for (int x = 8; x > 0; x--) { // int x=1; x<9; x++
				if (color == 0)
					g.setColor(Color.DARK_GRAY);
				else
					g.setColor(Color.gray);

				g.fillRect(x * size, y * size, size, size);

				if (Helper.checkSquare(x, y, board)) {

					if (board.get(Helper.getIndex(x, y, board)).color == 1) {
						g.setColor(Color.BLACK);
					} else {
						g.setColor(Color.WHITE);
					}
					g.fillOval(x * size, y * size, size, size);
				}
				if (color == 0)
					color = 1;
				else
					color = 0;
			}
		}
	}

	public void drawLabel(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.PLAIN, 36);
		g2.setFont(font);

		int lastDraw = -1;
		for (int y = 8; y > 0; y--) {
			for (int i = 1; i < 9; i++) {
				if (lastDraw != y)
					g2.drawString(Integer.toString(y), (y * 50) + 10, 45);
				lastDraw = y;
			}

		}
		for (int y = 8; y > 0; y--) {
			for (int i = 1; i < 9; i++) {
				if (lastDraw != y)
					g2.drawString(Integer.toString(y), 20, (y * 50) + 35);
				lastDraw = y;
			}

		}
	}

	public void paint(Graphics g) {

		this.drawBoard(g);
		this.drawLabel(g);
		repaint();
	}

}
