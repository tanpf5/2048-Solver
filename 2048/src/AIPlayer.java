import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AIPlayer implements Player {
	public static final int DEFAULT_DEPTH = 5;
    
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
        for (int action : GameManager.ACTIONS) {
        	double score = getScore(action);
        	scores[action] = score;
        	if (score > maxScore) {
        		maxScore = scores[action];
        		bestAction = action;
        	}
        }
        return bestAction;
	}
	private double getScore(int action) {
		int[][] clone = deep_copy(game.getCells());
        int bonus = GameManager.move(clone, action);
        return getExpectiValue(game.getScore() + bonus, clone, 0);
	}
	
	private double getExpectiValue(int totalScore, int[][] cells, int depth) {
		if (depth == DEFAULT_DEPTH) {
			return evaluationFunction(totalScore, cells);
		}
		List<Integer[]> emptyCells = getAllEmptyCells(cells);
		int n = emptyCells.size();
		int expectiScore = 0;
		for (Integer[] cell : emptyCells) {
			int[][] clone = deep_copy(cells);
			clone[cell[0]][cell[1]] = 2;
			expectiScore += getMaxValue(totalScore, clone, depth + 1) * 0.9 / (1.0 / n);
			clone[cell[0]][cell[1]] = 4;
			expectiScore += getMaxValue(totalScore, clone, depth + 1) * 0.1 / (1.0 / n);
		}
		return expectiScore;
	}
	
	private double getMaxValue(int totalScore, int[][] cells, int depth) {
		if (depth == DEFAULT_DEPTH) {
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
	
	private double evaluationFunction(int totalScore, int[][] cells) {
		return totalScore;
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
	
	private int[][] deep_copy(int[][] cells) {
		int[][] copy = new int[cells.length][cells[0].length];
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[0].length; j++)
				copy[i][j] = cells[i][j];
		return copy;
	}
}
