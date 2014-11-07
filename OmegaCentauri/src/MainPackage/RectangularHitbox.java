package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Michael Kieburtz
 */
public class RectangularHitbox extends Area {
    // in order of addition clockwise from top left!!!!!
    private Point2D.Double[] points = new Point2D.Double[4];
    private Point2D.Double centerPoint = new Point2D.Double();
    private Point2D.Double topRightPoint = new Point2D.Double();
    private Dimension dimensions;
    private double angle = 0;
    
    public RectangularHitbox(Point2D.Double[] points)
    {
        this.points = points;
        topRightPoint = new Point2D.Double(points[0].x, points[0].y);
        dimensions = new Dimension((int)(points[1].x - points[0].x), (int)(points[3].y - points[0].y));
        centerPoint = new Point2D.Double((points[0].x - points[1].x) / 2, (points[3].y - points[0].y) / 2);
        setShape();
    }
    
    public void rotateToAngle(double angle)
    {
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
        //System.out.println(points);
        setShape();
    }
    
    public void rotateRelivite(double angle)
    {
        for (Point2D.Double point : points)
        {
            Point2D.Double newPoint = Calculator.rotatePointAroundPoint(point, centerPoint, angle);
            point.x = newPoint.x;
            point.y = newPoint.y;
        }
        this.angle += angle;
        setShape();
    }
    
    public void moveToLocation(Point2D.Double location)
    {
        double distanceX = location.x - centerPoint.x;
        double distanceY = location.y - centerPoint.y;
        centerPoint.x += distanceX;
        centerPoint.y += distanceY;
        for (Point2D.Double point : points)
        {
            point.x += distanceX;
            point.y += distanceY;
        }
        
        topRightPoint = points[0];
        setShape();
    }
    
    public void moveRelitive(Point2D.Double distance)
    {
        centerPoint.x += distance.x;
        centerPoint.y += distance.y;
        for (Point2D.Double point : points)
        {
            point.x += distance.x;
            point.y += distance.y;
        }
        topRightPoint = points[0];
        setShape();
    }
    
    Area intersection;
    public boolean collides(RectangularHitbox other)
    {
        intersection = (Area)other.clone();
        intersection.intersect(this);
        //Toolkit.getDefaultToolkit().beep();
        return !intersection.isEmpty();
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        g2d.setColor(Color.RED);
        
        Line2D.Double line1 = new Line2D.Double(Calculator.getScreenLocation(cameraLocation, points[0]), Calculator.getScreenLocation(cameraLocation, points[1]));
        Line2D.Double line2 = new Line2D.Double(Calculator.getScreenLocation(cameraLocation, points[1]), Calculator.getScreenLocation(cameraLocation, points[2]));
        Line2D.Double line3 = new Line2D.Double(Calculator.getScreenLocation(cameraLocation, points[2]), Calculator.getScreenLocation(cameraLocation, points[3]));
        Line2D.Double line4 = new Line2D.Double(Calculator.getScreenLocation(cameraLocation, points[3]), Calculator.getScreenLocation(cameraLocation, points[0]));
        
        g2d.draw(line1);
        g2d.draw(line2);
        g2d.draw(line3);
        g2d.draw(line4);

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
    
    public Point2D.Double getCenterPoint()
    {
        return centerPoint;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public Dimension getDimensions()
    {
        return dimensions;
    }
    
    public Point2D.Double getTopLeftPoint()
    {
        return topRightPoint;
    }
}