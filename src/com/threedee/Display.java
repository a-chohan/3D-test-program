package com.threedee;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.threedee.graphics.Screen;
import com.threedee.input.Controller;
import com.threedee.input.InputHandler;

public class Display extends Canvas implements Runnable {
	
	static JFrame frame = new JFrame();

	private static final long serialVersionUID = 1L;
	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private boolean running = false;
	private InputHandler input;
	private int oldX = 0;
	private int newX = 0;
	private int oldY = 0;
	private int newY = 0;
	private int fps;
	private long currSeconds;

	private int[] pixels;

	public static int WIDTH = 1000;
	public static int HEIGHT = 800;
	
	

	public Display() {

		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(WIDTH, HEIGHT);
		game = new Game();
		// Grabs data from the pixels array then projects it to the screen, I think, I
		// could be wrong.
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

	}

	// Starts the program, checks if 'running' is true otherwise the 'running'
	// variable will be set to true and the program starts.
	// Called in the main method.
	private void start() {

		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();

		System.out.println("Running...");

	}
	
	private void stop() {
		
		if (!running) return;
		
		try {
			
			thread.join();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.exit(0);
			
		}
		
	}

	@Override
	public void run() {

		// Variables for the FPS counter
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		requestFocus();

		while (running) {

			// FPS counter math
			long currentTime = System.nanoTime();
			currSeconds = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;

			while (unprocessedSeconds > secondsPerTick) {

				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;

				if (tickCount % 60 == 0) {

					previousTime += 1000;
					fps = frames;
					frames = 0;

				}

			}

			if (ticked) {

				render();
				frames++;

			}

			// render();
			// frames++;

			// Detects mouse movement and adjusts the old variables to the new ones so that
			// they match.
			newX = InputHandler.MouseX;
			newY = InputHandler.MouseY;

			if (newX > oldX) {

				// System.out.println("Right");
				Controller.mouseRight = true;

			}
			if (newY > oldY) {

				// System.out.println("Right");
				Controller.mouseUp = true;

			}
			if (newX < oldX) {

				// System.out.println("Left");
				Controller.mouseLeft = true;

			}
			if (newY < oldY) {

				// System.out.println("Left");
				Controller.mouseDown = true;

			}
			if (newX == oldX && newY == oldY) {

				// System.out.println("Still");
				Controller.mouseLeft = false;
				Controller.mouseRight = false;
				Controller.mouseUp = false;
				Controller.mouseDown = false;

			}

			oldX = newX;
			oldY = newY;

		}

	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {

			createBufferStrategy(3);
			return;

		}

		screen.render(game);

		for (int i = 0; i < WIDTH * HEIGHT; i++) {

			pixels[i] = screen.pixels[i];

		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, (frame.getWidth()), (frame.getHeight()), null);
		g.setFont(new Font("Verdana", 0, 15));
		g.setColor(Color.GREEN);
		g.drawString(fps + "\nfps " + newX + ", " + newY + " " + currSeconds, 15, 50);
		g.dispose();
		
		bs.show();

	}

	// Probably the most important thing here, used to calculate FPS, movements, and
	// probably more.
	private void tick() {

		game.tick(input.key);

	}

	// Normal JFrame method, initialized in the main method once Frame(game) is
	// called
	public static void Frame(Component game) {

		
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Cursor blank to hide cursor in window
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");

		frame.add(game);
		frame.pack();
		frame.getContentPane().setCursor(blank);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Unnamed Program - v0.01");
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		

	}

	public static void main(String[] args) {
		Display game = new Display();
		Frame(game);
		game.start();

	}

}
