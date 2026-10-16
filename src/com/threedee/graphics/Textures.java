package com.threedee.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class Textures {

	public static Render floor = loadBitmap("res/textures/floor.bmp");

	public static Render loadBitmap(String fileName) {

		try {

			BufferedImage image = ImageIO.read(Textures.class.getResource(fileName));

			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;

		} catch (Exception e) {

			throw new RuntimeException(e);

		}

	}

}
