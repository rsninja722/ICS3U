package animationGame;

// magic numbers

public class Constants {
	
	public static final int roomWidth = 1008;
	public static final int roomHeight = 400;

	public static class Player {
		public static final double acceleration = 0.02;
		public static final double maxVelocity = 1.0;
		public static final double turnSpeed = 0.2;
	}
	
	public static class Enemy {
		public static final double BossAccel = 0.02;
		public static final double BossSpeed = 1.6;
	}
	
	public static class Camera {
		public static final double camZoomSpeed = 0.02;
	}
	
	public static class Dart {
		public static final double dartSpeed = 10.0;
	}
}
