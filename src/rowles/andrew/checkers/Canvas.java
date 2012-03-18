package rowles.andrew.checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import rowles.andrew.checkers.game.Helper;
import rowles.andrew.checkers.game.Logic;
import rowles.andrew.checkers.game.Piece;

public class Canvas extends JComponent {

    public ArrayList<Piece>	board;
	public int				size		= 60;
	public Logic			logic		= new Logic();
	public Helper			Helper		= new Helper();
	public Selector			Input		= new Selector(1, 1);
	public Boolean			canControl	= true;
	public Boolean			requestInfo = false;

	public Canvas()
	{
		board = new ArrayList<Piece>();
	}

	// TODO check README
	public void drawBoard(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.PLAIN, 12);
		int color = 0;

		DecimalFormat df = new DecimalFormat("#.00");

		for (int y = 1; y < 9; y++)
		{
			if (y == 1 || y == 3 || y == 5 || y == 7)
			{
				color = 0;
			} else
			{
				color = 1;
			}

			for (int x = 8; x > 0; x--)
			{
				if (color == 0)
				{
					g.setColor(Color.DARK_GRAY);
				} else
				{
					g.setColor(Color.gray);
				}

				g.fillRect(x * size, y * size, size, size);

				if (Helper.checkSquare(x, y, board))
				{
					int ix = Helper.getIndex(x, y, board);
					if (board.get(ix).color == 1)
					{
						g.setColor(Color.BLACK);
					} else
					{
						g.setColor(Color.WHITE);
					}
					g.fillOval(x * size, y * size, size, size);

					g2.setFont(font);
					g2.setColor(Color.red);
					if (board.get(ix).isKing()) {
						g2.drawString("K", (x * size) + 10,(y * size) + 10);
					}
						
					/*
					 * Shows point value of piece. This is disabled at the moment.
					double val = logic.AnalyzePiece(board, Helper.getIndex(x, y, board));;
					int digits = 10000; // keep 4 digits
					val = Math.floor(val * digits + .5) / digits;

					g2.drawString(Double.toString(digits), (x * size) + 10,
							(y * size) + 10);
					*/
				}
				if (color == 0)
				{
					color = 1;
				} else
				{
					color = 0;
				}
			}
		}
	}

	public void drawSelector(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(new Color(.3f, .4f, .5f, .8f));

		g2.fillRect(Input.getX() * size, Input.getY() * size, size, size);

		if (Input.isSelected())
		{
			g2.fillRect(Input.getLastX() * size, Input.getLastY() * size, size,
					size);
		}
	}

	public void drawLabel(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("Serif", Font.PLAIN, 36);
		g2.setFont(font);

		int lastDraw = -1;
		for (int y = 8; y > 0; y--)
		{
			for (int i = 1; i < 9; i++)
			{
				if (lastDraw != y)
				{
					g2.drawString(Integer.toString(y), (y * size) + 10, 45);
				}
				lastDraw = y;
			}

		}
		for (int y = 8; y > 0; y--)
		{
			for (int i = 1; i < 9; i++)
			{
				if (lastDraw != y)
				{
					g2.drawString(Integer.toString(y), 20, (y * size) + 35);
				}
				lastDraw = y;
			}

		}
	}

	public void paint(Graphics g)
	{

		this.drawBoard(g);
		this.drawLabel(g);
		this.drawSelector(g);
		repaint();
	}

	public void init(JFrame ob)
	{
		ob.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e)
			{

				int keycode = e.getKeyCode();

				if (canControl)
				{
					if (keycode == 40 && Input.getY() != 8)
						Input.updateXY(Input.getX(), Input.getY() + 1);
					if (keycode == 38 && Input.getY() != 1)
						Input.updateXY(Input.getX(), Input.getY() - 1);
					if (keycode == 39 && Input.getX() != 8)
						Input.updateXY(Input.getX() + 1, Input.getY());
					if (keycode == 37 && Input.getX() != 1)
						Input.updateXY(Input.getX() - 1, Input.getY());

					// 10 enter
					if (keycode == 10 && !Input.isSelected())
					{
						Input.updateLastXY(Input.getX(), Input.getY());
						Input.setSelected(true);
					} else if (keycode == 10 && Input.isSelected()
							&& Input.getX() != Input.getLastX()
							&& Input.getY() != Input.getLastY())
					{
						Input.isOkay = true;
					}
					
					if (keycode == 65 && !requestInfo) {
						requestInfo = true;
					}

					if (keycode == 8 && Input.isSelected())
					{
						Input.setSelected(false);
					}
				}
			}
			public void keyReleased(KeyEvent e)
			{
			}

			public void keyTyped(KeyEvent e)
			{

			}

		});
	}
}

