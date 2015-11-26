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
				System.out.println(score);
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
	public int moveRight(int[][] cells){
		int score = 0;
		for (int r = 0; r < cells.length; r++){
            score += mergeTiles(cells[r]);
        }
		return score;
	}
	public int moveDown(int[][] cells){
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
	public int moveLeft(int[][] cells){
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
	public int mergeTiles(int[] tiles){
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
