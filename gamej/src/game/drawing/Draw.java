package game.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.GameJava;

public class Draw extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JPanel panel;

    public static JFrame frame;

    private static BufferedImage buffer; // final buffer
    private static BufferedImage buffer1; // buffer used for scaling
    private static BufferedImage buffer2; // buffer used for rotation

    // graphics2D of the current buffer being used to draw
    public static Graphics2D canvas;

    // drawing offset
    private static int difx = 0;
    private static int dify = 0;

    // 0 = translations, 1 = adding scaling, 2 = adding rotations
    public static int drawingMode = 0;

    // limit for when to stop drawing
    private static int drawLimitLeft;
    private static int drawLimitRight;
    private static int drawLimitTop;
    private static int drawLimitBottom;

    private static int maxCvsSize = 1000;//Math.max(gw, gh);
    private static int sizeDif = 100;//maxCvsSize - Math.min(gw, gh);

    public static boolean absoluteDraw = false;

    // creates window
    public static void init() {
        System.out.println("[Draw] initizlizing");

        panel = new JPanel();
        
        buffer = new BufferedImage(GameJava.gw, GameJava.gh, BufferedImage.TYPE_4BYTE_ABGR);

        canvas = buffer.createGraphics();

        panel.setBackground(Color.BLUE);

        frame = new JFrame("wow");
        frame.add(panel);
        frame.setSize(GameJava.gw, GameJava.gh);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // sets color of drawing
    public static void setColor(Color c) {
        canvas.setColor(c);
    }

    // draws rectangle centered on x,y
    public static void rect(int x, int y, int w, int h) {
        canvas.fillRect(x - (w / 2) + Camera.x + difx, y - (h / 2) + Camera.y + dify, w, h);
    }

    // draws rectangle centered on x,y with a certain color
    public static void rect(int x, int y, int w, int h, Color c) {
        canvas.setColor(c);
        canvas.fillRect(x - (w / 2) + Camera.x + difx, y - (h / 2) + Camera.y + dify, w, h);
    }

    // draws circle centered on x,y
    public static void circle(int x, int y, int r) {
        canvas.fillOval(x - (r / 2) + Camera.x + difx, y - (r / 2) + Camera.y + dify, r, r);
    }

    // draws circle centered on x,y with a certain color
    public static void circle(int x, int y, int r, Color c) {
        canvas.setColor(c);
        canvas.fillOval(x - (r / 2) + Camera.x + difx, y - (r / 2) + Camera.y + dify, r, r);
    }

    // draws an image centered on x,y with an angle, scaled on the x and y axis.
    // Will not prevent drawing if off screen
    public static void imgIgnoreCutoff(Sprite spr, int x, int y, double angle, double sx, double sy) {
        AffineTransform t = canvas.getTransform();

        canvas.translate(Math.round(x + Camera.x + difx), Math.round(y + Camera.y + dify));
        canvas.scale(sx, sy);
        canvas.rotate(angle);

        canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

        canvas.setTransform(t);
    }

    // draws an image centered on x,y. Will not prevent drawing if off screen
    public static void imgIgnoreCutoff(Sprite spr, int x, int y) {
        canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);
    }

    // draws an image centered on x,y with an angle, scaled on the x and y axis
    public static void img(Sprite spr, int x, int y, double angle, double sx, double sy) {
        int half = spr.drawLimit * Math.max((int)sx,(int)sy);
        if ((x + half > drawLimitLeft && x - half < drawLimitRight && y + half > drawLimitTop && y - half < drawLimitBottom) || absoluteDraw) {
            AffineTransform t = canvas.getTransform();

            canvas.translate(Math.round(x + Camera.x + difx), Math.round(y + Camera.y + dify));
            canvas.scale(sx, sy);
            canvas.rotate(angle);

            canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

            canvas.setTransform(t);
        }
    }

    // draws an image centered on x,y
    public static void img(Sprite spr, int x, int y) {
        int half = spr.drawLimit;
        if ((x + half > drawLimitLeft && x - half < drawLimitRight && y + half > drawLimitTop && y - half < drawLimitBottom) || absoluteDraw) {
            canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);
        }
    }

    public static void preRender() {
        switchDrawMode();

        int limitModifier = 0;
        // if(drawingMode==2) {limitModifier=canvases.buffer2cvs.width-maxCvsSize;}
        drawLimitLeft = -Camera.x - (drawingMode == 2 ? sizeDif : 0) - limitModifier;
        drawLimitRight = -Camera.x + maxCvsSize + (drawingMode == 2 ? sizeDif : 0) + limitModifier;
        drawLimitTop = -Camera.y - (drawingMode == 2 ? sizeDif : 0) - limitModifier;
        drawLimitBottom = -Camera.y + maxCvsSize + (drawingMode == 2 ? sizeDif : 0) + limitModifier;
    }

    // sets the drawing mode based on what transformations are needed
    private static void switchDrawMode() {
        // if(Camera.zoom<0.1f) {
        // Camera.zoom=0.1f;
        // }
        // if(Camera.angle!=0) {
        // drawingMode=2;
        // } else if(Camera.zoom!=1) {
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
    public static void render() {
        // f.repaint();
        Graphics2D g2 = (Graphics2D) panel.getGraphics();
        

        
        Camera.angle += 0.01;

        if (drawingMode == 0) {
            g2.drawImage(buffer, 0, 0, null);
        } else if (drawingMode == 1) {

        } else if (drawingMode == 2) {
            AffineTransform t = g2.getTransform();
            g2.translate( GameJava.gw/2,GameJava.gh/2);
            g2.rotate(Camera.angle);
            g2.scale(2,2);
            g2.drawImage(buffer,-GameJava.gw/2,-GameJava.gh/2,null);
            g2.setTransform(t);
        }

        GameJava.gw = panel.getWidth();
        GameJava.gh = panel.getHeight();

        canvas.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }


}
