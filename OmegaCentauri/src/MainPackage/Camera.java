package MainPackage;
import java.awt.*;
import java.awt.geom.*;
// @author Michael Kieburtz

public class Camera {
    private Point size;
    private Point2D.Double location;
    
    public Camera(int width, int height)
    {
        size = new Point(width, height);
        location = new Point2D.Double(0.0, 0.0);
    }
    
    public void move(double x, double y) 
    {
        location.x = x; 
        location.y = y;
    }
    
    public Point2D.Double getLocation()
    {
        return this.location;
    }
    
    public Point getSize()
    {
        return this.size;
    }
    
    public boolean insideView(Point2D.Double point)
    {
        // use nested if statements because the conditionals are so long.
        
        if (point.x > location.x && point.x < location.x + size.x)
        {
            if (point.y > location.y && point.y < location.y + size.y)
            {
                return true;
            }
        }
        
        return false;
    }
}
