package com.threedee.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Gets the current position of the mouse cursor.
		MouseX = e.getX();
		MouseY = e.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// Ensures all input is ceased once the application is no longer in focus.
		for (int i = 0; i < key.length; i++) {

			key[i] = false;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// If a certain key is pressed, set the boolean to true.
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {

			key[keyCode] = true;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// If a certain key is released, set the boolean to false.
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {

			key[keyCode] = false;

		}

	}

}
