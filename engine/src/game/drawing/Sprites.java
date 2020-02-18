package game.drawing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Sprites {
	
	HashMap<String,Sprite> spriteList = new HashMap<String,Sprite>();
	
	private Path currentRelativePath = Paths.get("");
	private String baseDirectory = currentRelativePath.toAbsolutePath().toString() + "\\assets\\images\\";
	
	public Sprites() {
		File dir = new File(baseDirectory);
		String[] children = dir.list();
		for(int i=0;i<children.length;i++) {
			String name = children[i];
			
			if(name.endsWith(".png")) {
				String spriteName = name.substring(0, name.indexOf("."));
				spriteList.put(spriteName, new Sprite(baseDirectory+name));
			}
		}
	}
	
	public Sprite get(String spriteName) {
		Sprite s = spriteList.get(spriteName);
		if(s == null) {
			System.out.println("error: " + spriteName + " not found");
		}
		return spriteList.get(spriteName);
	}
}
