package rowles.andrew.checkers.game;

import java.util.ArrayList;
import java.util.Random;

public class Helper {

    final int			WHITE	= 0;
	final int			BLACK	= 1;
	ArrayList<Piece>	board	= null;

	public ArrayList<Piece> cloneArrayList(ArrayList<Piece> Alpha)
	{
		ArrayList<Piece> Delta = new ArrayList<Piece>();

		for (int i = 0; i < Alpha.size(); i++)
		{
			Delta.add(new Piece(Alpha.get(i).color, Alpha.get(i).x, Alpha
					.get(i).y));

			if (Alpha.get(i).isKing())
			{
				Delta.get(i).kingPiece();
			}
		}

		return Delta;
	}

	// removes piece
	public ArrayList<Piece> removePiece(ArrayList<Piece> b, int x, int y)
	{
		int index;

		if (this.checkSquare(x, y, b))
		{
			index = this.getIndex(x, y, b);

			b.remove(index);
		}
		System.out.println("Removed: (" + x + "," + y + ");");
		return b;
	}

	public boolean isPlayable(ArrayList<Piece> b)
	{

		int blue = 0;
		int red = 0;

		for (int i = 0; i < b.size(); i++)
		{
			if (this.getPossibleMoves(b, i) > 0)
			{
				if (b.get(i).color == BLACK)
				{
					blue++;
				} else
				{
					red++;
				}
			}
		}
		if (blue == 0 || red == 0)
		{
			return false;
		}
		return true;
	}

	public ArrayList<Piece> checkForKings(ArrayList<Piece> board)
	{
		for (int i = 0; i < board.size(); i++)
		{
			if (board.get(i).color == 0 && board.get(i).y == 8)
			{
				board.get(i).kingPiece();
			}
			if (board.get(i).color == 1 && board.get(i).y == 1)
			{
				board.get(i).kingPiece();
			}
		}

		return board;
	}

	// gets the index of a piece according to x, y
	public int getIndex(int x, int y, ArrayList<Piece> b)
	{
		for (int i = 0; i < b.size(); i++)
		{
			if (b.get(i).x == x && b.get(i).y == y)
			{
				return i;
			}
		}
		return -1;
	}

	public boolean checkSquare(int x, int y, ArrayList<Piece> a)
	{
		for (int i = 0; i < a.size(); i++)
		{
			if (a.get(i).x == x && a.get(i).y == y)
			{
				return true;
			}
		}
		if (x > 8 || y > 8 || x < 1 || y < 1)
			return true;

		return false;
	}

	public boolean checkSquare(int x, int y, ArrayList<Piece> a, int color)
	{
		for (int i = 0; i < a.size(); i++)
		{
			if (a.get(i).x == x && a.get(i).y == y && a.get(i).color == color)
			{
				return true;
			}
		}
		if (x > 8 || y > 8 || x < 1 || y < 1)
			return true;

		return false;
	}

