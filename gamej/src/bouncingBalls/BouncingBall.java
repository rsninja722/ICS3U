package bouncingBalls;

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

    public BouncingBall() {
        super(800, 600, 60, 60);
        Block.makeBlocks(blockCount);
        Ball.makeBalls(ballCount);
        Draw.alphaBetweenFrames = 0.3f; 
        LoopManager.startLoops(this);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "bouncing balls";
        new BouncingBall();   
    }
	
	@Override
	public void draw() {
		Draw.setColor(new Color(25,25,25));
		Draw.rect(gw/2,gh/2,gw,gh);
		if(Draw.canvas != null) {
			Draw.canvas.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
		}
		Draw.setLineWidth(3);
		Ball.drawBalls();
        Draw.setColor(Color.BLUE);
        Block.drawBlocks();
        Draw.rect(gw/2,0,gw,1);
        Draw.rect(gw/2,gh,gw,1);
        Draw.rect(0,gh/2,1,gh);
        Draw.rect(gw,gh/2,1,gh);  
    }
    
    

	@Override
	public void update() {
		if(Utils.rand(0, 100) == 1) {
			Circle explo = new Circle(Utils.rand(0, gw),Utils.rand(gh-gh/4, gh),200);
			for (int i = 0; i < Ball.balls.length; i++) {
				if(Physics.circlecircle(Ball.balls[i].collider, explo)) {
					double ang = Utils.pointTo(Ball.balls[i].collider.x, Ball.balls[i].collider.y, explo.x, explo.y);
					Ball.balls[i].velocity = new Point(Math.sin(ang) * 10,Math.cos(ang) * 10);
				}
			}
			
		}
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
        	Block.makeBlocks(blockCount);
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