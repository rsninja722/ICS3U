package game;

import java.awt.Color;

public class GameJava {
	
	public static int gw;
	public static int gh;
	
	public static int framePerSecond = 60;
	public static int updatesPerSecond = 60;
	
	public static long frameCount = 0;
	public static long updateCount = 0;
	
	private static final double nanosecondsPerSecond = 1000000000;
	private static double nanosPerFrame;
	private static double lastDrawTime = System.nanoTime();
	
	public static Drawing d;
	
	public static boolean running = true;
	
	public static void init() {
		init(800,600);
    }

	public static void init(int gameWidth, int gameHeight) {
		System.out.println("constructing GamaJava");
        gw = gameWidth;
        gh = gameHeight;
        
        d = new Drawing();
        
        nanosPerFrame = nanosecondsPerSecond / framePerSecond;
        
        new GameJava().startLoops();
    }
	
	private void startLoops() {
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
		}
	}
    
    public void draw() {
		d.rect((int)frameCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
		System.out.println("method not overwriten, frame: "+frameCount);
	}
}
