
public class House {

	/** 
	 *  Class object representing the Ghost House
	*/
	
	
	int row, col; // The upper left ccorner of the ghost house. 
	                    //  Note that coordinates start at the top
	PacManBoard pmb;
	
	public House(int r,  int c, PacManBoard pmb) {
		row = r;
		col = c;
		
		this.pmb = pmb;
		
		// build walls;
		pmb.board[row-1][col-1].isWall = true;
		pmb.board[row-1][col].isWall = true;
		pmb.board[row-1][col+1].isWall = true;
		pmb.board[row-1][col+2].isWall = true;

		pmb.board[row][col-1].isWall = true;
		pmb.board[row][col+2].isWall = true;
		pmb.board[row+1][col-1].isWall = true;
		pmb.board[row+1][col+2].isWall = true;

		pmb.board[row+2][col-1].isWall = true;
		pmb.board[row+2][col].isWall = true;
		pmb.board[row+2][col+1].isWall = true;
		pmb.board[row+2][col+2].isWall = true;

		clean();
	}
	
	public void clean() {
		/**
		 * The cells in the house are cannot be reached by the puck
		 * So they are uneatable (or already eaten)
		 */
		
		pmb.board[row][col].isEaten = true;
		pmb.board[row][col+1].isEaten = true;
		pmb.board[row+1][col].isEaten = true;
		pmb.board[row+1][col+1].isEaten = true;
	}
	
	public boolean in_house(Ghost g) {
		/**is the ghost in the house? */
		
		return ( ( g.row == row || g.row == row+1) && (g.col == col || g.col == col+1));
	}

}
