package MainPackage;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Calculator {
    /* 
     * this class that will have all sorts of useful methods to help
     * calculate stuff
     */


    public static double CalcAngleMoveX(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double CalcAngleMoveY(double angle) {
        return Math.sin(Math.toRadians(angle));
    }
    //@returns distance between two points

    public static double getDistance(Point2D.Double pt1, Point2D.Double pt2) {
        return Math.sqrt(Math.pow(Math.abs(pt2.x - pt1.x), 2) + Math.pow(Math.abs(pt2.y - pt1.y), 2));
    }

    //@returns angle between two points IN DEGREES
    public static double getAngleBetweenTwoPoints(Point2D.Double pt1, Point2D.Double pt2) {
        double angle = (double) Math.toDegrees(Math.atan2(pt2.x - pt1.x, pt2.y - pt1.y));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
    
    public static boolean collisionCheck(Rectangle2D.Double hitbox1,Rectangle2D.Double hitbox2)
    {
            return hitbox1.intersects(hitbox2);
    }
    
    public static double[] getDistancesBetweenAngles(double angle1, double angle2)
    {
        if (angle1 > angle2) {
            if (angle1 + 180 >= 360) // CASE 1
            {
                return new double[] { Math.abs((360 - angle1) + angle2), Math.abs(angle1 - angle2) };
            } else if (angle1 + 180 < 360) // CASE 2
            {
                return new double[] { Math.abs((360 - angle2) + angle1), Math.abs(angle2 - angle1) };
            }
        } else if (angle2 > angle1) {
            if (angle2 + 180 >= 360) // CASE 3
            {
                return new double[] { Math.abs(angle2 - angle1), Math.abs(360 - (angle2 - angle1)) };
            } else if (angle2 + 180 < 360) // CASE 4
            {
                return new double[] { Math.abs(angle2 - angle1), Math.abs(360 - (angle2 - angle1)) };
            }
        }
        
        System.err.println("ERROR!");
        return null;
    }
}
