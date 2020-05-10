package animationGame;

/** 2020.04.06
 * James N
 * Animation Game
 * Simple top down game where the player has to avoid creatures, and get to the right side.
 * The player is equipped with a tranquilizer dart the can stun the creatures
 * controls: wasd, hold then release left click to shoot
 * 
 * TODO
 * put more magic numbers in constants
 * sounds
 * music
 * particles?
 */

import java.awt.Color;

import animationGame.enemies.BaseEnemy;
import animationGame.enemies.EnemyBoss;
import animationGame.enemies.EnemyMedium;
import animationGame.enemies.EnemySmall;

import game.*;
import game.audio.Sounds;
import game.drawing.Camera;
import game.drawing.Draw;
import game.drawing.Sprites;
import game.physics.Point;

public class Main extends GameJava {
	static public Player player;

	// the main states the game can be in
	static enum GameState {
		titleScreen, playing, transition, death, win
	}

	static GameState state; // current state
	static GameState lastState; // state last frame
	static GameState desiredState; // state to transition to

	static boolean transitioning = false;
	static int transitionAlpha = 100;
	static int transitionDirection = -1; // what to add to the transition alpha
	static boolean shouldReloadLevel = true;

	static enum Level {
		tutorial, two, boss
	}

	static Level currentLevel;
	
	boolean showingTutorial = true;
	
	boolean introPlaying = true;
	
	Point cameraDirection;
	Point titleCam;

	Button startButton = new Button(480, gh/2, 200, 50, "Start", Button::startButton);

	Button retryButton = new Button(480, 200, 200, 50, "Retry", Button::retryButton);

	public Main() {
		super(1000, 800, 60, 60);

		player = new Player();

		// set state to titles screen
		state = GameState.titleScreen;
		desiredState = GameState.titleScreen;

		// start at level 1
		currentLevel = Level.tutorial;

		Camera.zoom = 3.0f;
		Camera.centerOn(200, 200);
		double camAngle = Math.toRadians(Utils.rand(0, 359));
		cameraDirection = new Point(Math.cos(camAngle)/4,Math.sin(camAngle)/4);
		titleCam = new Point(200,200);
		
		//create enemies for title screen
		for(int i=0;i<50;i++) {
			EnemySmall.create(Utils.rand(20, Constants.roomWidth-20), Utils.rand(70, 330), 0, 0);
		}
		for(int i=0;i<10;i++) {
			EnemyMedium.create(Utils.rand(20, Constants.roomWidth-20), Utils.rand(70, 330), 0, 0);
		}

		// prevent game from being resized
		Draw.frame.setResizable(false);
		Draw.allowFullScreen = false;

		// start draw and update loops
		LoopManager.startLoops(this);
	}

