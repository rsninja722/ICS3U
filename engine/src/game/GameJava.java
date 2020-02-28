package game;

/*
 * plan:
 * make a looper manager class that makes a new gameMain and calls methods in it
 * the main file of the game will have to have a specific name
 */

import game.drawing.Drawing;
import game.drawing.Sprites;

public class GameJava {
    public static GameJava game;
    
    // game width/height
	public int gw;
	public int gh;
    
    // fps to run updates and drawing at
	public int framePerSecond = 60;
	public int updatesPerSecond = 60;
    
    // numbers that increment every frame 
	public long frameCount = 0;
	public long updateCount = 0;
    
    // calculation for how long to wait between each frame
	public final double nanosecondsPerSecond = 1000000000;
	public double nanosPerFrame;
	public double lastDrawTime = System.nanoTime();
	
	public double lastUpdateTime = System.nanoTime();
	public double nanosPerUpdate;
    
    // drawing object used for all drawing
	public Drawing drawing;
    
    // if set to false, game will stop running
	public boolean running = true;
    
    // loads all sprites from the assets/images folder
    public Sprites sprites = new Sprites();
    
    // get mouse/key input
    public Input input = new Input();
	
	public GameJava(int gameWidth, int gameHeight, int fps, int ups) {
        game = this;
		init(gameWidth,gameHeight, fps, ups);
	}

    // calculates frame rate nanosecond speeds and initializes drawing 
	public void init(int gameWidth, int gameHeight, int fps, int ups) {
		System.out.println("constructing GamaJava");
		
        gw = gameWidth;
        gh = gameHeight;
        
        framePerSecond = fps;
        updatesPerSecond = ups;
        
        drawing = new Drawing();
        
        nanosPerFrame = nanosecondsPerSecond / framePerSecond;
        nanosPerUpdate = nanosecondsPerSecond / updatesPerSecond;
    }
    
    public void draw() {
        System.out.println("no draw method found in the main game file");
    }

    public void update() {
        System.out.println("no update method found in the main game file");
    }
}
