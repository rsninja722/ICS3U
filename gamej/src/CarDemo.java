import java.awt.Color;

import game.*;
import game.drawing.*;
import game.physics.*;

public class CarDemo extends GameJava {
    
    // positons of tracks and bricks
	public static int[][] track = {{0,0},{0,-1},{0,-2},{-1,-2},{-2,-2},{-2,-3},{-2,-4},{-3,-4},{-4,-4},{-4,-3},{-4,-2},{-4,-1},{-4,0},{-4,1},{-4,2},{-3,2},{-2,2},{-2,1},{-1,1},{0,1}};
	public static double[][] bricks = {{-0.5,0},{-1,0},{-1.5,0},{-2,0},{-2.5,0},{-2.5,-0.5},{-2.5,-1},{-2.5,-1.5},{-2.5,-2},{-2.5,-2.5},{-2.5,0.5},{-2.5,1},{-2.5,1.5}};
    
	Point[] tilePos;
	Rect[] trackRects;
	Rect[] brickRects;
	
    Car car;
    
    // car object
    public class Car {
        public Circle carCirc = new Circle(0,0,32);
        public double x = 100;
        public double y = 0;
        public int w = 32;
        public int h = 32;
        public double angle = -Math.PI;
        public double speed = 0;
        public double maxSpeed = 6;
        public double acceleration = 0.04;
        public double turningSpeed = 0.02;
        
        public Car() {}
    }
	
	public CarDemo() {		
        super(600, 600, 60, 60);
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "car demo";
        new CarDemo();   
    }
    
    // create track and wall
	public void setUp() {
		// generate track
		System.out.println(track);
        tilePos = new Point[track.length * 64];
        trackRects = new Rect[track.length];
        int tilePosIndex = 0;
        for(int i=0;i<track.length;i++) {
            int xCache = track[i][0] * 256;
            int yCache = track[i][1] * 256;
            for(int y=0;y<8;y++) {
                for(int x=0;x<8;x++) {
                	tilePos[tilePosIndex] = new Point(xCache+(x*32),yCache+(y*32));
                	tilePosIndex++;
                }   
            }

            trackRects[i] = new Rect(xCache+112,yCache+122,256,256);
        }
        
        // generate wall
        brickRects = new Rect[bricks.length];
        for(int i=0;i<bricks.length;i++) {
            double xCache = bricks[i][0] * 256;
            double yCache = bricks[i][1] * 256;

            brickRects[i] = new Rect((int)xCache,(int)yCache,128,128);
        }
        
        car = new Car();
	}
	
	public void draw() {
        // decide where to draw grass
        int edge = Math.max(gw,gh);
	    int xlim = (int) (Math.round((car.x-edge/2)/200)*200);
	    int ylim = (int) (Math.round((car.y-edge/2)/200)*200);
	    for(int x=xlim-400;x<xlim+edge+400;x+=200) {
	        for(int y=ylim-400;y<ylim+edge+400;y+=200) {
	        	Draw.image(Sprites.get("grass"),x,y,0,2);
	        }
	    }

        // road
        Draw.setColor(new Color(0.0f,0.0f,1.0f,0.5f));
	    for(int i=0;i<tilePos.length;i++) {
            Draw.image(Sprites.get("road"),(int)tilePos[i].x,(int)tilePos[i].y,0,2);
            if(Utils.debugMode) {
                Draw.rect((int)tilePos[i].x,(int)tilePos[i].y,32,32);
            }
        }

        // wall
        Draw.setColor(new Color(0.0f,1.0f,0.0f,0.5f));
	    for(int i=0;i<brickRects.length;i++) {
            Draw.image(Sprites.get("bricks"),(int)brickRects[i].x,(int)brickRects[i].y,0,2);
            if(Utils.debugMode) {
                Draw.rect((int)brickRects[i].x,(int)brickRects[i].y,128,128);
            }
        }

        // player hit box
        if(Utils.debugMode) {
            Draw.setColor(new Color(1.0f,0.0f,0.0f,0.5f));
            Draw.circle(car.carCirc);
        }
	}

