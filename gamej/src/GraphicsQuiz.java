import java.awt.Color;

import game.*;
import game.drawing.*;
import game.physics.*;

public class GraphicsQuiz extends GameJava {

    public GraphicsQuiz() {
        super(800, 600, 60, 60);
        LoopManager.startLoops(this);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "quiz";
        new GraphicsQuiz();   
    }
	
	@Override
	public void draw() {}

	@Override
	public void update() {}	
    
    @Override
    public void absoluteDraw() {
    	Draw.setColor(Color.BLACK);
    	Draw.rect(gw/2,gh/2,gw,gh);
    	
    	Draw.setColor(Color.GREEN);
    	Draw.setLineWidth(3);
        Draw.rectOutline(350, 300, 600, 300);
        
        Draw.circleOutline(650, 150, 50);
        
        Draw.text("this is text", 675, 450);
    }
}