package game;

public class LoopManager {

    GameJava g;

    public LoopManager (GameJava mainGameObject) {
        g = mainGameObject;
        startLoops();
    }

    private void startLoops() {
		System.out.println("starting loops");
		while(g.running) {
			
			// drawing
			double currentTime = System.nanoTime();
			if(currentTime - g.lastDrawTime >= g.nanosPerFrame) {
                synchronized(g.d) {
                	g.d.preRender();
                	g.draw();
                    g.d.render();
                }
				g.lastDrawTime = currentTime;
				g.frameCount++;
			}
			
			// updating
			currentTime = System.nanoTime();
			if(currentTime - g.lastUpdateTime >= g.nanosPerUpdate) {
				g.update();
                g.lastUpdateTime = currentTime;
                g.updateCount++;
			}
			
		}
	}
}