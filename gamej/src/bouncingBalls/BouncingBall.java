package bouncingBalls;

/** 2020.03.13
 * James N
 * Bouncing balls
 * ball bouncing animation
 * ENTER to restart
 */

import java.awt.Color;
import java.awt.RenderingHints;
import game.*;
import game.drawing.*;
import game.physics.Circle;
import game.physics.Physics;
import game.physics.Point;
import bouncingBalls.Ball;
import bouncingBalls.Block;

public class BouncingBall extends GameJava {
    static final int ballCount = 200;
    static final int blockCount = 20;
    
    int index = 0;
    int explosionTime = 0;

    public BouncingBall() {
        super(1000, 800, 60, 60);
        // create rectangles
        Block.makeBlocks(blockCount);
        // create balls
        Ball.makeBalls(ballCount);
        // set motion blur thing
        Draw.alphaBetweenFrames = 0.3f; 
        
        LoopManager.startLoops(this);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "bouncing balls";
        new BouncingBall();   
    }
	
	@Override
	public void draw() {
		// set background
		Draw.setColor(new Color(25,25,25));
		Draw.rect(gw/2,gh/2,gw,gh);
		
		// turn on anti alliasing
		if(Draw.canvas != null) {
			Draw.canvas.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		}
		// draw balls
		Draw.setLineWidth(3);
		Ball.drawBalls();
        Draw.setColor(Color.BLUE);
        // draw blocks
        Block.drawBlocks();
        // draw borders
        Draw.rect(gw/2,0,gw,1);
        Draw.rect(gw/2,gh,gw,1);
        Draw.rect(0,gh/2,1,gh);
        Draw.rect(gw,gh/2,1,gh);  
    }
    
    

	@Override
	public void update() {
		// randomly apply force to keep balls moving
		if(explosionTime == 100) {
			Circle explo = new Circle(Utils.rand(0, gw),Utils.rand(gh-gh/4, gh),200);
			for (int i = 0; i < Ball.balls.length; i++) {
				if(Physics.circlecircle(Ball.balls[i].collider, explo)) {
					double ang = Utils.pointTo(Ball.balls[i].collider.x, Ball.balls[i].collider.y, explo.x, explo.y);
					Ball.balls[i].velocity = new Point(Math.sin(ang) * 20,Math.cos(ang) * 20);
				}
			}
			explosionTime = 0;
		} else {
			explosionTime++;
		}
		
		// move balls
		Ball.moveBalls();
		
		// move one ball to mouse on click
		if(Input.mouseClick(0)) {
			Ball.balls[index] = new Ball(Input.mousePos.x,Input.mousePos.y);
			index++;
			if(index == Ball.balls.length) {
				index = 0;
			}
		}
        
		// reset
        if(Input.keyClick(KeyCodes.ENTER)) {
        	Block.makeBlocks(blockCount);
        	Ball.makeBalls(ballCount);
        }
        
        // camera translation
        if(Input.keyDown(KeyCodes.LEFT)) {Camera.move((int)(-5/Camera.zoom),0);}
        if(Input.keyDown(KeyCodes.RIGHT)) {Camera.move((int)(5/Camera.zoom),0);}
        if(Input.keyDown(KeyCodes.UP)) {Camera.move(0,(int)(-5/Camera.zoom));}
        if(Input.keyDown(KeyCodes.DOWN)) {Camera.move(0,(int)(5/Camera.zoom));}

        // zoom
        if(Input.keyClick(KeyCodes.EQUALS)) {Camera.zoom+=0.5;}
        if(Input.keyClick(KeyCodes.MINUS)) {Camera.zoom-=0.5;}

        // rotation
        if(Input.keyDown(KeyCodes.Q)) {Camera.angle-=0.01;}
        if(Input.keyDown(KeyCodes.E)) {Camera.angle+=0.01;}
    }	
    
    @Override
    public void absoluteDraw() {}
}