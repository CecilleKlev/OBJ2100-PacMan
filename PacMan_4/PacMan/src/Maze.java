

public class Maze
{

	/**
	 * Class object representing a maze with a solver
	 */
	
	
	public int[][] A;

	public Maze(PacManBoard pmb) {
		int rows = pmb.board.length;
		int cols = pmb.board[0].length;

		A = new int[rows][cols];

		for (int i = 0; i < rows; i++)
			for (int j =0; j < cols; j++) 
				if(pmb.board[i][j].isWall)
					A[i][j] = 0;
				else
					A[i][j] = 1;

	}

	public boolean solve(int r0, int c0, int r1, int c1)
	{
		boolean done = false;
		int[] directions =    new int[4];
		if (valid(r0,c0) != false)
		{
			A[r0][c0] = 3;   // cell has been tried
			if (r0 == r1 && c0 == c1)
				done = true; // maze is solved
			else
			{
				// decide in whigh order to search:the maze

				// directions to the puck;
				int dr = r1 - r0;
				int dc = c1 - c0;

				if (Math.abs(dr) > Math.abs(dc)) {
					// Greatest distance row-wise
					//: go vertical
					if (dr > 0) {
						directions[0] = Main.DOWN;
						directions[3] = Main.UP;
					}
					else {
						directions[0] = Main.UP;
						directions[3] = Main.DOWN;
					}
					if (dc > 0) {
						directions[1] = Main.RIGHT;
						directions[2] = Main.LEFT;
					}
					else {
						directions[1] = Main.LEFT; 
						directions[2] = Main.RIGHT; 					
					}

				}
				else {

					//  Greatest distance column-wise
					//: go horizontal
					if (dc > 0) {
						directions[0] = Main.RIGHT; 
						directions[3] = Main.LEFT;
					}
					else {
						directions[0] = Main.LEFT; 
						directions[3] = Main.RIGHT; 				
					}
					if (dr > 0) {
						directions[1] = Main.DOWN;
						directions[2] = Main.UP;
					}
					else {
						directions[1] = Main.UP;
						directions[2] = Main.DOWN;			
						//  Greatest distance column-wise
						//: go horizontal

					}

				}

				int ctr = 0;
				while (!done && ctr < 4) {

					switch(directions[ctr]) {
					case Main.UP:
						done=solve(r0-1,c0,  r1, c1); 
						ctr++;
						break;

					case Main.LEFT:
						done=solve(r0,c0-1,  r1, c1); 
						ctr++;
						break;

					case Main.DOWN:
						if (done == false)
							done=solve(r0+1,c0,  r1, c1); 
						ctr++;
						break;

					case Main.RIGHT:
						done=solve(r0,c0+1,  r1, c1);
						ctr++;
						break;

					}
				}
				if (done != false)
					A[r0][c0] = 5;
			}
		}
		return done;
	} // end solve

	public  boolean valid(int r, int c)
	{
		boolean result = false;
		if ( r >= 0 && r < A.length && c >= 0 && c < A[0].length )
			if ( A[r][c] == 1 ) 
				result = true;
		return result;
	}

	public  void display()
	{
		for (int r=0;r<A.length;r++)
		{
			for (int c=0;c<A[r].length;c++)
				System.out.print(A[r][c]);
			System.out.println();
		}
		System.out.println();
	} // end display

}
