import java.awt.Color;


/*
 * TODO:
 * focus loss
 * add utils
 * add lines
 * text
 * 
 * add sounds
 * set name and icon
 * javadoc comments
 */

import game.*;
import game.drawing.*;

public class ExampleUsage extends GameJava {

    public ExampleUsage() {
        super(800, 600, 600, 60);
	}
	
	public static void main(String[] args) throws InterruptedException {
        new ExampleUsage();   
    }
	
	// put code here to draw to screen
	public void draw() {
        Sprite boss = Sprites.get("Boss10");
        for(int y =0;y<gh;y+=40) {
            for(int x=0;x<gw;x+=40) {
                Draw.img(boss,x+10,y,(x+y)/(y+1) + frameCount/25.0,1);
            }    
        }
		Draw.rect((int)updateCount, 50 + (int)(Math.sin( Math.toRadians(frameCount*2))*20), 100, 100, Color.YELLOW);   
        if(Input.mouseDown(0)) {
            Draw.setColor(Color.GREEN);
        } else {
            Draw.setColor(Color.BLUE);
        }
		Draw.rect(40,70,30,(int)frameCount);
		
		Draw.circle(100, 100, (int)frameCount/10);
		
		Draw.circle((int)Input.mousePos.x, (int)Input.mousePos.y, (int)frameCount/15, Color.MAGENTA);
		
        Draw.imgIgnoreCutoff(Sprites.get("Boss10"),300,200,frameCount/100.0,8);
        Draw.imgIgnoreCutoff(Sprites.get("car"),400,400,frameCount/-75.0,3);
        
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
}