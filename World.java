/*
 * This is the World class for Test_Game. 
 * World is created by Test_Game, and it is then passed into Controller. 
 * It is essentially the model that gets displayed in Test_Game (view).
 * @author gliebchen
 * @version 1.0
 * @since 2016-03-16
 */
public class World {
	int maxRow = 0;
	int maxCol = 0;
	private char[][] map; 
	
	public World(int r, int c){
		maxRow = r;
		maxCol = c;
		map = new char[maxRow+1][maxCol+1];
		
		initialiseMap();
		
	}
	private void initialiseMap(){
		   //first row
		   for(int colCounter=maxCol;colCounter>-1;colCounter--){
			   map[0][colCounter]='X';
		   }
		   //middle rows
		   for(int rowCounter=maxRow-1;rowCounter>0;rowCounter--){
			   map[rowCounter][0]='X';
			   for(int colCounter=maxCol-1;colCounter>0;colCounter--){
				   map[rowCounter][colCounter]=' ';
			   }
			   map[rowCounter][maxCol]='O';
		   }
		   //last row
		   for(int colCounter=maxCol;colCounter>-1;colCounter--){
			   map[maxRow][colCounter]='X';
		   }
	   }
	
	public char[][] getMap(){
		return map;
	}
	public boolean isFree(int[] location){
		if(map[location[0]][location[1]]==' '){
			return true;
		}
		
		return false;
	}
	public char getWhatsInLocation(int[] location){
		return map[location[0]][location[1]];
	}
	public void putInWorld(int[] locationAndAppearance){
		map[locationAndAppearance[0]][locationAndAppearance[1]] = (char) locationAndAppearance[2];
	}
	
	public int getMaxRow(){
		return maxRow;
	}
	public int getMaxCol(){
		return maxCol;
	}

}
