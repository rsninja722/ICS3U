package animationGame;

// main player of the game

import animationGame.Main.GameState;
import game.GameJava;
import game.Input;
import game.KeyCodes;
import game.Utils;
import game.audio.Sounds;
import game.drawing.Camera;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Circle;
import game.physics.Point;

// main player of the game
public class Player {
	public Circle circle; // collider
	double acceleration;
	double velocity;
	double maxVelocity;
	double angle;
	double walkCycle; // used to determine what frame in the animation to draw
	boolean shooting; // if the player is shooting

	double ammo;

	int lives;

	Player() {
		this.acceleration = Constants.Player.acceleration;
		this.maxVelocity = Constants.Player.maxVelocity;
		this.angle = 0;
		circle = new Circle(0, 0, 8);
		this.walkCycle = 1;
		this.lives = 3;
		this.shooting = false;
		this.ammo = 1;
	}

	// resets the player (except for lives, because that needs to stay consistent
	// across the game)
	void reset() {
		this.acceleration = Constants.Player.acceleration;
		this.maxVelocity = Constants.Player.maxVelocity;
		this.angle = 0;
		this.walkCycle = 1;
		this.ammo = 1;
	}

	void draw() {
		// shadow
		Draw.image(Sprites.get("shadow"), (int) this.circle.x, (int) this.circle.y);

		// player
		if (this.shooting) {
			// draw player holding blow gun
			Draw.image(Sprites.get("playerShoot"), (int) this.circle.x, (int) this.circle.y, this.angle, 1.0);
		} else {
			// get number for which picture to use
			int cycle = (int) Math.floor(this.walkCycle);
			cycle = cycle >= 6 ? 11 - cycle : cycle;
			// draw frame of animation
			Draw.image(Sprites.get("player" + cycle), (int) this.circle.x, (int) this.circle.y, this.angle, 1.0);
		}
	}

	void absDraw() {
		// hearts
		for (int i = 0; i < this.lives; i++) {
			Draw.image(Sprites.get("heart"), 32 + i * 36, 32, 0.0, 2.0);
		}
		// dart
		for (int i = 0; i < this.ammo; i++) {
			Draw.image(Sprites.get("dart"), 32 + i * 36, 64, 0.0, 2.0);
		}
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
		

		// when lmb is pressed down, enter shooting mode
		if (Input.mouseClick(0) && this.ammo > 0) {
			this.shooting = true;
		}

		if (this.shooting) {
			// zoom in camera
			if (Camera.zoom < 3.0) {
				Camera.zoom += 0.01;
			}

			// point player towards mouse
			this.angle = Utils.pointTo(this.circle.x, this.circle.y, Input.mousePos.x, Input.mousePos.y);

			// make sure player doesn't keep momentum
			this.velocity = 0;
			
			// fire dart when lmb is released
			if (!Input.mouseDown(0)) {
				Dart.addDart(this.circle.x, this.circle.y, this.angle);
				this.ammo--;
				this.shooting = false;
			}
		} else {
			// zoom out camera
			if (Camera.zoom > 2.0) {
				Camera.zoom -= 0.05;
				Camera.zoom = (float) Math.max(Camera.zoom, 2.0);
			}
			
			// determine the average direction of the key presses
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

				// turn towards specified angle
				this.angle = Utils.turnTo(this.angle, dicircleion, Constants.Player.turnSpeed);
				Utils.putInDebugMenu("current angle", this.angle);
			} else {
				// decelerate
				this.velocity = Utils.friction(this.velocity, this.acceleration * 2);
			}

			// move
			circle.x += Math.cos(this.angle) * this.velocity;
			circle.y += Math.sin(this.angle) * this.velocity;

			// increase walk cycle for animation
			this.walkCycle += Math.abs(this.velocity / 5);
			// set animation to idle if not moving
			if (this.velocity == 0) {
				this.walkCycle = 3;
			}
			// loop cycle
			if (this.walkCycle >= 11) {
				this.walkCycle = 1;
			}

			// wall collision
			if (circle.y < 55) {
				circle.y = 55;
			}
			if (circle.y > 345) {
				circle.y = 345;
			}
			if (circle.x < 0) {
				circle.x = 0;
			}

			// when player reaches the end of the level
			if (circle.x > 1000) {
				// give the player an extra life
				this.lives++;
				// go to next level, or win screen
				switch (Main.currentLevel) {
				
				case tutorial:
					Main.currentLevel = Main.Level.two;
					Main.transitionTo(GameState.playing);
					Main.transitionAlpha = 254;
					Main.shouldReloadLevel = true;
					break;
					
				case two:
					Main.currentLevel = Main.Level.boss;
					Main.transitionTo(GameState.playing);
					Main.transitionAlpha = 254;
					Main.shouldReloadLevel = true;
					break;
					
				case boss:
					Main.transitionTo(GameState.win);
					break;
					
				}
			}
		}

		// camera movement
		
		// start camera at player 
		Point cameraTargetPosition = new Point(this.circle.x, this.circle.y);
		// move camera towards cursor if shooting
		if (this.shooting) {
			cameraTargetPosition.x += Math.cos(Utils.pointTo(this.circle.x, this.circle.y, Input.mousePos.x, Input.mousePos.y)) * (Camera.zoom - 2.0) * 25.0;
			cameraTargetPosition.y += Math.sin(Utils.pointTo(this.circle.x, this.circle.y, Input.mousePos.x, Input.mousePos.y)) * (Camera.zoom - 2.0) * 25.0;
		}

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

		// set camera position
		Camera.centerOn(cameraTargetPosition);
	}

}