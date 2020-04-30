package animationGame.enemies;

public class EnemySmall extends BaseEnemy {

	EnemySmall(double x, double y, double velx, double vely) {
		super(x, y, 7, 1, velx, vely);
	}

	// creates a new small enemy
	public static void create(double x, double y, double velx, double vely) {
		BaseEnemy.enemies.add(new EnemySmall(x, y, velx, vely));
	}
}