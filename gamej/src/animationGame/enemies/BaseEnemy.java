package animationGame.enemies;

// base class that all enemies extend

import java.awt.Color;
import java.util.ArrayList;

import animationGame.Constants;
import animationGame.Player;
import game.GameJava;
import game.Utils;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Circle;
import game.physics.Physics;
import game.physics.Point;

public class BaseEnemy {

	public static ArrayList<BaseEnemy> enemies = new ArrayList<BaseEnemy>();

	Circle collider;
	public Point velocity;
	double walkCycle; //  used to determine what frame in the animation to draw
	int sizeMultiplyer; // size of sprite
	double sleepAngle; // angle to draw at if there is no movement
	public double speed; // used by boss

	BaseEnemy(double x, double y, int radius, int sizeMultiplyer, double velx, double vely) {
		this.collider = new Circle(x, y, radius);
		this.velocity = new Point(velx, vely);
		this.walkCycle = 1;
		this.sizeMultiplyer = sizeMultiplyer;
		this.sleepAngle = Math.toRadians(Utils.rand(0, 3) * 90); // angle of 0, 90, 180, or 270
	}

	// draws all enemies
	public static void drawEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw();
		}
	}

	// updates all enemies
	public static void moveEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).move();
		}
	}
	
	// returns the index of the enemy a circle is hitting if there is a collision, otherwise returns -1
	public static int circleHittingEnemies(Circle c) {
		for (int i = 0; i < enemies.size(); i++) {
			if(Physics.circlecircle(enemies.get(i).collider, c)) {
				return i;
			}
		}
		return -1;
	}
	
	// removes all enemies
	public static void clearEneimes() {
		enemies.clear();
	}

	void draw() {
		// draw sleeping enemy if not moving
		if(velocity.x == 0 && velocity.y == 0) {
			
			// hit box
			if(Utils.debugMode) {
				Draw.setColor(new Color(0,0,255,155));
				Draw.circle(this.collider);
			}
			
			// get a number 0 or 1 for sleep animation, x and y position are used so every enemy doesn't breath at the same time 
			int sleepCycle = (int) (Math.round(((GameJava.frameCount+this.collider.x+this.collider.y)/125.0))%2);
			
			// draw sleeping enemy
			Draw.image(Sprites.get("enemySleep" + sleepCycle), (int) this.collider.x, (int) this.collider.y, this.sleepAngle, this.sizeMultiplyer);
		} else {
			
			// get number for which picture to use
			int cycle = (int) Math.floor(this.walkCycle);
			cycle = cycle >= 6 ? 11 - cycle : cycle;
			
			// determine angle
			double angle = 0;
			if (this.velocity.x < 0) {
				angle = Math.PI;
			} else if (this.velocity.y > 0) {
				angle = Math.PI / 2;
			} else if (this.velocity.y < 0) {
				angle = -Math.PI / 2;
			}
			
			// shadow
			Draw.image(Sprites.get("shadow"), (int) this.collider.x, (int) this.collider.y, 0, this.sizeMultiplyer);
			
			// hit box
			if(Utils.debugMode) {
				Draw.setColor(new Color(0,0,255,155));
				Draw.circle(this.collider);
			}
			
			// draw enemy
			Draw.image(Sprites.get("enemy" + cycle), (int) this.collider.x, (int) this.collider.y, angle, this.sizeMultiplyer);
		}
	}

	void move() {
		// move in the x
		this.collider.x += this.velocity.x;
		// if there is a collision, reverse x velocity
		if (this.colliding()) {
			this.velocity.x *= -1;
		}
		
		// move in the y
		this.collider.y += this.velocity.y;
		// if there is a collision, reverse y velocity
		if (this.colliding()) {
			this.velocity.y *= -1;
		}
		
		// increase cycle
		this.walkCycle += Math.abs(this.velocity.x / 5) + Math.abs(this.velocity.y / 5);
		// loop cycle
		if (this.walkCycle >= 11) {
			this.walkCycle = 1;
		}
	}

	// if the enemy is hitting other enemies, or the wall
	boolean colliding() {

		// hitting wall
		if (this.collider.y < 55 || this.collider.y > 345 || this.collider.x < 0 || this.collider.x > Constants.roomWidth) {
			return true;
		}

		// position to ignore
		int thisPosition = enemies.indexOf(this);

		for (int i = 0; i < enemies.size(); i++) {
			// is itself
			if (i == thisPosition) {
				continue;
			}
			// hitting other enemy
			if (Physics.circlecircle(this.collider, enemies.get(i).collider)) {
				return true;
			}
		}

		return false;
	}
}
