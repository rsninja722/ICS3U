package bouncingBalls;

import java.awt.Color;

import game.*;
import game.drawing.*;
import game.physics.*;
import bouncingBalls.Ball;

public class BouncingBall extends GameJava {

    static Ball[] balls = new Ball[250];
    int ballIndex = 0;
    
    static final double entropy = 0.95;
    static final double gravity = 0.1;

    public BouncingBall() {
        super(800, 600, 60, 60);
        for(int i=0;i<balls.length;i++) {
            balls[i] = new Ball(i);
        }
        LoopManager.startLoops(this);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "bouncing balls";
        new BouncingBall();   
    }
	
	@Override
	public void draw() {
		Draw.setColor(Color.RED);
        for(int i=0;i<balls.length;i++) {
            Draw.circle(balls[i].collider);
        }
        Draw.setColor(Color.BLUE);
        Draw.rect(gw/2,0,gw,1);
        Draw.rect(gw/2,gh,gw,1);
        Draw.rect(0,gh/2,1,gh);
        Draw.rect(gw,gh/2,1,gh);
        
    }
    
    public static boolean hittingOtherBalls(Circle circle,int index) {
        for(int j=0;j<balls.length;j++) {
            if(j!=index && balls[j] != null) {
                if(Physics.circlecircle(circle, balls[j].collider)) {
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public void update() {
        for(int i=0;i<balls.length;i++) {
			balls[i].collidedThisFrame = false;
			
			double oldVelX = balls[i].velocity.x;
			balls[i].collider.x += balls[i].velocity.x;
			
			if(balls[i].ifOnEdgeBounce(0,0, gw, gh) || balls[i].bounceOffOtherBalls(balls, i)) {
				balls[i].collider.x -= oldVelX;
			}
			
			double oldVelY = balls[i].velocity.y;
			balls[i].collider.y += balls[i].velocity.y;
			
			if(balls[i].ifOnEdgeBounce(0,0, gw, gh) || balls[i].bounceOffOtherBalls(balls, i)) {
				balls[i].collider.y -= oldVelY;
			}
			
			balls[i].fall();
			
//			balls[i].velocity.x *= 0.99;
//			balls[i].velocity.y *= 0.99;
			
			balls[i].velocity.y += gravity;
			
			
		}
        
        if(Input.keyClick(KeyCodes.ENTER)) {
        	for(int i=0;i<balls.length;i++) {
                balls[i] = new Ball(i);
            }
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