	public int getPossibleMoves(ArrayList<Piece> board, int index)
	{
		int moves = 0;

		if (board.get(index).color == WHITE || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x + 1,
					board.get(index).y + 1, board))
			{
				moves++;
			} else if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y + 2, board))
			{
				moves++;
			}
			if (!this.checkSquare(board.get(index).x - 1,
					board.get(index).y + 1, board))
			{
				moves++;
			} else if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y + 2, board))
			{
				moves++;
			}
		}
		if (board.get(index).color == BLACK || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x - 1,
					board.get(index).y - 1, board))
			{
				moves++;
			} else if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y - 2, board))
			{
				moves++;
			}
			if (!this.checkSquare(board.get(index).x + 1,
					board.get(index).y - 1, board))
			{
				moves++;
			} else if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y - 2, board))
			{
				moves++;
			}
		}

		return moves;
	}

	public int getPossibleAttacks(ArrayList<Piece> board, int index)
	{
		int attacks = 0;
		int targetColor;
		if (board.size() < index)
			return 0;
		if (board.get(index).color == WHITE)
			targetColor = BLACK;
		else
			targetColor = WHITE;

		if (board.get(index).color == WHITE || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y + 2, board)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y + 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y - 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y - 1, board, targetColor))
			{
				attacks++;
			}

			if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y + 2, board)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y + 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y - 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y - 1, board, targetColor))
			{
				attacks++;
			}
		}

		if (board.get(index).color == BLACK || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y - 2, board)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y - 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y + 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y + 1, board, targetColor))
			{
				attacks++;
			}

			if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y - 2, board)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y - 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y + 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y + 1, board, targetColor))
			{
				attacks++;
			}
		}

		return attacks;
	}

	public int getPossibleBenefitalAttacks(ArrayList<Piece> board, int index)
	{
		int attacks = 0;
		int targetColor;
		if (board.get(index).color == WHITE)
			targetColor = BLACK;
		else
			targetColor = WHITE;
		if (board.size() < index)
			return 0;
		if (board.get(index).color == WHITE || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y + 2, board)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y + 1, board)
					&& !this.checkSquare(board.get(index).x + 3,
							board.get(index).y + 3, board, targetColor))
			{
				attacks++;
			}

			if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y + 2, board)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y + 1, board)
					&& !this.checkSquare(board.get(index).x - 3,
							board.get(index).y + 3, board, targetColor))
			{
				attacks++;
			}
		}

		if (board.get(index).color == BLACK || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y - 2, board)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y - 1, board)
					&& !this.checkSquare(board.get(index).x - 3,
							board.get(index).y - 3, board, targetColor))
			{
				attacks++;
			}

			if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y - 2, board)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y - 1, board)
					&& !this.checkSquare(board.get(index).x + 3,
							board.get(index).y - 3, board, targetColor))
			{
				attacks++;
			}
		}

		return attacks;
	}

	// Gets the number of benefial moves
	public int getPossibleBenefitalMoves(ArrayList<Piece> board, int index)
	{
		int moves = 0;
		int attacks = this.getPossibleBenefitalAttacks(board, index);
		int targetColor;
		if (board.get(index).color == WHITE)
			targetColor = BLACK;
		else
			targetColor = WHITE;
		if (board.size() < index)
			return 0;
		if (board.get(index).color == WHITE || board.get(index).isKing())
		{
			if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y + 2, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y + 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y + 1, board)
					&& (board.get(index).x + 1) < 9
					&& (board.get(index).y + 1) < 9)
				moves++;
			if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y + 2, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y + 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y + 1, board)
					&& (board.get(index).x - 1) > 0
					&& (board.get(index).y + 1) < 9)
				moves++;
		}

		if (board.get(index).color == BLACK || board.get(index).isKing())
		{

			if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y - 2, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y - 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x + 1,
							board.get(index).y - 1, board)
					&& (board.get(index).x + 1) < 9
					&& (board.get(index).y - 1) > 0)
				moves++;
			// System.out.print(moves);
			if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y - 2, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y - 1, board, targetColor)
					&& !this.checkSquare(board.get(index).x - 1,
							board.get(index).y - 1, board)
					&& (board.get(index).x - 1) > 0
					&& (board.get(index).y - 1) > 0)
				moves++;
		}

		return (attacks + moves);
	}

	// Checks if two boards are equal
	public boolean equals(ArrayList<Piece> a, ArrayList<Piece> b)
	{

		boolean A, B;

		for (int i = 1; i < 9; i += 1)
		{

			for (int j = 1; j < 9; j += 1)
			{
				A = this.checkSquare(i, j, a);
				B = this.checkSquare(i, j, b);

				if (A != B)
				{
					return false;
				}
			}
		}

		return true;
	}

	// returns x/y difference between two
	public boolean getDifference(ArrayList<Piece> a, ArrayList<Piece> b,
			int color)
	{

		boolean A, B;
		int targetColor = this.BLACK;
		ArrayList<ArrayList<Integer>> differences = new ArrayList<ArrayList<Integer>>();

		if (color == this.BLACK)
			targetColor = this.WHITE;

		for (int i = 1; i < 9; i += 1)
		{
			for (int j = 1; j < 9; j += 1)
			{
				A = this.checkSquare(i, j, a);
				B = this.checkSquare(i, j, b);
				if (A != B)
				{
					ArrayList<Integer> c = new ArrayList<Integer>();
					c.add(i);
					c.add(j);
					differences.add(c);
				}
			}
		}

		if (differences.size() == 2)
		{
			if (this.isLegal(differences.get(0).get(0),
					differences.get(0).get(1), differences.get(1).get(0),
					differences.get(1).get(1), b, color))
			{
				return true;
			}

			if (this.isLegal(differences.get(1).get(0),
					differences.get(1).get(1), differences.get(0).get(0),
					differences.get(0).get(1), b, color))
			{
				return true;
			}
		}

		if (differences.size() > 2 && differences.size() < 5)
		{
			for (int i = 0; i < differences.size(); i++)
			{
				for (int j = 0; j < differences.size(); j++)
				{
					if (i != j)
					{
						ArrayList<Piece> t = b;

						for (int h = 0; h < t.size(); h++)
						{
							if (t.get(h).x != differences.get(i).get(0)
									|| t.get(h).x != differences.get(j).get(0))
							{
								t.add(new Piece(targetColor, t.get(h).x, t
										.get(h).y));
							}
						}

						if (this.isLegal(differences.get(i).get(0), differences
								.get(i).get(1), differences.get(j).get(0),
								differences.get(j).get(1), t, color))
						{
							return true;
						}

						if (this.isLegal(differences.get(j).get(0), differences
								.get(j).get(1), differences.get(i).get(0),
								differences.get(i).get(1), t, color))
						{
							return true;
						}

					}
				}
			}
		}

		return false;
	}

	// Gets random move for a color.
	public ArrayList<Piece> getRandomMove(ArrayList<Piece> b, int color)
	{
		ArrayList<Integer> pieces = new ArrayList<Integer>();

		Random generator = new Random();

		for (int i = 0; i < b.size(); i++)
		{
			if (b.get(i).color == color && this.getPossibleMoves(b, i) > 0)
			{
				pieces.add(i);
			}
		}
		ArrayList<ArrayList<Piece>> Moves = new ArrayList<ArrayList<Piece>>();
		if (pieces.size() > 0)
		{

			for (int i = 0; i < pieces.size(); i++)
			{
				if (this.getPossibleMoves(b, pieces.get(i)) > 0)
					Moves.add(this.getRndMove(b, pieces.get(i)));
			}

		}
		return Moves.get(generator.nextInt() * Moves.size());
	}

	// Gets a random move for a piece
	public ArrayList<Piece> getRndMove(ArrayList<Piece> b, int index)
	{
		int moves = this.getPossibleMoves(b, index);
		Random rnd = new Random();

		int ratio = (1 / moves);

		for (int i = 0; i < moves; i++)
		{
			int m = rnd.nextInt() * 100;

			if (m <= (ratio * 100))
			{
				// TODO find a way to stop repeating these checks.
				if (b.get(index).color == WHITE || b.get(index).isKing())
				{
					if (!this.checkSquare(b.get(index).x + 1,
							b.get(index).y + 1, b))
					{
						b.get(index).setXY(b.get(index).x + 1,
								b.get(index).y + 1);
						return b;
					} else if (!this.checkSquare(b.get(index).x + 2,
							b.get(index).y + 2, b))
					{
						b.get(index).setXY(b.get(index).x + 2,
								b.get(index).y + 2);
						return b;
					}
					if (!this.checkSquare(b.get(index).x - 1,
							b.get(index).y + 1, b))
					{
						b.get(index).setXY(b.get(index).x - 1,
								b.get(index).y + 1);
						return b;
					} else if (!this.checkSquare(b.get(index).x - 2,
							b.get(index).y + 2, b))
					{
						b.get(index).setXY(b.get(index).x - 2,
								b.get(index).y + 2);
						return b;
					}
				}
				if (b.get(index).color == BLACK || b.get(index).isKing())
				{
					if (!this.checkSquare(b.get(index).x - 1,
							b.get(index).y - 1, b))
					{
						b.get(index).setXY(b.get(index).x - 1,
								b.get(index).y - 1);
						return b;
					} else if (!this.checkSquare(b.get(index).x - 2,
							b.get(index).y - 2, b))
					{
						b.get(index).setXY(b.get(index).x - 2,
								b.get(index).y - 2);
						return b;
					}
					if (!this.checkSquare(b.get(index).x + 1,
							b.get(index).y - 1, b))
					{
						b.get(index).setXY(b.get(index).x + 1,
								b.get(index).y - 1);
						return b;
					} else if (!this.checkSquare(b.get(index).x + 2,
							b.get(index).y - 2, b))
					{
						b.get(index).setXY(b.get(index).x + 2,
								b.get(index).y - 2);
						return b;
					}
				}
			}
		}
		return b;
	}

	// Checks the legality of a move.
	public boolean isLegal(int x, int y, int nx, int ny, ArrayList<Piece> b,
			int playerColor)
	{
		this.board = cloneArrayList(b);
		boolean legal = false;
		boolean foundX = false;
		for (int e = 0; e < b.size(); e++)
		{
			if (b.get(e).x == x)
				foundX = true;
		}
		if (foundX == false)
			return false;

		int index = getIndex(x, y, b);

		if (b.get(index).color != playerColor)
		{
			return false;
		}

		int Ydifference, otherY, targetColor;

		if (b.get(index).color == 0)
		{
			Ydifference = 1;
			otherY = -1;
		} else
		{
			Ydifference = -1;
			otherY = 1;
		}
		if (b.get(index).color == 0)
		{
			targetColor = 1;
		} else
		{
			targetColor = 0;
		}

		ArrayList<Piece> Board = cloneArrayList(b);

		// all the possible moves
		int[][] possibleMoves = { { x + 1, y + Ydifference }, // 0
				{ x - 1, y + Ydifference }, // 1
				{ x + 2, y + (Ydifference * 2) }, // 2
				{ x - 2, y + (Ydifference * 2) }, // 3
				{ x + 1, y + otherY }, // 4
				{ x - 1, y + otherY }, // 5
				{ x + 2, y + (otherY * 2) }, // 6
				{ x - 2, y + (otherY * 2) } // 7
		};

		for (int m = 0; m < possibleMoves.length; m++)
		{
			if (possibleMoves[m][0] == nx && possibleMoves[m][1] == ny
					&& !checkSquare(nx, ny, b, targetColor))
			{
				System.out.println("Player chose legal move "
						+ this.getIndex(x, y, Board) + " "
						+ board.get(this.getIndex(x, y, Board)).king);

				if (m == 1 || m == 0)
				{
					legal = true;
				}

				if (m == 2)
				{
					if (checkSquare((x + 1), (y + Ydifference), Board,
							targetColor))
					{
						Board = removePiece(Board, (x + 1), (Ydifference + y));
						legal = true;
					}
				}

				if (m == 3)
				{
					if (checkSquare(x - 1, (y + Ydifference), Board,
							targetColor))
					{
						Board = removePiece(Board, (x - 1), (Ydifference + y));
						legal = true;
					}
				}

				if (board.get(this.getIndex(x, y, Board)).isKing())
				{
					if (m == 4 || m == 5)
						legal = true;

					if (m == 6)
					{
						if (checkSquare((x + 1), (y + otherY), Board,
								targetColor))
						{
							Board = removePiece(Board, (x + 1), (otherY + y));
							legal = true;
						}
					}
					if (m == 7)
					{
						if (checkSquare((x - 1), (y + otherY), Board,
								targetColor))
						{
							Board = removePiece(Board, (x - 1), (otherY + y));
							legal = true;
						}
					}
				}
			}
		}
		if (legal)
		{
			Board.get(index).setXY(nx, ny);
			if (checkSquare(x, y, Board))
			{
				Board = removePiece(Board, x, y);
			}
			this.board = cloneArrayList(Board);
		}
		return legal;
	}
}