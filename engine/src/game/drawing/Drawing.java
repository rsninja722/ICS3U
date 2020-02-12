package game.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
	
	public Graphics canvas;
	
	// camera to move view
	public Camera camera = new Camera();
	
	// drawing offset
	private int difx = 0;
	private int dify = 0;
	
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
	    Image img = spr.img;
//	    if(angle===0&&sx===1&&sy===1) {
//	        curCtx.drawImage(spr,Math.round(x+camera.x+difx-(spr.width/2)),Math.round(y+camera.y+dify-(spr.height/2)));
//	    } else {
//	        curCtx.setTransform(sx, 0, 0, sy, Math.round(x+camera.x+difx), Math.round(y+camera.y+dify));
//	        curCtx.rotate(angle);
//	        curCtx.drawImage(spr,Math.round(-spr.width/2),Math.round(-spr.height/2));
//	        curCtx.setTransform(1, 0, 0, 1, 0, 0);
//	    }
	}
	
	
	
	public void render() {
		// f.repaint();
		Graphics2D g2 = (Graphics2D) p.getGraphics();
		g2.rotate(camera.angle, GameJava.gw/2,GameJava.gh/2);
		g2.drawImage(buffer,0,0,this);
		g2.rotate(-camera.angle, GameJava.gw/2,GameJava.gh/2);
		camera.angle += 0.01;
		canvas.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
	}
	
}
