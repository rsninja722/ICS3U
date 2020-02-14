package game.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.drawing.Camera;

import game.GameJava;

public class Drawing extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public JPanel p;
	
	public JFrame f;
	
	private BufferedImage buffer;
	private BufferedImage buffer1;
	private BufferedImage buffer2;
	
	public Graphics2D canvas;
	
	// camera to move view
	public Camera camera = new Camera();
	
	// drawing offset
	private int difx = 0;
	private int dify = 0;
	
	// 0 = translations, 1 = adding scaling, 2 = adding rotations
	private int drawingMode = 0;
	
	public Drawing()  {
        System.out.println("constructing Drawing");
        
        p = new JPanel();
        
        buffer = new BufferedImage(GameJava.gw,GameJava.gh,BufferedImage.TYPE_4BYTE_ABGR);
        
        canvas = buffer.createGraphics();
        
        p.setBackground(Color.BLUE);
        
        f = new JFrame("wow");
        f.add(p);
        f.setSize(GameJava.gw, GameJava.gh);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(p, BorderLayout.CENTER);
        f.setVisible(true);
        
        System.out.println("initialized");
	}
	
	public void setColor(Color c) {
		canvas.setColor(c);
	}
	
	public void rect(int x,int y,int w,int h) {
		canvas.fillRect(x - (w/2) + camera.x + difx, y - (h/2) + camera.y + dify, w, h);
	}
	
	public void rect(int x,int y,int w,int h, Color c) {
		canvas.setColor(c);
		canvas.fillRect(x - (w/2) + camera.x + difx, y - (h/2) + camera.y + dify, w, h);
	}
	
	public void circle(int x, int y, int r, Color c) {
		canvas.setColor(c);
		canvas.fillOval(x - (r/2) + camera.x + difx, y - (r/2) + camera.y + dify, r, r);
	}
	
	public void circle(int x, int y, int r) {
		canvas.fillOval(x - (r/2) + camera.x + difx, y - (r/2) + camera.y + dify, r, r);
	}
	
	public void imgIgnoreCutoff(Sprite spr,int x,int y,double angle,double sx,double sy) {
        AffineTransform t = canvas.getTransform();
        
        canvas.translate(Math.round(x+camera.x+difx), Math.round(y+camera.y+dify)); 
        canvas.scale(sx, sy);
        canvas.rotate(angle);
        
        canvas.drawImage(spr.img,Math.round(-spr.width/2),Math.round(-spr.height/2),null);
        
        canvas.setTransform(t);
	}
	
	public void imgIgnoreCutoff(Sprite spr,int x,int y) {
        canvas.drawImage(spr.img,Math.round(-spr.width/2),Math.round(-spr.height/2),null);
	}
	
	public void preRender() {
		switchDrawMode();
	}
	
	// sets the drawing mode based on what transformations are needed
	private void switchDrawMode() {
//	    if(camera.zoom<0.1f) {
//	    	camera.zoom=0.1f;
//	    	}
//	    if(camera.angle!=0) {
//	    	drawingMode=2;
//	    } else if(camera.zoom!=1) {
//	    	drawingMode=1;
//	    } else {
//	    	drawingMode=0;
//	    }
	    switch (drawingMode) {
	        case 0: canvas = buffer.createGraphics(); break;
	        case 1: canvas = buffer1.createGraphics(); break;
	        case 2: canvas = buffer2.createGraphics(); break;
	    }
	}
	
	// draws buffers onto the screen
	public void render() {
		// f.repaint();
		Graphics2D g2 = (Graphics2D) p.getGraphics();
//		g2.rotate(camera.angle, GameJava.gw/2,GameJava.gh/2);
//		g2.drawImage(buffer,0,0,this);
//		g2.rotate(-camera.angle, GameJava.gw/2,GameJava.gh/2);
//		camera.angle += 0.01;
		
		
		if(drawingMode == 0) {
			g2.drawImage(buffer,0,0,this);
		} else if(drawingMode == 1) {
			
		} else if(drawingMode == 2) {
			
		}
		
		canvas.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
	}
	
}
