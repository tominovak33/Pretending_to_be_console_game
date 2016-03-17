
/*
 * This is the player class for Test_Game. Player is created by the Controller class.
 * @author gliebchen
 * @version 1.0
 * @since 2016-03-16
 */
public class Player {
	private int rowCoord;
	private int colCoord;
	private char appearance;
	
	public Player(){
		   intialisePlayer();
	   }
	
	private void intialisePlayer(){
		appearance = '@';
		rowCoord = 1;
		colCoord = 1;
	}
	
	public int[] suggestMove(char direction){
		int [] location = {rowCoord,colCoord};
		
		switch (direction){
		case 'u':
			location[0] = location[0]+1;
			return location;
		case 'd':
			location[0] = location[0]-1;
			return location;
		case 'l':
			location[1] = location[1]-1;
			return location;
		case 'r':
			location[1] = location[1]+1;
			return location;
		default: 
			return location;
		}
	}
	public char getAppearance(){
		return appearance;
	}
	public void setLocation(int[] location){
		rowCoord=location[0];
		colCoord=location[1];
	}
	public int[] getLocation(){
		int[] location = {rowCoord,colCoord};
		return location;
	}
}
