/*
 * This is the kick-off class for a little hack where a GUI pretends to be a console game.
 * Also creates World (model), which gets passed into Controller.
 * Test_Game also world as view.
 * @author gliebchen
 * @version 1.0
 * @since 2016-03-16
 */
import java.awt.*;
import java.awt.event.*;
import java.io.Console;

import javax.swing.*;


public class Test_Game {
	boolean gameRunning = true;
	private final int maxRow = 20;
	private final int maxCol = 49;
	private int run = 1;
	private boolean restart = false;
	private boolean waitingForMenuInput = false;

	private JFrame mainFrame;
	private JLabel gameDisplay;
	private World worldy;
	private Controller beardyGuy;

	public Test_Game(){
		prepareGame();
	}

	public static void main(String[] args){
		Test_Game game = new Test_Game();

		game.showKeyListenerDemo();
	}

	private void prepareGame(){
		mainFrame = new JFrame("Gernot's Game Hack");
		mainFrame.setSize(500,800);
		//mainFrame.setLayout(new GridLayout(3, 1));

		gameDisplay = new JLabel("",JLabel.CENTER );
		gameDisplay.setForeground(Color.YELLOW);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    

		mainFrame.getContentPane().setBackground(Color.BLACK);

		mainFrame.add(gameDisplay);
		mainFrame.setVisible(true);
	}


	private void showKeyListenerDemo(){

		worldy = new World(maxRow, maxCol);
		beardyGuy = new Controller(worldy);

		// Otherwise after every restart, each keystroke will be recorded one extra time as there would be an additional KeyListener attached duplicating the input
		if (run == 1) {
			mainFrame.addKeyListener(new CustomKeyListener());		   
		}
		mainFrame.setVisible(true);

		while(gameRunning || waitingForMenuInput){
			try {
				Thread.sleep(50);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			if (this.checkGameStatus()) {
				beardyGuy.moveEverything();
				displayMap();
			} else {
				waitingForMenuInput = true;
				gameRunning = false;
				displayMenu();
				if (restart == true) {
					this.restart();
				}
			}
		}
	}

	private Boolean checkGameStatus() {
		if (beardyGuy.getLives() < 1) {
			return false;
		}
		return true;
	}

	public void restart() {
		restart = false;
		waitingForMenuInput = false;
		worldy = null;
		beardyGuy = null;
		run++;
		gameRunning = true;
		showKeyListenerDemo();
	}

	private void displayMap() {
		String output = new String();
		char[][] map = worldy.getMap();

		output = "<html><pre>C:\\game &gt;<br>";

		for(int rowCounter=maxRow;rowCounter>-1;rowCounter--){
			output = output+String.valueOf(map[rowCounter])+"<br>";
		}
		output = output+"Score: "+String.valueOf(beardyGuy.getScoreAndLives()[0])
		+"<br>Lives: "+String.valueOf(beardyGuy.getScoreAndLives()[1]) + "</pre></html>";


		gameDisplay.setText(output);

	}

	private void displayMenu(){
		String output = "<html><p>Press <em>ENTER<em/> to play again.<p></html>";
		gameDisplay.setText(output);
	}

	class CustomKeyListener implements KeyListener{	  
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				gameRunning=false;
			}if(e.getKeyCode() == KeyEvent.VK_UP){
				beardyGuy.userInput('u');
			}if(e.getKeyCode() == KeyEvent.VK_DOWN){
				beardyGuy.userInput('d');
			}if(e.getKeyCode() == KeyEvent.VK_LEFT){
				beardyGuy.userInput('l');
			}if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				beardyGuy.userInput('r');
			}if(e.getKeyCode() == KeyEvent.VK_SPACE){
				beardyGuy.shoot();
			}if(e.getKeyCode() == KeyEvent.VK_ENTER && waitingForMenuInput){
				// needed to add waitingForMenuInput as otherwise if enter is pressed during the game, it would automatically restart once it ends
				restart = true;
			}
		}

		public void keyReleased(KeyEvent e) {
		}   
	}


}