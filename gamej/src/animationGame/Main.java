package animationGame;

/** 2020.04.06
 * James N
 * Animation Game
 * plan: simple top down game where the player has to avoid traps and reach the end
 * controls: wasd
 */

import java.awt.Color;

import animationGame.enemies.BaseEnemy;
import animationGame.enemies.EnemyMedium;
import animationGame.enemies.EnemySmall;

import game.*;
import game.drawing.Camera;
import game.drawing.Draw;
import game.drawing.Sprites;

class Main extends GameJava {
	static Player player;

	static enum GameState {
		titleScreen, playing, transition, death
	}

	static GameState state;
	static GameState lastState;
	static GameState desiredState;

	static enum Level {
		tutorial, two, boss
	}

	static Level currentLevel;

	public Main() {
		super(1000, 800, 144, 144);

		player = new Player();

		state = GameState.transition;
		desiredState = GameState.titleScreen;
		currentLevel = Level.tutorial;
		
		Button.generateButtons();

		Camera.zoom = 2.0f;
		Camera.centerOn(0, 0);

		Draw.frame.setResizable(false);
		Draw.allowFullScreen = false;

		LoopManager.startLoops(this);
	}

	public static void main(String[] args) throws InterruptedException {
		frameTitle = "gaming time";
		new Main();
	}
	
	@Override
	public void update() {
		boolean isNewState = false;
		if (state != lastState) {
			isNewState = true;
		}
		

		switch (state) {
		// title screen
		case titleScreen:
			Button.updateButtons();
			break;

		// playing
		case playing:
			if(isNewState) {
				
				loadLevel(currentLevel);
			}
			
			player.moveBasedOnInput();
			BaseEnemy.moveEnemies();
			
			if(BaseEnemy.playerHittingEnemies(player)) {
				state = GameState.transition;
				
				--player.lives;
				if(player.lives <= 0) {
					desiredState = GameState.death;
				} else {
					desiredState = GameState.playing;
				}
			}
			break;

		// transition
		case transition:
			// TODO add delay
			if(!isNewState) {
				state = desiredState;
			}
			break;

		// death
		case death:
		
			break;
		}
		
		lastState = state;
	}

	@Override
	public void draw() {
		switch (state) {
		// title screen
		case titleScreen:
			
			break;

		// playing
		case playing:
			switch (currentLevel) {
			case tutorial:
				Draw.image(Sprites.get("back1"), 500, 200);
				break;
			case boss:

				break;
			case two:

				break;
			}

			BaseEnemy.drawEnemies();

			if (Utils.debugMode) {
				Draw.setColor(new Color(0, 0, 255, 155));
				Draw.circle(player.circle);
			}
			player.draw();

			break;

		// transition
		case transition:

			break;

		// death
		case death:
			
			break;
		}
	}

	@Override
	public void absoluteDraw() {
		switch (state) {
		// title screen
		case titleScreen:
			Draw.setColor(new Color(36,36,36));
			Draw.rect(gw/2,gh/2,gw,gh);
			Button.drawButtons();
			break;

		// playing
		case playing:
			for(int i=0;i<player.lives; i++) {
				Draw.image(Sprites.get("heart"), 32+ i*36, 32, 0.0, 2.0);
			}
			break;

		// transition
		case transition:
			// TODO add delay
			state = desiredState;
			break;

		// death
		case death:
			Draw.setColor(Color.CYAN);
			Draw.text("DEd", 100, 100);
			break;
		}
	}

	public void loadLevel(Level levelToSwitchTo) {
		BaseEnemy.clearEneimes();
		player.reset();
		switch (levelToSwitchTo) {
		case tutorial:

			EnemySmall.create(300, 100, 0, 0.5);
			EnemySmall.create(400, 220, 0, -0.3);
			EnemySmall.create(520, 300, 0, -0.2);
			EnemySmall.create(620, 420, 0, 0.75);
			EnemySmall.create(620, 420, 0.3, 0);

			EnemyMedium.create(750, 150, 0, 0.1);
			EnemyMedium.create(750, 200, 0, 0.1);
			EnemyMedium.create(750, 250, 0, 0.1);
			EnemyMedium.create(750, 300, 0, 0.1);

			player.setPosition(200, 200);
			break;
		case boss:

			break;
		case two:

			break;
		}
	}

}