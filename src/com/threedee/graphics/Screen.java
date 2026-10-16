package com.threedee.graphics;

import java.util.Random;

import com.threedee.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render;

	public Screen(int width, int height) {

		super(width, height);
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);

		for (int i = 0; i < 256 * 256; i++) {

			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);

		}

	}

	// This is where the render-related methods are called and are drawn to the
	// screen.
	public void render(Game game) {

		for (int i = 0; i < width * height; i++) {

			pixels[i] = 0;

		}

		render.floor(game);
		render.renderDistanceLimiter();
		//render.wall();
		draw(render, 0, 0);

	}

}
