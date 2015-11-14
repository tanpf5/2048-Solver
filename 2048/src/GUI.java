import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class GUI {

	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	public static Color BG_COLOR = new Color(0xBBADA0);
	public static Color EMPTY_TILE_COLOR = new Color(0xCDC1B4);
	public static Color[] FONT_COLORS = new Color[]{ 
			new Color (0x776e65),
			new Color(0xf9f6f2)
	};
	public static Color[] TILE_BG_COLORS = new Color[]{
			new Color(0xEEE8DA),
			new Color(0xEDDEC9),
			new Color(0xF5B27D),
			new Color(0xEC9051),
			new Color(0xF67A60),
			new Color(0xEC5736),
			new Color(0xEDCE73),
			new Color(0xF0D24C),
			new Color(0xE6C22C),
			new Color(0xEEC744),
			new Color(0xECC230)
			
	};
	public static Font FONT = new Font("Helvetica Neue",Font.BOLD,55);
	
	private JFrame frame;
	public GUI(int width, int height, final GameManager gm){
		frame = new JFrame();
		frame.setTitle("2048-Solver");
		JButton startBtn = new JButton("START");
		JLabel score = new JLabel();
		score.setText("Score: 0");
		score.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(startBtn);
		menuBar.add(score);
		
		JPanel gridPanel = new JPanel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				int[][] cells = gm.getCells();
				Graphics2D graphics = (Graphics2D) g.create();
				graphics.setColor(getColor(BG_COLOR, 1.0f));
				graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				int tileOuterWidth = getWidth()/cells[0].length;
				int tileOuterHeight = getHeight()/cells.length;
				int tileInnerWidth = (int) (0.8d*tileOuterWidth);
				int tileInnerHeight = (int) (0.8d*tileOuterHeight);
				int widthPadding = (int) (0.1d * tileOuterWidth);
				int heightPadding = (int) (0.1d * tileOuterHeight);
				for(int r = 0; r<cells.length; r++){
					for(int c = 0; c<cells[r].length;c++){
						if(cells[r][c] == GameManager.EMPTY_TILE){
							graphics.setColor(getColor(EMPTY_TILE_COLOR,1.0f));
						}
						else{
							
						}
					}
				}
				
				
			}
			
			
		};
	}
	
	private Color getColor(Color color, float alpha){
		float[] ingredients = color.getRGBComponents(null);
		return new Color(ingredients[0],ingredients[1],ingredients[2],alpha);
	}


}
