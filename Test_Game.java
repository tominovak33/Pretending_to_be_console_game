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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;


public class Test_Game {
	boolean gameRunning = true;
	private final int maxRow = 20;
	private final int maxCol = 49;

	private JFrame mainFrame;
	private JLabel gameDisplay;
	private World worldy;
	private Controller beardyGuy;

	private File scores;
	private Scanner fileScan;
	private FileWriter fw;
	private BufferedWriter bw;
	private PrintWriter out;


	public Test_Game(){
		prepareGame();
		scores = new File("topScores.txt");
		try {
			fileScan = new Scanner(scores);
		} catch (FileNotFoundException e) {
			System.err.print("fine not found");
		}
		try {
			fw = new FileWriter("topScores.txt");
		} catch (IOException e) {
			System.err.print("scanner not found");
		}
		bw = new BufferedWriter(fw);
		out = new PrintWriter(bw);
	}

	public static void main(String[] args){
		Test_Game  game = new Test_Game();

		game.showKeyListenerDemo();
	}

	private void prepareGame(){
		mainFrame = new JFrame("Gernot's Game Hack");
		mainFrame.setSize(500,800);
		//mainFrame.setLayout(new GridLayout(3, 1));


		gameDisplay = new JLabel("",JLabel.CENTER );
		gameDisplay.setForeground(Color.BLUE);

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



		mainFrame.addKeyListener(new CustomKeyListener());
		mainFrame.setVisible(true);  

		while(gameRunning){
			try {
				Thread.sleep(50);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			if (beardyGuy.getScoreAndLives()[1]==0){
				gameRunning=false;
			}
			beardyGuy.moveEverything();
			displayMap();
		}
	}

	public void topScore(){
		int topScore = fileScan.nextInt();
		int newScore = beardyGuy.getScore();
		if (topScore < newScore){
			out.println(newScore);
		}
	}


	private void displayMap(){
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
			}
		}

		public void keyReleased(KeyEvent e) {
		}   
	}

}