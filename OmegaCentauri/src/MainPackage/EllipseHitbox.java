package MainPackage;

import java.awt.Color;
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
        return true;
    }
}
