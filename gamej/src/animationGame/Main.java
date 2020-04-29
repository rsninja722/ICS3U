package animationGame;

/** 2020.04.06
 * James N
 * Animation Game
 * plan: simple top down game where the player has to avoid traps and reach the end
 * controls: wasd
 */

import java.awt.Color;

import animationGame.enemies.BaseEnemy;
import animationGame.enemies.EnemyBoss;
import animationGame.enemies.EnemyMedium;
import animationGame.enemies.EnemySmall;

import game.*;
import game.drawing.Camera;
import game.drawing.Draw;
import game.drawing.Sprites;

public class Main extends GameJava {
	static public Player player;

	static enum GameState {
		titleScreen, playing, transition, death, win
	}

	static GameState state;
	static GameState lastState;
	static GameState desiredState;
	
	static boolean transitioning = false;
	static int transitionAlpha = 255;
	static int transitionDirection = -1;
	static boolean shouldReloadLevel = true;
	
	static enum Level {
		tutorial, two, boss
	}

	static Level currentLevel;
	
	Button startButton = new Button(500, 200, 200, 50, "Start", Button::startButton);
	Button retryButton = new Button(500, 200, 200, 50, "Retry", Button::retryButton);

	public Main() {
		super(1000, 800, 144, 144);

		player = new Player();

		state = GameState.titleScreen;
		desiredState = GameState.titleScreen;
		
		currentLevel = Level.tutorial;
		
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
		Utils.putInDebugMenu("A", state.toString());
		Utils.putInDebugMenu("A", desiredState.toString());
		if(!(transitioning && transitionAlpha != 255)) {
			boolean isNewState = false;
			if (state != lastState) {
				isNewState = true;
			}
			lastState = state;
	
			
			
			switch (state) {
			// title screen
			case titleScreen:
				startButton.update();
				break;
	
			// playing
			case playing:
				if(shouldReloadLevel) {
					loadLevel(currentLevel);
					shouldReloadLevel = false;
				}
				
				player.moveBasedOnInput();
				BaseEnemy.moveEnemies();
				
				if(BaseEnemy.playerHittingEnemies(player)) {
					--player.lives;
					if(player.lives <= 0) {
						Main.transitionTo(GameState.death);
					} else {
						Main.transitionTo(GameState.playing);
						shouldReloadLevel = true;
					}
				}
				break;
	
			// transition
			case transition:
				
				break;
	
			// death
			case death:
				retryButton.update();
				break;
			}
		}
		
		if(transitioning) {
			transitionAlpha += transitionDirection;
			if(transitionDirection == 1) {
				if(transitionAlpha == 255) {
					transitionDirection = -1;
					state = desiredState;
				}
			}
			if(transitionDirection == -1) {
				if(transitionAlpha == 0) {
					transitioning = false;
				}
			}
		}
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
			case two:
				Draw.image(Sprites.get("back2"), 500, 200);
				break;
			case boss:
				Draw.image(Sprites.get("back3"), 500, 200);
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
			startButton.draw();
			break;

		// playing
		case playing:
			for(int i=0;i<player.lives; i++) {
				Draw.image(Sprites.get("heart"), 32+ i*36, 32, 0.0, 2.0);
			}
			break;

		// transition
		case transition:
			
			break;

		// death
		case death:
			Draw.setColor(new Color(36,36,36));
			Draw.rect(gw/2,gh/2,gw,gh);
			Draw.setColor(Color.RED);
			Draw.setFontSize(6);
			Draw.text("You Died", 350, 300);
			retryButton.draw();
			break;
		
		// win
		case win:
			Draw.setColor(new Color(36,36,36));
			Draw.rect(gw/2,gh/2,gw,gh);
			Draw.setColor(Color.YELLOW);
			Draw.setFontSize(6);
			Draw.text("You Win!", 350, 300);
			Draw.image(Sprites.get("enemy3"), gw/2 + 100, gh/2, frameCount/100.0, 4);
			Draw.image(Sprites.get("enemy3"), gw/2 - 100, gh/2, frameCount/-100.0, 4);
			break;
		}
		
		if(transitioning) {
			Draw.setColor(new Color(0,0,0,transitionAlpha));
			Draw.rect(gw/2, gh/2, gw, gh);
			Draw.setColor(Color.WHITE);
			Draw.setFontSize(4);
			if(desiredState != GameState.death && desiredState != GameState.win) {
				switch (currentLevel) {
					case tutorial:
						Draw.text("Stage 1", gw/2-100, gh/2-20);
						break;
					case two:
						Draw.text("Stage 2", gw/2-100, gh/2-20);
						break;
					case boss:
						Draw.text("Final Stage", gw/2-150, gh/2-20);
						break;
				}
			}
		}
	}
	
	public static void transitionTo(GameState newState) {
		transitioning = true;
		desiredState = newState;
		transitionDirection = 1;
		transitionAlpha = 0;
	}

	public void loadLevel(Level levelToSwitchTo) {
		BaseEnemy.clearEneimes();
		player.reset();
		switch (levelToSwitchTo) {
		case tutorial:

			EnemySmall.create(300, 100, 0, 0.5);
			EnemySmall.create(400, 220, 0, -0.3);
			EnemySmall.create(400, 300, 0, -0.3);
			EnemySmall.create(520, 300, 0, -0.2);
			EnemySmall.create(620, 200, 0, 0.75);
			EnemySmall.create(620, 250, 0.3, 0);
			
			EnemySmall.create(100, 100, 0.4, 0);
			EnemySmall.create(50, 300, 0.3, 0);

			EnemyMedium.create(750, 150, 0, 0.1);
			EnemyMedium.create(750, 200, 0, 0.1);
			EnemyMedium.create(750, 250, 0, 0.1);
			EnemyMedium.create(750, 300, 0, 0.1);

			player.setPosition(200, 200);
			break;
		case two:
			for(int i=100;i<500;i+= 20) {
				if(i == 300) {
					continue;
				}
				EnemySmall.create(i, 25+i/1.5, 0, 0.5);
			}
			
			EnemyMedium.create(550, 75, 0, 0);
			EnemyMedium.create(550, 110, 0, 0);
			EnemyMedium.create(550, 290, 0, 0);
			EnemyMedium.create(550, 325, 0, 0);
			
			EnemyMedium.create(900, 75, 0, 0);
			EnemyMedium.create(900, 110, 0, 0);
			EnemyMedium.create(900, 290, 0, 0);
			EnemyMedium.create(900, 325, 0, 0);
			
			EnemySmall.create(600, 60, -0.3, 0);
			EnemySmall.create(650, 80, -0.4, 0);
			EnemySmall.create(700, 100, -0.4, 0);
			EnemySmall.create(750, 120, -0.3, 0);
			
			EnemySmall.create(750, 280, -0.3, 0);
			EnemySmall.create(600, 300, -0.4, 0);
			EnemySmall.create(650, 320, -0.4, 0);
			EnemySmall.create(700, 340, -0.3, 0);
			
			EnemyMedium.create(750, 150, 0, 0);
			EnemyMedium.create(750, 185, 0, 0);
			EnemyMedium.create(750, 210, 0, 0);
			EnemyMedium.create(750, 245, 0, 0);
			
			player.setPosition(25, 200);
			break;
		case boss:
			EnemyBoss.create(590, 150, 0, 0);
			EnemyBoss.create(590, 250, 0, 0);
			player.setPosition(25, 200);
			break;
		}
	}

}