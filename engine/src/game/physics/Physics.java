package game.physics;

public class Physics {

	// distance between two points
	public static double dist(double x1, double y1, double x2, double y2) {
		double one = (x2 - x1);
		double two = (y2 - y1);
		return Math.sqrt((one * one) + (two * two));
    }
    public static double dist(Point point1, Point point2) {
		double one = (point2.x - point1.x);
		double two = (point2.y - point1.y);
		return Math.sqrt((one * one) + (two * two));
	}

	// circle on circle
	public static boolean circlecircle(Circle circle1, Circle circle2) {
		if (dist(circle1.x, circle1.y, circle2.x, circle2.y) < (circle1.r + circle2.r)) {
			return true;
		} else {
			return false;
		}
	}

	// rectangle on rectangle
	public static boolean rectrect(Rect rect1, Rect rect2) {
		if (rect1.x + rect1.halfW >= rect2.x - rect2.halfW && rect1.x - rect1.halfW <= rect2.x + rect2.halfW
				&& rect1.y + rect1.halfH >= rect2.y - rect2.halfH && rect1.y - rect1.halfH <= rect2.y + rect2.halfH) {
			return true;
		} else {
			return false;
		}
	}
	
	// point on rectangle
	public static boolean rectpoint(Rect rect,Point point) {
	    if(rect.x + rect.w/2 >= point.x &&
	       rect.x - rect.w/2 <= point.x &&
	       rect.y + rect.h/2 >= point.y &&
	       rect.y - rect.h/2 <= point.y ) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	// circle on rectangle
	public static boolean circlerect(Circle circle,Rect rect) { //credit: https://yal.cc/rectangle-circle-intersection-test/
	    double rectHalfWidth  = rect.w/2;
	    double rectHalfHeight = rect.h/2;
	    double deltaX = circle.x - Math.max(rect.x - rectHalfWidth, Math.min(circle.x, rect.x + rectHalfWidth));
	    double deltaY = circle.y - Math.max(rect.y - rectHalfHeight, Math.min(circle.y, rect.y + rectHalfHeight));
	    return (deltaX * deltaX + deltaY * deltaY) < (circle.r * circle.r);
	}
}
