package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public class Hitbox extends Area {
    // in order of addition
    private ArrayList<Point2D.Double> points = new ArrayList<>();
    private Point2D.Double rotationPoint;
    private double angle = 0;
    
    public Hitbox(ArrayList<Point2D.Double> points, Point2D.Double rotationPoint)
    {
        this.points = points;
        this.rotationPoint = rotationPoint;
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
            double newX = rotationPoint.x + (point.x - rotationPoint.x) * Math.cos(Math.toRadians(360 - (angle - this.angle))) - (point.y - rotationPoint.y) * Math.sin(Math.toRadians(360 - (angle - this.angle)));
            double newY = rotationPoint.y + (point.x - rotationPoint.x) * Math.sin(Math.toRadians(360 - (angle - this.angle))) + (point.y - rotationPoint.y) * Math.cos(Math.toRadians(360 - (angle - this.angle)));
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
            double newX =rotationPoint.x + (point.x - rotationPoint.x) * Math.cos(Math.toRadians(360 - angle)) - (point.y - rotationPoint.y) * Math.sin(Math.toRadians(360 - angle));
            double newY =rotationPoint.y + (point.x - rotationPoint.x) * Math.sin(Math.toRadians(360 - angle)) + (point.y - rotationPoint.y) * Math.cos(Math.toRadians(360 - angle)); 
            point.x = newX;
            point.y = newY;
        }
        this.angle += angle;
        //System.out.println(points);
        setShape();
    }
    
    public void moveToLocation(Point2D.Double location)
    {
        double distanceX = location.x - rotationPoint.x;
        double distanceY = location.y - rotationPoint.y;
        rotationPoint.x += distanceX;
        rotationPoint.y += distanceY;
        for (Point2D.Double point : points)
        {
            point.x += distanceX;
            point.y += distanceY;
        }
        setShape();
    }
    
    public void moveRelitive(Point2D.Double distance)
    {
        rotationPoint.x += distance.x;
        rotationPoint.y += distance.y;
        for (Point2D.Double point : points)
        {
            point.x += distance.x;
            point.y += distance.y;
        }
        setShape();
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
        
    }
    
    private void setShape()
    {
        reset();
        
        Path2D.Double pathToAdd = new Path2D.Double();
        
        pathToAdd.moveTo(points.get(0).x, points.get(0).y);
        
        for (int i = 1; i < points.size(); i++)
        {
            pathToAdd.lineTo(points.get(i).x, points.get(i).y);
        }
        
        pathToAdd.closePath(); // just in case
        
        this.add(new Area(pathToAdd));
    }
}
