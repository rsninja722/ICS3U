package bouncingBalls;

import game.GameJava;
import game.Utils;
import game.drawing.Draw;
import game.physics.Rect;

public class Block {
	public static Block[] blocks;
	Rect collider;
	
	// constructor
	public Block(int x, int y, int w, int h) {
		collider = new Rect(x,y,w,h);
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
				Draw.rect(blocks[i].collider);
			}
		}
	}
}
