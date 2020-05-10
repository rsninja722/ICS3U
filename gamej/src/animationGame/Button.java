package animationGame;

// buttons used in GUI

import java.awt.Color;
import java.util.ArrayList;

import animationGame.Main.GameState;
import game.Input;
import game.audio.Sounds;
import game.drawing.Draw;
import game.physics.Physics;
import game.physics.Rect;

public class Button {

	// colors for all buttons
	static Color backGroundColor = new Color(46, 46, 46);
	static Color hoverColor = new Color(66,66,66);
	static Color outLineColor = new Color(76, 76, 76);
	static Color textColor = new Color(255, 255, 255);

	// button size and collision box
	Rect rect;
	// what to call when clicked
	Runnable callBack;
	// text to display
	String text;
	
	boolean shouldPlaySound = true;

	Button(int x, int y, int w, int h, String text, Runnable callBack) {
		this.rect = new Rect(x, y, w, h);
		this.callBack = callBack;
		this.text = text;
	}

	void draw() {
		// draw background color based on if the player is hovering over the button
		if (Physics.rectpoint(this.rect, Input.rawMousePos)) {
			if(shouldPlaySound) {
				Sounds.play("click");
				shouldPlaySound = false;
			}
			Draw.setColor(hoverColor);
		} else {
			shouldPlaySound = true;
			Draw.setColor(backGroundColor);
		}
		Draw.rect(this.rect);
		// outline
		Draw.setColor(outLineColor);
		Draw.setLineWidth(5.0f);
		Draw.rectOutline(this.rect);
		// text
		Draw.setColor(textColor);
		Draw.setFontSize(4);
		Draw.text(this.text, (int) (10 + this.rect.x - this.rect.w/2), (int) this.rect.y + 10);
	}

	void update() {
		// when clicked, call callback
		if (Physics.rectpoint(this.rect, Input.rawMousePos) && Input.mouseClick(0)) {
			Sounds.play("bigStep");
			this.callBack.run();
		}
	}

	// start button callback
	static void startButton() {
		Main.transitionTo(GameState.playing);
	}
	
	// retry button callback
	static void retryButton() {
		Main.currentLevel = Main.Level.tutorial;
		Main.player = new Player();
		Main.shouldReloadLevel = true;
		Main.transitionTo(GameState.playing);
	}
}
