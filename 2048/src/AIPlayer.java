import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class AIPlayer implements Player {
	public static int DEPTH = 5;
	public static final int BIG_TILE = 64;
	public static final int[] UP = new int[] { -1, 0 };
    public static final int[] RIGHT = new int[] { 0, 1 };
    public static final int[] DOWN = new int[] { 1, 0 };
    public static final int[] LEFT = new int[] { 0, -1 };
    public static final int[][] DIRECTIONS = new int[][] { UP, RIGHT, DOWN, LEFT };
    private final Random random;
    private final GameManager game;
    private int action;
    
    public AIPlayer(final Random random, final GameManager game) {
    	this.random = random;
		this.game = game;
		action = -1;
    }
    
	@Override
	public int getAction() {
		// TODO Auto-generated method stub
        final double[] scores = new double[4];
        int bestAction = -1;
        double maxScore = Integer.MIN_VALUE;
        int emptyCells = getAllEmptyCells(game.getCells()).size();
        if (emptyCells <= 4)
        	DEPTH = 7;
        else 
        	if (emptyCells >= 10)
        		DEPTH = 5;
        	else DEPTH = 6;
        for (int action : getLegalAction(GameManager.ACTIONS)) {
        	double score = getScore(action);
        	scores[action] = score;
        	if (score > maxScore) {
        		maxScore = scores[action];
        		bestAction = action;
        	}
        }
        return bestAction;
	}
	
	private Integer[] getLegalAction(int[] actions) {
		List<Integer> legalActions = new ArrayList<Integer>();
		for (int action : actions) {
			int[][] clone = deep_copy(game.getCells());
	        GameManager.move(clone, action);
	        if (!isSameCells(game.getCells(), clone)) {
	        	legalActions.add(action);
	        }
		}
		return legalActions.toArray(new Integer[legalActions.size()]);
	}
	
	private boolean isSameCells(int[][] original, int[][] changed) {
		for (int i = 0; i < original.length; i++)
			for (int j = 0; j < original[i].length; j++)
				if (original[i][j] != changed[i][j])
					return false;
		return true;
	}
	
	private double getScore(int action) {
		int[][] clone = deep_copy(game.getCells());
        int bonus = GameManager.move(clone, action);
        return getExpectiValue(game.getScore() + bonus, clone, 1);
	}
	
	private double getExpectiValue(int totalScore, int[][] cells, int depth) {
		if (depth == DEPTH) {
			return evaluationFunction(totalScore, cells);
		}
		List<Integer[]> emptyCells = getAllEmptyCells(cells);
		int n = emptyCells.size();
		double expectiScore = 0;
		for (Integer[] cell : emptyCells) {
			int[][] clone = deep_copy(cells);
			clone[cell[0]][cell[1]] = 2;
			expectiScore += getMaxValue(totalScore, clone, depth + 1) * 0.9 * (1.0 / n);
			clone[cell[0]][cell[1]] = 4;
			expectiScore += getMaxValue(totalScore, clone, depth + 1) * 0.1 * (1.0 / n);
		}
		return expectiScore;
	}
	
	private double getMaxValue(int totalScore, int[][] cells, int depth) {
		if (depth == DEPTH) {
			return evaluationFunction(totalScore, cells);
		}
        double maxScore = Integer.MIN_VALUE;
        for (int action : GameManager.ACTIONS) {
    		int[][] clone = deep_copy(cells);
            int bonus = GameManager.move(clone, action);
        	double score = getExpectiValue(totalScore + bonus, clone, depth + 1);
        	if (score > maxScore) {
        		maxScore = score;
        	}
        }
        return maxScore;
	}
	
	private double getScoreFactor(int totalScore) {
		/*int factor = 1;
		while (totalScore >= 10) {
			totalScore /= 10;
			factor *= 10;
		}
		return factor;*/
		return Math.log10(totalScore);
	}
	
	private double evaluationFunction(int totalScore, int[][] cells) {
		double scoreFactor = getScoreFactor(totalScore);
		return totalScore 
				+ getCornerBonus(cells) * scoreFactor
				+ getEmptyCellsBonus(cells) * scoreFactor
				+ getAdjacencyBonus(cells) * scoreFactor
				+ (GameManager.hasLost(cells) ? -20000 : 0);
	}
	
	private double getCornerBonus(int[][] cells) {
		double factor1 = 1.0d;
		
		double total = 0;
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[i].length; j++) 
				if (cells[i][j] != GameManager.EMPTY_TILE) {
					int n = 1;
					if (i % (cells.length - 1) == 0 && j % (cells.length - 1) == 0) n *= 8;
					else {
						if (i % (cells.length - 1) == 0) n *= 2.5;
						if (j % (cells.length - 1) == 0) n *= 2.5;
					}
					total += n * Math.log(cells[i][j]);
				}
		
        double[] totals = new double[4];
        for (int row = 0; row < cells.length; row++) {
            int currentColumn = 0;
            int nextColumn = currentColumn + 1;
            while (nextColumn < cells[row].length) {
                while (nextColumn < cells[row].length - 1 && isEmpty(cells, row, nextColumn))
                	nextColumn++;
                final double currentPower = !isEmpty(cells, row, currentColumn) ? Math.log(cells[row][currentColumn]) : 0;
                final double nextPower = !isEmpty(cells, row, nextColumn) ? Math.log(cells[row][nextColumn]) : 0;
                if (currentPower > nextPower)
                    totals[0] += currentPower - nextPower;
                else if (nextPower > currentPower)
                    totals[1] += nextPower - currentPower;
                currentColumn = nextColumn;
                nextColumn++;
            }
        }
        for (int column = 0; column < cells[0].length; column++) {
            int currentRow = 0;
            int nextRow = currentRow + 1;
            while (nextRow < cells.length) {
                while (nextRow < cells.length - 1 && isEmpty(cells, nextRow, column))
                    nextRow++;
                final double currentPower = !isEmpty(cells, currentRow, column) ? Math.log(cells[currentRow][column]) : 0;
                final double nextPower = !isEmpty(cells, nextRow, column) ? Math.log(cells[nextRow][column]) : 0;
                if (currentPower > nextPower)
                    totals[2] += currentPower - nextPower;
                else if (nextPower > currentPower)
                    totals[3] += nextPower - currentPower;
                currentRow = nextRow;
                nextRow++;
            }
        }
        int row = 0, col = 0;
        if (totals[0] < totals[1]) col = 3;
        if (totals[2] < totals[3]) row = 3;
        double factor2 = Math.log(cells[row][col]) * 0.02d;
        return total * factor1 + (Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3])) * factor2;
	}
	
	private double getEmptyCellsBonus(int[][] cells) {
		int count = getAllEmptyCells(cells).size();
		double factor = 3.0;
		return Math.log(count) * factor;
	}
	
	private List<Integer[]> getAllEmptyCells(int[][] cells) {
		List<Integer[]> emptyCells = new ArrayList<Integer[]>();
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[0].length; j++) {
				if (cells[i][j] == 0) {
					Integer[] cell = {i, j};
					emptyCells.add(cell);
				}
			}
		return emptyCells;
	}
	
	private boolean isValid(int[][] cells, int row, int column){
		return row > -1 &&
				row < cells.length &&
				column > -1 &&
				column < cells[row].length;
	}
	private boolean isEmpty(int[][] cells, int row, int column){
		return cells[row][column] == GameManager.EMPTY_TILE;
	}
	private int[] getFarthestCell(int[][] cells, int row, int column, int[] vector){
		do{
			row += vector[0];
			column += vector[1];
			
		}while(isValid(cells,row,column) && isEmpty(cells, row, column));
		return new int[] {row, column};
	}
	private double getAdjacencyBonus(int[][] cells){
		double bonus = 0.0d;
		double factor = 2.0d;
		for(int r = 0; r < cells.length; r++){
			for(int c = 0; c< cells[r].length; c++){
				if(!isEmpty(cells, r,c)){
					double p = Math.log(cells[r][c]);
					for(int dir = 1; dir <= 2; dir++){
						int[] farthestCell = getFarthestCell(cells, r, c, DIRECTIONS[dir]);
						if(isValid(cells, farthestCell[0], farthestCell[1] ) &&
								!isEmpty(cells, farthestCell[0],farthestCell[1])){
							double p2 = Math.log(cells[farthestCell[0]][farthestCell[1]]);
							bonus -= Math.abs(p - p2);
						}
					}
				}
			}
		}
		return bonus * factor;
	}
	private int[][] deep_copy(int[][] cells) {
		int[][] copy = new int[cells.length][cells[0].length];
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[0].length; j++)
				copy[i][j] = cells[i][j];
		return copy;
	}
	
	
}
