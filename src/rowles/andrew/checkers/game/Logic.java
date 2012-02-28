package rowles.andrew.checkers.game;

import java.util.ArrayList;

public class Logic {

    Helper Helper = new Helper();

    public int Compare(ArrayList<Piece> a, ArrayList<Piece> b, int colorA,
            int colorB) {

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

        double[] scores = {redScore, blueScore};

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

        if (color == 1) {
            return redScore;
        }

        return blueScore;
    }

    // values piece
    public double AnalyzePiece(ArrayList<Piece> a, int i) {
        int tscore = 0;
        int distance = 0;
        int attacks = 0;
        double score;

        if (a.get(i).x == 1 || a.get(i).x == 8) {
            tscore += 2;
        }
        int moves = Helper.getPossibleMoves(a, i);

        if (!a.get(i).isKing()) {
            if (a.get(i).color == Helper.BLUE) {
                distance = a.get(i).y - 1;

                if (distance == 0) {
                    tscore += 2;
                    distance = 1;
                }
            }

            if (a.get(i).color == Helper.RED) {
                distance = 8 - a.get(i).y;

                if (distance == 0) {
                    tscore += 2;
                    distance = 1;
                }
            }
        }

        attacks = Helper.getPossibleAttacks(a, Helper.getIndex(a.get(i).x, a.get(i).y, a));

        if (distance != 0) {
            score = tscore + moves + distance;
        } else {
            score = (tscore + moves);
        }

        if (a.get(i).isKing()) {
            score = score * 1.5;
        }

        score += attacks;

        return score;
    }

    // gets best move for color
    public ArrayList<Piece> getBestMove(ArrayList<Piece> b, int color) {
        int targetColor;
        int bestIndex = 0;
        ArrayList<Piece> move;
        ArrayList<ArrayList<Piece>> MOVES = new ArrayList<ArrayList<Piece>>();

        if (color == Helper.RED)
            targetColor =  Helper.BLUE;
        else
            targetColor = Helper.RED;
        
        for (int index = 0; index < b.size(); index++) {
            if (b.get(index).color == color) {
                move = this.bestMove(b, index);
                if (!Helper.equals(b, move)) {
                    MOVES.add(this.bestMove(b, index));
                }
            }
        }

        for (int i = 0; i < MOVES.size(); i++) {

            double s = this.getScore(MOVES.get(i), color);
            double best = this.getScore(MOVES.get(bestIndex), color);

            double sT = this.getScore(MOVES.get(i), targetColor);
            double bestT = this.getScore(MOVES.get(bestIndex), targetColor);

            //ArrayList <Piece> future = this.getBestMove(MOVES.get(i), targetColor);
            //ArrayList <Piece> futureBest = this.getBestMove(MOVES.get(bestIndex), targetColor);

            //double currentFuture = this.getScore(MOVES.get(i), targetColor);
            //double bestFuture = this.getScore(MOVES.get(bestIndex), targetColor);

            if (s > best && sT <= bestT) {
                bestIndex = i;
            }
        }



        return MOVES.get(bestIndex);
    }

    // gets best move for random piece
    public ArrayList<Piece> getRandomMove(ArrayList<Piece> b, int color) {
        ArrayList<Piece> C = b;
        for (int i = 0; i < b.size(); i++) {

            if (b.get(i).color == color && Helper.getPossibleMoves(b, i) != 0) {
                C = this.bestMove(C, i);
                return C;
            }
        }

        return b;
    }

