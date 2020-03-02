import java.awt.Color;

import game.*;
import game.drawing.*;
import game.physics.*;

public class ExampleUsage extends GameJava {

    public ExampleUsage() {
        super(800, 600, 60, 60);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "example";
        new ExampleUsage();   
    }
	
	// put code here to draw to screen
	public void draw() {
        Sprite boss = Sprites.get("Boss10");
        for(int y =0;y<gh;y+=40) {
            for(int x=0;x<gw;x+=40) {
                Draw.image(boss,x,y);
            }    
        }
        
        Draw.setColor(Color.YELLOW);
		Draw.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100);   
        if(Input.mouseDown(0)) {
            Draw.setColor(Color.GREEN);
        } else {
            Draw.setColor(Color.BLUE);
        }
		Draw.rect(40,70,30,(int)frameCount);
		
		Draw.circle(100, 100, (int)frameCount/10);
        
        Draw.setColor(Color.MAGENTA);
		Draw.circle((int)Input.mousePos.x, (int)Input.mousePos.y, 10);
		Utils.putInDebugMenu("Angle", Utils.pointTo(new Point(300,200), new Point(Input.mousePos.x,Input.mousePos.y)));
        Draw.imageIgnoreCutoff(Sprites.get("Boss10"),300,200,Utils.pointTo(new Point(300,200), new Point(Input.mousePos.x,Input.mousePos.y)),8);
        Draw.imageIgnoreCutoff(Sprites.get("car"),400,400,frameCount/-75.0,3);
        Draw.setLineWidth(10);
        Draw.line(new Point(300,200), new Point(Input.mousePos.x,Input.mousePos.y));

        Draw.setLineWidth(5);
        Draw.setColor(new Color(0.0f,1.0f,1.0f,0.6f));

        Draw.line(40, 50, 550, 600);

        Draw.setFontSize(4);
        Draw.setColor(Color.BLACK);
        Draw.text("testt", 100, 100);
	}

	// put code here to update game
	public void update() {
        if(Input.keyDown(KeyCodes.LEFT)) {Camera.move(-5,0);}
        if(Input.keyDown(KeyCodes.RIGHT)) {Camera.move(5,0);}
        if(Input.keyDown(KeyCodes.UP)) {Camera.move(0,-5);}
        if(Input.keyDown(KeyCodes.DOWN)) {Camera.move(0,5);}

        if(Input.keyClick(KeyCodes.EQUALS)) {Camera.zoom+=0.5;}
        if(Input.keyClick(KeyCodes.MINUS)) {Camera.zoom-=0.5;}

        if(Input.keyDown(KeyCodes.Q)) {Camera.angle-=0.01;}
        if(Input.keyDown(KeyCodes.E)) {Camera.angle+=0.01;}
    }	
    
    @Override
    public void absoluteDraw() {
        Draw.text("absolute", 10, 26);
        Draw.rect(new Rect(400,300,30,50));
    }
}