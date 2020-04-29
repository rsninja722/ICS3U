package animationGame.enemies;

import java.awt.Color;

import game.GameJava;
import game.Utils;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Physics;
import animationGame.Main;

public class EnemyBoss extends BaseEnemy {
	
	double angle;
	double speed;
	boolean asleep;
	static double maxSpeed = 0.8;
	static double accel = 0.01;

	EnemyBoss(double x, double y, double velx, double vely) {
		super(x, y, 28, 4, velx, vely);
		this.angle = Math.PI;
		this.speed = 0;
		this.asleep = true;
		this.sleepAngle = Math.PI;
	}
	
	@Override
	void draw() {
		if(velocity.x == 0 && velocity.y == 0) {
			// hit box
			if(Utils.debugMode) {
				Draw.setColor(new Color(0,0,255,155));
				Draw.circle(this.collider);
			}
			Draw.image(Sprites.get("enemySleep" + (Math.round(((GameJava.frameCount+this.collider.x+this.collider.y)/125.0))%2)), (int) this.collider.x, (int) this.collider.y, this.sleepAngle, this.sizeMultiplyer);
		} else {
			// get number for which picture to use
			int cycle = (int) Math.floor(this.walkCycle);
			cycle = cycle >= 6 ? 11 - cycle : cycle;
			
			// shadow
			Draw.image(Sprites.get("shadow"), (int) this.collider.x, (int) this.collider.y, 0, this.sizeMultiplyer);
			// hit box
			if(Utils.debugMode) {
				Draw.setColor(new Color(0,0,255,155));
				Draw.circle(this.collider);
			}
			// enemy
			Draw.image(Sprites.get("enemy" + cycle), (int) this.collider.x, (int) this.collider.y, angle, this.sizeMultiplyer);
		}
	}
	
	@Override
	void move() {
		if(this.asleep) {
			if(Physics.dist(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y) < 200) {
				this.asleep = false;
			}
		} else {
			// accelerate
			if(this.speed < maxSpeed) {
				this.speed += accel;
			}
			
			// gradually turn to player
			this.angle = Utils.turnTo(this.angle, Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y), 0.01 - (this.speed/100.0));
			
			// set velocity
			this.velocity.x = Math.cos(this.angle) * this.speed;
			this.velocity.y = Math.sin(this.angle) * this.speed;
			
			// move in x
			this.collider.x += this.velocity.x;
			if (this.colliding()) {
				this.collider.x -= this.velocity.x;
				this.speed *= -1;
			}
	
			// move in y
			this.collider.y += this.velocity.y;
			if (this.colliding()) {
				this.collider.y -= this.velocity.y;
				this.speed *= -1;
			}
			
			// turn if missed charge
			if(Physics.dist(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y) > 200 
				&& this.speed > 0.3
				&& ! (Math.round(this.angle) == Math.round(Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y)))) {
				
				this.speed -= accel * 2;
				this.angle = Utils.turnTo(this.angle, Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y), 0.01);
				
			}
			
			if(Physics.dist(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y) > 400) {
				this.angle = Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x,  Main.player.circle.y);
				this.speed = 1;
			}
			
			// increase cycle
			this.walkCycle += Math.abs(this.velocity.x / 5) + Math.abs(this.velocity.y / 5);
			// reset cycle
			if (this.walkCycle >= 11) {
				this.walkCycle = 1;
			}
		}
	}

	public static void create(double x, double y, double velx, double vely) {
		BaseEnemy.enemies.add(new EnemyBoss(x, y, velx, vely));
	}
}