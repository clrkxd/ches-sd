package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseDetection extends MouseAdapter{
	
	
	public int x, y;
	public boolean pressed;
	public boolean dragged;
	public boolean justPressed;
	
	public int pressX, pressY;

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		dragged = false;
		justPressed = true;

	    pressX = e.getX();
	    pressY = e.getY();
		x = e.getX();
	    y = e.getY();
		
//		 x = e.getX();
//		 y = e.getY();
		 
//		 System.out.println("MOUSE PRESSED: " + x + ", " + y);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		x = e.getX();
		 y = e.getY();
		pressed = false;
		 
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	
		x = e.getX();
	    y = e.getY();

	    int dx = x - pressX;
	    int dy = y - pressY;

	    if (Math.abs(dx) > 5 || Math.abs(dy) > 5) {
	        dragged = true;
	    }
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
}
