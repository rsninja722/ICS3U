package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public JPanel p;
	
	public JFrame f;
	
	private BufferedImage buffer;
	
	public Graphics canvas;
	
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
		canvas.fillRect(x - (w/2), y - (h/2), w, h);
	}
	
	public void rect(int x,int y,int w,int h, Color c) {
		canvas.setColor(c);
		canvas.fillRect(x, y, w, h);
	}
	
	public void circle(int x, int y, int r, Color c) {
		canvas.setColor(c);
		canvas.fillOval(x - (r / 2), y - (r / 2), r, r);
	}
	
	public void circle(int x, int y, int r) {
		canvas.fillOval(x - (r / 2), y - (r / 2), r, r);
	}
	
	

	public void render() {
		// f.repaint();
		Graphics g = p.getGraphics();
		g.drawImage(buffer,0,0,this);
		canvas.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
	}
	
}
