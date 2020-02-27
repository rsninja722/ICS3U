import java.awt.Color;

import game.*;

public class ExampleUsage extends GameJava {

	public ExampleUsage() {
        super(800,600,60,60);
        new LoopManager(this);
	}
    
    public int number1 = 563;
	
	public static void main(String[] args) throws InterruptedException {
        new ExampleUsage();   
    }
	
	// put code here to draw to screen
	public void draw() {
		d.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
		d.setColor(Color.GREEN);
		d.rect(40,70,30,(int)frameCount);
		
		d.circle(100, 100, (int)frameCount/10);
		
		d.circle(150, 150, (int)frameCount/15, Color.MAGENTA);
		
        d.imgIgnoreCutoff(sprites.get("Boss10"),300,200,frameCount/100.0,8,8);
        d.imgIgnoreCutoff(sprites.get("car"),400,400,frameCount/-75.0,6,8);
	}

	// put code here to update game
	public void update() {
		// d.camera.x++;
	}	
	

}