import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI {

	public static int WIDTH = 406;
	public static int HEIGHT = 459;
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
	public GUI(final int width, final int height, final GameManager gm){
		frame = new JFrame();
		frame.setTitle("2048-Solver");
		JButton startBtn = new JButton("START");
		startBtn.setFocusPainted(false);
		startBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				gm.start();
			}
			
		});
		final JLabel score = new JLabel();
		score.setText("Score: 0");
		score.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(startBtn);
		menuBar.add(Box.createHorizontalGlue());
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
				graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
				//System.out.println("width: "+getWidth());
				//System.out.println("height: "+getHeight());
				int tileInnerWidth = (int) (0.2d*getWidth());
				//System.out.println("innerWidth"+tileInnerWidth);
				int tileInnerHeight = (int) (0.2d*getHeight());
				//System.out.println("tileInnerHeight"+tileInnerHeight);
				int widthPadding = (int) (0.2d * tileInnerWidth);
				int heightPadding = (int) (0.2d * tileInnerHeight);
				for(int r = 0; r<cells.length; r++){
					for(int c = 0; c<cells[r].length;c++){
						int position = (int) (Math.log(cells[r][c])/Math.log(2));
						//set background color of each tile
						if(cells[r][c] == 0){
							graphics.setColor(getColor(EMPTY_TILE_COLOR,1.0f));
						}
						else{
							graphics.setColor(getColor(TILE_BG_COLORS[position-1],1.0f));
						}
						int xPos = (c+1)*widthPadding + c*tileInnerWidth;
						int yPos = (r+1)*heightPadding+ r*tileInnerHeight;
						graphics.fillRoundRect(xPos, yPos, tileInnerWidth, tileInnerHeight, 3, 3);
						
						//if the cell is not an empty tile, set specific font color and text.
						if(cells[r][c] > 0){
							if(position == 1 || position == 2){
								graphics.setColor(getColor(FONT_COLORS[0],1.0f));
							}
							else{
								graphics.setColor(getColor(FONT_COLORS[1],1.0f));
							}
							String text = String.valueOf(cells[r][c]);
							graphics.setFont(FONT);
							FontMetrics fm = g.getFontMetrics(graphics.getFont());
							int textWidth = fm.stringWidth(text);
							int textHeight = fm.getHeight();
							graphics.drawString(text, (int)((xPos + 0.5d * tileInnerWidth) - (textWidth * 0.5d)), (int)((yPos + 0.5d * tileInnerHeight)+(textHeight * 0.3d)));
							
							
						}
					}
				}
				
				score.setText("Score: " + String.valueOf(gm.getScore()));
				
				
			}
			
			
		};
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(gridPanel);
		frame.setJMenuBar(menuBar);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	public GUI(GameManager gm){
		this(WIDTH,HEIGHT, gm);
	}
	
	public void update(){
		frame.revalidate();
		frame.repaint();
	}
	private Color getColor(Color color, float alpha){
		float[] ingredients = color.getRGBComponents(null);
		return new Color(ingredients[0],ingredients[1],ingredients[2],alpha);
	}

	public static void main(String[] args){
		GameManager gm = new GameManager(4,4);
		final GUI gui = new GUI(gm);
		ActionListener taskController = new ActionListener(){


			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gui.update();
			}
		};
		new Timer(100, taskController).start();
	}

}
