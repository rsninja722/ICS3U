package game;

import game.drawing.Drawing;

public class GameJava {
	
	public static int gw;
	public static int gh;
	
	public static int framePerSecond = 60;
	public static int updatesPerSecond = 60;
	
	public static long frameCount = 0;
	public static long updateCount = 0;
	
	public static final double nanosecondsPerSecond = 1000000000;
	public static double nanosPerFrame;
	public static double lastDrawTime = System.nanoTime();
	
	public static double lastUpdateTime = System.nanoTime();
	public static double nanosPerUpdate;
	
	public static Drawing d;
	
	public static boolean running = true;
	
	public GameJava(int gameWidth, int gameHeight, int fps, int ups) {
		init(gameWidth,gameHeight, fps, ups);
	}

	public void init(int gameWidth, int gameHeight, int fps, int ups) {
		System.out.println("constructing GamaJava");
		
        gw = gameWidth;
        gh = gameHeight;
        
        framePerSecond = fps;
        updatesPerSecond = ups;
        
        d = new Drawing();
        
        nanosPerFrame = nanosecondsPerSecond / framePerSecond;
        nanosPerUpdate = nanosecondsPerSecond / updatesPerSecond;
	}
}
