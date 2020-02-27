package game.physics;

public class Rect {
	public double x;
	public double y;
	public int w;
	public int h;

	public int halfW;
	public int halfH;

	public Rect(double x, double y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		this.halfW = w / 2;
		this.halfH = h / 2;
	}

	public void resize(int w, int h) {
		this.w = w;
		this.h = h;
		this.halfW = w / 2;
		this.halfH = h / 2;
	}
}
