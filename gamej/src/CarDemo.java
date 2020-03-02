import game.*;
import game.drawing.*;
import game.physics.*;
import racing.Car;

public class CarDemo extends GameJava {
	
	public static int[][] track = {{0,0},{0,-1},{0,-2},{-1,-2},{-2,-2},{-2,-3},{-2,-4},{-3,-4},{-4,-4},{-4,-3},{-4,-2},{-4,-1},{-4,0},{-4,1},{-4,2},{-3,2},{-2,2},{-2,1},{-1,1},{0,1}};
	public static double[][] bricks = {{-0.5,0},{-1,0},{-1.5,0},{-2,0},{-2.5,0},{-2.5,-0.5},{-2.5,-1},{-2.5,-1.5},{-2.5,-2},{-2.5,-2.5},{-2.5,0.5},{-2.5,1},{-2.5,1.5}};
    
	Point[] tilePos;
	Rect[] trackRects;
	Rect[] brickRects;
	
	Car car;
	
	public CarDemo() {		
        super(600, 600, 60, 60);
        if (track == null) System.out.println("T is null");
	}
	
	public static void main(String[] args) throws InterruptedException {
        frameTitle = "car demo";
        new CarDemo();   
    }
	
	public void setUp() {
		// generate track
		System.out.println(track);
        tilePos = new Point[track.length * 64];
        trackRects = new Rect[track.length];
        int tilePosIndex = 0;
        for(int i=0;i<track.length;i++) {
            int xCache = track[i][0] * 128;
            int yCache = track[i][1] * 128;
            for(int y=0;y<8;y++) {
                for(int x=0;x<8;x++) {
                	tilePos[tilePosIndex] = new Point(xCache+(x*16),yCache+(y*16));
                	tilePosIndex++;
                }   
            }

            trackRects[i] = new Rect(xCache+56,yCache+56,128,128);
        }
        
        // generate wall
        brickRects = new Rect[bricks.length];
        for(int i=0;i<bricks.length;i++) {
            double xCache = bricks[i][0] * 128;
            double yCache = bricks[i][1] * 128;

            brickRects[i] = new Rect((int)xCache,(int)yCache,64,64);
        }
        
        car = new Car();
	}
	
	public void draw() {
	    int xlim = (int) (Math.round((car.x-500)/100)*100);
	    int ylim = (int) (Math.round((car.y-500)/100)*100);
	    for(int x=xlim;x<xlim+1000;x+=100) {
	        for(int y=ylim;y<ylim+1000;y+=100) {
	        	Draw.image(Sprites.get("grass"),x*2,y*2,0,2);
	        }
	    }

	    for(int i=0;i<tilePos.length;i++) {
	        Draw.image(Sprites.get("road"),tilePos[i].x*2,tilePos[i].y*2,0,2);
	    }
	    for(int i=0;i<brickRects.length;i++) {
	        Draw.image(Sprites.get("bricks"),brickRects[i].x*2,brickRects[i].y*2,0,2);
	    }
	}

	public void update() {
		Camera.centerOn((int)car.x,(int)car.y);
	    Camera.angle = (float) (car.angle+Math.PI);

	    boolean anyInput=false;
	    double div = car.turningSpeed/(Math.abs(car.speed)/8<1?1:Math.abs(car.speed)/8)== Double.POSITIVE_INFINITY?0:car.turningSpeed/(Math.abs(car.speed)/8<1?1:Math.abs(car.speed)/8);
	    double multi = car.turningSpeed*Math.abs(car.speed);
	    if(Input.keyDown(KeyCodes.W) || Input.keyDown(KeyCodes.UP)) {
	        car.speed += car.acceleration;
	        anyInput=true;
	    }
	    if(Input.keyDown(KeyCodes.S) || Input.keyDown(KeyCodes.DOWN)) {
	        if(car.speed>0) {
	            car.speed -= car.acceleration*2;
	        } else {
	            car.speed -= car.acceleration/2;
	        }
	        anyInput=true;
	    }
	    if(Input.keyDown(KeyCodes.A) || Input.keyDown(KeyCodes.LEFT)) {
	        if(car.speed<1&&car.speed>-1) {
	            car.angle += multi;
	        } else {
	            car.angle += div;
	        }
	    }
	    if(Input.keyDown(KeyCodes.D) || Input.keyDown(KeyCodes.RIGHT)) {
	        if(car.speed<1&&car.speed>-1) {
	            car.angle -= multi;
	        } else {
	            car.angle -= div;
	        }
	    }
	    if(!anyInput) {
	        if(car.speed>0) {
	            car.speed -= car.acceleration*0.5;
	        }
	        if(car.speed<0) {
	            car.speed += car.acceleration/2;
	        }
	        if(car.speed<0.05&&car.speed>-0.05) {car.speed=0;}
	    }
	    car.maxSpeed=2;
	    Point carPoint = new Point((int)car.x,(int)car.y);
	    for(int i=0;i<trackRects.length;i++) {
	        if(Physics.rectpoint(trackRects[i],carPoint)) {
	            car.maxSpeed=4;
	        }
	    }
	    if(car.speed>car.maxSpeed) {
	        car.speed -= car.acceleration*4;
	    }

	    Point vel = new Point((int)Math.sin(car.angle),(int)Math.cos(car.angle));
	    
	    for(int i=0;i<brickRects.length;i++) {
	        if(Physics.rectrect(brickRects[i],car.carRect)) {
	            car.speed*=-1;
	            car.x += vel.x * car.speed;
	            car.y += vel.y * car.speed;
	            car.speed=0;
	            break;
	        }
	    }
	    car.x += vel.x * car.speed;
	    car.y += vel.y * car.speed;
	    
	    car.carRect.x = (int) car.x;
	    car.carRect.y = (int) car.y;
    }	
    
    public void absoluteDraw() {
    	Draw.image(Sprites.get("car"), gw/2, gh/2, 0, 2);
    }
}