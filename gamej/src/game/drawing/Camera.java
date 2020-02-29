package game.drawing;

import game.GameJava;

// object to offset drawing by translations, rotation, and scaling
public class Camera {
	public static int x = 0;
	public static int y = 0;
	public static float angle = 0;
    public static float zoom = 1;

    public static void centerOn(int xPosition,int yPosition) {
        x = -xPosition+GameJava.gw/2;
        y = -yPosition+GameJava.gh/2;
    }
    
    public static void move(int xMovement,int yMovement) {
        x -= yMovement * Math.sin(angle);
        y -= yMovement * Math.cos(angle);
        x -= xMovement * Math.sin(angle + 1.57079632);
        y -= xMovement * Math.cos(angle + 1.57079632);
    }
}
