package graphics;

/* 2020.02.07
 * James N
 * Graphics1
 * makes moving pattern of rgb by setting 1 x 1 rectangles
 * 
 */

import hsa2.GraphicsConsole;
import java.awt.Color;

public class Graphics1 {

	public static void main(String[] args) {
		new Graphics1();
	}
	
	GraphicsConsole gc = new GraphicsConsole (1000,800, "HSA2 Graphics");

	Graphics1(){		
		//do all drawing here
		
		// offsets to animate
		int count = 0;
		int yoff = 0;
		int xoff = 0;
		
		
		
		// loop
		while(true) {
			
				
			// go through every pixel
			for(int y=0;y<800;y++) {
				for(int x=0;x<1000;x++) {
					
					// set color based on position and offsets
					gc.setColor(new Color(((xoff+x)*2)%255,(yoff+y)%255,count%255));
					
					// draw pixel
					gc.fillRect(x,y,1,1);
			
					// change offsets
					count+=1;
				}
				yoff+=2;
			}
			xoff+=4;
			
		}
	}
}