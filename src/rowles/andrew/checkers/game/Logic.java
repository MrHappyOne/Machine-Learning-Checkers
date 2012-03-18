ppackage rowles.andrew.checkers.game;

import java.util.ArrayList;
import java.util.Random;

public class Logic {

    Helper	Helper	= new Helper();

	public int Compare(ArrayList<Piece> a, ArrayList<Piece> b, int colorA,
			int colorB)
	{
		int A = 1;
		int B = 2;
		int even = 0;

		double scoresA[] = this.getScore(a);
		double scoresB[] = this.getScore(b);

		double Ascore = scoresA[colorA - 1];
		double Bscore = scoresB[colorB - 1];

		if (Ascore > Bscore)
		{
			return A;
		}
		if (Bscore > Ascore)
		{
			return B;
		}

		return even;
	}

	public double[] getScore(ArrayList<Piece> a)
	{
		double redScore = 0;
		double blueScore = 0;

		for (int i = 0; i < a.size(); i++)
		{
			double score = AnalyzePiece(a, i);

			if (a.get(i).color == 1)
			{
				redScore += score;
			} else
			{
				blueScore += score;
			}

		}

		double[] scores = { redScore, blueScore };

		return scores;
	}

	public double getScore(ArrayList<Piece> a, int color)
	{
		double redScore = 0;
		double blueScore = 0;

		for (int i = 0; i < a.size(); i++)
		{
			double score = AnalyzePiece(a, i);

			if (a.get(i).color == 1)
			{
				redScore += score;
			} else
			{
				blueScore += score;
			}

		}

		if (color == 1)
		{
			return redScore;
		}

		return blueScore;
	}

	// Scores piece.
	// TODO needs adjustment.
	// If the bonus for a side move is worth to much, a piece moves to the side inside of attacking or defending.
	public double AnalyzePiece(ArrayList<Piece> a, int i)
	{
		int distance = 1;
		double score;

		if (a.get(i).isKing())
			score = 5;
		else
			score = 3;

		if (!a.get(i).isKing()) // distance is only relevant for normal pieces.
		{
			distance = 0;
			if (a.get(i).color == Helper.BLACK)
			{
				distance = a.get(i).y - 1;

				if (distance == 0)
				{
					distance = 1;
				}
			}

			if (a.get(i).color == Helper.WHITE)
			{
				distance = 8 - a.get(i).y;

				if (distance == 0)
				{
					distance = 1;
				}
			}
		}
		if (distance != 0)
		{
			score = score / distance;
		}
		score += Helper.getPossibleBenefitalMoves(a, i);
		if (a.get(i).x == 1 || a.get(i).x == 8 || a.get(i).y == 1
				|| a.get(i).y == 8)
		{
			score += .1;
		}

		return score;
	}

	// This finds the best move.
	// TODO Factor in future moves and scores.
	public ArrayList<Piece> getBestMove(ArrayList<Piece> b, int color)
	{
		int targetColor;
		int bestIndex = 0;
		boolean foundBest = false;
		ArrayList<Piece> move;
		ArrayList<ArrayList<Piece>> MOVES = new ArrayList<ArrayList<Piece>>();

		if (color == Helper.WHITE)
			targetColor = Helper.BLACK;
		else
			targetColor = Helper.WHITE;

		for (int index = 0; index < b.size(); index++)
		{
			if (b.get(index).color == color)
			{
				move = this.bestMove(b, index);
				if (!Helper.equals(b, move))
				{
					MOVES.add(this.bestMove(b, index));
				}
			}
		}

		for (int i = 0; i < MOVES.size(); i++)
		{

			double s = this.getScore(MOVES.get(i), color);
			double best = this.getScore(MOVES.get(bestIndex), color);

			if (s > best && s > this.getScore(b,color))
			{
				bestIndex = i;
				foundBest = true;
			}
		}

		if (!foundBest)
		{
			for (int i = 0; i < MOVES.size(); i++)
			{

				double s = this.getScore(MOVES.get(i), color);
				double best = this.getScore(MOVES.get(bestIndex), color);

				if (s > best)
				{
					bestIndex = i;
					foundBest = true;
				}
			}
		}
		
		if (!foundBest)
		{
			int top = 0;
			for (int i = 0; i < MOVES.size(); i++)
			{
				if (this.getScore(MOVES.get(top), color)<=this.getScore(MOVES.get(i), color)) {
					top = i;
					foundBest = true;
				}
			}
		}
		
		if (!foundBest && MOVES.size() > 0) {
			System.out.println ("Making random move "+MOVES.size());
			
			return b;
		}
		
		System.out.println (foundBest +" "+MOVES.size());
		
		if (!foundBest && MOVES.size() == 0)
			return Helper.getRandomMove(b, color);
		
		if (foundBest && MOVES.size() != 0)
			return MOVES.get(bestIndex);
		else
			return MOVES.get(bestIndex);

	}

