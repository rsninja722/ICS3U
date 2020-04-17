package animationGame;

import java.awt.Color;

import animationGame.enemies.BaseEnemy;
import animationGame.enemies.EnemyMedium;
import animationGame.enemies.EnemySmall;

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
		
		Draw.frame.setResizable(false);
		Draw.allowFullScreen = false;
		
		EnemySmall.create(100, 100, 0.5, 0);
		EnemySmall.create(100, 120, -0.6, 0);
		EnemySmall.create(120, 100, 0, -0.2);
		EnemySmall.create(120, 120, 0, 1.0);
		
		EnemyMedium.create(150, 150, 0, 0.75);
		
		for(int i=0;i<25;i++) {
			int dir = Utils.rand(0, 1);
			EnemySmall.create(Utils.rand(400, 800), Utils.rand(100, 300), dir == 1 ? Utils.rand(1,10) / 10.0 : 0, dir == 0 ? Utils.rand(1,10) / 10.0 : 0);
		}

		LoopManager.startLoops(this);
	}

	public static void main(String[] args) throws InterruptedException {
		frameTitle = "gaming time";
		new Main();
	}

	@Override
	public void draw() {
		Draw.image(Sprites.get("back1"), 500, 200);
		
		BaseEnemy.drawEnemies();
		
		if(Utils.debugMode) {
			Draw.setColor(new Color(0,0,255,155));
			Draw.circle(player.circle);
		}
		player.draw();
	}

	@Override
	public void update() {
		switch (state) {
			case titleScreen:
	
				break;
			case playing:
				player.moveBasedOnInput();
				BaseEnemy.moveEnemies();
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