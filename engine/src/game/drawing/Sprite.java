package game.drawing;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	public Image img;
	public int width;
	public int height;
	public int drawLimit;
	
	public Sprite(String filePath) {
		try {
			img = ImageIO.read(new File("assets\\images\\"+filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = img.getWidth(null);
		height = img.getHeight(null);
		
		drawLimit = (int) Math.ceil(Math.max(width,height)/2.0);
	}
}
