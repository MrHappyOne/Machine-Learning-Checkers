package rowles.andrew.checkers.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileSystem {

	// TODO get the save working
	public void save(ArrayList<ArrayList<Piece>> a, String filename)
			throws IOException {

		FileOutputStream saveFile = new FileOutputStream(filename);

		ObjectOutputStream save = new ObjectOutputStream(saveFile);

		try {
			save.writeObject(a);

			save.close();

		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}

	// TODO implement and test this
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<Piece>> load(String filename) {

		ArrayList<ArrayList<Piece>> a = null;

		try {
			FileInputStream saveFile = new FileInputStream(filename);

			ObjectInputStream save = new ObjectInputStream(saveFile);

			a = (ArrayList<ArrayList<Piece>>) save.readObject();

			save.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return a;
	}
}
