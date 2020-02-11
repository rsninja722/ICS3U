package graphics;

/* 2020.02.07
 * James N
 * Mandelbrot
 * Mandelbrot zooming adapted from https://rsninja.dev/notGames/mandelbrot/mandelbrot.html
 * 
 */


import hsa2.GraphicsConsole;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Mandelbrot {

	public static void main(String[] args) {
		new Mandelbrot();
	}
	
	GraphicsConsole gc = new GraphicsConsole (800,800, "HSA2 Graphics");
	BufferedImage img;
	
	double cw = 800;
	double ch = 800;
	
	double realMin = -2;
	double realMax = 1;
	double imgnryMin = -1.5;
	double imgnryMax = imgnryMin+(realMax-realMin)*ch/cw;

	double realFactor = (realMax-realMin)/(cw-1);
	double imgnryFactor = (imgnryMax-imgnryMin)/(ch-1);
	
	double zoom = 0.5;
	
	Mandelbrot(){		
		//do all drawing here
		
		
		img = new BufferedImage(800,800, BufferedImage.TYPE_INT_RGB);
		
		// loop
		while(true) {
			Graphics2D g2 = img.createGraphics();	
			
			redraw();
			
			int i=0;
			for(int y=0;y<ch;y++) {
				double imgnryC = imgnryMax - y*imgnryFactor;
				for(int x=0;x<cw;x++) {
					double realC = realMin + x*realFactor;
					
					double Z_re = realC, Z_im = imgnryC;
		            boolean isInside = true;
		            int n;
		            for(n=0; n<205; ++n)
		            {
		                double Z_re2 = Z_re*Z_re, Z_im2 = Z_im*Z_im;
		                if(Z_re2 + Z_im2 > 4)
		                {
		                    isInside = false;
		                    break;
		                }
		                Z_im = 2*Z_re*Z_im + imgnryC;
		                Z_re = Z_re2 - Z_im2 + realC;
		            }
		            
			        int c;
		            if(isInside) {
		            	c = new Color(0,0,0).getRGB();
		            } else {
		            	c = new Color(50,50+n,50+n).getRGB();
		            }
					
					// set pixel
					img.setRGB(x, y, c);
				}
			}
			gc.drawImage(img, 0, 0);
			
		   
		   
		}
		
	}
	
	void redraw() {
	    zoom = -(realMin-realMax)/50;

	    realMin += zoom*0.17;
	    realMax  -= zoom*0.83;
	    imgnryMin += zoom*0.5;
	    imgnryMax = imgnryMin+(realMax-realMin)*ch/cw;

	    realFactor = (realMax-realMin)/(cw-1);
	    imgnryFactor = (imgnryMax-imgnryMin)/(ch-1);
	}
}