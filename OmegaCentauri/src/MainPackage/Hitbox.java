package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public class Hitbox extends Area {
    // in order of addition
    private ArrayList<Point> points = new ArrayList<>();
    private Point rotationPoint;
    private double angle = 0;
    
    public Hitbox(ArrayList<Point> points, Point rotationPoint)
    {
        this.points = points;
        this.rotationPoint = rotationPoint;
        setShape();
    }
    
    public void rotateToAngle(double angle)
    {
        for (Point point : points)
        {
            point.x = (int)Math.round(rotationPoint.x + (point.x - rotationPoint.x) * Math.cos(Math.toRadians(angle - this.angle)) - (point.y - rotationPoint.y) * Math.sin(Math.toRadians(angle - this.angle)));
            point.y = (int)Math.round(rotationPoint.y + (point.x - rotationPoint.x) * Math.sin(Math.toRadians(angle - this.angle)) + (point.y - rotationPoint.y) * Math.cos(Math.toRadians(angle - this.angle)));
        }
        this.angle = angle;
        //System.out.println(points);
        setShape();
    }
    
    public void rotateRelivite(double angle)
    {
        for (Point point : points)
        {
            point.x = (int)Math.round(rotationPoint.x + (point.x - rotationPoint.x) * Math.cos(Math.toRadians(angle)) - (point.y - rotationPoint.y) * Math.sin(Math.toRadians(angle)));
            point.y = (int)Math.round(rotationPoint.y + (point.x - rotationPoint.x) * Math.sin(Math.toRadians(angle)) + (point.y - rotationPoint.y) * Math.cos(Math.toRadians(angle)));
        }
        this.angle += angle;
        //System.out.println(points);
        setShape();
    }
    
    public void moveToLocation(Point location)
    {
        double distanceX = location.x - rotationPoint.x;
        double distanceY = location.y - rotationPoint.y;
        rotationPoint.x += distanceX;
        rotationPoint.y += distanceY;
        for (Point point : points)
        {
            point.x += distanceX;
            point.y += distanceY;
        }
    }
    
    public void moveRelitive(Point distance)
    {
        rotationPoint.x += distance.x;
        rotationPoint.y += distance.y;
        for (Point point : points)
        {
            point.x += distance.x;
            point.y += distance.y;
        }
    }
    
    public boolean collides(Hitbox other)
    {
        Area intersection = (Area)other.clone();
        intersection.intersect(this);
        
        return !intersection.isEmpty();
    }
    
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.RED);
        for (int i = 0; i < points.size(); i++)
        {
            if (i != points.size() - 1)
            {
                g2d.draw(new Line2D.Double(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y));
            }
            else
            {
                g2d.draw(new Line2D.Double(points.get(i).x, points.get(i).y, points.get(0).x, points.get(0).y));
            }
        }
        
        g2d.drawOval(100, 100, 100, 100);
        
        g2d.drawRect(rotationPoint.x, rotationPoint.y, 1, 1);
    }
    
    private void setShape()
    {
        reset();
        
        Polygon shapeToAdd = new Polygon();
        
        for (Point point : points)
        {
            shapeToAdd.addPoint(point.x, point.y);
        }
        
        add(new Area(shapeToAdd));
    }
}
