package game.drawing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Sprites {
	
	private static HashMap<String,Sprite> spriteList = new HashMap<String,Sprite>();
    
    private static Path currentRelativePath = Paths.get("");
    // absolute path of the images folder
    private static String baseDirectory = currentRelativePath.toAbsolutePath().toString() + "\\assets\\images\\";
	
	public static void loadSprites() {
        System.out.println("[Sprites] loading sprites from: " + baseDirectory);
        // create file to get all other files in directory
        File dir = new File(baseDirectory);

        // get array of other files
        String[] children = dir.list();

        System.out.println(Arrays.toString(children));
        
        // go through all files
		for(int i=0;i<children.length;i++) {
			String name = children[i];
            
            // if it is a portable network graphic, create a sprite with the image, and add it to the hashmap
			if(name.endsWith(".png")) {
				String spriteName = name.substring(0, name.indexOf("."));
				spriteList.put(spriteName, new Sprite(baseDirectory+name));
			}
		}
	}
    
    // returns a sprite from the hash map
	public static Sprite get(String spriteName) {
		Sprite s = spriteList.get(spriteName);
		if(s == null) {
			System.out.println("error: " + spriteName + " not found");
		}
		return spriteList.get(spriteName);
	}
}