    // gets best move for piece
    public ArrayList<Piece> bestMove(ArrayList<Piece> b, int index) {

        int x, y, Ydifference, otherY, targetColor; // otherY is used only for
        // the king
        ArrayList<Piece> cloneAlpha, cloneBeta, cloneGamma, cloneDelta;

        if (Helper.getPossibleMoves(b, index) > 0) {
            // These must be deep copies
            cloneAlpha = Helper.cloneArrayList(b);
            cloneBeta = Helper.cloneArrayList(b);
            // cloneGamma && cloneDelta are used for the king
            cloneGamma = Helper.cloneArrayList(b);
            cloneDelta = Helper.cloneArrayList(b);

            x = b.get(index).x; // x = 4
            y = b.get(index).y; // y = 3

            if (b.get(index).color == Helper.RED) {
                Ydifference = 1;
                otherY = -1;
            } else {
                Ydifference = -1;
                otherY = 1;
            }

            if (b.get(index).color == Helper.RED) {
                targetColor = 1;
            } else {
                targetColor = 0;
            }

            //if ((x+1) > 8 || (x-1) < 1)

            if ((y+Ydifference > 9) || ((y+otherY) > 9) || ((y+Ydifference) < 0) || ((y+otherY) < 0))
                Ydifference = 0;

            if (((y+otherY) > 9) || ((y+otherY) < 0))
                otherY = 0;
            // x + 1 = 5
            // y + 1 = 4
            if (!Helper.checkSquare(x + 1, y + Ydifference, cloneAlpha)) {
                cloneAlpha.get(index).setXY((x + 1), (y + Ydifference));
            } else if (!Helper.checkSquare(x + 2, y + (Ydifference) * 2,
                    cloneAlpha)
                    && Helper.checkSquare(x + 1, y + Ydifference, cloneAlpha,
                    targetColor)) {
                cloneAlpha.get(index).setXY((x + 2), (y + (Ydifference) * 2));
                cloneAlpha = Helper.cloneArrayList(Helper.removePiece(
                        cloneAlpha, x + 1, y + Ydifference));
            }

            // x - 1 = 3
            // y + 1 = 4
            if (!Helper.checkSquare(x - 1, y + Ydifference, cloneBeta)) {
                cloneBeta.get(index).setXY(x - 1, y + Ydifference);
            } else if (!Helper.checkSquare(x - 2, y + (Ydifference) * 2,
                    cloneBeta)
                    && Helper.checkSquare(x - 1, y + Ydifference, cloneBeta,
                    targetColor)) {
                cloneBeta.get(index).setXY((x - 2), (y + (Ydifference) * 2));
                cloneBeta = Helper.cloneArrayList(Helper.removePiece(cloneBeta,
                        x - 1, y + Ydifference));
            }

            if (b.get(index).isKing()) {
                if (!Helper.checkSquare(x + 1, y + otherY, cloneGamma)) {
                    cloneGamma.get(index).setXY((x + 1), (y + otherY));
                } else if (!Helper.checkSquare(x + 2, y + (otherY) * 2,
                        cloneGamma)
                        && Helper.checkSquare(x + 1, y + otherY, cloneGamma,
                        targetColor)) {
                    cloneGamma.get(index).setXY((x + 2), (y + (otherY) * 2));
                    cloneGamma = Helper.cloneArrayList(Helper.removePiece(
                            cloneGamma, x + 1, y + otherY));
                }

                if (!Helper.checkSquare(x - 1, y + otherY, cloneDelta)) {
                    cloneDelta.get(index).setXY(x - 1, y + otherY);
                } else if (!Helper.checkSquare(x - 2, y + (otherY) * 2,
                        cloneDelta)
                        && Helper.checkSquare(x - 1, y + otherY, cloneDelta,
                        targetColor)) {
                    cloneDelta.get(index).setXY((x - 2), (y + (otherY) * 2));
                    cloneDelta = Helper.cloneArrayList(Helper.removePiece(
                            cloneDelta, x - 1, y + otherY));
                }
            }

            if (b.get(index).isKing()) {
                double[] SCORES = {
                    this.getScore(cloneAlpha, b.get(index).color),
                    this.getScore(cloneBeta, b.get(index).color),
                    this.getScore(cloneGamma, b.get(index).color),
                    this.getScore(cloneDelta, b.get(index).color)};
                int best = 0;

                for (int m = 0; m < SCORES.length; m++) {
                    if (SCORES[m] > SCORES[best]) {
                        best = m;
                    }
                }

                switch (best) {
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
                    cloneBeta, b.get(index).color)) {
                return cloneBeta;
            }
            return cloneAlpha;

        }
        return b;
    }
}
