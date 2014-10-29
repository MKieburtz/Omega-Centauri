package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class EllipseHitbox {
    
    private Point2D.Double centerPoint = new Point2D.Double();
    // this is the longer "radius" length
    private double semiMajorAxisLength;
    // this is the shorter "radius" length
    private double semiMinorAxisLength;
    
    private double horizontalRadiusLength;
    private double verticalRadiusLength;
    
    private double angle = 0;
    
    private boolean circle;
    
    public EllipseHitbox(double horizontalLength, double verticalLength)
    {
        this.horizontalRadiusLength = horizontalLength;
        this.verticalRadiusLength = verticalLength;
        
        this.semiMajorAxisLength = horizontalLength >= verticalLength ? horizontalLength : verticalLength;
        this.semiMinorAxisLength = semiMajorAxisLength == verticalLength ? horizontalLength : verticalLength;
        
        circle = semiMajorAxisLength == semiMinorAxisLength;
    }
    
    public void rotateToAngle(double angle)
    {
        this.angle = angle;
        this.angle = Calculator.confineAngleToRange(this.angle);
    }
    
    public void rotateRelative(double amount)
    {
        angle += amount;
        angle = Calculator.confineAngleToRange(angle);
    }
    
    public void moveToLocation(Point2D.Double location)
    {
        this.centerPoint = location;
    }
    
    public void moveRelative(Point2D.Double distance)
    {
        centerPoint.x += distance.x;
        centerPoint.y += distance.y;
    }
    
    public void draw(Graphics2D g2d)
    {
        Line2D.Double line1 = new Line2D.Double(centerPoint.x - horizontalRadiusLength, centerPoint.y, centerPoint.x, centerPoint.y - verticalRadiusLength);
        Line2D.Double line2 = new Line2D.Double(centerPoint.x, centerPoint.y - verticalRadiusLength, centerPoint.x + horizontalRadiusLength, centerPoint.y);
        Line2D.Double line3 = new Line2D.Double(centerPoint.x + horizontalRadiusLength, centerPoint.y, centerPoint.x, centerPoint.y + verticalRadiusLength);
        Line2D.Double line4 = new Line2D.Double(centerPoint.x, centerPoint.y + verticalRadiusLength, centerPoint.x + horizontalRadiusLength, centerPoint.y);
        g2d.draw(line1);
        g2d.draw(line2);
        g2d.draw(line3);
        g2d.draw(line4);
    }
    
    public boolean collides(RectangularHitbox other)
    {
        Point2D.Double distanceToRect = getDistanceToEdgeOfRectangle(other.getCenterPoint(), centerPoint, other.getDimensions(), other.getAngle());
        return true;
    }
    
    private Point2D.Double getDistanceToEdgeOfRectangle(Point2D.Double rectCenter, Point2D.Double point, Dimension rectDimensions, double rectAngle)
    {
        double relx = point.x - rectCenter.x;
        double rely = point.y - rectCenter.y;
        double rotx = relx * Math.cos(Math.toRadians(-rectAngle)) - rely * Math.sin(Math.toRadians(-rectAngle));
        double roty = relx * Math.sin(Math.toRadians(-rectAngle)) + rely * Math.cos(Math.toRadians(-rectAngle));
        
        return new Point2D.Double(
                Math.max(Math.abs(rotx) - rectDimensions.width / 2, 0),
                Math.max(Math.abs(roty) - rectDimensions.height / 2, 0)
        );
    }
    
    /*
    * this method computes which quadrant of a graph formed by the center point
    * of the rect of the origin the (x, y) is relative to the (cx, cy)
    *  1____|____2
    *  |    |    |
    *  | ___|___ | ________
    *  |    |    | 
    * 3.....|.....4
    *
    * 1. {-1, -1}
    * 2. {1, -1}
    * 3. {-1, 1}
    * 4. {1, 1}
    * 
    * 1 means add to get to the point, -1 means subtract. If (x, y) lies on 
    * any of the axies, then the output will contain a 0
    */
    private int[] getDistanceModification(Point2D.Double centerLocation, Point2D.Double outsideLocation)
    {
        // 1
        if (outsideLocation.x < centerLocation.x && outsideLocation.y < centerLocation.y)
        {
            return new int[] {-1, -1};
        }
        // 2
        else if (outsideLocation.x > centerLocation.x && outsideLocation.y < centerLocation.y)
        {
            return new int[] {1, -1};
        }
        // 3
        else if (outsideLocation.x < centerLocation.x && outsideLocation.y > centerLocation.y)
        {
            return new int[] {-1, 1};
        }
        // 4
        else if (outsideLocation.x > centerLocation.x && outsideLocation.y > centerLocation.y)
        {
            return new int[] {1, 1};
        }
        // same point
        if (outsideLocation.x == centerLocation.x && outsideLocation.y == centerLocation.y)
        {
            return new int[] {0, 0};
        }
        // smae x
        if (outsideLocation.x == centerLocation.x)
        {
            // above the point
            if (outsideLocation.y < centerLocation.y)
            {
                return new int[] {0, -1};
            }
            // below the point
            else // outsideLocation.y > centerLocation.y
            {
                return new int[] {0, 1};
            }
        }
        // same y
        else if (outsideLocation.y == centerLocation.y)
        {
            // to the left of the point
            if (outsideLocation.x < centerLocation.x)
            {
                return new int[] {-1, 0};
            }
            // to the right of the point
            else // outsideLocation.x > centerLocation.x
            {
                return new int[] {1, 0};
            }
        }
        
        return null;
    }
}
