package MainPackage;

import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class ShapeHitbox extends Area implements Hitbox{

    private Point2D.Double[] points = new Point2D.Double[4];
    private Point2D.Double centerPoint = new Point2D.Double();
    private double angle = 0;
    
    public ShapeHitbox(Point2D.Double[] points, Point2D.Double centerPoint)
    {
        this.points = points;
        this.centerPoint = centerPoint;
        setShape();
    }
    
    @Override
    public void rotateToAngle(double angle) {
        if (this.angle - angle == 0)
        {
            return;
        }
        for (Point2D.Double point : points)
        {
            Point2D.Double newPoint = Calculator.rotatePointAroundPoint(point, centerPoint, angle - this.angle);
            
            point.x = newPoint.x;
            point.y = newPoint.y;
        }
        this.angle = angle;
        setShape();
    }

    @Override
    public void rotateRelitive(double amount) {
         for (Point2D.Double point : points)
        {
            Point2D.Double newPoint = Calculator.rotatePointAroundPoint(point, centerPoint, angle);
            point.x = newPoint.x;
            point.y = newPoint.y;
        }
        this.angle += amount;
        setShape();
    }

    @Override
    public void moveToLocation(Point2D.Double location) {
        double distanceX = location.x - centerPoint.x;
        double distanceY = location.y - centerPoint.y;
        centerPoint.x += distanceX;
        centerPoint.y += distanceY;
        for (Point2D.Double point : points)
        {
            point.x += distanceX;
            point.y += distanceY;
        }
        setShape();
    }

    @Override
    public void moveRelitive(Point2D.Double distance) {
        centerPoint.x += distance.x;
        centerPoint.y += distance.y;
        for (Point2D.Double point : points)
        {
            point.x += distance.x;
            point.y += distance.y;
        }
        setShape();
    }


    Area intersection;
    @Override
    public boolean collides(RectangularHitbox other) {
        intersection = (Area)other.clone();
        intersection.intersect(this);
        //Toolkit.getDefaultToolkit().beep();
        return !intersection.isEmpty();
    }

    @Override
    public boolean collides(ShapeHitbox other) {
        intersection = (Area)other.clone();
        intersection.intersect(this);
        //Toolkit.getDefaultToolkit().beep();
        return !intersection.isEmpty();
    }

    private void setShape()
    {
        reset();
        
        Path2D.Double pathToAdd = new Path2D.Double();
        
        pathToAdd.moveTo(points[0].x, points[0].y);
        
        for (int i = 1; i < points.length; i++)
        {
            pathToAdd.lineTo(points[i].x, points[i].y);
        }
        
        pathToAdd.closePath(); // just in case
        
        this.add(new Area(pathToAdd));
    }
}
