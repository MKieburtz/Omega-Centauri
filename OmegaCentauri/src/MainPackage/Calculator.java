
package MainPackage;

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
        return (double) (Math.cos(Math.toRadians(angle)));
    }

    public static double CalcAngleMoveY(double angle) {
        return (double) (Math.sin(Math.toRadians(angle)));
    }
    
    public static Point2D.Double CalcPositionToShoot(Point2D.Double centerLocation, double radius, double shipAngle) {
        /*
         * a point on the outer edge of a circle given the center of a rectangle
         * bounding box (cx, cy), the radius (r) and the angle where the ship is pointing
         * (a) is
         * x = cx + r + Math.cos(Math.toRadians(a));
         * y = cy + r + Math.sin(Math.toRadians(a));
         * 
         * 
         */

        double x = centerLocation.x + radius + (Math.cos(Math.toRadians(shipAngle)));
        double y = centerLocation.y + radius + (Math.sin(Math.toRadians(shipAngle)));
        
        
        return new Point2D.Double(x, y);
    }
}
