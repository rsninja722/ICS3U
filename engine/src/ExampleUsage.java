import java.awt.Color;


/*
 * TODO:
 * add input
 * finish drawing
 * add utils
 * add lines
 * add comments
 */

import game.*;
import game.drawing.*;

public class ExampleUsage extends GameJava {

    public ExampleUsage() {
        super(800, 600, 60, 60);
        LoopManager.startLoops(this);
	}
    
    public int number1 = 563;
	
	public static void main(String[] args) throws InterruptedException {
        new ExampleUsage();   
    }
	
	// put code here to draw to screen
	public void draw() {
		Draw.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
		Draw.setColor(Color.GREEN);
		Draw.rect(40,70,30,(int)frameCount);
		
		Draw.circle(100, 100, (int)frameCount/10);
		
		Draw.circle(150, 150, (int)frameCount/15, Color.MAGENTA);
		
        Draw.imgIgnoreCutoff(Sprites.get("Boss10"),300,200,frameCount/100.0,8,8);
        Draw.imgIgnoreCutoff(Sprites.get("car"),400,400,frameCount/-75.0,6,8);
	}

	// put code here to update game
	public void update() {

		// d.camera.x++;
	}	
	

}