import java.awt.Color;
import game.*;
import game.drawing.*;

public class Example2 extends GameJava {

    public Example2() {
        super(600, 600, 30, 60);
        LoopManager.startLoops(this);
	}
    
    public int number1 = 563;
	
	public static void main(String[] args) throws InterruptedException {
        new Example2();   
    }
	
	public void draw() {
		Draw.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100);
		Draw.setColor(Color.GREEN);
		Draw.rect(40,70,30,(int)frameCount);
    }
    
	public void update() {
		Camera.x++;
	}	

}