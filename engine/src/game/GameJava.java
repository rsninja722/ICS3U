package game;

import game.drawing.Drawing;
import game.drawing.Sprites;

public class GameJava {
    
    // game width/height
	public static int gw;
	public static int gh;
    
    // fps to run updates and drawing at
	public static int framePerSecond = 60;
	public static int updatesPerSecond = 60;
    
    // numbers that increment every frame 
	public static long frameCount = 0;
	public static long updateCount = 0;
    
    // calculation for how long to wait between each frame
	public static final double nanosecondsPerSecond = 1000000000;
	public static double nanosPerFrame;
	public static double lastDrawTime = System.nanoTime();
	
	public static double lastUpdateTime = System.nanoTime();
	public static double nanosPerUpdate;
    
    // drawing object used for all drawing
	public static Drawing d;
    
    // if set to false, game will stop running
	public static boolean running = true;
    
    // loads all sprites from the assets/images folder
	public Sprites sprites = new Sprites();
	
	public GameJava(int gameWidth, int gameHeight, int fps, int ups) {
		init(gameWidth,gameHeight, fps, ups);
	}

    // calculates frame rate nanosecond speeds and initializes drawing 
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
