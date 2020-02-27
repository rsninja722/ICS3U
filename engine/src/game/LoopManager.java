package game;

public class LoopManager {

    GameJava mainGameClass;

    public LoopManager (GameJava mainGameObject) {
        mainGameClass = mainGameObject;
        startLoops();
    }

    private void startLoops() {
		System.out.println("starting loops");
		while(mainGameClass.running) {
			
			// drawing
			double currentTime = System.nanoTime();
			if(currentTime - mainGameClass.lastDrawTime >= mainGameClass.nanosPerFrame) {
                synchronized(mainGameClass.drawing) {
                	mainGameClass.drawing.preRender();
                	mainGameClass.draw();
                    mainGameClass.drawing.render();
                }
				mainGameClass.lastDrawTime = currentTime;
				mainGameClass.frameCount++;
			}
			
			// updating
			currentTime = System.nanoTime();
			if(currentTime - mainGameClass.lastUpdateTime >= mainGameClass.nanosPerUpdate) {
				mainGameClass.update();
                mainGameClass.lastUpdateTime = currentTime;
                mainGameClass.updateCount++;
			}
			
		}
	}
}