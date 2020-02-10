import java.awt.Color;

import game.*;

public class ExampleUsage extends GameJava {

	static GameJava g;
	
	public static void main(String[] args) throws InterruptedException {
		
		init(800,600);
		
    }
    
	@Override
	public void draw() {
        d.rect(10,40,40,60,Color.GREEN);
		System.out.println("method was overwriten, frame: "+frameCount);
    }

}
