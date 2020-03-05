package bouncingBalls;

import game.*;
import game.drawing.Draw;
import game.physics.*;
import bouncingBalls.BouncingBall;

public class Ball {
	public static Ball[] balls;
    Circle collider;
    Point velocity;
    Double angle;
    int lastCollisionIndex = -1;
    boolean collidedThisFrame = false;
    

    static final double entropy = 0.95;
    static double gravity = 0.1;
    
    // constructor
    public Ball(int index) {
        int radius = Utils.rand(10,15);
        // prevent intersection with other balls
        collider = new Circle(Utils.rand(radius,GameJava.gw-radius),Utils.rand(radius,GameJava.gh-radius),radius);
        while(hittingOtherBalls(this,index)) {
            collider.x = Utils.rand(radius,(GameJava.gw-radius));
            collider.y = Utils.rand(radius,(GameJava.gh-radius));
        }
        angle = Math.toRadians(Utils.rand(0,359));
        velocity = new Point(Math.sin(angle),Math.cos(angle));
    }
    public Ball(double x,double y) {
    	 int radius = Utils.rand(10,15);
    	 collider = new Circle(x,y,radius);
    	 angle = Math.toRadians(90);
         velocity = new Point(Math.sin(angle),Math.cos(angle));
    }
    
    // generate
    public static void makeBalls(int count) {
    	balls = new Ball[count];
        for(int i=0;i<balls.length;i++) {
            balls[i] = new Ball(i);
        }
    }
    
    // draw
    public static void drawBalls() {
    	for(int i=0;i<balls.length;i++) {
    		if(balls[i] != null) {
    			Draw.circle(balls[i].collider);
    		}
        }
    }
    
    public boolean hittingOtherBalls(Ball circle,int index) {
        for(int j=0;j<balls.length;j++) {
            if(j!=index && balls[j] != null) {
                if(Physics.circlecircle(circle.collider, balls[j].collider)) {
                	lastCollisionIndex = j;
                    return true;
                }
            }
        }
        return false;
    }

    
    
    public static void moveBalls() {
    	for(int i=0;i<balls.length;i++) {
    		balls[i].move(i);
    	}
    }
    
    public void move(int index) {
    	double oldVelX = velocity.x;
    	collider.x += velocity.x;
    	if(bounceOfWallsX()) {
    		collider.x -= oldVelX;
    	}
    	if(hittingOtherBalls(this,index)) {
    		collider.x -= oldVelX;
    		velocity.x = 0;
    	}
    	
    	double oldVelY = velocity.y;
    	collider.y += velocity.y;
    	if(bounceOfWallsY()) {
    		collider.y -= oldVelY;
    	}
    	if(hittingOtherBalls(this,index)) {
    		collider.y -= oldVelY;
    		velocity.y *= 0.5;
    	}
    	
    	velocity.x *= 0.99;
    	velocity.y *= 0.99;
    	
    	if(lastCollisionIndex != -1) {
    		Circle last = balls[lastCollisionIndex].collider;
    		if(last.y > collider.y) {
    			if(Physics.dist(collider.x,collider.y,last.x,last.y) < (collider.r+last.r) * 1.5) { 
	    			if(last.x > collider.x) {
	    				velocity.x -= 0.1;
	    			} else {
	    				velocity.x += 0.1;
	    			}
    			}
    		}
    	}
    	
    	velocity.y += gravity;
    }
    
    public boolean bounceOfWallsX() {
    	if (collider.x - collider.r < 0 && velocity.x < 0) {
			velocity.x *= -1;
			lastCollisionIndex = -1;
			return true;
		}
		if (collider.x + collider.r > GameJava.gw && velocity.x > 0) {
			velocity.x *= -1;
			lastCollisionIndex = -1;
			return true;
		}
		return false;
    }
    public boolean bounceOfWallsY() {
		if (collider.y - collider.r < 0 && velocity.y < 0) {
			velocity.y *= -1;
			lastCollisionIndex = -1;
			return true;
		}
		if (collider.y + collider.r > GameJava.gh && velocity.y > 0) {
			velocity.y *= -1;
			lastCollisionIndex = -1;
			return true;
		}
		return false;
    }
    
}