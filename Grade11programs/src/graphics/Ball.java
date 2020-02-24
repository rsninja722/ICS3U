package graphics;

import java.awt.Color;

import hsa2.GraphicsConsole;

public class Ball {

	public double x;
	public double y;

	public double vx;
	public double vy;
	public double speed;

	public double r;

	public boolean collidedThisFrame;
	public int lastCollisionIndex;

	public Color c;

	public Ball(double x, double y, double r, double speed) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.speed = speed;

		double angle = Math.random() * 6.2;

		this.vx = Math.sin(angle) * this.speed;
		this.vy = Math.cos(angle) * this.speed;

		this.collidedThisFrame = false;

		randomizeColor();
	}

	public void move() {
		x += vx;
		y += vy;
	}

	public void ifOnEdgeBounce(int limitLeft, int limitTop, int limitRight, int limitBottom) {
		if (x - r < limitLeft && vx < 0) {
			vx *= -1;
			randomizeColor();
			lastCollisionIndex = -1;
		}
		if (y - r < limitTop && vy < 0) {
			vy *= -1;
			randomizeColor();
			lastCollisionIndex = -1;
		}
		if (x + r > limitRight && vx > 0) {
			vx *= -1;
			randomizeColor();
			lastCollisionIndex = -1;
		}
		if (y + r > limitBottom && vy > 0) {
			vy *= -1;
			randomizeColor();
			lastCollisionIndex = -1;
		}
	}

	public void bounceOffOtherBalls(Ball[] ballArray, int thisIndex) {
		// go through balls
		for (int i = 0; i < ballArray.length; i++) {
			// if it isn't itself and the ball hasn't collided already
			if (i != thisIndex && ballArray[i].collidedThisFrame == false && i != lastCollisionIndex) {
				// if hitting swap velocities
				if (ballBall(this, ballArray[i])) {
					double thisVX = this.vx;
					double thisVY = this.vy;

					this.vx = ballArray[i].vx;
					this.vy = ballArray[i].vy;

					ballArray[i].vx = thisVX;
					ballArray[i].vy = thisVY;

					// randomize colors
					randomizeColor();
					ballArray[i].randomizeColor();
					// make this ball not collide again this frame, and not hit ball i until another
					// collision
					this.collidedThisFrame = true;
					lastCollisionIndex = i;
					break;
				}
			}
		}
	}

	public void randomizeColor() {
		c = Color.getHSBColor((float) Math.random(), 1.0f, 1.0f);
	}

	public void draw(GraphicsConsole g) {
		g.setColor(c);
		g.fillOval((int) (x - r), (int) (y - r), (int) r * 2, (int) r * 2);
	}

	public static double dist(Ball ball1, Ball ball2) {
		double one = (ball2.x - ball1.x);
		double two = (ball2.y - ball1.y);
		return Math.sqrt((one * one) + (two * two));
	}

	public static boolean ballBall(Ball ball1, Ball ball2) {
		if (dist(ball1, ball2) < (ball1.r + ball2.r)) {
			return true;
		} else {
			return false;
		}
	}
}
