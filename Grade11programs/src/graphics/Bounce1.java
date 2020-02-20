package graphics;

import java.awt.Color;

import hsa2.GraphicsConsole;

public class Bounce1 {

	public static void main(String[] args) {
		new Bounce1();

	}

	public static final int sw = 1000;
	public static final int sh = 800;

	public double ballX = 100.0;
	public double ballY = 100.0;

	public double ballSpeed = 5.0;

	public double ballVX;
	public double ballVY;

	public double ballR = 50.0;

	GraphicsConsole gc = new GraphicsConsole(sw, sh);

	Bounce1() {
		setup();
		while (gc.getKeyCode() != 'Q') {
			moveBall();
			draw();
			gc.sleep(16);
		}
	}

	public void setup() {
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);
		gc.setTitle("Bouncing ball");
		gc.setBackgroundColor(new Color(99, 175, 178));

		double angle = Math.random() * 6.2;

		ballVX = Math.sin(angle) * ballSpeed;
		ballVY = Math.cos(angle) * ballSpeed;
	}

	public void moveBall() {
		ballX += ballVX;
		ballY += ballVY;

		double halfR = ballR / 2;
		if (ballX - halfR < 0) {
			ballVX *= -1;
		}
		if (ballY - halfR < 0) {
			ballVY *= -1;
		}
		if (ballX + halfR > sw) {
			ballVX *= -1;
		}
		if (ballY + halfR > sh) {
			ballVY *= -1;
		}
	}

	public void draw() {
		synchronized (gc) {
			gc.clear();
			gc.fillOval((int) (ballX - ballR / 2), (int) (ballY - ballR / 2), (int) ballR, (int) ballR);
		}
	}

}