	// This method returns the best move, based on the score, for a piece. 
	// TODO check for bugs
	public ArrayList<Piece> bestMove(ArrayList<Piece> b, int index)
	{

		int x, y, Ydifference, otherY, targetColor; // otherY is used only for
													// the king
		ArrayList<Piece> cloneAlpha, cloneBeta, cloneGamma, cloneDelta;

		if (Helper.getPossibleMoves(b, index) > 0)
		{
			// These must be deep copies
			cloneAlpha = Helper.cloneArrayList(b);
			cloneBeta = Helper.cloneArrayList(b);
			// cloneGamma && cloneDelta are used for the king
			cloneGamma = Helper.cloneArrayList(b);
			cloneDelta = Helper.cloneArrayList(b);

			x = b.get(index).x;
			y = b.get(index).y;

			if (b.get(index).color == Helper.WHITE)
			{
				Ydifference = 1;
				otherY = -1;
			} else
			{
				Ydifference = -1;
				otherY = 1;
			}

			if (b.get(index).color == Helper.WHITE)
			{
				targetColor = 1;
			} else
			{
				targetColor = 0;
			}

			if ((y + Ydifference > 9) || ((y + otherY) > 9)
					|| ((y + Ydifference) < 0) || ((y + otherY) < 0))
				Ydifference = 0;

			if (((y + otherY) > 9) || ((y + otherY) < 0))
				otherY = 0;

			if (!Helper.checkSquare(x + 1, y + Ydifference, cloneAlpha))
			{
				cloneAlpha.get(index).setXY((x + 1), (y + Ydifference));
			} else if (!Helper.checkSquare(x + 2, y + (Ydifference) * 2,
					cloneAlpha)
					&& Helper.checkSquare(x + 1, y + Ydifference, cloneAlpha,
							targetColor)
					&& Helper.getPossibleBenefitalAttacks(b, index) > 0)
			{
				cloneAlpha.get(index).setXY((x + 2), (y + (Ydifference) * 2));
				cloneAlpha = Helper.cloneArrayList(Helper.removePiece(
						cloneAlpha, x + 1, y + Ydifference));
			}

			if (!Helper.checkSquare(x - 1, y + Ydifference, cloneBeta))
			{
				cloneBeta.get(index).setXY(x - 1, y + Ydifference);
			} else if (!Helper.checkSquare(x - 2, y + (Ydifference) * 2,
					cloneBeta)
					&& Helper.checkSquare(x - 1, y + Ydifference, cloneBeta,
							targetColor)
					&& Helper.getPossibleBenefitalAttacks(b, index) > 0)
			{
				cloneBeta.get(index).setXY((x - 2), (y + (Ydifference) * 2));
				cloneBeta = Helper.cloneArrayList(Helper.removePiece(cloneBeta,
						x - 1, y + Ydifference));
			}

			if (b.get(index).isKing())
			{
				if (!Helper.checkSquare(x + 1, y + otherY, cloneGamma))
				{
					cloneGamma.get(index).setXY((x + 1), (y + otherY));
				} else if (!Helper.checkSquare(x + 2, y + (otherY) * 2,
						cloneGamma)
						&& Helper.checkSquare(x + 1, y + otherY, cloneGamma,
								targetColor)
						&& Helper.getPossibleBenefitalAttacks(b, index) > 0)
				{
					cloneGamma.get(index).setXY((x + 2), (y + (otherY) * 2));
					cloneGamma = Helper.cloneArrayList(Helper.removePiece(
							cloneGamma, x + 1, y + otherY));
				}

				if (!Helper.checkSquare(x - 1, y + otherY, cloneDelta))
				{
					cloneDelta.get(index).setXY(x - 1, y + otherY);
				} else if (!Helper.checkSquare(x - 2, y + (otherY) * 2,
						cloneDelta)
						&& Helper.checkSquare(x - 1, y + otherY, cloneDelta,
								targetColor)
						&& Helper.getPossibleBenefitalAttacks(b, index) > 0)
				{
					cloneDelta.get(index).setXY((x - 2), (y + (otherY) * 2));
					cloneDelta = Helper.cloneArrayList(Helper.removePiece(
							cloneDelta, x - 1, y + otherY));
				}
			}

			if (b.get(index).isKing())
			{
				double[] SCORES = {
						this.getScore(cloneAlpha, b.get(index).color),
						this.getScore(cloneBeta, b.get(index).color),
						this.getScore(cloneGamma, b.get(index).color),
						this.getScore(cloneDelta, b.get(index).color) 
				};
				int best = 0;

				for (int m = 0; m < SCORES.length; m++)
				{
					if (SCORES[m] > SCORES[best])
					{
						best = m;
					}
				}

				switch (best)
				{
					case 0:
						return cloneAlpha;
					case 1:
						return cloneBeta;
					case 2:
						return cloneGamma;
					case 3:
						return cloneDelta;
				}
			}

			if (this.getScore(cloneAlpha, b.get(index).color) < this.getScore(
					cloneBeta, b.get(index).color))
			{
				return cloneBeta;
			}
			return cloneAlpha;

		}
		return b;
	}
}