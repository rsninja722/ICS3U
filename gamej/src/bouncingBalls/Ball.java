package bouncingBalls;

import game.*;
import game.drawing.Camera;
import game.drawing.Draw;
import game.physics.*;
import bouncingBalls.BouncingBall;

import java.awt.Color;

import bouncingBalls.Block;

public class Ball {
	// list of all balls
	public static Ball[] balls;

	// physics collider for ball
	Circle collider;
	// x and y velocity
	Point velocity;
	// last ball this collided with, used for line drawing
	int lastCollisionIndex = -1;
	// if this has already bounced this frame
	boolean collidedThisFrame = false;
	// this ball's color
	Color color;

	// multiplier for velocity during collisions
	static final double entropy = 0.975;
	// amount added to y velocity every frame
	static double gravity = 0.2;

	// constructor
	public Ball() {
		// radius
		int radius = Utils.rand(10, 15);
		// create collider at random position
		collider = new Circle(Utils.rand(radius, GameJava.gw - radius), Utils.rand(radius, GameJava.gh - radius), radius);
		
		// set angle to random number 
		Double angle = Math.toRadians(Utils.rand(0, 359));
		// set speed to random number
		double speed = Utils.rand(10, 30) / 10;
		// calculate velocity with speed and angle
		velocity = new Point(Math.sin(angle) * speed, Math.cos(angle) * speed);
		// randomize color
		color = Color.getHSBColor((float) Utils.rand(0, 100) / 100, 1.0f, 0.8f);
	}
	public Ball(double x, double y) {
		int radius = Utils.rand(10, 15);
		collider = new Circle(x, y, radius);
		Double angle = Math.toRadians(90);
		velocity = new Point(Math.sin(angle), Math.cos(angle));
		color = Color.getHSBColor((float) Utils.rand(0, 100) / 100, 1.0f, 0.8f);
	}

	// used when spawning balls, moves a ball until it isn't hitting others
	public void preventOverlays(int index) {
		while (hittingOtherBalls(index) || hittingBlocks()) {
			collider.x = Utils.rand(this.collider.r, (GameJava.gw - this.collider.r));
			collider.y = Utils.rand(this.collider.r, (GameJava.gh - this.collider.r));
		}
	}

	// generate balls
	public static void makeBalls(int count) {
		balls = new Ball[count];
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball();
			balls[i].preventOverlays(i);
		}
	}

	// draw
	public static void drawBalls() {
		// go through all balls and draw lines between them 
		for (int i = 0; i < balls.length; i++) {
			if (balls[i] != null) {
				Draw.setColor(balls[i].color);
				int last = balls[i].lastCollisionIndex;
				// if there is collision with another ball, draw a line to it
				if(last > -1) {
				Draw.line((int) balls[i].collider.x, (int) balls[i].collider.y, (int) balls[last].collider.x, (int) balls[last].collider.y);
				}
			}
		}
		// go through all balls and draw them
		for (int i = 0; i < balls.length; i++) {
			if (balls[i] != null) {
				Draw.setColor(balls[i].color);
				Draw.circle(balls[i].collider);
			}
		}
	}

	// move
	public static void moveBalls() {
		for (int i = 0; i < balls.length; i++) {
			balls[i].move(i);
		}
	}

	public void move(int index) {
		// move in the x
		double oldVelX = velocity.x;
		collider.x += velocity.x;
		// if hitting anything, move back
		if (bounceOfWallsX()) {
			collider.x -= oldVelX;
		}
		if (hittingOtherBalls(index)) {
			collider.x -= oldVelX;
		}
		if (hittingBlocks()) {
			collider.x -= oldVelX;
			this.velocity.x *= -1;
		}

		// move in the y
		double oldVelY = velocity.y;
		collider.y += velocity.y;
		// if hitting anything, move back
		if (bounceOfWallsY()) {
			collider.y -= oldVelY;
		}
		if (hittingOtherBalls(index)) {
			collider.y -= oldVelY;
		}
		if (hittingBlocks()) {
			collider.y -= oldVelY;
			this.velocity.y *= -1;
		}

		// friction
		velocity.x *= 0.99;
		velocity.y *= 0.99;

		// simulate the balls rolling off each other
		if (lastCollisionIndex != -1) {
			Circle last = balls[lastCollisionIndex].collider;
			// if on top of a ball
			if (last.y > collider.y) {
				if (Physics.dist(collider.x, collider.y, last.x, last.y) < (collider.r + last.r) * 1.5) {
					// if to the left move more left, if to the right move more right
					if (last.x > collider.x) {
						velocity.x -= 0.1;
					} else {
						velocity.x += 0.1;
					}
				}
			}
		}

		// be affected by gravity
		velocity.y += gravity * Math.cos(Camera.angle);
		velocity.x += gravity * Math.sin(Camera.angle);
	}

	// goes through all balls and bounces of them if hitting
	public boolean hittingOtherBalls(int index) {
		for (int j = 0; j < balls.length; j++) {
			// if the ball being checked isn't this, and exists
			if (j != index && balls[j] != null) {
				// if hitting
				if (Physics.circlecircle(this.collider, balls[j].collider)) {

					// swap velocities
					double velX = balls[j].velocity.x;
					double velY = balls[j].velocity.y;

					balls[j].velocity.x = this.velocity.x * entropy;
					balls[j].velocity.y = this.velocity.y * entropy;

					this.velocity.x = velX * entropy;
					this.velocity.y = velY * entropy;

					// track what ball was hit
					lastCollisionIndex = j;
					return true;
				}
			}
		}
		return false;
	}

	// returns true if any blocks are hit
	public boolean hittingBlocks() {
		for (int j = 0; j < Block.blocks.length; j++) {
			if (Physics.circlerect(this.collider, Block.blocks[j].collider)) {
				return true;
			}
		}
		return false;
	}

	// if hitting the left or right wall, invert the x velocity
	public boolean bounceOfWallsX() {
		if (collider.x - collider.r < 0 && velocity.x < 0) {
			velocity.x *= -1;
			return true;
		}
		if (collider.x + collider.r > GameJava.gw && velocity.x > 0) {
			velocity.x *= -1;
			return true;
		}
		return false;
	}

	// if hitting the top or bottom wall, invert the y velocity
	public boolean bounceOfWallsY() {
		if (collider.y - collider.r < 0 && velocity.y < 0) {
			velocity.y *= -1;
			return true;
		}
		if (collider.y + collider.r > GameJava.gh && velocity.y > 0) {
			velocity.y *= -1;
			return true;
		}
		return false;
	}
}