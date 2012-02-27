package rowles.andrew.checkers.game;

import java.util.ArrayList;

public class Helper {
	final int RED = 0;
	final int BLUE = 1;

	ArrayList<Piece> board = null; // used only for isLegal

	public ArrayList<Piece> cloneArrayList(ArrayList<Piece> Alpha) {
		ArrayList<Piece> Delta = new ArrayList<Piece>();

		for (int i = 0; i < Alpha.size(); i++) {
			Delta.add(new Piece(Alpha.get(i).color, Alpha.get(i).x, Alpha
					.get(i).y));
		}

		return Delta;
	}

	// removes piece
	public ArrayList<Piece> removePiece(ArrayList<Piece> b, int x, int y) {
		int index;

		if (this.checkSquare(x, y, b)) {
			index = this.getIndex(x, y, b);

			b.remove(index);
		}
		System.out.println("Removed: (" + x + "," + y + ");");
		return b;
	}

	public boolean isPlayable(ArrayList<Piece> b) {

		int blue = 0;
		int red = 0;

		for (int i = 0; i < b.size(); i++) {
			if (this.getPossibleMoves(b, i) > 0) {
				if (b.get(i).color == BLUE)
					blue++;
				else
					red++;
			}
		}
		if (blue == 0 || red == 0) {
			return false;
		}
		return true;
	}

	public ArrayList<Piece> checkForKings(ArrayList<Piece> board) {
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i).color == 0 && board.get(i).y == 8)
				board.get(i).kingPiece();
			if (board.get(i).color == 1 && board.get(i).y == 1)
				board.get(i).kingPiece();
		}

		return board;
	}

	// gets the index of a piece according to x, y
	public int getIndex(int x, int y, ArrayList<Piece> b) {
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i).x == x && b.get(i).y == y) {
				return i;
			}
		}
		return -1;
	}

	public boolean checkSquare(int x, int y, ArrayList<Piece> a) {
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).x == x && a.get(i).y == y) {
				return true;
			}
		}
		return false;
	}

	public boolean checkSquare(int x, int y, ArrayList<Piece> a, int color) {
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i).x == x && a.get(i).y == y && a.get(i).color == color) {
				return true;
			}
		}
		return false;
	}

	public int getPossibleMoves(ArrayList<Piece> board, int index) {
		int moves = 0;

		if (board.get(index).color == RED || board.get(index).isKing()) {
			if (!this.checkSquare(board.get(index).x + 1,
					board.get(index).y + 1, board)) {
				moves++;
			} else if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y + 2, board)) {
				moves++;
			}
			if (!this.checkSquare(board.get(index).x - 1,
					board.get(index).y + 1, board)) {
				moves++;
			} else if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y + 2, board)) {
				moves++;
			}
		}
		if (board.get(index).color == BLUE || board.get(index).isKing()) {
			if (!this.checkSquare(board.get(index).x - 1,
					board.get(index).y - 1, board)) {
				moves++;
			} else if (!this.checkSquare(board.get(index).x - 2,
					board.get(index).y - 2, board)) {
				moves++;
			}
			if (!this.checkSquare(board.get(index).x + 1,
					board.get(index).y - 1, board)) {
				moves++;
			} else if (!this.checkSquare(board.get(index).x + 2,
					board.get(index).y - 2, board)) {
				moves++;
			}
		}

		return moves;
	}

	public boolean equals(ArrayList<Piece> a, ArrayList<Piece> b) {

		boolean A, B;

		for (int i = 1; i < 9; i += 1) {

			for (int j = 1; j < 9; j += 1) {
				A = this.checkSquare(i, j, a);
				B = this.checkSquare(i, j, b);

				if (A != B) {
					return false;
				}
			}
		}

		return true;
	}

	// returns x/y difference between two
	public int[] getDifference(ArrayList<Piece> a, ArrayList<Piece> b) {

		boolean A, B;

		for (int i = 1; i < 9; i += 1) {

			for (int j = 1; j < 9; j += 1) {

				A = this.checkSquare(i, j, a);
				B = this.checkSquare(i, j, b);

				if (A != B) {

					int[] c = { i, j };

					return c;
				}

			}

		}
		return null;
	}

	public boolean isLegal(int x, int y, int nx, int ny, ArrayList<Piece> b,
			int playerColor) {
		this.board = cloneArrayList(b);
		boolean legal = false;

		int index = getIndex(x, y, b);

		if (b.get(index).color != playerColor)
			return false;

		int Ydifference, otherY, targetColor; // otherY is used only for the
												// king

		if (b.get(index).color == 0) {
			Ydifference = 1;
			otherY = -1;
		} else {
			Ydifference = -1;
			otherY = 1;
		}
		if (b.get(index).color == 0)
			targetColor = 1;
		else
			targetColor = 0;

		ArrayList<Piece> Board = cloneArrayList(b);

		// all the possible moves
		int[][] possibleMoves = { { x + 1, y + Ydifference },
				{ x - 1, y + Ydifference }, { x + 2, y + (Ydifference * 2) },
				{ x - 2, y + (Ydifference * 2) }, { x + 1, y + otherY },
				{ x - 1, y + otherY }, { x + 2, y + (otherY * 2) },
				{ x - 2, y + (otherY * 2) } };

		for (int m = 0; m < possibleMoves.length; m++) {
			if (possibleMoves[m][0] == nx && possibleMoves[m][1] == ny
					&& !checkSquare(nx, ny, b, targetColor)) {
				System.out.println("xy on list");
				if (m < 4) {
					if (m == 1 || m == 0)
						legal = true;

					if (m == 2) {
						if (checkSquare((x + 1), (y + Ydifference), Board,
								targetColor)) {
							Board = removePiece(Board, (x + 1),
									(Ydifference + y));
							legal = true;
						}
					}

					if (m == 3) {
						System.out.println("m == 3");
						if (checkSquare(x - 1, (y + Ydifference), Board,
								targetColor)) {
							Board = removePiece(Board, (x - 1),
									(Ydifference + y));
							legal = true;
						}
					}

				} else {
					if (b.get(index).isKing()) {
						if (m == 5 || m == 6) {
							legal = true;
							if (m == 7) {
								if (checkSquare(x + 1, (y + otherY), Board,
										targetColor)) {
									Board = removePiece(Board, (x + 1),
											(otherY + y));
									legal = true;
								}
							}
							if (m == 8) {
								if (checkSquare(x - 1, (y + otherY), Board,
										targetColor)) {
									Board = removePiece(Board, (x - 1),
											(otherY + y));
									legal = true;
								}

							}
						}
					}
				}
			}
		}
		if (legal) {
			Board.get(index).setXY(nx, ny);
			this.board = cloneArrayList(Board);
		}
		return legal;
	}

}
