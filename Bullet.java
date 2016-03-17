/*
 * This is the Bullet class for Test_Game. 
 * Bullet is created by Controller (in front of the player). 
 * Very similar to Player (might be good for inheritance). 
 * @author gliebchen
 * @version 1.0
 * @since 2016-03-16
 */

public class Bullet {
	private int rowCoord;
	private int colCoord;
	private char appearance;
	private boolean dead;
	
	public Bullet(int r, int c){
		   intialiseBullet(r,c);
	   }
	
	private void intialiseBullet(int r, int c){
		appearance = '.';
		rowCoord = r;
		colCoord = c;
		dead = false;
	}
	
	public int[] suggestMove(){
		int [] location = {rowCoord,colCoord+1};
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

}
