import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import javax.swing.Spring;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.shape.*;


/**
 * Class object representing the game
 */

public class PacManBoard extends Canvas{
	
	/**
	 * The dimension of the pacman game board
	 * In addition to this, we add an outer wall
	 *  so the total board will be (nx+2) columns x (ny+2) rows
	 */
	
	
	int nx; // number of columns
	int ny; // number of rows

	int hc, hr; // Upper left corner of the house
	                 // (The home of the ghosts)

	Cell[][] board; // the cells in the playing area

	Puck puck; // The PacMan

	House house;   // (The home of the ghosts)

	// The four ghosts
	Blinky blinky;  
	Clyde clyde;
	Inky inky;
	Pinky pinky;

	// Main application; needed to update the information fields
	Main main;

	boolean running; // Controlls the timer; if false, the timer is stopped

	int width, height, size; // cell_size

	AnimationTimer timer;

	/**
	 * Creates the game board object;
	 */
	
	public PacManBoard (int x, int y, int s) {
		super((x+2)*s, (y+2)*s);

		nx = x;
		ny = y;

		size = s;
		hc=10;
		hr=9;

		width = (nx+2)*s;
		height = (ny+2)*s;
		
		// allow space for the cells at the borders
		board = new Cell[ny+2][nx+2];

		// Build the board cell by cell, include borders
		for (int i = 0; i <= ny+1; i++) {
			for (int j = 0; j <= nx+1; j++) {
				board[i][j] = new Cell(i, j, s);
			}
		}
		
		// start a new game
		newGame();
	}

	public void newGame() {


        // set  "not Eaten"
		setLiving();
		
		// set bonus squares
		setBonus();

		// Build the outer and inner walls
		outerWall();
		innerWall();

		// Define the squares where ghosts live
		house = new House(9, 10, this);

		// The PacMan
		puck = new Puck(hr+3, hc, size, this);
		
		// The four ghosts
		blinky = new Blinky(hr, hc, size, this);
		inky = new Inky(hr+1, hc, size, this);
		pinky = new Pinky(hr, hc+1, size, this);
		clyde = new Clyde(hr+1, hc+1, size, this);

		timer = new MyTimer();
		timer.start();
		running = true;
	}



	public void outerWall() {

		// Outer walls;
		
		// upper wall
		for (int i = 0; i <= nx+1; i++) {
			board[0][ i].isWall = true;
		}
		
		// lower wall
		for (int i = 0; i <= nx+1; i++) {
			board[ny+1][ i].isWall = true;
		}
		
		// left wall
		for (int i = 0; i <= ny+1; i++) {
			board[i][0].isWall = true;
		}
		
		// right wall
		for (int i = 0; i <= ny+1; i++) {
			board[i][ nx+1].isWall = true;
		}
	}
	
	/**Inner walls */

	public void innerWall() {

	// A list of the cells that make up the inner walls
		int[][] innerwall = {{1, 6}, {1, 15},
				{2,2}, {2, 3},{2, 4}, {2, 6},  {2,8}, {2, 9},{2, 10}, {2, 11}, {2, 12}, {2,13}, {2, 15},  {2, 17}, {2, 18}, {2,19},
				{3,2}, {3,19},
				{4,4}, {4, 6}, {4, 7}, {4, 9}, {4, 10}, {4, 11}, {4, 12 },  {4, 14}, {4, 15},  {4, 17}, 
				{5,1}, {5, 2}, {5, 4}, {5, 6}, {5, 15}, {5, 17}, {5, 19}, {5, 20},
				{6,4}, {6,6},  {6, 8},  {6, 9}, {6, 10}, {6, 11}, {6, 12},  {6, 13}, {6,15},  {6, 17},  
				{7, 2}, {7, 19},  
				{8, 2}, {8, 4},  {8, 5}, {8, 6}, {8, 7}, {8, 14}, {8, 15}, {8, 16}, {8, 17},  {8, 19},
				{9, 2}, {9, 7}, {9, 14},   {9, 19},  
				{10, 2}, {10, 3}, {10, 4}, {10, 5}, {10, 7}, {10, 14}, {10, 16},  {10, 17}, {10, 18},  {10, 19},
				{11, 7},  {11, 14},
				{12, 2}, {12, 3}, {12, 4}, {12, 5},  {12, 16}, {12, 17}, {12, 18},   {12, 19},
				{13, 2}, {13, 8}, {13, 9}, {13, 10},   {13, 11},  {13, 12},   {13, 13},  {13, 19},
				{14, 2}, {14, 4}, {14, 6},   {14, 15},   {14, 17},  {14, 19}, 
				{15, 2}, {15, 4}, {15, 6},  {15, 8},  {15, 10},   {15, 11},   {15, 13},  {15, 15}, {15, 17},    {15, 19}, 
				{16, 2}, {16, 4}, {16, 6},  {16, 8},  {16, 10},   {16, 11},   {16, 13},  {16, 15}, {16, 17},    {16, 19}}  ;

		// go through the list and maark the corresponding cells as walls
		for (int i = 0; i < innerwall.length; i++) {
			int r = innerwall[i][0];
			int c = innerwall[i][1];
			board[r][c].isWall = true;
		}
	}

	public int livingCells() {
		/**
		 * Count the number of cells that have not been eaten
		 * Ignore the  wall cells
		 */
		
		int living = 0;
		for (int i = 1; i <= ny; i++) {
			for (int j = 1; j <= nx; j++) {
				if (!board[i][j].isWall && !board[i][j].isEaten)
					living++;
			}
		}
		return living;
	}

	public void setLiving() {
		/**
		 * Set the cells that are not walls to be living
		 */
		
		for (int i = 1; i <= ny; i++) {
			for (int j = 1; j <= nx; j++) {
				Cell cell = board[i][j];
				if (!cell.isWall)
					cell.isEaten = false;
			}
		}
	}

	/**
	 * Add 4 bonus cells at thecorneres
	 */
	public void setBonus() {
		board[1][1].setBonusCell();
		board[1][nx].setBonusCell();
		board[ny][1].setBonusCell();
		board[ny][nx].setBonusCell();
	}
	
	/**
	 * Go to the nnext level
	 */
	public void upLevel() {
		puck.increaseLevel();
		setLiving();
		setBonus();
	}

	private class MyTimer extends AnimationTimer {

		@Override
		public void handle(long now) {

			doHandle();
		}


		/**
		 * Do one turn of the game:
		 * nove the puck and the ghost
		 */
		
		private void doHandle() {

			GraphicsContext gc = getGraphicsContext2D();
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, width, height);

			for (int i = 0; i <= ny+1; i++) {
				for (int j = 0; j <= nx+1; j++) {
					board[i][j].draw(gc, size);
				}
			}
			
			puck.move();
			puck.draw(gc);
			puck.display_status();

			inky.chase();
			inky.move();
			inky.draw(gc);

			pinky.chase();
			pinky.move();
			pinky.draw(gc);

			blinky.chase();
			blinky.move();
			blinky.draw(gc);

			clyde.chase();
			clyde.move();
			clyde.draw(gc);

		}
	};
}


