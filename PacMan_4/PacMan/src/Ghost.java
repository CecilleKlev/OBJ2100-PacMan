import javafx.scene.canvas.GraphicsContext;

/** 
 * Base class for the Ghosts  
*/


public class Ghost extends Piece {

	/** Representing the game board as a maze
	 * 
	*/
	
	Maze m;
	
	/**Constructor for the ghost */
	
	public Ghost(int r, int c, int s, PacManBoard pmb) {

		super(r, c, s, pmb);

		dir = Main.UP;
	}

	/**Moving the ghosts (The direction is set in the chase() method */
	 
	public void move()  {

		switch(dir) {

		case Main.RIGHT:

			// check that the neighbour is not a wall
			if (!pmb.board[row][col+1].isWall
					| px <= size*col	) {
				px += speed;
			}

			break;

		case Main.LEFT:

			// check that the neighbour is not a wall
			if (!pmb.board[row][col-1].isWall
					| px >= size*col) {
				px -= speed;
			}

			break;

		case Main.UP:

			// The ghost is allowed to move up through the wall when he is at home
			if (pmb.house.in_house(this) || 
					(!pmb.board[row-1][col].isWall
							| py >= size*row) )    {
				py -= speed;
			}

			break;

		case Main.DOWN:

			// check that the neighbour is not a wall
			if (!pmb.board[row+1][col].isWall
					| py <= size*row	) {
				py += speed;

			}

			break;
		} 

		// compute new posiyiom and check if entering a new cell
		int new_col = (int) Math.round(px/size);
		int new_row = (int) Math.round(py/size);

		if ((new_col != col) | (new_row != row) ) {
			// enter cell

			col = new_col;
			row = new_row;

			if (col == pmb.puck.col && row == pmb.puck.row)
				pmb.puck.dies();
			
			px += (size*col-px)/5;
			py += ( size*row-py)/5;
			

		}            
	}
	/**determines the direction to move; different implementation for each ghost */
	
	public void chase() {
		;
	}
	
	/**finds a path to the puck; different implementation for each ghost */
	
	public boolean solve(int r0, int c0, int r1, int c1) {
		
		return false;
	}

public void  draw (GraphicsContext gc) {

	int x = (int) ( Math.round(px));
	int y = (int) ( Math.round(py));

	gc.setFill(color);
	gc.fillOval(x +size/8, y+size/8,  size*3/4, size*3/4);
}



}

