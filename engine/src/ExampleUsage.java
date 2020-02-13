import java.awt.Color;

import game.*;
import game.drawing.Sprite;

public class ExampleUsage extends GameJava {

	public ExampleUsage(int gameWidth, int gameHeight, int fps, int ups) {
		super(gameWidth, gameHeight, fps, ups);
		startLoops();
	}

	static GameJava g;
	
	Sprite boss = new Sprite("S:\\ICS3U\\engine\\Boss10.png");
	
	public static void main(String[] args) throws InterruptedException {
		g = new ExampleUsage(800, 700, 60, 60);
    }
	
	// put code here to draw to screen
	public void draw() {
		d.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
		d.setColor(Color.GREEN);
		d.rect(40,70,30,(int)frameCount);
		
		d.circle(100, 100, (int)frameCount/10);
		
		d.circle(150, 150, (int)frameCount/15, Color.MAGENTA);
		
		d.imgIgnoreCutoff(boss,200,200,0,1,1);
	}

	// put code here to update game
	public void update() {
		d.camera.x++;
	}	
	
	// used to run loops, do not change
	private void startLoops() {
		System.out.println("starting loops");
		while(running) {
			
			// drawing
			double currentTime = System.nanoTime();
			if(currentTime - lastDrawTime >= nanosPerFrame) {
                synchronized(d) {
                	d.preRender();
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