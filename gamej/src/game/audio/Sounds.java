package game.audio;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.Clip;

import game.GameJava;
import game.audio.Sound;
import game.drawing.Sprite;

public class Sounds {
	private static HashMap<String, Sound> soundList = new HashMap<String, Sound>();
	
	private static String directoryChar = System.getProperty("file.separator");

    // absolute path of the images folder
    private static String soundsDirectory;

    // finds images, loads them, and puts them in the hashmap
    public static void loadSounds() {
    	
    	// if security stuff prevents the file separator from being accessed, default to windows separator
    	if( !directoryChar.equals("\\") && !directoryChar.equals("/")) {
    		directoryChar = "\\";
    	}
    	
    	soundsDirectory = GameJava.baseDirectory + directoryChar + "audio" + directoryChar;
    	
        System.out.println("[Sounds] loading sounds from: " + soundsDirectory);
        
        StringBuilder debugMsg = new StringBuilder("[Sounds] Loaded sounds: ");
        
        debugMsg.append(loadFromDirectory(soundsDirectory));

        System.out.println(debugMsg.toString().substring(0, debugMsg.toString().length()-1));
    }
    
    private static String loadFromDirectory(String path) {
    	StringBuilder loadedFiles = new StringBuilder();
    	
    	File dir = new File(path);

        // get array of other files
        String[] children = dir.list();
        
        for (int i = 0; i < children.length; i++) {
            String name = children[i];

            if (name.endsWith(".wav") || name.endsWith(".mp3")) {
                String soundName = name.substring(0, name.indexOf("."));
                soundList.put(soundName, new Sound(path + name));
                loadedFiles.append(soundName + ",");
            } else {
            	loadedFiles.append(loadFromDirectory(path + name + directoryChar));
            }
        }
        
        return loadedFiles.toString();
    }
	
	public static void play(String soundName) {
		Sound s = soundList.get(soundName);
        if (s == null) {
            System.out.println("error: " + soundName + " not found");
        }
        soundList.get(soundName).play();
	}

	public static void stop(String soundName) {
		Sound s = soundList.get(soundName);
        if (s == null) {
            System.out.println("error: " + soundName + " not found");
        }
        soundList.get(soundName).stop();
	}
	
	public static void loop(String soundName) {
		Sound s = soundList.get(soundName);
        if (s == null) {
            System.out.println("error: " + soundName + " not found");
        }
        soundList.get(soundName).loop();
	}
	
	public static void ajustGain(String soundName) {
		Sound s = soundList.get(soundName);
        if (s == null) {
            System.out.println("error: " + soundName + " not found");
        }
        soundList.get("step").ajustGain(10.0f);
	}
}