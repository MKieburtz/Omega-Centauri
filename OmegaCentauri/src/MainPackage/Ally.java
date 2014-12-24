package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Ally extends Ship {

    public Ally(int x, int y, Type shipType, double baseMaxVel, double maxVel, double maxAngleVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) 
    {
        super(x, y, shipType, baseMaxVel, maxVel, maxAngleVel, angleIncrement, acceleration, shootingDelay, 
             health);
    }

    @Override
    public void shoot(Point2D.Double cameraLocation) 
    {
        
    }
}
