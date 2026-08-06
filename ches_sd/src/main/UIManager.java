package main;

import java.awt.Graphics2D;

public class UIManager {

	private GamePanel gp = new GamePanel();
	
	public enum Disp {
		MENU, PLAY, PAUSE, ABOUT, EXIT
	}
	
	private Disp currentState = Disp.PLAY;
	
	public UIManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setScreen(Disp state) {
        currentState = state;
    }

    public Disp getScreen() {
        return currentState;
    }
    
    public void draw(Graphics2D g2) {
    	
    	switch (currentState) {
    	case MENU:
    		drawMenu(g2);
    		break;
    	case PLAY:
    		drawPlay(g2);
    		break;
    	case PAUSE:
    		drawPause(g2);
    		break;
    	case ABOUT:
    		drawAbout(g2);
    		break;
    	case EXIT:
    		System.exit(0);
    		break;
		default:
			break;
    	}
    }
    
    private void drawMenu(Graphics2D g2) {
    	
    	
    }
    
    private void drawPlay(Graphics2D g2) {
    	
    }
    
    private void drawPause(Graphics2D g2) {
    	
    }
    
    private void drawAbout(Graphics2D g2) {
    	
    }
    
    
}
