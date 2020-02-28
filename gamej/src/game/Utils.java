package game;

import game.physics.Point;

public class Utils {
    public static double pointTo(Point point,Point targetPoint) {
        double adjacent = (targetPoint.x - point.x);
        double opposite = (targetPoint.y - point.y);
        double h = Math.atan2(opposite, adjacent);
        return h;
    }
    public static double pointTo(double x1,double y1, double x2, double y2) {
        double adjacent = (x2 - x1);
        double opposite = (y2 - y1);
        double h = Math.atan2(opposite, adjacent);
        return h;
    }
}