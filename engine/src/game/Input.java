package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import game.drawing.Draw;

public class Input {

    public static void init() {
        System.out.println("[Input] initizlizing");
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
        
        Draw.panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	System.out.println("press: x: "+e.getX()+ " y: " + e.getY() + "code: " + e.getButton());
            }
        });
        Draw.panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                System.out.println("x: "+e.getX()+ " y: " + e.getY());
            }
        });
    }
}