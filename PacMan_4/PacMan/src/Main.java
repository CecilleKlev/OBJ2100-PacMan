import java.nio.file.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;


public class Main extends Application {

	final static int DOWN=0, RIGHT=1, LEFT=2, UP=3;

//	public static int DOWN;
//	public static int LEFT;
//	public static int RIGHT;
//	public static int UP;

	
	Stage window;  // The top-level window container
	Scene scene;    // Container for all elements in a scene graph

	// Text fields for storing information, with explaining text labels
	
	// High score
	Label hiscorelbl;
	TextField hiscorefield;

	// Score
	Label scorelbl;
	TextField scorefield;

	// Lives left
	Label liveslbl;
	TextField livesfield;   

	// Level
	Label levellbl;
	TextField levelfield;

	// Bonus
	Label bonuslbl ;
	TextField bonusfield;

	// Class element extending Canvas
	PacManBoard pmb;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	
		window = primaryStage;
		window.setTitle("JavaFX - PacMan");

		
		window.setOnCloseRequest( e -> { 
			e.consume();
			closeProgram(); });

		// Listens for keyboard keys
		window.addEventFilter(KeyEvent.KEY_PRESSED, new MyKeyHandler());

		hiscorelbl = new Label("HiScore");
		hiscorefield = new TextField("0");
		hiscorefield.setEditable(false);
		hiscorefield.setAlignment(Pos.CENTER_RIGHT);   

		scorelbl = new Label("Score");
		scorefield = new TextField("0");
		scorefield.setEditable(false);
		scorefield.setAlignment(Pos.CENTER_RIGHT);   

		liveslbl = new Label("Lives");
		livesfield = new TextField("0");
		livesfield.setEditable(false);
		livesfield.setAlignment(Pos.CENTER_RIGHT);   

		levellbl = new Label("Level");
		levelfield = new TextField("0");
		levelfield.setEditable(false);
		levelfield.setAlignment(Pos.CENTER_RIGHT);   

		bonuslbl = new Label("Bonus");
		bonusfield = new TextField("0");
		bonusfield.setEditable(false);
		bonusfield.setAlignment(Pos.CENTER_RIGHT);   

		// pauses/resumes the game by stopping/startung the timer'
		Button pausebutton = new Button("Pause/Run");
		//Create the EventHandler
		pausebutton.setOnAction( new EventHandler<ActionEvent>() {
			@Override
			/** 
			 * handle method
			 */
			public void handle(ActionEvent ev) {
				//if the program is running, pause, otherwise resume
				if( pmb.running)
				{
					pmb.timer.stop();
				}
				else
				{
					pmb.timer.start();
				}
				pmb.running = !pmb.running;
				ev.consume();
			}
		});
		
		// Button for starting a new game
		Button newgamebutton = new Button("New Game");
		newgamebutton.setOnAction(e -> newGame());

		// closing the game
		Button closebutton = new Button("Close Program");
		closebutton.setOnAction(e -> closeProgram());

		
		// Lay out the elements in a grid
		GridPane dashboardPane = new GridPane();

       //	High Score	
		dashboardPane.add(hiscorelbl,0,0 );
		dashboardPane.add(hiscorefield, 1,0 );

		// Score
		dashboardPane.add(scorelbl,0,1 );
		dashboardPane.add(scorefield, 1,1 );

		// Number of lives left
		dashboardPane.add(liveslbl, 0,2 );
		dashboardPane.add(livesfield, 1,2 );

		// Level
		dashboardPane.add(levellbl,0,3 );
		dashboardPane.add(levelfield, 1,3 );

		// Bonus
		dashboardPane.add(bonuslbl,0,4 );
		dashboardPane.add(bonusfield, 1,4 );

		// Pause and exit buttons
		dashboardPane.add(pausebutton, 0, 5);
		dashboardPane.add(newgamebutton, 1, 5);
		dashboardPane.add(closebutton, 1, 6);


		// for putting the Canvas with the PacMan board in
		Group pacman_gr = new Group();

		// Set up a new board with 20 columns, 17 rows, 
		// 32 pixels height and width for each cell
		pmb = new PacManBoard(20, 17, 32); 		//cols, rows, cell size (pixels)
		
		pacman_gr.getChildren().add(pmb);
		pmb.main = this;

		// layout0 - the  children elements (dashboard and the pacman canvas) 
		// are laid out horizontally
		
		HBox layout0 = new HBox();
		layout0.getChildren().addAll(dashboardPane, pacman_gr);
		
		// Create a scene
		scene = new Scene(layout0, 1000,625);

		// set this as the window scene
		window.setScene(scene);
		window.show();
	}

	private void newGame( ) {
		pmb.newGame();
	}

	public void closeProgram( ) {
		window.close();
	}

	public void pauseProgram( ) {
		pmb.timer.stop();
	}	
	
	/**
	 * Process incoming events (the user pushes an arrow key)
	 */
	
	private class MyKeyHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent e ) {

			doHandle(e);

		}

		private void doHandle(KeyEvent e) {

			switch(e.getCode()) {

			case UP:
				pmb.puck.dir = Main.UP;
				break;
				
			case DOWN:
				pmb.puck.dir = Main.DOWN;
				break;

			case LEFT:
				pmb.puck.dir = Main.LEFT;
				break;
				
			case RIGHT:
				pmb.puck.dir = Main.RIGHT;
				break;

			}
		}
	}


}


