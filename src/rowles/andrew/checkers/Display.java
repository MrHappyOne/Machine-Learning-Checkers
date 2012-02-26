package rowles.andrew.checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import rowles.andrew.checkers.learn.Logic;
import rowles.andrew.checkers.learn.Piece;

public class Display {
	
	Logic logic = new Logic ();
	
	public void title () {
		System.out.println ("ML Checkers");
		System.out.println (" - Andrew Rowles");
	}
	
	public void menu () {
		this.title();
		System.out.println ("Choose game type:");
		System.out.println ("	- [L]earn");
		System.out.println ("	- [P]lay");
	}
	
	public void drawBoard (ArrayList<Piece> b) {
		System.out.println("-------------------------------------");
		System.out.println(" ");
		String msg = "	";
		
		for (int i=8; i>0; i--) { // int i=1; i<9; i++
			
			msg = "		";
			
			for (int j=1; j<9; j++) { // int j=8; j>0; j--
				if (logic.checkSquare(j, i, b)) {
					
					if (b.get(logic.getIndex(j, i, b)).color == 1) 
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
	
	public int promptMenu () {
		String s = null;
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    s = bufferRead.readLine();
	 
		    //System.out.println(s);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if (s.equalsIgnoreCase("l")) {
			return 1;
		} else if (s.equalsIgnoreCase("p")) {
			return 0;
		} else {
			return -1;
		}
		
	}
	
	public String getPlayerMoveInput () {
		String s = null;
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    s = bufferRead.readLine();
	 
		    //System.out.println(s);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return s;
	}
}
