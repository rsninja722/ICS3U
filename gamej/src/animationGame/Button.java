package animationGame;

import java.awt.Color;
import java.util.ArrayList;

import animationGame.Main.GameState;
import game.Input;
import game.drawing.Draw;
import game.physics.Physics;
import game.physics.Rect;

public class Button {

	static Color backGroundColor = new Color(46, 46, 46);
	static Color hoverColor = new Color(66,66,66);
	static Color outLineColor = new Color(76, 76, 76);
	static Color textColor = new Color(255, 255, 255);

	Rect rect;
	Runnable callBack;
	String text;

	Button(int x, int y, int w, int h, String text, Runnable callBack) {
		this.rect = new Rect(x, y, w, h);
		this.callBack = callBack;
		this.text = text;
	}

	void draw() {
		if (Physics.rectpoint(this.rect, Input.rawMousePos)) {
			Draw.setColor(hoverColor);
		} else {
			Draw.setColor(backGroundColor);
		}
		Draw.rect(this.rect);
		Draw.setColor(outLineColor);
		Draw.setLineWidth(5.0f);
		Draw.rectOutline(this.rect);
		Draw.setColor(textColor);
		Draw.setFontSize(4);
		Draw.text(this.text, (int) (10 + this.rect.x - this.rect.w/2), (int) this.rect.y + 10);
	}

	void update() {
		if (Physics.rectpoint(this.rect, Input.rawMousePos) && Input.mouseClick(0)) {
			this.callBack.run();
		}
	}

	// start button
	static void startButton() {
		Main.transitionTo(GameState.playing);
	}
	
	// retry button
	static void retryButton() {
		Main.currentLevel = Main.Level.tutorial;
		Main.player = new Player();
		Main.shouldReloadLevel = true;
		Main.transitionTo(GameState.playing);
	}
}
