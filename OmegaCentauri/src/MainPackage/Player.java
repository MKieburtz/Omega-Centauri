package MainPackage;
import java.awt.*;
import java.io.File;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {
    
    private String name;
    private DrawingPanel panel;
    
    public String getName()
    {
        return this.name;
    }
    
    public Player(int x, int y, Type shipType, DrawingPanel panel)
    {
        this.panel = panel;
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
        panel.redraw();
    }
    
}
