package game.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
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

    public JPanel panel;

    public JFrame f;

    private BufferedImage buffer; // final buffer
    private BufferedImage buffer1; // buffer used for scaling
    private BufferedImage buffer2; // buffer used for rotation

    // graphics2D of the current buffer being used to draw
    public Graphics2D canvas;

    // camera to move view
    public Camera camera = new Camera();

    // drawing offset
    private int difx = 0;
    private int dify = 0;

    // 0 = translations, 1 = adding scaling, 2 = adding rotations
    private int drawingMode = 0;

    // limit for when to stop drawing
    private int drawLimitLeft;
    private int drawLimitRight;
    private int drawLimitTop;
    private int drawLimitBottom;

    private int maxCvsSize = 1000;//Math.max(gw, gh);
    private int sizeDif = 100;//maxCvsSize - Math.min(gw, gh);

    public boolean absoluteDraw = false;

    public GameJava gameJavaInstance;

    // creates window
    public Drawing() {
        System.out.println("constructing Drawing");

        panel = new JPanel();

        gameJavaInstance = GameJava.instance;

        buffer = new BufferedImage(gameJavaInstance.gw, gameJavaInstance.gh, BufferedImage.TYPE_4BYTE_ABGR);

        canvas = buffer.createGraphics();

        panel.setBackground(Color.BLUE);

        f = new JFrame("wow");
        f.add(panel);
        f.setSize(gameJavaInstance.gw, gameJavaInstance.gh);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(panel, BorderLayout.CENTER);
        f.setVisible(true);

        System.out.println("initialized");
    }

    // sets color of drawing
    public void setColor(Color c) {
        canvas.setColor(c);
    }

    // draws rectangle centered on x,y
    public void rect(int x, int y, int w, int h) {
        canvas.fillRect(x - (w / 2) + camera.x + difx, y - (h / 2) + camera.y + dify, w, h);
    }

    // draws rectangle centered on x,y with a certain color
    public void rect(int x, int y, int w, int h, Color c) {
        canvas.setColor(c);
        canvas.fillRect(x - (w / 2) + camera.x + difx, y - (h / 2) + camera.y + dify, w, h);
    }

    // draws circle centered on x,y
    public void circle(int x, int y, int r) {
        canvas.fillOval(x - (r / 2) + camera.x + difx, y - (r / 2) + camera.y + dify, r, r);
    }

    // draws circle centered on x,y with a certain color
    public void circle(int x, int y, int r, Color c) {
        canvas.setColor(c);
        canvas.fillOval(x - (r / 2) + camera.x + difx, y - (r / 2) + camera.y + dify, r, r);
    }

    // draws an image centered on x,y with an angle, scaled on the x and y axis.
    // Will not prevent drawing if off screen
    public void imgIgnoreCutoff(Sprite spr, int x, int y, double angle, double sx, double sy) {
        AffineTransform t = canvas.getTransform();

        canvas.translate(Math.round(x + camera.x + difx), Math.round(y + camera.y + dify));
        canvas.scale(sx, sy);
        canvas.rotate(angle);

        canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

        canvas.setTransform(t);
    }

    // draws an image centered on x,y. Will not prevent drawing if off screen
    public void imgIgnoreCutoff(Sprite spr, int x, int y) {
        canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);
    }

    // draws an image centered on x,y with an angle, scaled on the x and y axis
    public void img(Sprite spr, int x, int y, double angle, double sx, double sy) {
        int half = spr.drawLimit * Math.max((int)sx,(int)sy);
        if ((x + half > drawLimitLeft && x - half < drawLimitRight && y + half > drawLimitTop && y - half < drawLimitBottom) || absoluteDraw) {
            AffineTransform t = canvas.getTransform();

            canvas.translate(Math.round(x + camera.x + difx), Math.round(y + camera.y + dify));
            canvas.scale(sx, sy);
            canvas.rotate(angle);

            canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

            canvas.setTransform(t);
        }
    }

    // draws an image centered on x,y
    public void img(Sprite spr, int x, int y) {
        int half = spr.drawLimit;
        if ((x + half > drawLimitLeft && x - half < drawLimitRight && y + half > drawLimitTop && y - half < drawLimitBottom) || absoluteDraw) {
            canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);
        }
    }

    public void preRender() {
        switchDrawMode();

        int limitModifier = 0;
        // if(drawingMode==2) {limitModifier=canvases.buffer2cvs.width-maxCvsSize;}
        drawLimitLeft = -camera.x - (drawingMode == 2 ? sizeDif : 0) - limitModifier;
        drawLimitRight = -camera.x + maxCvsSize + (drawingMode == 2 ? sizeDif : 0) + limitModifier;
        drawLimitTop = -camera.y - (drawingMode == 2 ? sizeDif : 0) - limitModifier;
        drawLimitBottom = -camera.y + maxCvsSize + (drawingMode == 2 ? sizeDif : 0) + limitModifier;
    }

    // sets the drawing mode based on what transformations are needed
    private void switchDrawMode() {
        // if(camera.zoom<0.1f) {
        // camera.zoom=0.1f;
        // }
        // if(camera.angle!=0) {
        // drawingMode=2;
        // } else if(camera.zoom!=1) {
        // drawingMode=1;
        // } else {
        // drawingMode=0;
        // }
        switch (drawingMode) {
        case 0:
            canvas = buffer.createGraphics();
            break;
        case 1:
            canvas = buffer1.createGraphics();
            break;
        case 2:
            canvas = buffer2.createGraphics();
            break;
        }
    }

    // draws buffers onto the screen
    public void render() {
        // f.repaint();
        Graphics2D g2 = (Graphics2D) panel.getGraphics();
        

        
        camera.angle += 0.01;

        if (drawingMode == 0) {
            g2.drawImage(buffer, 0, 0, this);
        } else if (drawingMode == 1) {

        } else if (drawingMode == 2) {
            AffineTransform t = g2.getTransform();
            g2.translate( gameJavaInstance.gw/2,gameJavaInstance.gh/2);
            g2.rotate(camera.angle);
            g2.scale(2,2);
            g2.drawImage(buffer,-gameJavaInstance.gw/2,-gameJavaInstance.gh/2,this);
            g2.setTransform(t);
        }

        // f.pack();
//        System.out.println(p.getWidth());

        canvas.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }


}
