package rowles.andrew.checkers.game;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rowles.andrew.checkers.game.Piece;
import rowles.andrew.checkers.Terminal;
import rowles.andrew.checkers.Window;

public class Game {

	final double				Version		= 0.4;

	ArrayList<Piece>			board		= null;
	ArrayList<ArrayList<Piece>>	history;

	String						directory	= "Games/";

	FileSystem					fileManager	= new FileSystem();
	Logic						logic		= new Logic();
	Window						display		= new Window(Version);
	Terminal					terminal	= new Terminal();
	Helper						Helper		= new Helper();

	public Game()
	{
		initBoard(); // init must be called before display.updateBoard(board);

		display.updateBoard(board);
		//learnLoop();
		play();

	}

	public void play()
	{

		boolean isAIturn = true;
		int AIcolor = 0;
		int PlayerColor = 1;

		history = new ArrayList<ArrayList<Piece>>();
		history.add(board);

		try
		{
			fileManager.save(history, "test1.game");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		ArrayList<ArrayList<Piece>> a = fileManager.load("test1.game");
		System.out.println(a.get(0).get(1).x);
		while (Helper.isPlayable(board))
		{

			// terminal.drawBoard(board);
			display.updateBoard(board);

			if (isAIturn)
			{
				board = Helper
						.cloneArrayList(logic.getBestMove(board, AIcolor));
				isAIturn = false;
			} else
			{
				board = Helper
						.cloneArrayList(getPlayerMove(board, PlayerColor));
				isAIturn = true;
			}

			display.updateBoard(board);

			board = Helper.cloneArrayList(Helper.checkForKings(board));

			history.add(board);
		}

	}

	// TODO Perfect player controls
	public ArrayList<Piece> getPlayerMove(ArrayList<Piece> b, int Pcolor)
	{
		boolean promptOkay = false;
		int d;

		ArrayList<Piece> deltaBoard = Helper.cloneArrayList(b);

		while (!promptOkay)
		{
			display.canvas.canControl = true;

			if (display.canvas.Input.isOkay
					&& Helper.isLegal(display.canvas.Input.getLastX(),
							display.canvas.Input.getLastY(),
							display.canvas.Input.getX(),
							display.canvas.Input.getY(), b, Pcolor))
			{
				display.canvas.Input.isOkay = false;
				display.canvas.Input.setSelected(false);
				promptOkay = true;
				deltaBoard = Helper.board;
			} else if (display.canvas.Input.isOkay)
			{
				display.canvas.Input.isOkay = false;
				display.canvas.Input.setSelected(false);
			}

			if (display.canvas.requestInfo
					&& Helper.checkSquare(display.canvas.Input.getX(),
							display.canvas.Input.getY(), b))
			{
				d = Helper.getIndex(display.canvas.Input.getX(),
						display.canvas.Input.getY(), b);
				System.out.println("-------Piece Information-------");
				System.out.println("x = " + display.canvas.Input.getX()
						+ " y = " + display.canvas.Input.getY());
				System.out.println("Possible benefital attacks = "
						+ Helper.getPossibleBenefitalAttacks(b, d));
				System.out.println("Possible attacks           = "
						+ Helper.getPossibleAttacks(b, d));
				System.out.println("Possible benefital moves   = "
						+ Helper.getPossibleBenefitalMoves(b, d));
				System.out.println("Possible moves             = "
						+ Helper.getPossibleMoves(b, d));
				System.out.println(logic.AnalyzePiece(b, d));
			}
			display.canvas.requestInfo = false;

		}

		display.canvas.canControl = false;

		return deltaBoard;
	}

	// Will be implemented when fileSystem save is working
	public void learn()
	{

	}

	// Will be implemented when fileSystem save is working
	public ArrayList<ArrayList<Piece>> learnLoop()
	{

		boolean isRedTurn = true;

		while (Helper.isPlayable(board))
		{
			if (isRedTurn)
			{
				board = checkDir(board, Helper.WHITE);
				isRedTurn = false;
			} else
			{
				board = checkDir(board, Helper.BLACK);
				isRedTurn = true;
			}
			display.updateBoard(board);
			terminal.drawBoard(board);
		}

		String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.game'")
				.format(new Date());
		try
		{
			fileManager.save(history, directory + fileName);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return history;
	}

	public ArrayList<Piece> checkDir(ArrayList<Piece> a, int color)
	{
		File directory = new File("Games/");

		ArrayList<ArrayList<Piece>> Games = new ArrayList<ArrayList<Piece>>();

		File games[] = directory.listFiles();

		for (File G : games)
		{
			ArrayList<ArrayList<Piece>> GAME = fileManager.load(G
					.getAbsolutePath());

			for (int i = 0; i < GAME.size(); i++)
			{

				if (Helper.equals(board, GAME.get(i)))
				{

					int score = logic.Compare(a, GAME.get(i + 1), 0, 1);

					if (score == 2 || score == 0)
					{
						if (Helper.getDifference(a, GAME.get(i + 1), color))
						{
							Games.add(GAME.get(i + 1));
						}
					}

				}
			}
		}

		if (Games.size() > 0)
		{
			int best = 0;

			for (int r = 0; r < Games.size(); r++)
			{
				if (logic.getScore(Games.get(best), color) < logic.getScore(
						Games.get(r), color))
				{
					best = r;
				}
			}

			return Games.get(best);
		}

		return Helper.getRandomMove(a, color);

	}

	public void initBoard()
	{
		board = new ArrayList<Piece>(); // color, x, y

		int nums = 0;

		for (int i = 1; i < 4; i += 1)
		{
			if (i == 2)
			{
				for (int j = 1; j < 8; j += 2)
				{
					board.add(new Piece(Helper.WHITE, j, i));
					board.get(board.size() - 1).id = nums;
					nums++;
				}
			} else
			{
				for (int j = 2; j < 9; j += 2)
				{
					board.add(new Piece(Helper.WHITE, j, i));
					board.get(board.size() - 1).id = nums;
					nums++;
				}
			}
		}

		for (int i = 6; i < 9; i += 1)
		{
			if (i == 8 || i == 6)
			{
				for (int j = 1; j < 8; j += 2)
				{
					board.add(new Piece(Helper.BLACK, j, i));
					board.get(board.size() - 1).id = nums;
					nums++;
				}
			} else
			{
				for (int j = 2; j < 9; j += 2)
				{
					board.add(new Piece(Helper.BLACK, j, i));
					board.get(board.size() - 1).id = nums;
					nums++;
				}
			}
		}
	}

}
