import java.util.Random;

public class GameManager {
	public static int NUM_OF_ROWS = 4;
	public static int NUM_OF_COLS = 4;
	public static int[] BASIC_TILES = new int[]{2,4};
	public static int EMPTY_TILE = 0;
	
	
	private int[][] cells;
	private int[] tiles;
	private int score;
	
	public GameManager(int row, int column){
		
		cells = new int[row][column];
	}
	
	public int[][] getCells(){
		return cells;
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
	public void start(){
		for(int r = 0; r< cells.length;r++){
			for(int c = 0; c<cells[r].length; c++){
				cells[r][c] = EMPTY_TILE;
			}
		}
		score = 0;
		Random random = new Random();
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
}
