/*
 * This is the Controller class for Test_Game. 
 * Controller is created by Test_Game, and it gets passed in the World object (the model).
 * @author gliebchen
 * @version 1.0
 * @since 2016-03-16
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
	private Player player;
	private World worldy;
	private List<Evil>evils=new ArrayList<Evil>();
	private List<Bullet>bullets=new ArrayList<Bullet>();
	private int score;
	private int lives;
	private int maxRow = 0;
	private int maxCol = 0;
	private int delay = 0; // if i don't put in the delay it will delete the evil as soon as it creates it
	
	
	public Controller(World w){
		worldy = w;
		player = new Player();
		score = 0;
		lives = 4;
		maxRow = worldy.getMaxRow();
		maxCol = worldy.getMaxCol();
		int[] locationAndAppearanceOfPlayer = {player.getLocation()[0],player.getLocation()[1],player.getAppearance()};
		worldy.putInWorld(locationAndAppearanceOfPlayer);
	}
	
	public void userInput(char direction){
		int[] location = player.suggestMove(direction);
		int[] locationAndAppearanceBefore = {player.getLocation()[0],player.getLocation()[1],(int)' '};
		
		int[] locationAndAppearanceAfter = {location[0],location[1],player.getAppearance()};
		
		if(worldy.isFree(location)){
			
			player.setLocation(location);
			worldy.putInWorld(locationAndAppearanceBefore);
			
			worldy.putInWorld(locationAndAppearanceAfter);
		}
	}
	
	public void shoot(){
		int[]location = player.getLocation();
		location[1] = location[1]+1;			//bullets are created in front of the player
		
		if(worldy.isFree(location)){
			Bullet newB = new Bullet(location[0], location[1]);
			int[] newETempLocAndAppearance ={newB.getLocation()[0],newB.getLocation()[1],(int)newB.getAppearance()};

			worldy.putInWorld(newETempLocAndAppearance);
			//add to list
			bullets.add(newB);
		}
		
	}
	
	public void moveEverything(){
		delay=delay+1;
		
		//spawn evils
		if(delay%10==0){
			Evil newE = new Evil(randInt(2,maxRow-1), maxCol-1);
			int[] newETempLocAndAppearance ={newE.getLocation()[0],newE.getLocation()[1],(int)newE.getAppearance()};

			worldy.putInWorld(newETempLocAndAppearance);
			//add to list
			evils.add(newE);
			delay=0;
		}
		//move evils
		moveEntities();
		//get rid of the dead
		cleanTheDead();
	}
	
	private void moveEntities(){
		int listLooper = 0;
		//check each entity's position
		for(listLooper=0; listLooper<evils.size(); listLooper++){
			Evil e = evils.get(listLooper);
			int[] eCurrentLoc = e.getLocation();
			int[] eSuggestedLoc = e.suggestMove();
		
			if(worldy.isFree(eSuggestedLoc)){
				int[] eTempLocAndAppearance ={eCurrentLoc[0],eCurrentLoc[1],(int)' '};
				worldy.putInWorld(eTempLocAndAppearance);
				e.setLocation(eSuggestedLoc);
				eTempLocAndAppearance[0]=eSuggestedLoc[0];
				eTempLocAndAppearance[1]=eSuggestedLoc[1];
				eTempLocAndAppearance[2]=(int)e.getAppearance();
				//System.out.println(eTempLocAndAppearance[0]+" "+eTempLocAndAppearance[1]+" "+eTempLocAndAppearance[2]+" ");
				worldy.putInWorld(eTempLocAndAppearance);
			}else{
				//here is the collision happening
				if(worldy.getWhatsInLocation(eSuggestedLoc)==player.getAppearance()){
					lives=lives-1;		
				}else if(worldy.getWhatsInLocation(eSuggestedLoc)=='.'){
					score=score+1;		
					System.out.println(score);
				}
				//and tidy up after collision	
				int[] eTempLocAndAppearance ={eCurrentLoc[0],eCurrentLoc[1],(int)' '};
				worldy.putInWorld(eTempLocAndAppearance);
				e.setDead(true);
			}
		}
		for(listLooper=0; listLooper<bullets.size(); listLooper++){
			Bullet b = bullets.get(listLooper);
			int[] bCurrentLoc = b.getLocation();
			int[] bSuggestedLoc = b.suggestMove();
		
			if(worldy.isFree(bSuggestedLoc)){
				int[] bTempLocAndAppearance ={bCurrentLoc[0],bCurrentLoc[1],(int)' '};
				worldy.putInWorld(bTempLocAndAppearance);
				b.setLocation(bSuggestedLoc);
				bTempLocAndAppearance[0]=bSuggestedLoc[0];
				bTempLocAndAppearance[1]=bSuggestedLoc[1];
				bTempLocAndAppearance[2]=(int)b.getAppearance();
				//System.out.println(eTempLocAndAppearance[0]+" "+eTempLocAndAppearance[1]+" "+eTempLocAndAppearance[2]+" ");
				worldy.putInWorld(bTempLocAndAppearance);
			}else{
				//and tidy up after the collision
				int[] bTempLocAndAppearance ={bCurrentLoc[0],bCurrentLoc[1],(int)' '};
				worldy.putInWorld(bTempLocAndAppearance);
				b.setDead(true);
			}
		}
	}
	private void cleanTheDead(){
		int listLooper = 0;
		//check each entity's position
		for(listLooper=evils.size()-1; listLooper>-1; listLooper--){
			Evil e = evils.get(listLooper);
			if(e.isDead()){
				evils.remove(listLooper);
			}
		}
		for(listLooper=bullets.size()-1; listLooper>-1; listLooper--){
			Bullet b = bullets.get(listLooper);
			if(b.isDead()){
				bullets.remove(listLooper);
			}
			
		}
	}
	private int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	public int[] getScoreAndLives(){
		int[] scoreAndLives = {score,lives};
		return scoreAndLives;
	}
}
