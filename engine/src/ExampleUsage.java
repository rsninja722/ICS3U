import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import game.*;

public class ExampleUsage extends GameJava {

    public ExampleUsage() {
        super(800, 600, 60, 60);

        game.drawing.f.addKeyListener(new KeyListener() {
            
            public void keyPressed(KeyEvent e) {
                System.out.println("click");
                System.out.println(e.getKeyCode() + " " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) { System.out.println("click");
            System.out.println(e.getKeyCode() + " " + e.getKeyChar());}

            @Override
            public void keyTyped(KeyEvent e) { System.out.println("click");
            System.out.println(e.getKeyCode() + " " + e.getKeyChar());}
        });
        
        game.drawing.panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	System.out.println("press: x: "+e.getX()+ " y: " + e.getY() + "code: " + e.getButton());
            }
        });
        game.drawing.panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.out.println("x: "+e.getX()+ " y: " + e.getY());
            }
        });
        new LoopManager(this);
	}
    
    public int number1 = 563;
	
	public static void main(String[] args) throws InterruptedException {
        new ExampleUsage();   
    }
	
	// put code here to draw to screen
	public void draw() {
		drawing.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);
		drawing.setColor(Color.GREEN);
		drawing.rect(40,70,30,(int)frameCount);
		
		drawing.circle(100, 100, (int)frameCount/10);
		
		drawing.circle(150, 150, (int)frameCount/15, Color.MAGENTA);
		
        drawing.imgIgnoreCutoff(sprites.get("Boss10"),300,200,frameCount/100.0,8,8);
        drawing.imgIgnoreCutoff(sprites.get("car"),400,400,frameCount/-75.0,6,8);
	}

	// put code here to update game
	public void update() {

		// d.camera.x++;
	}	
	

}