package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseDetection extends MouseAdapter{
	
	
	public int x, y;
	public boolean clicked;

	@Override
	public void mousePressed(MouseEvent e) {
		clicked = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		clicked = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	
		x = e.getX();
		y = e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
}
