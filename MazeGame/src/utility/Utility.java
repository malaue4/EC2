package utility;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Martin on 10-03-2017.
 */
public class Utility {
	public static Point2D interpolateLinear(Point p1, Point p2, double t){
		return new Point2D.Double(p1.x*t+p2.x*(1-t), p1.y*t+p2.y*(1-t));
	}
}
