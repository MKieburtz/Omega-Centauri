package MainPackage;

import java.awt.geom.Point2D;
import java.util.ArrayList;

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
    
    
     protected void rotateToAngle(double angle) 
     {
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);

        if (Math.abs(angle - faceAngle) >= 5) 
        {
            if (distances[0] < distances[1]) 
            {
                if (distances[0] > angleIcrement) 
                {
                    rotate(ShipState.TurningLeft);
                }
            }
            else 
            {
                if (distances[1] > angleIcrement) 
                {
                    rotate(ShipState.TurningRight);
                }
            }
        }
        else
        {
            if (rotatingRight)
            {
                rotate(ShipState.AngleDriftingRight);
            }
            else
            {
                rotate(ShipState.AngleDriftingLeft);
            }
        }
    }
     
     public boolean isRotating()
     {
         return angularVelocity != 0;
     }
}
