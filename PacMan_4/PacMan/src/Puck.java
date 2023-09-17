import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The PacMan - an extension of the base class Piece
 */

  


public class Puck extends Piece {

	int level;    // game level
	int score;   // points earned
	int bonus;  // bonus earned
	int lives;     // lives left

	int size;

	/**
	 * where the puck is located at the start the game,
	 * and returns to when starting a new life
	 */

	
	int start_row, start_col;

	Label label; // To be implemented

	public Puck(int r, int c, int s, PacManBoard pmb) {

		/**
		 * Class instance for the PacMan
		 */
		

		super(r, c, s, pmb); 

		// starting position
		start_row = r;
		start_col = c;

		row = r;
		col = c;

		color = Color.YELLOW;

		size = pmb.size;
		px = size*c;
		py = size*r;

		lives = 6;
		level = 1;

		// Note that speed is inherited from base class Piece

		dir = Main.RIGHT;
		level = 1;

		// Image image  = new Image("pacman.PNG");
		// ImageView view = new ImageView(image);
		// label = new Label("   ", view);

		// Check starting cell for something to eat
		Cell cell = pmb.board[row][col];
		if (! cell.isEaten) {
			cell.isEaten = true;
			score += 10;
			if (cell.isBonus) {
				bonus += 100;
			}
		}
	}

	public void increaseLevel() {
		/**
		 * go up one level
		 */
		level += 1;
	}

	/**
	 * Move the PacMan according to the directions given by the player
	 */
	   

	public void move()  {

		switch(dir) {

		case Main.RIGHT:
			// check that the neighbour is not a wall
			if (!pmb.board[row][col+1].isWall
					| px <= size*col	)
				px += speed;

			break;

		case Main.LEFT:
			// check that the neighbour is not a wall
			if (!pmb.board[row][col-1].isWall
					| px >= size*col	)
				px -= speed;

			break;

		case Main.UP:
			// check that the neighbour is not a wall
			if (!pmb.board[row-1][col].isWall
					| py >= size*row	)
				py -= speed;

			break;

		case Main.DOWN:
			// check that the neighbour is not a wall
			if (!pmb.board[row+1][col].isWall
					| py <= size*row	)
				py += speed;
			break;
		} 

		//  Check if the puck enters a new cell

		int new_col = (int) Math.round(px/size);
		int new_row = (int) Math.round(py/size);

		if ((new_col != col) | (new_row != row) ) {

			// enter cell

			col = new_col;
			row = new_row;
			Cell cell = pmb.board[row][col];
			if (! cell.isEaten) {
				cell.isEaten = true;
				score += 10;
				if (cell.isBonus) {
					bonus += 100;
				}

				// count the number of remaining dots , if 0  then set the cells as uneaten
				// and go up one level

				if (pmb.livingCells() == 0) 
					pmb.upLevel();

			}
		}

	}            


	/**
	 * Eaten by a ghost :-(
	 * Lose a life and go back to starting position
	 */
	public void dies() {
		lives--;
		if (lives ==  0)
			pmb.main.pauseProgram();
		col = start_col;
		row = start_row;

		px = col*size;
		py = row*size;
	}



	public void  draw (GraphicsContext gc) {

		int x = (int) ( Math.round(px));
		int y = (int) ( Math.round(py));

		gc.setFill(color);
		gc.fillOval(x +size/8, y+size/8,  size*3/4, size*3/4);
	}


	public void display_status()  {

		/**
		 * report status to dashboard
		 */
		
		pmb.main.scorefield.setText(Integer.toString(score));
		int hiscore = Integer.parseInt(pmb.main.hiscorefield.getText());
		if (hiscore < score) 
			pmb.main.hiscorefield.setText(Integer.toString(score));

		pmb.main.livesfield.setText(Integer.toString(lives));
		pmb.main.levelfield.setText(Integer.toString(level));
		pmb.main.bonusfield.setText(Integer.toString(bonus));


	}

}

