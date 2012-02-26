package rowles.andrew.checkers.learn;

import java.util.ArrayList;

public class Logic {

	ArrayList <Piece> B = null;
	
	
	public int Compare(ArrayList<Piece> a, ArrayList<Piece> b, int colorA, int colorB) {

		int A = 1;
		int B = 2;
		int even = 0;

		double scoresA[] = this.getScore(a);
		double scoresB[] = this.getScore(b);

		double Ascore = scoresA[colorA - 1];
		double Bscore = scoresB[colorB - 1];

		if (Ascore > Bscore) {
			return A;
		}
		if (Bscore > Ascore) {
			return B;
		}

		return even;
	}

	public double[] getScore(ArrayList<Piece> a) {
		double redScore = 0;
		double blueScore = 0;

		for (int i = 0; i < a.size(); i++) {
			double score = AnalyzePiece(a, i);

			if (a.get(i).color == 1) {
				redScore += score;
			} else {
				blueScore += score;
			}

		}

		double[] scores = { redScore, blueScore };
		//System.out.println (redScore +" | "+ blueScore);
		return scores;
	}
	
	public double getScore(ArrayList<Piece> a, int color) {
		double redScore = 0;
		double blueScore = 0;

		for (int i = 0; i < a.size(); i++) {
			double score = AnalyzePiece(a, i);

			if (a.get(i).color == 1) {
				redScore += score;
			} else {
				blueScore += score;
			}

		}
		
		if (color == 1)
			return redScore;

		return blueScore;
	}

	// values piece
	public double AnalyzePiece(ArrayList<Piece> a, int i) {
		int tscore = 0;
		int distance = 0;

		if (a.get(i).x == 1 || a.get(i).x == 8) {
			tscore += 2;
		}
		int moves = this.getPossibleMoves(a, i);

		if (!a.get(i).isKing()) {
			if (a.get(i).color == 1) {
				distance = 8 - a.get(i).y;

				if (distance == 0) {
					distance = 1;
				}
			}

			if (a.get(i).color == 2) {
				distance = a.get(i).y - 1;

				if (distance == 0) {
					distance = 1;
				}
			}
		}
		
		double score;
		
		if (distance != 0) {
			score = (tscore + moves) / distance;
		} else {
			score = (tscore + moves);
		}
		

		if (a.get(i).isKing()) {
			score = score * 1.5;
		}

		return score;
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

	public int getPossibleMoves(ArrayList<Piece> a, int n) {
		int moves = 0;
		//System.out.println("color = " + a.get(n).color);
		if (a.get(n).color == 0) {
			if (!this.checkSquare(a.get(n).x + 1, a.get(n).y + 1, a)) {
				moves++;
			} else if (!this.checkSquare(a.get(n).x + 2, a.get(n).y + 2, a)) {
				moves++;
			}
			if (!this.checkSquare(a.get(n).x - 1, a.get(n).y + 1, a)) {
				moves++;
			} else if (!this.checkSquare(a.get(n).x - 2, a.get(n).y + 2, a)) {
				moves++;
			}
		}
		if (a.get(n).color == 1) {
			if (!this.checkSquare(a.get(n).x - 1, a.get(n).y - 1, a)) {
				moves++;
			}
			if (!this.checkSquare(a.get(n).x + 1, a.get(n).y - 1, a)) {
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
					
					int[] c = {i, j};
					
					return c;
				}
				
			}

		}
		return null;
	}
	
