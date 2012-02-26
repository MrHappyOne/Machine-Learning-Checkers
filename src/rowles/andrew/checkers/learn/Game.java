package rowles.andrew.checkers.learn;

import java.io.File;
import java.util.ArrayList;
import rowles.andrew.checkers.learn.Piece;
import rowles.andrew.checkers.Display;
import rowles.andrew.checkers.Window;


public class Game {
	
	ArrayList<Piece> board = null;
	ArrayList<ArrayList<Piece>> history;
	Window display = new Window();
	String directory = "Games/";
	FileSystem fileManager;
	Logic logic = new Logic();
	Display screen;
	
	public Game () {
		
		initBoard();
		
		fileManager = new FileSystem();

		display.updateBoard(board);
		
		screen = new Display();
		screen.menu();
		
		if (screen.promptMenu() == 1 || screen.promptMenu() == 0) {
			play();
		} else {
			
		}
		//learn();
		//logic.getScore(board);
		//screen.drawBoard(board);
		//System.out.println(" ");
		//System.out.println(" ");
	}
	
	public void play () {
		initBoard();
		boolean isAIturn = true;
		int AIcolor = 1;
		int PlayerColor = 0;
		
		while (logic.isPlayible(board)) {
			screen.drawBoard(board);
			display.updateBoard(board);
			if (isAIturn) {
				board = logic.cloneArrayList(logic.getBestMove(board, AIcolor));
				isAIturn = false;
			} else {
				board = logic.cloneArrayList(getPlayerMove(board, PlayerColor));
				isAIturn = true;
			}
			board = logic.cloneArrayList(logic.checkForKings(board));
		}
	}
	
	public ArrayList<Piece> getPlayerMove (ArrayList <Piece> b, int Pcolor) { // "x y to x y"
		System.out.println ("Please enter your move: ");
		
		String reply = null;
		String[] parsed = null;
		boolean promptOkay = false;
		
		ArrayList<Piece> deltaBoard = logic.cloneArrayList(b);
		
		while (promptOkay == false) {
			reply = screen.getPlayerMoveInput();
			parsed = reply.split("\\s+");
			
			if (parsed.length == 5) {
				if (logic.isLegal (Integer.parseInt(parsed[0]), Integer.parseInt(parsed[1]), Integer.parseInt(parsed[3]), Integer.parseInt(parsed[4]), b, Pcolor)) {
					promptOkay = true;
					deltaBoard = logic.cloneArrayList(logic.B);
				} else {
					System.out.println ("Not legal");
				}
			} else {
				System.out.println ("Not legal");
			}
		}
		
		return deltaBoard;
	}
	
	public void learn () {
		
		//screen.drawBoard(board);
		//System.out.println (board.get(ix).color);
		//System.out.println(board.get(ix).x +", "+ board.get(ix).y);
		//board = this.logic.bestMove(board, ix);
		//System.out.println("best move = "+board.get(ix).x +", "+ board.get(ix).y);
		
		//board = logic.getBestMove(board, 1);
		
		//board.remove(9);
		//screen.drawBoard(board);
		//initBoard();
		
		int amount = 2;
		
		for (int i = 1; i<amount; i++) {
			learnLoop();
			initBoard();
		}
	}
	
	public ArrayList<ArrayList<Piece>> learnLoop () {
		
		@SuppressWarnings("unused")
		boolean game = true;
		boolean isRedTurn = true; // red = 0;
		history = new ArrayList<ArrayList<Piece>>();
		history.add(board);
		
		/*while (game) {
			if(logic.isPlayible(board)){
				if (isRedTurn) {
					board = logic.getBestMove(board, 0);
					isRedTurn = false;
				} else {
					board = logic.getBestMove(board, 1);
				}
			} else {
				game = false;
				System.out.println("#*#*#*#*#*#* Game ended");
			}
			//logic.getScore(board);
			screen.drawBoard(board);
			//System.out.println(" ");
			//System.out.println(" ");
			history.add(board);
		}
		
		if(logic.isPlayible(board)){
			if (isRedTurn) {
				board = logic.getBestMove(board, 0);
				isRedTurn = false;
			} else {
				board = logic.getBestMove(board, 1);
			}
		}
		screen.drawBoard(board);
		if(logic.isPlayible(board)){
			if (isRedTurn) {
				board = logic.getBestMove(board, 0);
				isRedTurn = false;
			} else {
				board = logic.getBestMove(board, 1);
			}
		}
		screen.drawBoard(board);
		*/
		while (logic.isPlayible(board)) {
			if (isRedTurn) {
				board = logic.getBestMove(board, 0);
				isRedTurn = false;
			} else {
				board = logic.getBestMove(board, 1);
			}
			screen.drawBoard(board);
		}
		
		return history;
	}
	
	public void checkDir () {
		File directory = new File ("/Games");
		
		File games[] = directory.listFiles();
		
		for (File G : games) {
			ArrayList<ArrayList<Piece>> GAME = fileManager.load(G.getAbsolutePath());
			
			//logic.equals(a, b
			
			
			for (int i = 0; i < GAME.size(); i++) {
				
				if (logic.equals(board, GAME.get(i))) {
					
					int score = logic.Compare(board, GAME.get(i+1), 0, 1);
					
					if (score == 0) {
						//int[] d = logic.getDifference(board, GAME.get(i+1));
						
						//int index = logic.getIndex(d[0], d[1], board);
						
						//ArrayList<Piece> b1 = board;
						
						//b1.get(index).x;
						//b1.get(index).y;
						
					}

				}
			}
		}
	}
	
	public void initBoard () {// color, x, y
		board = new ArrayList<Piece>();
		
		//board.add(new Piece(0,4,3));
		//board.add(new Piece(1,5,4));
		//board.add(new Piece(1,3,4));
		
		for (int i = 1; i < 4; i += 1) {
			if (i == 2) {
				for(int j = 1; j<8; j += 2) {
					board.add(new Piece(0,j,i));
					//System.out.println(i + ", " + j);
				}
			} else {
				for(int j = 2; j<9; j += 2) {
					board.add(new Piece(0,j,i));
					//System.out.println(i + ", " + j);
				}
			}
		}
		
		for (int i = 6; i < 9; i += 1) {
			if (i == 8 || i == 6) {
				for(int j = 1; j<8; j += 2) {
					board.add(new Piece(1,j,i));
					//System.out.println(i + ", " + j);
				}
			} else {
				for(int j = 2; j<9; j += 2) {
					board.add(new Piece(1,j,i));
					//System.out.println(i + ", " + j);
				}
			}
		}
	}
	
}
