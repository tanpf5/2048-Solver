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
	
	private Player curPlayer;
	private Boolean hasWon;
	private int[][] cells;
	private int[] tiles;
	private int score;
	private Random random = new Random();
	
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
	public void setCurPlayer(Player curPlayer){
		this.curPlayer = curPlayer;
	}
	public void start(){
		for(int r = 0; r< cells.length;r++){
			for(int c = 0; c<cells[r].length; c++){
				cells[r][c] = EMPTY_TILE;
			}
		}
		score = 0;
		
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
		while(hasWon == null){
			if(curPlayer != null){
				int tmpAction =curPlayer.getAction();
				System.out.println(tmpAction);
				score += move(cells, tmpAction);
				
			}			
			
		}
		
	}
	public int move(int[][]cells, int direction){
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
	public int moveUp(int[][] cells){
		int score = 0;
		return score;
	}
	public int moveRight(int[][] cells){
		int score = 0;
		return score;
	}
	public int moveDown(int[][] cells){
		int score = 0;
		return score;
	}
	public int moveLeft(int[][] cells){
		int score = 0;
		return score;
	}
}
