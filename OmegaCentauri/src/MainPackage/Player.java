package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

/*
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Player
{   
    private Ally controllingShip;
    
    public void controlShip(Ally shipToControl)
    {
        controllingShip = shipToControl;
    }
    
    public void update(ArrayList<Command> commands)
    {
        controllingShip.update(commands);
    }
    
    public void draw(Graphics2D g2d) 
    {
        controllingShip.draw(g2d);
    }
    
    public Point2D.Double getShipLocation()
    {
        return controllingShip.getLocation();
    }
    
    public BufferedImage getShipActiveImage()
    {
        return controllingShip.getActiveImage();
    }
    
    public Ship getControllingShip()
    {
        return controllingShip;
    }
    
    public ImageRotationState getImageRotationState()
    {
        return controllingShip.getImageRotationState();
    }
    
    public ImageMovementState getImageMovementState()
    {
        return controllingShip.getImageMovementState();
    }
}
