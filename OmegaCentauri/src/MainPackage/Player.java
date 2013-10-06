package MainPackage;
import java.awt.*;
import java.io.File;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {
    
    private String name;
    
    public String getName()
    {
        return this.name;
    }
    
    public Player(int x, int y, Type shipType)
    {
        location = new Point(x, y);
        type = shipType;
        imageFile = new File("resources/FighterGrey.png");
        setUpShipImage();
    }
    
    public Point getLocation()
    {
        return location;
    }
    
    public void moveTo(int x, int y)
    {
        location.move(x, y);
    }
    
    public void moveRelitive(int dx, int dy)
    {
        location.move(location.x + dx, location.y + dy);
    }
    
}
