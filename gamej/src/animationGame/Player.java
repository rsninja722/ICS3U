package animationGame;

import game.Input;
import game.KeyCodes;
import game.Utils;
import game.physics.Circle;

// main player of the game
public class Player {
	Circle circle; // collider
	double acceleration;
	double velocity;
	double maxVelocity;
	double angle;

	Player() {
		this.acceleration = Constants.Player.acceleration;
		this.maxVelocity = Constants.Player.maxVelocity;
		this.angle = 0;
		circle = new Circle(0, 0, 8);
	}

	void setPosition(double x, double y) {
		this.circle.x = x;
		this.circle.y = y;
		this.velocity = 0;
		this.angle = 0;
	}

	void moveBasedOnInput() {
		Utils.putInDebugMenu("x", circle.x);
		Utils.putInDebugMenu("y", circle.y);

		// move in the average direction of the key presses
		double angle = 0;
		double denominator = 0;

		// up
		if (Input.keyDown(KeyCodes.W) || Input.keyDown(KeyCodes.UP)) {
			angle -= Math.PI / 2;
			++denominator;
		}
		// down
		if (Input.keyDown(KeyCodes.S) || Input.keyDown(KeyCodes.DOWN)) {
			angle += Math.PI / 2;
			++denominator;
		}
		// left
		if (Input.keyDown(KeyCodes.A) || Input.keyDown(KeyCodes.LEFT)) {
			angle += Math.PI * ((Input.keyDown(KeyCodes.W) || Input.keyDown(KeyCodes.UP)) ? -1 : 1); // edge case that I don't know how to work around
			++denominator;
		}
		// right
		if (Input.keyDown(KeyCodes.D) || Input.keyDown(KeyCodes.RIGHT)) {
			++denominator;
		}

		if (denominator > 0) {
			// calculate direction
			double dicircleion = angle / denominator;
			Utils.putInDebugMenu("target angle", dicircleion);

			// accelerate
			if(this.velocity < this.maxVelocity) {
				this.velocity += this.acceleration;
			}
			
			// turn to specified angle
			this.angle = Utils.turnTo(this.angle, dicircleion, Constants.Player.turnSpeed);
			Utils.putInDebugMenu("current angle", this.angle);

			
		} else {
			// decelerate
			this.velocity = Utils.friction(this.velocity, this.acceleration*2);
		}
		
		// move
		circle.x += Math.cos(this.angle) * this.velocity;
		circle.y += Math.sin(this.angle) * this.velocity;

	}

}