	public void update() {
        // reset
        if(Input.keyClick(KeyCodes.ENTER)) {
            car.x = 100;
            car.y = 0;
            car.speed = 0;
            car.angle = 0;
        }
        
        // move camera
		Camera.centerOn((int)car.x,(int)car.y);
	    Camera.angle = (float) (car.angle+Math.PI);

        boolean anyInput=false;
        // what? thanks me from 6 months ago
	    double div = car.turningSpeed/(Math.abs(car.speed)/8<1?1:Math.abs(car.speed)/8)== Double.POSITIVE_INFINITY?0:car.turningSpeed/(Math.abs(car.speed)/8<1?1:Math.abs(car.speed)/8);
        double multi = car.turningSpeed*Math.abs(car.speed);
        
        // forwards
	    if(Input.keyDown(KeyCodes.W) || Input.keyDown(KeyCodes.UP)) {
	        car.speed += car.acceleration;
	        anyInput=true;
        }
        
        // backwards
	    if(Input.keyDown(KeyCodes.S) || Input.keyDown(KeyCodes.DOWN)) {
	        if(car.speed>0) {
	            car.speed -= car.acceleration*2;
	        } else {
	            car.speed -= car.acceleration/2;
	        }
	        anyInput=true;
        }
        
        // left
	    if(Input.keyDown(KeyCodes.A) || Input.keyDown(KeyCodes.LEFT)) {
	        if(car.speed<1&&car.speed>-1) {
	            car.angle += multi;
	        } else {
	            car.angle += div;
	        }
        }
        
        // right
	    if(Input.keyDown(KeyCodes.D) || Input.keyDown(KeyCodes.RIGHT)) {
	        if(car.speed<1&&car.speed>-1) {
	            car.angle -= multi;
	        } else {
	            car.angle -= div;
	        }
        }

        // friction
	    if(!anyInput) {
	        if(car.speed>0) {
	            car.speed -= car.acceleration*0.5;
	        }
	        if(car.speed<0) {
	            car.speed += car.acceleration/2;
	        }
	        if(car.speed<0.05&&car.speed>-0.05) {car.speed=0;}
        }
        
        // if on road, max speed is higher
	    car.maxSpeed=2;
	    Point carPoint = new Point(car.x,car.y);
	    for(int i=0;i<trackRects.length;i++) {
	        if(Physics.rectpoint(trackRects[i],carPoint)) {
	            car.maxSpeed=6;
	        }
        }
        
        // smoothly slow down if above max speed
	    if(car.speed>car.maxSpeed) {
	        car.speed -= car.acceleration*4;
	    }

        // if hitting a wall, stop moving
	    Point vel = new Point(Math.sin(car.angle),Math.cos(car.angle));  
	    for(int i=0;i<brickRects.length;i++) {
	        if(Physics.circlerect(car.carCirc,brickRects[i])) {
	            car.x -= vel.x * car.speed * 2;
	            car.y -= vel.y * car.speed * 2;
	            car.speed=0;
	            break;
	        }
        }
        // move
	    car.x += vel.x * car.speed;
	    car.y += vel.y * car.speed;
        
        // update hitbox
	    car.carCirc.x = car.x;
        car.carCirc.y = car.y;
        // put stuff in debugger
        Utils.putInDebugMenu("speed", car.speed);
        Utils.putInDebugMenu("max", car.maxSpeed);
        Utils.putInDebugMenu("angle", car.angle);
        Utils.putInDebugMenu("velx", vel.x);
        Utils.putInDebugMenu("vely", vel.y);
        Utils.putInDebugMenu("x", car.x);
        Utils.putInDebugMenu("y", car.y);
    }	
    
    // draw car
    public void absoluteDraw() {
    	Draw.image(Sprites.get("car"), gw/2, gh/2, 0, 2);
    }
}