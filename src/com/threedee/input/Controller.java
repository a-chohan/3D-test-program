package com.threedee.input;

public class Controller {

	public double x, z, y, rotation, xa, ya, za, rotationa, rotationb;
	public static boolean mouseLeft = false;
	public static boolean mouseRight = false;
	public static boolean mouseUp = false;
	public static boolean mouseDown = false;
	public static boolean walking = false;
	public static boolean crouchBob = false;
	public static boolean running = false;

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean turnLeft, boolean turnRight,
			boolean jump, boolean crouch, boolean sprint) {

		double rotationSpeed = 0.05;
		double jumpHeight = 1;
		double crouchHeight = 0.3;
		double walkSpeed = 0.5;

		double xMove = 0;
		double yMove = 0;
		double zMove = 0;

		if (forward) {

			zMove++;
			walking = true;

		}

		if (back) {

			zMove--;
			walking = true;

		}

		if (left) {

			xMove--;
			walking = true;

		}

		if (right) {

			xMove++;
			walking = true;

		}

		if (turnLeft) {

			rotationa -= rotationSpeed;

		}

		if (mouseUp) {

			rotationb -= rotationSpeed;

		}

		if (mouseDown) {

			rotationb += rotationSpeed;

		}

		if (mouseLeft) {

			rotationa -= rotationSpeed;

		}

		if (turnRight) {

			rotationa += rotationSpeed;

		}

		if (mouseRight) {

			rotationa += rotationSpeed;

		}

		if (jump) {

			y += jumpHeight;

		}

		if (crouch) {

			y -= crouchHeight;
			walkSpeed = 0.2;
			sprint = false;
			crouchBob = true;

		} else {

			crouchBob = false;

		}

		if (sprint) {

			walkSpeed = 1;
			walking = true;
			running = true;

		} else {

			running = false;

		}

		if (!forward && !back && !left && !right) {

			walking = false;

		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
		// ya += (yMove * Math.cos(rotation) - zMove * Math.sin(rotation));

		x += xa;
		y *= 0.9;
		z += za;
		xa *= 0.1;
		ya *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		// rotation += rotationb;
		rotationa *= 0.2;
		// rotationb *= 0.2;

	}

}
