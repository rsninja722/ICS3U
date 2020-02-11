import java.awt.Color;

import game.*;

public class ExampleUsage extends GameJava {

	public ExampleUsage(int gameWidth, int gameHeight, int fps, int ups) {
		super(gameWidth, gameHeight, fps, ups);
		startLoops();
	}

	static GameJava g;
	
	public static void main(String[] args) throws InterruptedException {
		g = new ExampleUsage(800, 500, 60, 60);
    }
	
	// put code here to draw to screen
	public void draw() {
		d.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
		d.setColor(Color.GREEN);
		d.rect(40,70,30,(int)frameCount);
		
		d.circle(100, 100, (int)frameCount/10);
		
		d.circle(150, 150, (int)frameCount/15, Color.MAGENTA);
	}

	// put code here to update game
	public void update() {
	}
	
	
	// used to run loops, do not change
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
			if(currentTime - lastUpdateTime >= nanosPerUpdate) {
				update();
                lastUpdateTime = currentTime;
                updateCount++;
			}
			
		}
	}

}