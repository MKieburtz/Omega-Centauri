package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public abstract class Enemy extends Ship
{
    protected Ally targetShip;
    
    public Enemy(int x, int y, Type shipType, double maxVel,
            double maxAngleVelocity, double angleIncrement, double acceleration,
            int shootingDelay, int health, GameActionListener actionListener)
    {
        super(x, y, shipType, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelay, health, actionListener);
    }
    
    @Override
    public void update()
    {
        updateHitbox();
        if (explosion != null) // for the medium fighter
        {
            explosion.updateLocation(location);
        }
    }
    
    public abstract int getID();
    
    @Override
    public abstract Point2D.Double getLocation();
    
    protected enum StateChange
    {
        rotateRight,
        rotateLeft,
        stopRotating,
        thrust,
        stopThrust
    }
}
