import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HumanPlayer implements Player{
	
	private Object monitor= new Object();
	private int action;
	public HumanPlayer(Component viewer){

		viewer.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int keyCode = e.getKeyCode();
				switch(keyCode){
				case KeyEvent.VK_UP:
					action = GameManager.UP;
					System.out.println("UP pressed");
					break;
				case KeyEvent.VK_DOWN:
					action = GameManager.DOWN;
					System.out.println("DOWN pressed");
					break;
				case KeyEvent.VK_LEFT:
					action = GameManager.LEFT;
					System.out.println("LEFT pressed");
					break;
				case KeyEvent.VK_RIGHT:
					action = GameManager.RIGHT;
					System.out.println("RIGHT pressed");
					break;
				}
				if(action != -1){
					synchronized(monitor){
						monitor.notifyAll();
					}
				}
			}
			

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	@Override
	public int getAction(){
		action = -1;
		while(action == -1){
			synchronized(monitor){
				try{
					monitor.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		return action;
	}

}
