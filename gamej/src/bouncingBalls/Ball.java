package bouncingBalls;

import game.*;
import game.physics.*;
import bouncingBalls.BouncingBall;

public class Ball {
    Circle collider;
    Point velocity;
    Double angle;
    int lastCollisionIndex = -1;
    boolean collidedThisFrame = false;

    public Ball(int index) {
        int radius = Utils.rand(5,15);
        // prevent intersection with other balls
        collider = new Circle(Utils.rand(radius,GameJava.gw-radius),Utils.rand(radius,GameJava.gh-radius),radius);
        while(BouncingBall.hittingOtherBalls(collider,index)) {
            collider.x = Utils.rand(radius,GameJava.gw-radius);
            collider.y = Utils.rand(radius,GameJava.gh-radius);
        }
        angle = Math.toRadians(Utils.rand(0,359));
        velocity = new Point(Math.sin(angle),Math.cos(angle));
    }

    public void move() {
		collider.x += velocity.x;
		collider.y += velocity.y;
	}

	public boolean ifOnEdgeBounce(int limitLeft, int limitTop, int limitRight, int limitBottom) {
		if (collider.x - collider.r < limitLeft && velocity.x < 0) {
			velocity.x *= -BouncingBall.entropy;
			lastCollisionIndex = -1;
			return true;
		}
		if (collider.y - collider.r < limitTop && velocity.y < 0) {
			velocity.y *= -BouncingBall.entropy;
			lastCollisionIndex = -1;
			return true;
		}
		if (collider.x + collider.r > limitRight && velocity.x > 0) {
			velocity.x *= -BouncingBall.entropy;
			lastCollisionIndex = -1;
			return true;
		}
		if (collider.y + collider.r > limitBottom && velocity.y > 0) {
			velocity.y *= -BouncingBall.entropy;
			lastCollisionIndex = -1;
			return true;
		}
		return false;
	}

	public boolean bounceOffOtherBalls(Ball[] ballArray, int thisIndex) {
		// go through balls
		for (int i = 0; i < ballArray.length; i++) {
			// if it isn't itself and the ball hasn't collided already
			if (i != thisIndex && ballArray[i].collidedThisFrame == false && i != lastCollisionIndex) {
				// if hitting swap velocities
				if (Physics.circlecircle(this.collider, ballArray[i].collider)) {
					double thisVelocityX = this.velocity.x;
					double thisVelocityY = this.velocity.y;

					this.velocity.x = ballArray[i].velocity.x * BouncingBall.entropy;
					this.velocity.y = ballArray[i].velocity.y * BouncingBall.entropy;

					ballArray[i].velocity.x = thisVelocityX * BouncingBall.entropy;
					ballArray[i].velocity.y = thisVelocityY * BouncingBall.entropy;
					// make this ball not collide again this frame, and not hit ball i until another
					// collision
					this.collidedThisFrame = true;
					lastCollisionIndex = i;
					return true;
				}
			}
		}
		return false;
	}
	
	public void fall() {
		if(lastCollisionIndex != -1 && Math.abs(velocity.x) < 0.5) {
			if(BouncingBall.balls[lastCollisionIndex].collider.y > collider.y) {
				if(BouncingBall.balls[lastCollisionIndex].collider.x > collider.x) {
					velocity.x -= 0.11;
				}
				if(BouncingBall.balls[lastCollisionIndex].collider.x < collider.x) {
					velocity.x += 0.11;
				}
			}
		}
	}
}