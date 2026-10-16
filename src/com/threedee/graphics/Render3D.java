package com.threedee.graphics;

import java.util.Random;

import com.threedee.Game;
import com.threedee.input.Controller;

// The most complicated class in this program yet.
public class Render3D extends Render {

	public double[] zBuffer;
	private double renderDistance = 1500;
	private double forwardGlobal;
	Random random = new Random();

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
	}

	public void floor(Game game) {

		double floorPosition = 4;
		double ceilingPosition = 20;
		double forward = game.controls.z;
		forwardGlobal = forward;
		double right = game.controls.x;
		double up = game.controls.y;
		double walking = Math.sin(game.time / 6.0) * 0.2;

		double rotation = game.controls.rotation;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);

		if (Controller.crouchBob) {

			walking = Math.sin(game.time / 6.0) * 0.08;

		} else if (Controller.running) {

			walking = Math.sin(game.time / 6.0) * 0.5;

		}

		for (int y = 0; y < height; y++) {

			double ceiling = (y - height / 2.0) / height;

			double z = (floorPosition + up) / ceiling;

			if (Controller.walking) {

				z = (floorPosition + up + walking) / ceiling;

			}

			if (ceiling < 0) {

				z = (ceilingPosition - up) / -ceiling;

				if (Controller.walking) {

					z = (ceilingPosition - up - walking) / -ceiling;

				}

			}

			// For loop which adds depth to the environment
			for (int x = 0; x < width; x++) {

				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = Textures.floor.pixels[(xPix & 15) + (yPix & 15) * 16];

				if (z > 500) {

					pixels[x + y * width] = 0;

				}

			}

		}
		
	}

	public void wall() {
		
		for (int i = 0; i < 10000; i++) {
			double xx = random.nextDouble();
			double yy = random.nextDouble();
			double zz = 1.5 - forwardGlobal / 16;
		
			int xPixel = (int) (xx / zz * height / 2 + width) / 2;
			int yPixel = (int) (yy / zz * height / 2 + height) / 2;
			if (xPixel >= 0 && yPixel >= 0 && xPixel < width && yPixel < height) {
			
				pixels[xPixel + yPixel * width] = 0xfffff;
			
			}
		}
		
	}
	
	// As the name suggests, it limits the render distance however this algorithm is
	// inefficient.
	// When called in the Screen class, FPS drops from 900-1000 to 250-320.
	// Will need to write a more efficient algorithm.
	public void renderDistanceLimiter() {

		for (int i = 0; i < width * height; i++) {

			int color = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));

			// Simple if statements to ensure that the brightness variable does not exceed 0
			// or 255.
			if (brightness < 0) {

				brightness = 0;

			}

			if (brightness > 255) {

				brightness = 255;

			}

			/*
			 * Adds a black fog to the maximum render distance
			 * 
			 * Bit shifting refers to moving the bits of a binary number to the left or
			 * right. The left shift operator "<<" multiplies the number by 2 for each
			 * shift. The right shift operator ">>" divides the number by 2, preserving the
			 * sign for negative numbers. The unsigned right shift operator ">>>" shifts
			 * zeroes regardless of the sign.
			 * 
			 */
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;

		}

	}

}