	// gets the index of a piece according to x, y
	public int getIndex (int x, int y, ArrayList<Piece> b) {
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i).x == x && b.get(i).y == y) {
				return i;
			}
		}
		return -1;
	}
	
	// gets best move for color
	public ArrayList<Piece> getBestMove (ArrayList<Piece> b, int color) {
		
		int bestIndex = 0;
		
		ArrayList<ArrayList<Piece>> MOVES = new ArrayList<ArrayList<Piece>>();
		
		for (int index = 0; index < b.size(); index++) {
			if (b.get(index).color == color) {
				MOVES.add(this.bestMove(b, index));
			}
		}
		
		for (int i = 0; i < MOVES.size(); i ++) {
			
			double s = this.getScore(MOVES.get(i), color);
			double best = this.getScore(MOVES.get(bestIndex), color);
			if (s > best)
				bestIndex = i;
		}
		
		return MOVES.get(bestIndex);
	}
	
	// gets best move for random piece
	public ArrayList<Piece> getRandomMove (ArrayList<Piece> b, int color) {
		ArrayList<Piece> C = b;
		for (int i = 0; i < b.size(); i++) {
			
			// 
			if (b.get(i).color == color && this.getPossibleMoves(b, i) != 0) {
				C = this.bestMove(C, i);
				return C;
			}
		}
		
		return b;
	}
	
	public ArrayList<Piece> cloneArrayList (ArrayList<Piece> Alpha) {
		ArrayList<Piece> Delta = new ArrayList<Piece> ();
		
		for (int i = 0; i < Alpha.size(); i++) {
			//System.out.println (i + ", "  + Alpha.get(i).x + ", " + Alpha.get(i).y + ", " + Alpha.get(i).color+ ", ");
			Delta.add(new Piece (Alpha.get(i).color, Alpha.get(i).x, Alpha.get(i).y));
		}
		
		return Delta;
	}
	
	// gets best move for piece
	public ArrayList<Piece> bestMove (ArrayList<Piece> b, int index) {
		
		int Ydifference, otherY; // otherY is used only for the king
		
		if (b.get(index).color == 0) {
			Ydifference = 1;
			otherY = -1;
		}else{
			Ydifference = -1;
			otherY = 1;
		}
		
		if (this.getPossibleMoves(b, index) > 0) { // the problem isn't here
			
			// is the problem here?
			ArrayList<Piece> cloneAlpha = this.cloneArrayList(b);
			ArrayList<Piece> cloneBeta = this.cloneArrayList(b);
			
			ArrayList<Piece> cloneGamma = this.cloneArrayList(b);
			ArrayList<Piece> cloneDelta = this.cloneArrayList(b);
			
			
			int x = b.get(index).x; // x = 4
			int y = b.get(index).y; // y = 3

			int targetColor;
			
			if (b.get(index).color == 0)
				targetColor = 1;
			else 
				targetColor = 0;
			
			// x + 1 = 5
			// y + 1 = 4 
			if (!this.checkSquare(x+1, y+Ydifference, cloneAlpha)) {
				cloneAlpha.get(index).setXY((x+1), (y+Ydifference));
			} else if (!this.checkSquare(x+2, y+(Ydifference)*2, cloneAlpha) && this.checkSquare(x+1, y+Ydifference, cloneAlpha, targetColor)) {
				cloneAlpha.get(index).setXY((x+2), (y+(Ydifference)*2));
				cloneAlpha = this.cloneArrayList(this.removePiece(cloneAlpha, x+1, y+Ydifference));
			}

			// x - 1 = 3
			// y + 1 = 4
			if (!this.checkSquare(x-1, y+Ydifference, cloneBeta)) {
				cloneBeta.get(index).setXY(x-1, y+Ydifference);
			} else if (!this.checkSquare(x-2, y+(Ydifference)*2, cloneBeta) && this.checkSquare(x-1, y+Ydifference, cloneBeta, targetColor)) {
				cloneBeta.get(index).setXY((x-2), (y+(Ydifference)*2));
				cloneBeta = this.cloneArrayList(this.removePiece(cloneBeta, x-1, y+Ydifference));
			}
			
			if (b.get(index).isKing()) {
				if (!this.checkSquare(x+1, y+otherY, cloneGamma)) {
					cloneGamma.get(index).setXY((x+1), (y+otherY));
				} else if (!this.checkSquare(x+2, y+(otherY)*2, cloneGamma) && this.checkSquare(x+1, y+otherY, cloneGamma, targetColor)) {
					cloneGamma.get(index).setXY((x+2), (y+(otherY)*2));
					cloneGamma = this.cloneArrayList(this.removePiece(cloneGamma, x+1, y+otherY));
				}
				
				if (!this.checkSquare(x-1, y+otherY, cloneDelta)) {
					cloneDelta.get(index).setXY(x-1, y+otherY);
				} else if (!this.checkSquare(x-2, y+(otherY)*2, cloneDelta) && this.checkSquare(x-1, y+otherY, cloneDelta, targetColor)) {
					cloneDelta.get(index).setXY((x-2), (y+(otherY)*2));
					cloneDelta = this.cloneArrayList(this.removePiece(cloneDelta, x-1, y+otherY));
				}
			}
			
			if (b.get(index).isKing()) {
				double[] SCORES = {this.getScore(cloneAlpha, b.get(index).color), this.getScore(cloneBeta, b.get(index).color), this.getScore(cloneGamma, b.get(index).color), this.getScore(cloneDelta, b.get(index).color)};
				int best = 0;
				
				for (int m = 0; m < SCORES.length; m ++) {
					if (SCORES[m] > SCORES[best])
						best = m;
				}
				
				switch (best) {
				case 0: return cloneAlpha;
				case 1: return cloneBeta;
				case 2: return cloneGamma;
				case 3: return cloneDelta;
				}
				
			}
			
			if (this.getScore(cloneAlpha, b.get(index).color) < this.getScore(cloneBeta, b.get(index).color))
				return cloneBeta;
			return cloneAlpha;
			
		}
		return b;
	}
	
	// removes piece
	public ArrayList<Piece> removePiece (ArrayList<Piece> b, int x, int y) {
		int index;
		
		if (this.checkSquare(x, y, b)){
			index = this.getIndex(x, y, b);
			
			b.remove(index);
		}
		System.out.println ("Removed: (" + x + "," + y+");");
		return b;
	}

	public boolean isPlayible (ArrayList<Piece> b) {
		
		int blue = 0;
		int red = 0;
		
		for (int i=0;i<b.size();i++) {
			if (b.get(i).color == 0) {
				blue ++;
			} else {
				red ++;
			}
		}
		//System.out.println (blue +" . " + red);
		if (blue == 0 || red == 0) {
			return false;
		}
		return true;
	}
	
	public boolean isLegal (int x, int y, int nx, int ny, ArrayList<Piece> b, int playerColor) {
		this.B = this.cloneArrayList(b);
		boolean legal = false;
		
		int index = this.getIndex(x, y, b);
		
		if (b.get(index).color != playerColor)
			return false;
		
		int Ydifference, otherY, targetColor; // otherY is used only for the king
		
		if (b.get(index).color == 0) {
			Ydifference = 1;
			otherY = -1;
		}else{
			Ydifference = -1;
			otherY = 1;
		}
		if (b.get(index).color == 0)
			targetColor = 1;
		else 
			targetColor = 0;
		
		ArrayList<Piece> Board = this.cloneArrayList(b);
		
		// all the possible moves
		int[][] possibleMoves = {{x+1, y+Ydifference}, {x-1, y+Ydifference}, {x+2, y+(Ydifference*2)}, {x-2, y+(Ydifference*2)}, {x+1, y+otherY}, {x-1, y+otherY}, {x+2, y+(otherY*2)}, {x-2, y+(otherY*2)}};
		
		for (int m=0; m < possibleMoves.length; m++) {
			if (possibleMoves[m][0] == nx && possibleMoves[m][1] == ny && !this.checkSquare(nx, ny, b, targetColor)) {
				System.out.println ("xy on list");
				if (m < 4) {
					if (m == 1 || m == 0)
						legal = true;
					
						if (m == 2) {
							if (this.checkSquare((x+1), (y+Ydifference), Board, targetColor)) { 
								//Board.remove(this.getIndex((x+1), Ydifference, Board));
								Board = this.removePiece(Board, (x+1), (Ydifference+y));
								legal = true;
							}
						}
					
						if (m == 3) {
							System.out.println ("m == 3");
							if (this.checkSquare(x-1, (y+Ydifference), Board, targetColor)) {
								//Board.remove(this.getIndex((x-1), Ydifference, Board));
								Board = this.removePiece(Board, (x-1), (Ydifference+y));
								legal = true;
							}
						}
					
					
				} else {
					if (b.get(index).isKing()) {
						if (m == 5 || m == 6) {
							legal = true;
						if (m == 7) {
							if (this.checkSquare(x+1, (y+otherY), Board, targetColor))  {
								//Board.remove(this.getIndex((x+1), otherY, Board));
								Board = this.removePiece(Board, (x+1), (otherY+y));
								legal = true;
							}
						}
						if (m == 8) {
							if (this.checkSquare(x-1, (y+otherY), Board, targetColor)) {
								//Board.remove(this.getIndex((x-1), otherY, Board));
								Board = this.removePiece(Board, (x-1), (otherY+y));
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
			this.B = this.cloneArrayList(Board);
		}
		return legal;
	}
	
	public ArrayList <Piece> checkForKings (ArrayList <Piece> board) {
		for (int i=0;i<board.size();i++) {
			if (board.get(i).color == 0 && board.get(i).y == 8) 
				board.get(i).kingPiece();
			if (board.get(i).color == 1 && board.get(i).y == 1)
				board.get(i).kingPiece();
		}
		
		return board;
	}
	
}
