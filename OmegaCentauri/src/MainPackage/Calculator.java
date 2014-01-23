
package MainPackage;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class Calculator { 
    /* 
     * this class that will have all sorts of useful methods to help
     * calculate stuff
     */
    
    private Calculator() {
        // never used because all methods will be static
    }
    
    public static double CalcAngleMoveX(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double CalcAngleMoveY(double angle) {
        return Math.sin(Math.toRadians(angle));
    }
    //@returns distance between two points
    public static double getDistance(Point2D.Double pt1, Point2D.Double pt2)
    {
        return Math.sqrt(Math.pow(Math.abs(pt2.x - pt1.x), 2) + Math.pow(Math.abs(pt2.y - pt1.y), 2));
    }
    
    //@returns angle between two points IN DEGREES
    public static double getAngleBetweenTwoPoints(Point2D.Double pt1, Point2D.Double pt2)
    {
        double angle = (double)Math.toDegrees(Math.atan2(pt2.x - pt1.x, pt2.y - pt1.y));
        
        if (angle < 0)
            angle += 360;
        
        return angle;
    }
}
