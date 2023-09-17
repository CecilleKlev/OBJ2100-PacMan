import javafx.scene.paint.Color;

public class Piece {

	/**
	 * Base class for the moving objects (the puck  and the Ghosts)
	 */


	public int row, col;   // the cell where the piece is
	public int dir;           // the direction the piece moves in
	int size;                     // the size of the piece in pixels

	public double px;    // the x-position of the piece in pixels
	public double py;     // the y-position of the piece in pixels
	double speed = 0.5; // how fast the piece moves per turn (in pixels)
	PacManBoard pmb;     // The gameboard object

	Color color;

	public Piece(int r, int c, int s,  PacManBoard pmb) {
		
		// Creates the object with coordinates and size
		row = r;
		col = c;
		size = s;
		this.pmb = pmb;

		// location in pixels
		px = size*c;
		py = size*r;


	}

}
