import javafx.scene.paint.Color;

public class Clyde extends Ghost {

	/** Class object representing the ghost Clyde*/
	

	public Clyde(int r, int c, int s, PacManBoard pmb) {

		super(r, c, s, pmb);
		color = Color.ORANGE;
		dir = Main.UP;

	}

	@Override
	public void chase() {

		/**select a direction to move  */ 

		// representing the PacMan board as a Maze object
		m = new Maze(pmb);


		Puck puck = pmb.puck;

		int pcol = puck.col;
		int prow = puck.row;

		boolean found = m.solve(row, col, prow, pcol);

		if (found) {
			if (col > 1) 
				if (m.A[row][col-1] == 5)
					dir = Main.LEFT;

			if (col < pmb.nx) 
				if (m.A[row][col+1] == 5)
					dir = Main.RIGHT;		

			if (row > 1) 
				if (m.A[row-1][col] == 5)
					dir = Main.UP;

			if (row < pmb.ny) 
				if (m.A[row+1][col] == 5)
					dir = Main.DOWN;
		}

	}
	@Override
	public boolean solve(int r0, int c0, int r1, int c1)
	{
		/**Finding a path from (r0, c0) to (r1, c1) by recursively */ 
		// solving the Maze m			    

		boolean done = false;
		int[] directions =    new int[4];
		if (m.valid(r0,c0) != false)
		{
			m.A[r0][c0] = 3;   // cell has been tried
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
						directions[3] = Main.DOWN;
						directions[0] = Main.UP;
					}
					else {
						directions[3] = Main.UP;
						directions[0] = Main.DOWN;
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
					m.A[r0][c0] = 5;
			}
		}
		return done;
	} // end solve
}
