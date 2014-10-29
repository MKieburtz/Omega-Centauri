package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class RectangularHitbox extends Area {
    // in order of addition counter clockwise from top left!!
    private Point2D.Double[] points = new Point2D.Double[4];
    private Point2D.Double centerPoint = new Point2D.Double();
    private Dimension dimensions;
    private double angle = 0;
    
    public RectangularHitbox(Point2D.Double[] points)
    {
        this.points = points;
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
            double newX = centerPoint.x + (point.x - centerPoint.x) * Math.cos(Math.toRadians(360 - (angle - this.angle))) - (point.y - centerPoint.y) * Math.sin(Math.toRadians(360 - (angle - this.angle)));
            double newY = centerPoint.y + (point.x - centerPoint.x) * Math.sin(Math.toRadians(360 - (angle - this.angle))) + (point.y - centerPoint.y) * Math.cos(Math.toRadians(360 - (angle - this.angle)));
            point.x = newX;
            point.y = newY;
        }
        this.angle = angle;
        //System.out.println(points);
        setShape();
    }
    
    public void rotateRelivite(double angle)
    {
        for (Point2D.Double point : points)
        {
            double newX =centerPoint.x + (point.x - centerPoint.x) * Math.cos(Math.toRadians(360 - angle)) - (point.y - centerPoint.y) * Math.sin(Math.toRadians(360 - angle));
            double newY =centerPoint.y + (point.x - centerPoint.x) * Math.sin(Math.toRadians(360 - angle)) + (point.y - centerPoint.y) * Math.cos(Math.toRadians(360 - angle)); 
            point.x = newX;
            point.y = newY;
        }
        this.angle += angle;
        //System.out.println(points);
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
    
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.RED);
        for (int i = 0; i < points.length; i++)
        {
            if (i != points.length - 1)
            {
                g2d.draw(new Line2D.Double(points[i].x, points[i].y, points[i + 1].x, points[i].y));
            }
            else
            {
                g2d.draw(new Line2D.Double(points[i].x, points[i].y, points[i].x, points[i].y));
            }
        }
        
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
}
