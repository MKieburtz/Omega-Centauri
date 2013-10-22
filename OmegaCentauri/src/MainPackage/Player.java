package MainPackage;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {
    
    private String name;
    private double angle = 0; // maybe move to Ship Class
    private int speed = 2;
    public String getName()
    {
        return this.name;
    }
    
    public Player(int x, int y, Type shipType)
    {
        location = new Point2D.Double(x, y);
        type = shipType;
        imageFile = new File("resources/FighterGrey.png");
        setUpShipImage();
    }
    
    
    public Point2D.Double getLocation()
    {
        return location;
    }
    
    public void moveTo(double x, double y)
    {
        location.x = x;
        location.y = y;
    }
    
    public void moveTo(Point2D.Double location)
    {
        this.location.x = location.x;
        this.location.y = location.y;
    }
    
    public void moveRelitive(double dx, double dy)
    {
        this.location.x += dx;
        this.location.y += dy;
    }

    public Player() {
    }
    
    public void rotate(boolean positive)
    {
        if (positive)
            angle += 5;
        else if (!positive && angle == 0)
        {
            angle = 360;
            angle -= 5;
        }
        else
            angle -= 5;
            
        
        if (angle == 360)
            angle = 0;
        
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public int getSpeed()
    {
        return speed;
    }
}
