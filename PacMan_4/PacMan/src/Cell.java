import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Cell {

	/** 
	 * represents one square of the board 
	 */
	

	int row, col;

	int size;

	// Marks different cell states
	boolean isWall;
	boolean isEaten;
	boolean isBonus;

	// Colors corresponding to the cell states
	Color color =  Color.YELLOW; 
	Color wallColor = Color.CYAN;
	Color bonusColor = Color.LIMEGREEN;
	;
	public Cell(int row, int col, int size) {
		this.col = col;
		this.row = row;
		this.size = size;

		isWall = false;
		isEaten = false;

	}

	public void  draw (GraphicsContext gc, int c ) {

		//  Display the cell according to the state (wall/eaten/bonus)

		int y = row*c;
		int x = col*c;

		if (isWall) {
			gc.setFill(wallColor);
			gc.fillRect(x, y,  c, c);
		}
		else {
			gc.setFill(color);
			if (!isEaten) {
				if (isBonus) {
					gc.setFill(bonusColor);
					// Larger circle for a Bonus cell
					gc.fillOval(x + c/8, y+c/8,  c*3/4, c*3/4);
				}				
				else
					// Smaller circle for an ordinary cell
					gc.fillOval(x + c/4, y+c/4,  c/4, c/4);
			}
		}
	}

	/**
	 * Mark cell as a bonus cell
	 */
	public void setBonusCell() {
		isBonus = true;
	}

}
