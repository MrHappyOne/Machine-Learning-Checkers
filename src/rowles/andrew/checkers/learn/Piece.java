package rowles.andrew.checkers.learn;

public class Piece {
	public int color, x, y; // 1 red, 2 blue
	private boolean king;
	
	public Piece (int color, int x, int y) {
		this.king = false;
		setColor(color);
		setX(x);
		setY(y);
	}
	
	private void setColor (int color) {
		this.color = color;
	}
	
	public void kingPiece () {
		this.king = true;
	}
	
	public void setX (int x) {
		this.x = x;
	}
	
	public void setY (int y) {
		this.y = y;
	}
	
	public void setXY (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isKing () {
		return king;
	}
}
