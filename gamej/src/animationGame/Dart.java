package animationGame;

// tranq dart the player can fire

import java.util.ArrayList;

import animationGame.enemies.BaseEnemy;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Circle;
import game.physics.Point;

public class Dart {

	static ArrayList<Dart> darts = new ArrayList<Dart>();
	
	Circle collider;
	Point velocity;
	double angle;
	boolean hasHit;

	Dart(double x, double y, double angle) {
		collider = new Circle(x, y, 3);
		velocity = new Point(Math.cos(angle) * 5.0, Math.sin(angle) * 5.0);
		this.angle = angle;
		this.hasHit = false;
	}
	
	void move() {
		this.collider.x += this.velocity.x;
		this.collider.y += this.velocity.y;
		
		// if this is the first time hitting an enemy
		if(BaseEnemy.circleHittingEnemies(this.collider) != -1 && this.hasHit == false) {
			BaseEnemy e = BaseEnemy.enemies.get(BaseEnemy.circleHittingEnemies(this.collider));
			// "stun" it
			e.velocity.x = 0;
			e.velocity.y = 0;
			// for the boss, knock it back
			e.speed = -1.0;
			// stop moving the dart
			this.velocity.x = 0;
			this.velocity.y = 0;
			
			this.hasHit = true;
		}
		
		// if dart hits a wall, stop moving
		if(this.collider.y < 55 || this.collider.y > 345 || this.collider.x < 0 || this.collider.x > 1000) {
			this.velocity.x = 0;
			this.velocity.y = 0;
			this.hasHit = true;
		}
		
	}
	
	void draw() {
		Draw.image(Sprites.get("dart"), (int) this.collider.x, (int) this.collider.y, this.angle, 1.0);
	}
	
	// draws all darts
	public static void drawDarts() {
		for (int i = 0; i < darts.size(); i++) {
			darts.get(i).draw();
		}
	}

	// updates all darts
	public static void moveDarts() {
		for (int i = 0; i < darts.size(); i++) {
			darts.get(i).move();
		}
	}
	
	// creates a new dart
	public static void addDart(double x, double y, double angle) {
		darts.add(new Dart(x,y,angle));
	}
	
	// removes all darts
	public static void clearDarts() {
		darts.clear();
	}
}