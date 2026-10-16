package com.threedee;

import java.awt.event.KeyEvent;

import com.threedee.input.Controller;

public class Game {

	public double time;
	public Controller controls;

	public Game() {

		controls = new Controller();

	}

	// Player movements are tied to tick(), I honestly don't know if this is a good
	// idea.
	public void tick(boolean[] key) {
		// First time using a boolean array, all variables below are stored in
		// (boolean[] key) then they can be used in the Controller class.
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean turnLeft = key[KeyEvent.VK_LEFT];
		boolean turnRight = key[KeyEvent.VK_RIGHT];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean sprint = key[KeyEvent.VK_SHIFT];

		controls.tick(forward, back, left, right, turnLeft, turnRight, jump, crouch, sprint);

	}

}
