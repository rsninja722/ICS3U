package bouncingBalls;

import java.awt.Color;

import game.GameJava;
import game.Utils;
import game.drawing.Draw;
import game.physics.Rect;

public class Block {
	public static Block[] blocks;
	Rect collider;
	Color color;
	
	// constructor
	public Block(int x, int y, int w, int h) {
		collider = new Rect(x,y,w,h);
		color = Color.getHSBColor((float)Utils.rand(0, 100)/100, 1.0f, 0.8f);
	}

	// generate
	public static void makeBlocks(int count) {
		blocks = new Block[count];
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new Block(Utils.rand(0,GameJava.gw),Utils.rand(0,GameJava.gh),Utils.rand(10,100),Utils.rand(10,100));
		}
	}

	// draw
	public static void drawBlocks() {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != null) {
				Draw.setColor(blocks[i].color);
				Draw.rectOutline(blocks[i].collider);
			}
		}
	}
}
