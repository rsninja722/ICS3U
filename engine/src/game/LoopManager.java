package game;

import game.drawing.Draw;

public class LoopManager {

    private static GameJava mainGameClass;

    // calculation for how long to wait between each frame
	public static final double nanosecondsPerSecond = 1000000000;
	public static double nanosPerFrame;
	private static double lastDrawTime = System.nanoTime();
	
    public static double nanosPerUpdate;
    private static double lastUpdateTime = System.nanoTime();

    public static void startLoops(GameJava mainGameObject) {
        System.out.println("[LoopManager] starting loops with: " + mainGameObject.toString());
        mainGameClass = mainGameObject;

        nanosPerFrame = nanosecondsPerSecond / GameJava.framePerSecond;
        nanosPerUpdate = nanosecondsPerSecond / GameJava.updatesPerSecond;
		
		while(GameJava.running) {
			
			// drawing
			double currentTime = System.nanoTime();
			if(currentTime - lastDrawTime >= nanosPerFrame) {
                synchronized(Draw.panel) {
                	Draw.preRender();
                	mainGameClass.draw();
                    Draw.render();
                }
				lastDrawTime = currentTime;
				GameJava.frameCount++;
			}
			
			// updating
			currentTime = System.nanoTime();
			if(currentTime - lastUpdateTime >= nanosPerUpdate) {
				mainGameClass.update();
                lastUpdateTime = currentTime;
                GameJava.updateCount++;
			}
			
		}
	}
}