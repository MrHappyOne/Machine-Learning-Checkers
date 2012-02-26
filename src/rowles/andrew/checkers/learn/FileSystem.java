package rowles.andrew.checkers.learn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileSystem {

	public void save(ArrayList<ArrayList<Piece>> a, String filename) {
		
		try {
			// Open a file to write to, named SavedObjects.sav.
			FileOutputStream saveFile = new FileOutputStream(filename);

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(a);

			// Close the file.
			save.close(); // This also closes saveFile.

		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<Piece>> load (String filename) {
		
		ArrayList<ArrayList<Piece>> a = null;
		
		try {
			// Open file to read
			FileInputStream saveFile = new FileInputStream(filename);

			// Create an ObjectInputStream to get objects from save file.
			ObjectInputStream save = new ObjectInputStream(saveFile);
			
			a = (ArrayList<ArrayList<Piece>>) save.readObject();
			
			save.close(); // This also closes saveFile.
		  }
		  catch(Exception exc){
		   exc.printStackTrace(); // If there was an error, print the info.
		  }
		return a;
	}
}
