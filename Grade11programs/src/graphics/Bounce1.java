package graphics;

/* 2020.02.24
 * James N
 * Bounce1
 * Bouncing balls
 * press q to exit
 * hold w to stop erasing
 */

import java.awt.Color;

import hsa2.GraphicsConsole;

import graphics.Ball;

public class Bounce1 {

	public static void main(String[] args) {
		new Bounce1();
	}

	public static final int sw = 1000;
	public static final int sh = 800;
	
	Ball[] balls = new Ball[100];

	GraphicsConsole gc = new GraphicsConsole(sw, sh);

	Bounce1() {
		setup();
		while (gc.getKeyCode() != 'Q') {
			moveBall();
			draw();
			gc.sleep(16);
		}
	}

	public void setup() {
		gc.setAntiAlias(true);
		gc.setLocationRelativeTo(null);
		gc.setTitle("Bouncing ball");
		gc.setBackgroundColor(new Color(50,50,50));

		for(int i=0;i<balls.length;i++) {
			// create new ball
			balls[i] = new Ball(Math.random()*(sw-100)+50,Math.random()*(sw-100)+50,15,Math.random()*3+1);
			
			//prevent intersections
			boolean hittingOthers = false;
			for(int j=0;j<balls.length;j++) {
				if(balls[j] != null && i != j) {
					if(Ball.ballBall(balls[i], balls[j])) {
						hittingOthers = true;
						break;
					}
				}
			}
			while(hittingOthers == true) {
				balls[i].x = Math.random()*(sw-100)+50;
				balls[i].y = Math.random()*(sw-100)+50;
				hittingOthers = false;
				for(int j=0;j<balls.length;j++) {
					if(balls[j] != null && i != j) {
						if(Ball.ballBall(balls[i], balls[j])) {
							hittingOthers = true;
							break;
						}
					}
				}
			}
		}
	}

	public void moveBall() {
		for(int i=0;i<balls.length;i++) {
			balls[i].collidedThisFrame = false;
			balls[i].move();
			balls[i].ifOnEdgeBounce(0,0, sw, sh);
			balls[i].bounceOffOtherBalls(balls, i);
		}
	}

	public void draw() {
		synchronized (gc) {
			if(!(gc.getKeyCode() == 'W')) {
				gc.clear();
			}
			for(int i=0;i<balls.length;i++) {
				balls[i].draw(gc);
			}
		}
	}

}
