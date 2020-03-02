package racing;

import game.physics.Rect;

public class Car {
	public Rect carRect = new Rect(0,0,32,32);
	public double x = 0;
	public double y = 0;
	public int w = 32;
	public int h = 32;
	public double angle = -Math.PI;
	public double speed = 0;
	public double maxSpeed = 4;
	public double acceleration = 0.025;
	public double turningSpeed = 0.02;
	
	public Car() {}
}
