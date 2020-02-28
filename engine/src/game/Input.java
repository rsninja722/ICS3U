package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import game.drawing.Camera;
import game.drawing.Draw;
import game.physics.Physics;
import game.physics.Point;
import game.Utils;

public class Input {

    public static Point rawMousePos = new Point(0,0);
    public static Point mousePos = new Point(0,0);

    private static byte[] mouseInputs = new byte[3]; 

    public static void init() {
        System.out.println("[Input] initializing");

        for(int i=0;i<mouseInputs.length;i++) {
            mouseInputs[i] = 0;
        }

        Draw.frame.addKeyListener(new KeyListener() {
            
            public void keyPressed(KeyEvent e) {
                System.out.println("click");
                System.out.println(e.getKeyCode() + " " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) { System.out.println("click");
            System.out.println(e.getKeyCode() + " " + e.getKeyChar());}

            @Override
            public void keyTyped(KeyEvent e) { System.out.println("click");
            System.out.println(e.getKeyCode() + " " + e.getKeyChar());}
        });
        
        // mouse presses
        Draw.panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseInputs[e.getButton()-1] = 2;
            }
        });

         // mouse unpressed
        Draw.panel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                mouseInputs[e.getButton()-1] = 0;
            }
        });

        // mouse movement
        Draw.panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                setMousePosition(e.getX(), e.getY());
            }
        });
    }

    private static void setMousePosition(int x,int y) {
        rawMousePos.x = x;
        rawMousePos.y = y;

        if(Draw.drawingMode==0) {
            mousePos.x = x-Camera.x;
            mousePos.y = y-Camera.y;
        } else if(Draw.drawingMode==1) {
            double xoff = GameJava.gw/2;
            double yoff = GameJava.gh/2;
            mousePos.x = ((x-xoff)/Camera.zoom+xoff)-Camera.x;
            mousePos.y = ((y-yoff)/Camera.zoom+yoff)-Camera.y;
        } else {
            double xoff = GameJava.gw/2;
            double yoff = GameJava.gh/2;
            Point tempPos = new Point(((x-xoff)/Camera.zoom+xoff)-Camera.x,((y-yoff)/Camera.zoom+yoff)-Camera.y);
    
            Point center = new Point(-Camera.x + GameJava.gw/2, -Camera.y + GameJava.gh/2);
            double tempAngle = Utils.pointTo(center,tempPos) - Camera.angle; 
            double tempDist = Physics.dist(center,tempPos);
    
            mousePos.x = center.x + (Math.cos(tempAngle) * tempDist);
            mousePos.y = center.y + (Math.sin(tempAngle) * tempDist);
        }
    }

    // needs to be called every cycle to make sure clicks are registered properly 
    public static void manageInput() {
        for(int i=0;i<mouseInputs.length;i++) {
            if(mouseInputs[i] == 2) {
                mouseInputs[i] = 1;
            }
        }
    }

    /** @param buttonID 0 = lmb, 1 = middle click, 2 = rmb @return if that mouse button is held down  */
    public static boolean mouseDown(int buttonId) {
        return mouseInputs[buttonId] > 0;
    }
    /** @param buttonID 0 = lmb, 1 = middle click, 2 = rmb @return only true the first cycle the button is held down */
    public static boolean mouseClick(int buttonId) {
        return mouseInputs[buttonId] == 2;
    }
}