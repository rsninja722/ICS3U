import java.awt.Color;

import game.*;

public class ExampleUsage extends GameJava {

	public ExampleUsage(int gameWidth, int gameHeight) {
		super(gameWidth, gameHeight);
		startLoops();
	}

	static GameJava g;
	
	public static void main(String[] args) throws InterruptedException {
		g = new ExampleUsage(800, 500);
    }
	
	public void draw() {
		d.rect((int)frameCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
    }

	public void update() {
		System.out.println("updated");
	}
	
	private void startLoops() {
		System.out.println("starting loops");
		while(running) {
			
			// drawing
			double currentTime = System.nanoTime();
			if(currentTime - lastDrawTime >= nanosPerFrame) {
                synchronized(d) {
                	draw();
                    d.render();
                }
				lastDrawTime = currentTime;
				frameCount++;
			}
			
			// updating
			currentTime = System.nanoTime();
			if(currentTime - lastUpdateTime >= nanosPerFrame) {
				update();
                lastUpdateTime = currentTime;
                updateCount++;
			}
			
		}
	}

}