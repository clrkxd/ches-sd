package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseDetection extends MouseAdapter{
	
	
	public int x, y;
	public boolean pressed;

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
//		 x = e.getX();
//		 y = e.getY();
		 
//		 System.out.println("MOUSE PRESSED: " + x + ", " + y);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		 x = e.getX();
		 y = e.getY();
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
