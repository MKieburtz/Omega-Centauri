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
        if (controllingShip != null)
        {
            controllingShip.setControllingShip(false); // uncontrol the previous
        }
        
        controllingShip = shipToControl;
        controllingShip.setControllingShip(true);
    }
    
//    public void highLightShips(ArrayList<Ally> allyShips)
//    {
//        allyShips.remove(controllingShip);
//        
//        Ally closest = allyShips.get(0);
//        
//        for (Ally a : allyShips)
//        {
//            if (Calculator.getDistance(controllingShip.getLocation(), a.getLocation()) 
//                    < Calculator.getDistance(controllingShip.getLocation(), closest.getLocation()))
//            {
//                closest = a;
//            }
//        }
//        
//        closest.setHighlighted(true);
//    }
    
    public void update()
    {
        controllingShip.update();
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
