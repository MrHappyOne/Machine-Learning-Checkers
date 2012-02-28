package rowles.andrew.checkers.game;

import java.io.File;
import java.util.ArrayList;

import rowles.andrew.checkers.game.Piece;
import rowles.andrew.checkers.Terminal;
import rowles.andrew.checkers.Window;

public class Game {

	final double Version = 0.02;

	ArrayList<Piece> board = null;
	ArrayList<ArrayList<Piece>> history;

	String directory = "/Games/";

	FileSystem fileManager = new FileSystem();;
	Logic logic = new Logic();
	Window display = new Window(Version);
	Terminal terminal = new Terminal();
	Helper Helper = new Helper();

	public Game() {
		initBoard(); // init must be called before display.updateBoard(board);

		display.updateBoard(board);

		if (terminal.promptMenu(Version) == 1) {
			play();
		}
	}

	// TODO get the save history working
	public void play() {

		boolean isAIturn = true;
		int AIcolor = 1;
		int PlayerColor = 0;

		history = new ArrayList<ArrayList<Piece>>();

		while (Helper.isPlayable(board)) {

			terminal.drawBoard(board);
			display.updateBoard(board);

			if (isAIturn) {
				board = Helper
						.cloneArrayList(logic.getBestMove(board, AIcolor));
				isAIturn = false;
			} else {
				board = Helper
						.cloneArrayList(getPlayerMove(board, PlayerColor));
				isAIturn = true;
			}
			board = Helper.cloneArrayList(Helper.checkForKings(board));

			history.add(board);
		}
		/*
		 * String fileName = new
		 * SimpleDateFormat("yyyyMMddhhmmss'.game'").format(new Date()); try {
		 * fileManager.save(history, directory + fileName); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
	}

	public ArrayList<Piece> getPlayerMove(ArrayList<Piece> b, int Pcolor) { // "x y to x y"
		System.out.println("Please enter your move: ");

		String reply = null;
		String[] parsed = null;
		boolean promptOkay = false;

		ArrayList<Piece> deltaBoard = Helper.cloneArrayList(b);

		while (promptOkay == false) {
			reply = terminal.getUserInput();
			parsed = reply.split("\\s+");

			if (parsed.length == 5) {
				if (Helper.isLegal(Integer.parseInt(parsed[0]),
						Integer.parseInt(parsed[1]),
						Integer.parseInt(parsed[3]),
						Integer.parseInt(parsed[4]), b, Pcolor)) {
					promptOkay = true;
					deltaBoard = Helper.cloneArrayList(Helper.board);
				} else {
					System.out.println("Not legal");
				}
			} else {
				System.out.println("Not legal");
			}
		}

		return deltaBoard;
	}

	// Will be implemented when fileSystem save is working
	public void learn() {

	}

	// Will be implemented when fileSystem save is working
	public ArrayList<ArrayList<Piece>> learnLoop() {

		boolean isRedTurn = true;

		while (Helper.isPlayable(board)) {
			if (isRedTurn) {
				board = logic.getBestMove(board, Helper.RED);
				isRedTurn = false;
			} else {
				board = logic.getBestMove(board, Helper.BLUE);
			}
			terminal.drawBoard(board);
		}

		return history;
	}

	public void checkDir() {
		File directory = new File("/Games");

		File games[] = directory.listFiles();

		for (File G : games) {
			ArrayList<ArrayList<Piece>> GAME = fileManager.load(G
					.getAbsolutePath());

			for (int i = 0; i < GAME.size(); i++) {

				if (Helper.equals(board, GAME.get(i))) {

					int score = logic.Compare(board, GAME.get(i + 1), 0, 1);

					if (score == 0) {

					}

				}
			}
		}
	}

	public void initBoard() {
		board = new ArrayList<Piece>(); // color, x, y

		for (int i = 1; i < 4; i += 1) {
			if (i == 2) {
				for (int j = 1; j < 8; j += 2) {
					board.add(new Piece(Helper.RED, j, i));
				}
			} else {
				for (int j = 2; j < 9; j += 2) {
					board.add(new Piece(Helper.RED, j, i));
				}
			}
		}

		for (int i = 6; i < 9; i += 1) {
			if (i == 8 || i == 6) {
				for (int j = 1; j < 8; j += 2) {
					board.add(new Piece(Helper.BLUE, j, i));
				}
			} else {
				for (int j = 2; j < 9; j += 2) {
					board.add(new Piece(Helper.BLUE, j, i));
				}
			}
		}
	}

}
