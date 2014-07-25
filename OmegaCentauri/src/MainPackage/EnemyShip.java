package MainPackage;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public abstract class EnemyShip extends Ship{

    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health)
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);
    }
    
    public abstract void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> otherShips);
    
    public abstract int getID();
    
    public abstract Point2D.Double getLocation();
}