	public static void main(String[] args) {
		frameTitle = "⫷Animation Game⫸";
		new Main();
	}

	
	@Override
	public void update() {
		
		// update only if not transitioning. Also update once when the screen is black so any code that sets stuff in place runs
		if (!(transitioning && transitionAlpha != 100)) {
			
			// check if the state changed from last frame
			boolean isNewState = false;
			if (state != lastState) {
				isNewState = true;
			}
			lastState = state;

			switch (state) {
			
			// title screen
			case titleScreen:
				if(introPlaying && frameCount >= 200) {
					Sounds.play("title");
					introPlaying = false;
				}
				if(!introPlaying) {
					startButton.update();
				}
				
				// move camera
				titleCam.x += cameraDirection.x;
				titleCam.y += cameraDirection.y;
				// bounce 
				if (titleCam.x < GameJava.gw / 2 / Camera.zoom) {
					titleCam.x = (int) (GameJava.gw / 2 / Camera.zoom) + 1;
					cameraDirection.x *= -1;
				}
				if (titleCam.y < GameJava.gh / 2 / Camera.zoom) {
					titleCam.y = (int) (GameJava.gh / 2 / Camera.zoom) + 1;
					cameraDirection.y *= -1;
				}
				if (titleCam.x > Constants.roomWidth - GameJava.gw / 2 / Camera.zoom - 8) {
					titleCam.x = (int) (Constants.roomWidth - GameJava.gw / 2 / Camera.zoom - 8) - 1;
					cameraDirection.x *= -1;
				}
				if (titleCam.y > Constants.roomHeight - GameJava.gh / 2 / Camera.zoom - 8) {
					titleCam.y = (int) (Constants.roomHeight - GameJava.gh / 2 / Camera.zoom - 8) - 1;
					cameraDirection.y *= -1;
				}
				Camera.centerOn(titleCam);
				break;

			// playing
			case playing:
				
				if(isNewState) {
					Camera.zoom = 2.0f;
				}
				
				if (shouldReloadLevel) {
					loadLevel(currentLevel);
					shouldReloadLevel = false;
				}

				player.moveBasedOnInput();

				// move all the enemies
				BaseEnemy.moveEnemies();

				// move any darts
				Dart.moveDarts();

				// if the player is hitting any enemies
				if (BaseEnemy.circleHittingEnemies(player.circle) != -1) {
					Sounds.play("hit");
					showingTutorial = false;
					// subtract a life
					--player.lives;
					// if there are no lives, go to game over screen
					if (player.lives <= 0) {
						Main.transitionTo(GameState.death);
					// if the player still has lives, reset the level
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
				
			// win
			case win:
				break;
				
			}
		}

		// transition blackout 
		if (transitioning) {
		
			transitionAlpha += transitionDirection;
			
			// when screen is black, switch states and set transitionDirection to unblacken the screen
			if (transitionDirection == 1) {
				if (transitionAlpha == 100) {
					transitionDirection = -1;
					if(desiredState == GameState.playing && (state == state.titleScreen || state == state.death)) {
						Sounds.stop("title");
						Sounds.stop("boss");
						Sounds.ajustGain("mainSong",0.95f);
						Sounds.loop("mainSong");
					}
					state = desiredState;
				}
			}
			// when done transitioning, stop being in the transitioning state
			if (transitionDirection == -1) {
				if (transitionAlpha == 0) {
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
			Draw.image("back1", 500, 200);
			BaseEnemy.drawEnemies();
			break;

		// playing
		case playing:
			// draw level background
			switch (currentLevel) {
			case tutorial:
				Draw.image("back1", 500, 200);
				if(showingTutorial) {
					Draw.image("tutorial", 269, 204, 0, 2);
				}
				break;
			case two:
				Draw.image("back2", 500, 200);
				break;
			case boss:
				Draw.image("back3", 500, 200);
				break;
			}

			Dart.drawDarts();
			
			BaseEnemy.drawEnemies();

			// player hit box when in debug mode (hit f3) 
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
			
		// win
		case win:
			break;
			
		}
	}

	@Override
	public void absoluteDraw() {
		switch (state) {
		
		// title screen
		case titleScreen:
			// intro animation
			if(introPlaying) {
				if(frameCount > 150) {
					Draw.setColor(new Color(10, 10, 10, (int)Utils.mapRange(frameCount, 150, 200, 255, 0)));
				} else {
					Draw.setColor(new Color(10, 10, 10));
				}
				Draw.rect(gw / 2, gh / 2, gw, gh);
				
				// impact sounds
				if(frameCount == 50 || frameCount == 100) {
					Sounds.play("bigStep");
				}
				if(frameCount == 150) {
					Sounds.play("riser");
				}
				// ani
				if(frameCount < 50) {
					Draw.image("ani", gw/2, 100, 0, 1.0 + (50-frameCount)/10.0);
				} else {
					Draw.image("ani", gw/2, 100);
				}
				
				// mation
				if(frameCount > 50) {
					if(frameCount < 100) {
						Draw.image("mation", gw/2, 100, 0, 1.0 + (100-frameCount)/10.0);
					} else {
						Draw.image("mation", gw/2, 100);
					}
				}
				
				// game
				if(frameCount > 100) {
					if(frameCount < 150) {
						Draw.image("game", gw/2, 100, 0, 1.0 + (150-frameCount)/10.0);
					} else {
						Draw.image("game", gw/2, 100);
					}
				}
			}
			
			if(!introPlaying) {
				startButton.draw();
				Draw.image("ani", gw/2, 100);
				Draw.image("mation", gw/2, 100);
				Draw.image("game", gw/2, 100);
			}
			break;

		// playing
		case playing:
			// hearts and darts
			player.absDraw();
			break;

		// transition
		case transition:
			break;

		// death
		case death:
			// background
			Draw.setColor(new Color(36, 36, 36));
			Draw.rect(gw / 2, gh / 2, gw, gh);
			// you died text
			Draw.setColor(Color.RED);
			Draw.setFontSize(6);
			Draw.text("You Died", 350, 300);
			
			retryButton.draw();
			break;

		// win
		case win:
			// background
			Draw.setColor(new Color(36, 36, 36));
			Draw.rect(gw / 2, gh / 2, gw, gh);
			// you win text
			Draw.setColor(Color.YELLOW);
			Draw.setFontSize(6);
			Draw.text("You Win!", 350, 300);
			// spinning enemies for no reason
			Draw.image("enemy3", gw / 2 + 100, gh / 2, frameCount / 100.0, 4);
			Draw.image("enemy3", gw / 2 - 100, gh / 2, frameCount / -100.0, 4);
			break;
		}

		// transition blackout
		if (transitioning) {
			// set color to black with desired alpha level 
			Draw.setColor(new Color(0, 0, 0,  (int) Utils.mapRange(transitionAlpha, 0, 100, 0, 255)));
			// over lay color
			Draw.rect(gw / 2, gh / 2, gw, gh);
			// level specific text if the player hasen't won, or died
			Draw.setColor(Color.WHITE);
			Draw.setFontSize(4);
			if (desiredState != GameState.death && desiredState != GameState.win) {
				switch (currentLevel) {
				case tutorial:
					Draw.text("Stage 1", gw / 2 - 100, gh / 2 - 20);
					break;
				case two:
					Draw.text("Stage 2", gw / 2 - 100, gh / 2 - 20);
					break;
				case boss:
					Draw.text("Final Stage", gw / 2 - 150, gh / 2 - 20);
					break;
				}
			}
		}
	}

	// transitions to a GameState
	public static void transitionTo(GameState newState) {
		transitioning = true;
		desiredState = newState;
		transitionDirection = 1;
		transitionAlpha = 0;
	}

	// resets all enemies
	public void loadLevel(Level levelToSwitchTo) {
		BaseEnemy.clearEneimes();
		Dart.clearDarts();
		player.reset();
		switch (levelToSwitchTo) {
		
		// level 1
		case tutorial:

			EnemySmall.create(300, 100, 0, 1.0);
			EnemySmall.create(400, 220, 0, -0.6);
			EnemySmall.create(400, 300, 0, -0.6);
			EnemySmall.create(520, 300, 0, -0.5);
			EnemySmall.create(620, 200, 0, 1.5);
			EnemySmall.create(620, 250, 0.6, 0);

			EnemySmall.create(100, 100, 0.8, 0);
			EnemySmall.create(50, 300, 0.6, 0);

			EnemyMedium.create(750, 150, 0, 0.2);
			EnemyMedium.create(750, 200, 0, 0.2);
			EnemyMedium.create(750, 250, 0, 0.2);
			EnemyMedium.create(750, 300, 0, 0.2);

			player.setPosition(200, 200);
			break;
			
		// level 2
		case two:
			for (int i = 100; i < 500; i += 20) {
				EnemySmall.create(i, 25 + i / 1.5, 0, 1.0);
			}

			EnemyMedium.create(550, 75, 0, 0);
			EnemyMedium.create(550, 110, 0, 0);
			EnemyMedium.create(550, 290, 0, 0);
			EnemyMedium.create(550, 325, 0, 0);

			EnemyMedium.create(900, 75, 0, 0);
			EnemyMedium.create(900, 110, 0, 0);
			EnemyMedium.create(900, 290, 0, 0);
			EnemyMedium.create(900, 325, 0, 0);

			EnemySmall.create(600, 60, -0.6, 0);
			EnemySmall.create(650, 80, -0.8, 0);
			EnemySmall.create(700, 100, -0.8, 0);
			EnemySmall.create(750, 120, -0.6, 0);

			EnemySmall.create(750, 280, -0.6, 0);
			EnemySmall.create(600, 300, -0.8, 0);
			EnemySmall.create(650, 320, -0.8, 0);
			EnemySmall.create(700, 340, -0.6, 0);

			EnemyMedium.create(750, 150, 0, 0);
			EnemyMedium.create(750, 180, 0, 0);
			EnemyMedium.create(750, 215, 0, 0);
			EnemyMedium.create(750, 250, 0, 0);

			player.setPosition(25, 200);
			break;
		
		// final stage
		case boss:
			EnemyBoss.create(590, 150, 0, 0);
			EnemyBoss.create(590, 250, 0, 0);
			player.setPosition(25, 200);
			break;
			
		}
	}

}