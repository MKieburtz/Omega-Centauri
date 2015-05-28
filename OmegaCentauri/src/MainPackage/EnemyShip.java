package MainPackage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import sun.security.util.AuthResources_it;

/**
 * @author Michael Kieburtz
 */
public abstract class EnemyShip extends Ship
{
    public EnemyShip(int x, int y, Type shipType, double maxVel,
            double maxAngleVelocity, double angleIncrement, double acceleration, int shootingDelay, int health)
    {
        super(x, y, shipType, maxVel, maxAngleVelocity, angleIncrement, acceleration, shootingDelay, health);
    }
    
    public abstract void update(Player player, Point2D.Double cameraLocation, ArrayList<EnemyShip> otherShips);
    
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
    
     protected void rotateToAngle(double angle) 
     {
        if (Math.abs(angle - faceAngle) >= 5) 
        {
            double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);
            if (distances[0] < distances[1]) 
            {
                if (distances[0] > angleIcrement) 
                {
                    changeImage(StateChange.rotateLeft);
                    rotate(ShipState.TurningLeft);
                }
            }
            else 
            {
                if (distances[1] > angleIcrement) 
                {
                    changeImage(StateChange.rotateRight);
                    rotate(ShipState.TurningRight);
                }
            }
        }
        else
        {
            if (rotationState == RotationState.rotatingRight)
            {
                changeImage(StateChange.stopRotating); // just for the image
                rotate(ShipState.AngleDriftingRight);
            }
            else if (rotationState == RotationState.rotatingLeft)
            {
                changeImage(StateChange.stopRotating); // also just for the image
                rotate(ShipState.AngleDriftingLeft);
            }
        }
    }
     
     protected abstract void changeImage(StateChange change);
}
