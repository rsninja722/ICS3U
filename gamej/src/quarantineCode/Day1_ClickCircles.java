package quarantineCode;

import java.awt.Color;
import java.util.ArrayList;

import game.*;
import game.drawing.Draw;

public class Day1_ClickCircles extends GameJava {
	
	class Circle {
		// position
		int x;
		int y;
		// current step in animation
		int animationStep = 0;
		// total animation steps of this circle
		int animationSteps;
		// what angle to start drawing arc from
		int startAngle;
		// multiplier for angle 1 or -1
		int direction;
		// color of this circle
		Color color;
		
		public Circle(int x,int y) {
			// set position
			this.x = x;
			this.y = y;
			// randomize start angle, animation speed, direction, and color
			this.startAngle = Utils.rand(0, 359);
			this.animationSteps = Utils.rand(20, 70);
			this.direction = Utils.rand(0, 1) == 0 ? -1 : 1;
			this.color = Color.getHSBColor((float) Utils.rand(0, 100) / 100, 1.0f, 0.8f);
		}
		
		public void update() {
			// step animation if not complete
			if(this.animationStep < animationSteps) {
				this.animationStep++;
			}
		}
		
		public void draw() {
			// draw an arc with an angle and size depending on how far through the animation this is
			Draw.setColor(this.color);
			double progress = (double)this.animationStep / (double)this.animationSteps;
			Draw.arc(this.x, this.y, 25 + (int)(progress * 25.0), this.startAngle, (int)(progress * 360.0) * this.direction);
		}
	}
	
	// arrayList of circles
	ArrayList<Circle> circles;
	
	public Day1_ClickCircles() {
		// make window
        super(800, 600, 60, 60);
        // initialize circle list
        circles = new ArrayList<Day1_ClickCircles.Circle>(0);
        // turn antialiasing on
        Draw.antialiasing = true;
        // set title
        GameJava.frameTitle = "cick somewhere on the window to add circles";
        // start game
        LoopManager.startLoops(this);
	}

	public static void main(String[] args) {
		new Day1_ClickCircles();
	}
	
	@Override
    public void update() {
		// add new circle at mouse position on click
		if(Input.mouseClick(0)) {
			circles.add(new Circle((int)Input.mousePos.x,(int)Input.mousePos.y));
		}

		// update circle animations
		for(int i=0;i<circles.size();i++) {
			circles.get(i).update();
		}
		
		// put stuff in debug(f3) for fun
		Utils.putInDebugMenu("Circle Count", circles.size());
	}
	
	@Override
    public void draw() {
		// set background
		Draw.setColor(new Color(25,25,25));
		Draw.rect(gw/2,gh/2,gw,gh);
		
		// draw circles
		for(int i=0;i<circles.size();i++) {
			circles.get(i).draw();
		}
	}
	
	@Override
    public void absoluteDraw() {}

}
