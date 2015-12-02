
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
	public static int NUM_OF_ROWS = 4;
	public static int NUM_OF_COLS = 4;
	public static int[] BASIC_TILES = new int[]{2,4};
	public static int EMPTY_TILE = 0;
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int[] ACTIONS = {UP, DOWN, LEFT, RIGHT};
	
	private Player curPlayer;
	private Player player;
	private Boolean hasWon;
	private int[][] cells;
	private int[] tiles;
	private int score;
	private Random random = new Random();
	
	public GameManager(int row, int column){
		
		cells = new int[row][column];
	}
	
	public Random getRandom() {
		return random;
	}
	
	public int[][] getCells(){
		return cells;
	}
	public Boolean getWinFlag(){
		return hasWon;
	}
	public int[] getTiles(){
		return tiles;
	}
	public int getScore(){
		return score;
	}
	public void setScore(int score){
		this.score = score;
	}
	public void setCurPlayer(Player curPlayer){
		this.curPlayer = curPlayer;
	}
	public void setPlayer(Player p){
		this.player = p;
	}
	public void start(){
		for(int r = 0; r< cells.length;r++){
			for(int c = 0; c<cells[r].length; c++){
				cells[r][c] = EMPTY_TILE;
			}
		}
		score = 0;
		hasWon = null;
		int randomX = 0;
		int randomY = 0;
		int randomX2 = 0;
		int randomY2 = 0;
		while(randomX == randomX2 && randomY == randomY2){
			randomX = random.nextInt(NUM_OF_ROWS);
			randomY = random.nextInt(NUM_OF_COLS);
			randomX2 = random.nextInt(NUM_OF_ROWS);
			randomY2 = random.nextInt(NUM_OF_COLS);			
		}
		cells[randomX][randomY] = BASIC_TILES[0];
		cells[randomX2][randomY2] = BASIC_TILES[0];
	}
	public void play(){
		
		while (hasWon == null) {
			synchronized(this) {
				curPlayer = player;
			}
			if (curPlayer != null) {
				int tmpAction =curPlayer.getAction();
				System.out.println(tmpAction);
				score += move(cells, tmpAction);
				System.out.println(score);
				generateNewTiles(cells);
			}
			if (hasWon(cells)) {
				hasWon = true;
			}
			else if (hasLost(cells)) {
				hasWon = false;
			}
		}
		System.out.println("game over");	
	}
	public boolean hasWon(int[][] cells){
		for(int r = 0; r< cells.length; r++){
			for(int c = 0; c < cells[r].length; c++){
				if (cells[r][c] == 2048){
					return true;
				}
			}
		}
		return false;
		
	}
	public boolean hasLost(int[][] cells){
		for (int r = 0; r < cells.length; r++) {
			for (int c =0; c < cells[r].length; c++) {
				if (cells[r][c] == EMPTY_TILE) {
					return false;
				}
			}
		}
		for (int r = 0; r <cells.length; r++) {
			for (int c = 0; c< cells[r].length; c++) {
				if (canBeMerged(cells, r, c)) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean canBeMerged(int[][] cells, int row, int column){
		return ((row > 0 && cells[row - 1][column] == cells[row][column]) ||
	            (column < cells[row].length - 1 && cells[row][column + 1] == cells[row][column]) ||
	            (row < cells.length - 1 && cells[row + 1][column] == cells[row][column]) ||
	            (column > 0 && cells[row][column - 1] == cells[row][column]));
		
	}
	public void generateNewTiles(int[][] cells){
		List<int[]> emptyTiles = getEmptyTiles(cells);
		if(!emptyTiles.isEmpty()){
			int[] randomTile = emptyTiles.get(random.nextInt(emptyTiles.size()));
			int value;
			if(random.nextDouble() < 0.9d ){
				value = 2;
			}
			else{
				value = 4;
			}
			cells[randomTile[0]][randomTile[1]] = value;
		}
		
	}
	public static List<int[]> getEmptyTiles(int[][] cells){
		List<int[]> emptyTiles = new ArrayList<>();
		for(int r =0; r<cells.length; r++){
			for(int c = 0; c < cells[r].length; c++){
				if(cells[r][c] == EMPTY_TILE){
					emptyTiles.add(new int[] {r,c});
				}
			}
		}
		return emptyTiles;
	}
	public static int move(int[][]cells, int direction){
		switch(direction){
		case UP:
			return moveUp(cells);
		case DOWN:
			return moveDown(cells);
		case RIGHT:
			return moveRight(cells);
		case LEFT:
			return moveLeft(cells);
			
		default:
			return 0;
		}
	}
	public static int moveUp(int[][] cells){
		int score = 0;
		for (int c = 0; c < cells[0].length; c++)
        {
            final int[] tiles = new int[cells.length];
            int i = 0;
            for (int r = cells.length - 1; r >= 0; r--){
                tiles[i] = cells[r][c];
                i++;
            }
            score += mergeTiles(tiles);
            i = 0;
            for (int r = cells.length - 1; r >= 0; r--){
            	cells[r][c] = tiles[i];
                i++;
            }
        }
		return score;
	}
	public static int moveRight(int[][] cells){
		int score = 0;
		for (int r = 0; r < cells.length; r++){
            score += mergeTiles(cells[r]);
        }
		return score;
	}
	public static int moveDown(int[][] cells){
		int score = 0;
		for (int c = 0; c < cells[0].length; c++)
        {
            final int[] tiles = new int[cells.length];
            int i = 0;
            for (int r = 0; r < cells.length; r++){
                tiles[i] = cells[r][c];
                i++;
            }
            score += mergeTiles(tiles);
            i = 0;
            for (int r = 0; r < cells.length; r++){
            	cells[r][c] = tiles[i];
                i++;
            }
        }
		return score;
	}
	public static int moveLeft(int[][] cells){
		int score = 0;
		for (int r = 0; r < cells.length; r++){
            final int[] tiles = new int[cells[r].length];
            int i = 0;
            for (int c = cells[r].length - 1; c >= 0; c--){
                tiles[i] = cells[r][c];
                i++;
            }
            score += mergeTiles(tiles);
            i = 0;
            for (int c = cells[r].length - 1; c >= 0; c--){
            	cells[r][c] = tiles[i];
                i++;
            }
        }
		return score;
	}
	public static int mergeTiles(int[] tiles){
		for(int i = tiles.length - 2; i>= 0; i--){
			int j = i;
			while(j < tiles.length -1){
				if(tiles[j+1] == EMPTY_TILE){
					tiles[j+1] = tiles[j];
					tiles[j] = EMPTY_TILE;
				}
				j++;
			}
		}
		int score = 0;
		for(int i = tiles.length - 2; i >= 0; i--){
			if(tiles[i+1] == tiles[i]){
				tiles[i+1] = tiles[i+1] + tiles[i];
				score += tiles[i+1];
				tiles[i] = 0;
			}
		}
		for(int i = tiles.length - 2; i >= 0; i--){
			int j = i;
			while(j < tiles.length - 1){
				if(tiles[j+1] == EMPTY_TILE){
					tiles[j+1] = tiles[j];
					tiles[j] = EMPTY_TILE;
				}
				j++;
			}
		}
		return score;
	}
}
