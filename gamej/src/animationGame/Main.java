package animationGame;

import java.awt.Color;

/** 2020.04.06
 * James N
 * Animation Game
 * plan: simple top down game where the player has to avoid traps and reach the end
 * controls: wasd
 */

import game.*;
import game.drawing.Camera;
import game.drawing.Draw;
import game.drawing.Sprites;

class Main extends GameJava {
	Player player;

	GameState state;

	enum GameState {
		titleScreen, playing, transition, death
	}

	public Main() {
		super(1000, 800, 144, 144);

		player = new Player();

		state = GameState.playing;

		Camera.zoom = 2.0f;
		Camera.centerOn(0, 0);

		LoopManager.startLoops(this);
	}

	public static void main(String[] args) throws InterruptedException {
		frameTitle = "gaming time";
		new Main();
	}

	@Override
	public void draw() {
		if(Utils.debugMode) {
			Draw.setColor(new Color(0,0,255,155));
			Draw.circle(player.circle);
		}
		Draw.image(Sprites.get("player"), (int) player.circle.x, (int) player.circle.y, player.angle, 1.0);
	}

	@Override
	public void update() {
		switch (state) {
			case titleScreen:
	
				break;
			case playing:
				player.moveBasedOnInput();
				break;
			case transition:
	
				break;
			case death:
	
				break;
		}
	}

	@Override
	public void absoluteDraw() {
		Draw.text("press f3 for debug", 100, 10);
	}
}