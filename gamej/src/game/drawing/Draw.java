package game.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import game.GameJava;
import game.Input;

public class Draw extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JPanel panel;

    public static JFrame frame;

    public static String frameTitle = "game title";

    public static boolean fullScreen = false;
    public static int windowedWidth;
    public static int windowedHeight;

    private static VolatileImage buffer; // final buffer
    private static VolatileImage buffer1; // buffer used for scaling
    private static VolatileImage buffer2; // buffer used for rotation

    private static Graphics2D bufferGraphics; // final buffer
    private static Graphics2D buffer1Graphics; // buffer used for scaling
    private static Graphics2D buffer2Graphics; // buffer used for rotation

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

    private static int maxCvsSize = Math.max(GameJava.gw, GameJava.gh);
    private static int sizeDif = maxCvsSize - Math.min(GameJava.gw, GameJava.gh);

    public static boolean absoluteDraw = false;

    public static final GraphicsEnvironment graphicsEnviro = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsConfiguration graphicsConfig = graphicsEnviro.getDefaultScreenDevice()
            .getDefaultConfiguration();

    // creates window
    public static void init() {
        System.out.println("[Draw] initizlizing");

        panel = new JPanel(new BorderLayout());

        buffer = graphicsConfig.createCompatibleVolatileImage(GameJava.gw, GameJava.gh);
        buffer1 = graphicsConfig.createCompatibleVolatileImage(GameJava.gw, GameJava.gh);
        buffer2 = graphicsConfig.createCompatibleVolatileImage(GameJava.gw, GameJava.gh);
        // new BufferedImage(, BufferedImage.TYPE_4BYTE_ABGR);

        canvas = buffer.createGraphics();

        panel.setBackground(Color.WHITE);

        frame = new JFrame(frameTitle);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setUndecorated(false);
        frame.setVisible(true);
        frame.pack();

        frame.setSize(GameJava.gw, GameJava.gh);

        frame.setLocationRelativeTo(null);

        windowedWidth = GameJava.gw;
        windowedHeight = GameJava.gh;
    }

    public static void toggleFullSreen() {
        // toggle full screen
        fullScreen = !fullScreen;
        // destroy and remake jframe
        frame.dispose();
        frame = new JFrame(frameTitle);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setUndecorated(fullScreen);
        frame.setVisible(true);
        frame.pack();

        // if entering windowed mode, set size to size set in GameJava init
        if(!fullScreen) {
            frame.setSize(windowedWidth, windowedHeight);
        }

        frame.setLocationRelativeTo(null);

        // set or unset frame to be the fullscreen window
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        if(fullScreen) {
            device.setFullScreenWindow(Draw.frame);
        } else {
            device.setFullScreenWindow(null);
        }

        // add the key listeners back to the frame
        Input.initKeys();
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
    public static void imgIgnoreCutoff(Sprite spr, int x, int y, double angle, double scale) {
        AffineTransform t = canvas.getTransform();

        canvas.translate(Math.round(x + Camera.x + difx), Math.round(y + Camera.y + dify));
        canvas.scale(scale, scale);
        canvas.rotate(angle);

        canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

        canvas.setTransform(t);
    }

    // draws an image centered on x,y. Will not prevent drawing if off screen
    public static void imgIgnoreCutoff(Sprite spr, int x, int y) {
        canvas.drawImage(spr.img, Math.round(x + Camera.x + difx - (spr.width / 2)), Math.round(y + Camera.y + dify - (spr.height / 2)), null);
    }

    // draws an image centered on x,y with an angle, scaled on the x and y axis
    public static void img(Sprite spr, int x, int y, double angle, double scale) {
        int half = (int) (spr.drawLimit * scale);
        if ((x + half > drawLimitLeft && x - half < drawLimitRight && y + half > drawLimitTop && y - half < drawLimitBottom) || absoluteDraw) {
            AffineTransform t = canvas.getTransform();

            canvas.translate(Math.round(x + Camera.x + difx), Math.round(y + Camera.y + dify));
            canvas.scale(scale, scale);
            canvas.rotate(angle);

            canvas.drawImage(spr.img, Math.round(-spr.width / 2), Math.round(-spr.height / 2), null);

            canvas.setTransform(t);
        }
    }

    // draws an image centered on x,y
    public static void img(Sprite spr, int x, int y) {
        int half = spr.drawLimit;
        if ((x + half > drawLimitLeft && x - half < drawLimitRight && y + half > drawLimitTop && y - half < drawLimitBottom) || absoluteDraw) {
            canvas.drawImage(spr.img, Math.round(x + Camera.x + difx - (spr.width / 2)), Math.round(y + Camera.y + dify - (spr.height / 2)), null);
        }
    }

    public static void preRender() {
        // change what buffer is used depending in the camera
        if (Camera.zoom < 1.0f) {
            Camera.zoom = 1.0f;
        }
        if (Camera.angle != 0) {
            drawingMode = 2;
        } else if (Camera.zoom != 1) {
            drawingMode = 1;
        } else {
            drawingMode = 0;
        }

        // set size of panel to fit frame
        int w = frame.getWidth();
        int h = frame.getHeight();
        panel.setSize(w, h);
        GameJava.gw = panel.getWidth();
        GameJava.gh = panel.getHeight();

        // recalculate the biggest of width and height, and get the difference (this is
        // used for the buffer sizes)
        maxCvsSize = Math.max(GameJava.gw, GameJava.gh);
        sizeDif = maxCvsSize - Math.min(GameJava.gw, GameJava.gh);

        // calculate the size buffer 2 should be, so it can be rotated and not miss
        // drawing in corners
        double tempSize = maxCvsSize / Camera.zoom;
        double tempSizeAndPadding = tempSize + (tempSize / 2);

        // resize buffers
        if (drawingMode == 2) {
            buffer2.flush();
            buffer2 = graphicsConfig.createCompatibleVolatileImage((int) tempSizeAndPadding, (int) tempSizeAndPadding);
            buffer2Graphics = buffer2.createGraphics();
        }
        if (drawingMode > 0) {
            buffer1.flush();
            buffer1 = graphicsConfig.createCompatibleVolatileImage(GameJava.gw, GameJava.gh);
            buffer1Graphics = buffer1.createGraphics();
        }
        buffer.flush();
        buffer = graphicsConfig.createCompatibleVolatileImage(GameJava.gw, GameJava.gh);
        bufferGraphics = buffer.createGraphics();

        // if drawing to buffer 2, find off set for every draw
        if (drawingMode == 2) {
            difx = (int) (tempSizeAndPadding - GameJava.gw) / 2;
            dify = (int) (tempSizeAndPadding - GameJava.gh) / 2;
        } else {
            difx = 0;
            dify = 0;
        }

        // calculate limits of where to draw
        int limitModifier = 0;
        // if(drawingMode==2) {limitModifier=buffer2.getWidth()-maxCvsSize;}
        drawLimitLeft = -Camera.x - (drawingMode == 2 ? sizeDif : 0) - limitModifier;
        drawLimitRight = -Camera.x + maxCvsSize + (drawingMode == 2 ? sizeDif : 0) + limitModifier;
        drawLimitTop = -Camera.y - (drawingMode == 2 ? sizeDif : 0) - limitModifier;
        drawLimitBottom = -Camera.y + maxCvsSize + (drawingMode == 2 ? sizeDif : 0) + limitModifier;

        // set the drawing target
        switch (drawingMode) {
            case 0:
                canvas = bufferGraphics;
                break;
            case 1:
                canvas = buffer1Graphics;
                break;
            case 2:
                canvas = buffer2Graphics;
                break;
        }

        // canvas.clearRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    // draws buffers onto the screen
    public static void render() {
        // f.repaint();
        Graphics2D g2 = (Graphics2D) panel.getGraphics();


        // if (drawingMode == 0) {
            
        // } else if (drawingMode == 1) {

        // } else if (drawingMode == 2) {
        //     AffineTransform t = g2.getTransform();
        //     g2.translate(GameJava.gw / 2, GameJava.gh / 2);
        //     g2.rotate(Camera.angle);
        //     g2.scale(2, 2);
        //     g2.drawImage(buffer, -GameJava.gw / 2, -GameJava.gh / 2, null);
        //     g2.setTransform(t);
        // }

        switch (drawingMode) {
            case 0:
                g2.drawImage(buffer, 0, 0, null);
                break;
            case 1:
                imgRotScale(GameJava.gw/2,GameJava.gh/2,0,Camera.zoom,buffer1,bufferGraphics);
                g2.drawImage(buffer, 0, 0, null);
                break;
            case 2:
                imgRotScale(GameJava.gw/2,GameJava.gh/2,Camera.angle,1,buffer2,buffer1Graphics);
                imgRotScale(GameJava.gw/2,GameJava.gh/2,0,Camera.zoom,buffer1,bufferGraphics);
                g2.drawImage(buffer, 0, 0, null);
                break;
        }

        g2.dispose();
    }
    
    //used for camera movement
    private static void imgRotScale(double x,double y,double angle,double scale,VolatileImage pic,Graphics2D ctx) { 
        AffineTransform t = ctx.getTransform();
        ctx.translate(x,y);
        ctx.scale(scale,scale);
        ctx.rotate(angle);
        ctx.drawImage(pic,-pic.getWidth()/2,-pic.getHeight()/2,null);
        ctx.setTransform(t);
    }
}
