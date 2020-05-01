package animationGame.enemies;

import java.awt.Color;

import game.GameJava;
import game.Utils;
import game.audio.Sounds;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Physics;
import animationGame.Constants;
import animationGame.Main;

public class EnemyBoss extends BaseEnemy {

	double angle;
	boolean asleep;
	static double maxSpeed = Constants.Enemy.BossSpeed;
	static double accel = Constants.Enemy.BossAccel;

	EnemyBoss(double x, double y, double velx, double vely) {
		super(x, y, 28, 4, velx, vely);
		this.angle = Math.PI;
		this.speed = 0;
		this.asleep = true;
		this.sleepAngle = Math.PI;
	}

	@Override
	void draw() {
		if (velocity.x == 0 && velocity.y == 0) {

			// hit box
			if (Utils.debugMode) {
				Draw.setColor(new Color(0, 0, 255, 155));
				Draw.circle(this.collider);
			}
			// get a number 0 or 1 for sleep animation, x and y position are used so every enemy doesn't breath at the same time
			int sleepCycle = (int) (Math.round(((GameJava.frameCount + this.collider.x + this.collider.y) / 125.0)) % 2);

			// draw sleeping enemy
			Draw.image(Sprites.get("enemySleep" + sleepCycle), (int) this.collider.x, (int) this.collider.y,
					this.sleepAngle, this.sizeMultiplyer);

		} else {

			// get number for which picture to use
			int cycle = (int) Math.floor(this.walkCycle);
			cycle = cycle >= 6 ? 11 - cycle : cycle;

			// shadow
			Draw.image(Sprites.get("shadow"), (int) this.collider.x, (int) this.collider.y, 0, this.sizeMultiplyer);

			// hit box
			if (Utils.debugMode) {
				Draw.setColor(new Color(0, 0, 255, 155));
				Draw.circle(this.collider);
			}

			// enemy
			Draw.image(Sprites.get("enemy" + cycle), (int) this.collider.x, (int) this.collider.y, angle, this.sizeMultiplyer);
		}
	}

	@Override
	void move() {
		// wake up when player gets close
		if (this.asleep) {
			if (Physics.dist(this.collider.x, this.collider.y, Main.player.circle.x, Main.player.circle.y) < 200) {
				Sounds.play("roar");
				this.asleep = false;
			}
		} else {
			// accelerate
			if (this.speed < maxSpeed) {
				this.speed += accel;
			}

			// gradually turn to player
			this.angle = Utils.turnTo(this.angle, Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x, Main.player.circle.y), 0.02 - (this.speed / 80.0));

			// set velocity
			this.velocity.x = Math.cos(this.angle) * this.speed;
			this.velocity.y = Math.sin(this.angle) * this.speed;

			// move in x
			this.collider.x += this.velocity.x;
			// if there is a collision, move back and bounce
			if (this.colliding()) {
				this.collider.x -= this.velocity.x;
				this.speed *= -1;
			}

			// move in y
			this.collider.y += this.velocity.y;
			// if there is a collision, move back and bounce
			if (this.colliding()) {
				this.collider.y -= this.velocity.y;
				this.speed *= -1;
				if(lastBounceSoundTime > 10) {
					Sounds.play("bounce" + Utils.rand(0, 2));
					lastBounceSoundTime = 0;
				}
			}

			// turn if missed charge
			if (Physics.dist(this.collider.x, this.collider.y, Main.player.circle.x, Main.player.circle.y) > 200
			&& this.speed > 0.6 
			&& !(Math.round(this.angle) == Math.round(Utils.pointTo(this.collider.x,this.collider.y, Main.player.circle.x, Main.player.circle.y)))) {
				this.speed -= accel * 2;
				this.angle = Utils.turnTo(this.angle, Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x, Main.player.circle.y), 0.02);
			}

			// if off screen turn directly to player and go
			if (Physics.dist(this.collider.x, this.collider.y, Main.player.circle.x, Main.player.circle.y) > 400) {
				this.angle = Utils.pointTo(this.collider.x, this.collider.y, Main.player.circle.x, Main.player.circle.y);
				this.speed = 1;
			}
			
			double lastCycle = Math.floor(this.walkCycle);
			lastCycle = lastCycle >= 6 ? 11 - lastCycle : lastCycle;

			// increase cycle
			this.walkCycle += Math.abs(this.velocity.x / 5) + Math.abs(this.velocity.y / 5);
			// loop cycle
			if (this.walkCycle >= 11) {
				this.walkCycle = 1;
			}
			
			double newCycle = Math.floor(this.walkCycle);
			newCycle = newCycle >= 6 ? 11 - newCycle : newCycle;
			
			if(lastCycle != 3 && newCycle == 3) {
				Sounds.play("bigStep");
			}
		}
	}

	// creates a new boss
	public static void create(double x, double y, double velx, double vely) {
		BaseEnemy.enemies.add(new EnemyBoss(x, y, velx, vely));
	}
}