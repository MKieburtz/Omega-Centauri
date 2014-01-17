package MainPackage;

// @author Michael Kieburtz

import java.awt.geom.Point2D;


public abstract class EnemyShip extends Ship{
    
    public EnemyShip(int x, int y, Type shipType, Point2D.Double maxVelocity)
            // delegate assigning images to the types of ships
    {
        super(x, y, shipType);
    }
}
