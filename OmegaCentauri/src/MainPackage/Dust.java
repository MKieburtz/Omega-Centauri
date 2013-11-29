package MainPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
//@author Michael Kieburtz
public class Dust {
    private Point2D.Double location;
    private final int dimension = 2;
    private Rectangle2D.Double image;
    
    public Dust(double x, double y)
    {
        location = new Point2D.Double(x, y);
        image = new Rectangle2D.Double(this.location.x, this.location.y, dimension, dimension);
    }
    public Dust(Point2D.Double location)
    {
        this.location = new Point2D.Double(location.x, location.y);
        image = new Rectangle2D.Double(this.location.x, this.location.y, dimension, dimension);
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {   
        image.x = location.x - cameraLocation.x;
        image.y = location.y - cameraLocation.y;
        
        
        g2d.setColor(Color.GRAY);
        g2d.fill(image);
    }
    
    public Point2D.Double getLocation()
    {
        return location;
    }
}
