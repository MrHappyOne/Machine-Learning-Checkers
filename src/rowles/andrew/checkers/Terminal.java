package rowles.andrew.checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import rowles.andrew.checkers.game.Helper;
import rowles.andrew.checkers.game.Logic;
import rowles.andrew.checkers.game.Piece;

public class Terminal {

	Logic logic = new Logic();
	Helper Helper = new Helper();

	public void title(double version) {
		System.out.println("ML Checkers " + version);
		System.out.println(" - Andrew Rowles");
	}

	public void menu() {
		System.out.println("Choose game type:");
		System.out.println("	- [L]earn");
		System.out.println("	- [P]lay");
	}

	public void drawBoard(ArrayList<Piece> b) {
		System.out.println("-------------------------------------");
		System.out.println(" ");
		String msg = "	";

		for (int i = 8; i > 0; i--) { // int i=1; i<9; i++

			msg = "		";

			for (int j = 1; j < 9; j++) { // int j=8; j>0; j--
				if (Helper.checkSquare(j, i, b)) {

					if (b.get(Helper.getIndex(j, i, b)).color == 1)
						msg += " X";
					else
						msg += " O";

				} else {
					msg += " -";
				}
			}

			System.out.println(msg);
		}
		System.out.println(" ");
		System.out.println(" ");

	}

	public int promptMenu(double version) {

		boolean canContinue = false;

		title(version);
		menu();

		while (!canContinue) {
			String userResponse = getUserInput();

			if (userResponse.equalsIgnoreCase("p")
					|| userResponse.equalsIgnoreCase("play")) {
				return 1;
			} else if (userResponse.equalsIgnoreCase("l")
					|| userResponse.equalsIgnoreCase("learn")) {
				return 0;
			} else {
				System.out.println("Cannot understand user response");
			}
		}

		return -1;
	}

	public String getUserInput() {
		String userResponse = null;
		try {
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));
			userResponse = bufferRead.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return userResponse;
	}
}
