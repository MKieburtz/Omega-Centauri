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
        if (shipToControl != null)
        {
            if (controllingShip != null)
            {
                controllingShip.setControllingShip(false); // uncontrol the previous
            }

            controllingShip = shipToControl;
            controllingShip.setControllingShip(true);
        }
        else
        {
            controllingShip = null;
        }
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
    
    public boolean isControllingShip()
    {
        return controllingShip != null;
    }
    
    public void update()
    {
        if (isControllingShip())
            controllingShip.update();
    }
    
    public void update(ArrayList<Command> commands)
    {
        if (isControllingShip())
            controllingShip.update(commands);
    }
    
    public void draw(Graphics2D g2d) 
    {
        if (isControllingShip())
            controllingShip.draw(g2d);
    }
    
    public Point2D.Double getShipLocation()
    {
        if (isControllingShip())
            return controllingShip.getLocation();
        return null;
    }
    
    public BufferedImage getShipActiveImage()
    {
        if (isControllingShip())
            return controllingShip.getActiveImage();
        return null;
    }
    
    public Ship getControllingShip()
    {
        if (isControllingShip())
            return controllingShip;
        return null;
    }
    
    public ImageRotationState getImageRotationState()
    {
        if (isControllingShip())
            return controllingShip.getImageRotationState();
        return null;
    }
    
    public ImageMovementState getImageMovementState()
    {
        if (isControllingShip())
            return controllingShip.getImageMovementState();
        return null;
    }
}
