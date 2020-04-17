package animationGame;

import game.GameJava;
import game.Input;
import game.KeyCodes;
import game.Utils;
import game.drawing.Camera;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Circle;
import game.physics.Point;

// main player of the game
public class Player {
	Circle circle; // collider
	double acceleration;
	double velocity;
	double maxVelocity;
	double angle;
	double walkCycle;

	Player() {
		this.acceleration = Constants.Player.acceleration;
		this.maxVelocity = Constants.Player.maxVelocity;
		this.angle = 0;
		circle = new Circle(200, 200, 8);
		this.walkCycle = 1;
	}

	void draw() {
		// increase cycle
		this.walkCycle += Math.abs(this.velocity/5);
		// idle
		if (this.velocity == 0) {
			this.walkCycle = 3;
		}
		// reset cycle
		if (this.walkCycle >= 11) {
			this.walkCycle = 1;
		}
		// get number for which picture to use
		int cycle = (int) Math.floor(this.walkCycle);
		cycle = cycle >= 6 ? 11 - cycle : cycle;
		
		// shadow
		Draw.image(Sprites.get("shadow"), (int) this.circle.x, (int) this.circle.y);
		// player
		Draw.image(Sprites.get("player" + cycle), (int) this.circle.x, (int) this.circle.y,this.angle, 1.0);
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
			if (this.velocity < this.maxVelocity) {
				this.velocity += this.acceleration;
			}

			// turn to specified angle
			this.angle = Utils.turnTo(this.angle, dicircleion, Constants.Player.turnSpeed);
			Utils.putInDebugMenu("current angle", this.angle);

		} else {
			// decelerate
			this.velocity = Utils.friction(this.velocity, this.acceleration * 2);
		}

		// move
		circle.x += Math.cos(this.angle) * this.velocity;
		circle.y += Math.sin(this.angle) * this.velocity;
		
		// walls
		if(circle.y < 55) {
			circle.y = 55;
		}
		if(circle.y > 345) {
			circle.y = 345;
		}

		// camera movement
		Point cameraTargetPosition = new Point(this.circle.x, this.circle.y);
		// limit camera from going off screen
		if (cameraTargetPosition.x < GameJava.gw / 2 / Camera.zoom) {
			cameraTargetPosition.x = GameJava.gw / 2 / Camera.zoom;
		}
		if (cameraTargetPosition.y < GameJava.gh / 2 / Camera.zoom) {
			cameraTargetPosition.y = GameJava.gh / 2 / Camera.zoom;
		}
		if (cameraTargetPosition.x > Constants.roomWidth - GameJava.gw / 2 / Camera.zoom - 8) {
			cameraTargetPosition.x = Constants.roomWidth - GameJava.gw / 2 / Camera.zoom - 8;
		}
		if (cameraTargetPosition.y > Constants.roomHeight - GameJava.gh / 2 / Camera.zoom - 8) {
			cameraTargetPosition.y = Constants.roomHeight - GameJava.gh / 2 / Camera.zoom - 8;
		}
		Camera.centerOn(cameraTargetPosition);
	}

}
