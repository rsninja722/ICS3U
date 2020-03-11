package bouncingBalls;

import game.*;
import game.drawing.Camera;
import game.drawing.Draw;
import game.physics.*;
import bouncingBalls.BouncingBall;

import java.awt.Color;

import bouncingBalls.Block;

public class Ball {
	public static Ball[] balls;
	Circle collider;
	Point velocity;
	Double angle;
	int lastCollisionIndex = -1;
	boolean collidedThisFrame = false;
	
	Color color;

	static final double entropy = 0.975;
	static double gravity = 0.2;

	// constructor
	public Ball() {
		int radius = Utils.rand(10, 15);
		collider = new Circle(Utils.rand(radius, GameJava.gw - radius), Utils.rand(radius, GameJava.gh - radius),
				radius);
		angle = Math.toRadians(Utils.rand(0, 359));
		double speed = Utils.rand(10, 30) / 10;
		velocity = new Point(Math.sin(angle) * speed, Math.cos(angle) * speed);
		color = Color.getHSBColor((float)Utils.rand(0, 100)/100, 1.0f, 0.8f);
	}

	public void preventOverlays(int index) {
		while (hittingOtherBalls(index) || hittingBlocks()) {
			collider.x = Utils.rand(this.collider.r, (GameJava.gw - this.collider.r));
			collider.y = Utils.rand(this.collider.r, (GameJava.gh - this.collider.r));
		}
	}

	public Ball(double x, double y) {
		int radius = Utils.rand(10, 15);
		collider = new Circle(x, y, radius);
		angle = Math.toRadians(90);
		velocity = new Point(Math.sin(angle), Math.cos(angle));
		color = Color.getHSBColor((float)Utils.rand(0, 100)/100, 1.0f, 0.8f);
	}

	// generate
	public static void makeBalls(int count) {
		balls = new Ball[count];
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball();
			balls[i].preventOverlays(i);
		}
	}

	// draw
	public static void drawBalls() {
		for (int i = 0; i < balls.length; i++) {
			if (balls[i] != null) {
				Draw.setColor(balls[i].color);
				int last = balls[i].lastCollisionIndex;
				last = last < 0 ? i : last;
				Draw.line((int)balls[i].collider.x, (int)balls[i].collider.y, (int)balls[last].collider.x, (int)balls[last].collider.y);
			}
		}
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

	public boolean hittingOtherBalls(int index) {
		for (int j = 0; j < balls.length; j++) {
			if (j != index && balls[j] != null) {
				if (Physics.circlecircle(this.collider, balls[j].collider)) {

					double velX = balls[j].velocity.x;
					double velY = balls[j].velocity.y;

					balls[j].velocity.x = this.velocity.x * entropy;
					balls[j].velocity.y = this.velocity.y * entropy;

					this.velocity.x = velX * entropy;
					this.velocity.y = velY * entropy;

					lastCollisionIndex = j;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hittingBlocks() {
		for(int j=0;j<Block.blocks.length;j++) {
			if(Physics.circlerect(this.collider, Block.blocks[j].collider)) {
				return true;
			}
		}
		return false;
	}

	public void move(int index) {
		double oldVelX = velocity.x;
		collider.x += velocity.x;
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

		double oldVelY = velocity.y;
		collider.y += velocity.y;
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

		velocity.x *= 0.99;
		velocity.y *= 0.99;

//		if (lastCollisionIndex != -1) {
//			Circle last = balls[lastCollisionIndex].collider;
//			if (last.y > collider.y) {
//				if (Physics.dist(collider.x, collider.y, last.x, last.y) < (collider.r + last.r) * 1.5) {
//					if (last.x > collider.x) {
//						velocity.x -= 0.1;
//					} else {
//						velocity.x += 0.1;
//					}
//				}
//			}
//		}

		velocity.y += gravity * Math.cos(Camera.angle);
		velocity.x += gravity * Math.sin(Camera.angle);
	}

	public boolean bounceOfWallsX() {
		if (collider.x - collider.r < 0 && velocity.x < 0) {
			velocity.x *= -1;
//			lastCollisionIndex = -1;
			return true;
		}
		if (collider.x + collider.r > GameJava.gw && velocity.x > 0) {
			velocity.x *= -1;
//			lastCollisionIndex = -1;
			return true;
		}
		return false;
	}

	public boolean bounceOfWallsY() {
		if (collider.y - collider.r < 0 && velocity.y < 0) {
			velocity.y *= -1;
//			lastCollisionIndex = -1;
			return true;
		}
		if (collider.y + collider.r > GameJava.gh && velocity.y > 0) {
			velocity.y *= -1;
//			lastCollisionIndex = -1;
			return true;
		}
		return false;
	}

}