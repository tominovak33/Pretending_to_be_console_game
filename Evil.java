/*
 * This is the Evil class for Test_Game. 
 * Evil is created by Controller. Very similar to Player (might be good for inheritance). 
 * @author gliebchen
 * @version 1.0
 * @since 2016-03-16
 */

public class Evil {
	private int rowCoord;
	private int colCoord;
	private char appearance;
	private boolean dead;

	  
	public Evil(int r, int c){
		   intialiseEvil(r,c);
	   }
	
	private void intialiseEvil(int r, int c){
		appearance = 'x';
		rowCoord = r;
		colCoord = c;
		dead = false;
	}
	
	public int[] suggestMove(){
		int direction = upOrDown();
		int [] location = {rowCoord,colCoord};
		location[1] = colCoord-1;
		location[0] = rowCoord-direction;

		return location;

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
	public boolean isDead(){
		return dead;
	}
	public void setDead(boolean death){
		dead = death;
	}
	public int upOrDown(){
		int rand = (int) (Math.random() * 3);
		int var=0;
		if (rand==1)
			var=-1;
		else if (rand==2)
			var=0;
		else 
			var=1;
		return var;
		
	}

}
