

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
}
