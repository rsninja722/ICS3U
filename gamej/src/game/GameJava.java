package game;

import game.drawing.Draw;
import game.drawing.Sprites;

public class GameJava {
    public static GameJava game;
    
    // game width/height
	public static int gw;
	public static int gh;
    
    // fps to run updates and drawing at
	public static int framePerSecond = 60;
	public static int updatesPerSecond = 60;
    
    // numbers that increment every frame 
	public static long frameCount = 0;
	public static long updateCount = 0;
    
    // if set to false, game will stop running
	public static boolean running = true;
	
	public GameJava(int gameWidth, int gameHeight, int fps, int ups) {
        game = this;
        init(gameWidth,gameHeight, fps, ups);
        LoopManager.startLoops(this);
	}

    // calculates frame rate nanosecond speeds and initializes drawing 
	public static void init(int gameWidth, int gameHeight, int fps, int ups) {
		System.out.println("[GameJava] initizlizing");
		
        gw = gameWidth;
        gh = gameHeight;
        
        framePerSecond = fps;
        updatesPerSecond = ups;

        Sprites.loadSprites();
        
        Draw.init();

        Input.init();
    }
    
    public void draw() {
        System.out.println("no draw method found in the main game file");
    }

    public void update() {
        System.out.println("no update method found in the main game file");
    }
}
