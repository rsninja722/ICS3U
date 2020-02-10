package graphics;

/* 2020.02.07
 * James N
 * GraphicsIMG
 * makes moving pattern of rgb by setting rgb of a bufferd image
 * 
 */


import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GraphicsIMG {

	public static void main(String[] args) {
		new GraphicsIMG();
	}
	
	GraphicsConsole gc = new GraphicsConsole (1000,800, "HSA2 Graphics");
	BufferedImage img;
	
	GraphicsIMG(){		
		//do all drawing here
		
		// offsets to animate
		int count = 0;
		int yoff = 0;
		int xoff = 0;
		
		img = new BufferedImage(1000,800, BufferedImage.TYPE_INT_RGB);
		
		// loop
		while(true) {
			// time at the start of drawing
			long lastTime = System.nanoTime();
			
			Graphics2D g2 = img.createGraphics();	
			// go through every pixel
			for(int y=0;y<800;y++) {
				for(int x=0;x<1000;x++) {
					
					// set color based on position and offsets
					int c = new Color(((xoff+x)*2)%255,(yoff+y)%255,count%255).getRGB();
					
					// set pixel
					img.setRGB(x, y, c);
			
					// change offsets
					count+=1;
				}
				yoff+=1;
			}
			xoff+=1;
			gc.drawImage(img, 0, 0);
			
			// calculate fps
			double fps = 1000000000.0 / (System.nanoTime() - lastTime); 
            lastTime = System.nanoTime();
            
            System.out.print("fps: ");
            System.out.println(fps);

		}
	}
}