package utility;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Martin on 10-03-2017.
 */
public class Utility {
	/**
	 * Returns a 2d point on the line formed by two points, t determines the exact location.
	 * t=0 is at p1, t=1 is at p2. a t between 0-1 is the interpolated point, otherwise the point is extrapolated.
	 * @param p1 the first point
	 * @param p2 the second point
	 * @param t the thingamajig
	 * @return the interpolated/extrapolated point
	 */
	public static Point2D interpolateLinear(Point p1, Point p2, double t){
		return new Point2D.Double(p1.x*(1-t)+p2.x*t, p1.y*(1-t)+p2.y*t);
	}
}
