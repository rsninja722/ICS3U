package bouncingBalls;

import java.awt.Color;
import java.awt.RenderingHints;

import game.*;
import game.drawing.*;
import bouncingBalls.Ball;

public class BouncingBall extends GameJava {
    static final int ballCount = 200;
    int index = 0;

    public BouncingBall() {
        super(800, 600, 60, 60);
        Ball.makeBalls(ballCount);
        LoopManager.startLoops(this);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "bouncing balls";
        new BouncingBall();   
    }
	
	@Override
	public void draw() {
		Draw.setColor(Color.RED);
		if(Draw.canvas != null) {
			Draw.canvas.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		}
		Ball.drawBalls();
        Draw.setColor(Color.BLUE);
        Draw.rect(gw/2,0,gw,1);
        Draw.rect(gw/2,gh,gw,1);
        Draw.rect(0,gh/2,1,gh);
        Draw.rect(gw,gh/2,1,gh);
        
    }
    
    

	@Override
	public void update() {
		Ball.moveBalls();
		
		if(Input.mouseClick(0)) {
			Ball.balls[index] = new Ball(Input.mousePos.x,Input.mousePos.y);
			index++;
			if(index == Ball.balls.length) {
				index = 0;
			}
		}
		
		if(Input.keyClick(KeyCodes.A)) {
			Ball.gravity *= -1;
		}
        
        if(Input.keyClick(KeyCodes.ENTER)) {
        	Ball.makeBalls(ballCount);
        }

        
        if(Input.keyDown(KeyCodes.LEFT)) {Camera.move((int)(-5/Camera.zoom),0);}
        if(Input.keyDown(KeyCodes.RIGHT)) {Camera.move((int)(5/Camera.zoom),0);}
        if(Input.keyDown(KeyCodes.UP)) {Camera.move(0,(int)(-5/Camera.zoom));}
        if(Input.keyDown(KeyCodes.DOWN)) {Camera.move(0,(int)(5/Camera.zoom));}

        if(Input.keyClick(KeyCodes.EQUALS)) {Camera.zoom+=0.5;}
        if(Input.keyClick(KeyCodes.MINUS)) {Camera.zoom-=0.5;}

        if(Input.keyDown(KeyCodes.Q)) {Camera.angle-=0.01;}
        if(Input.keyDown(KeyCodes.E)) {Camera.angle+=0.01;}
    }	
    
    @Override
    public void absoluteDraw() {}
}