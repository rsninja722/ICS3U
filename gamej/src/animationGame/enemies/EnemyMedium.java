package animationGame.enemies;

public class EnemyMedium extends BaseEnemy {

	EnemyMedium(double x, double y, double velx, double vely) {
		super(x, y, 13, 2, velx, vely);
	}

	// creates a new medium enemy
	public static void create(double x, double y, double velx, double vely) {
		BaseEnemy.enemies.add(new EnemyMedium(x, y, velx, vely));
	}
}