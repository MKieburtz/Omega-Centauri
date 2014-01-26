package MainPackage;

// @author Michael Kieburtz

import java.awt.geom.Point2D;

public abstract class EnemyShip extends Ship{
    
    public EnemyShip(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration)
            // delegate assigning images to the types of ships
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration);
    }
    
    protected void update(Point2D.Double playerLocation)
    {
        // main AI goes here
        
        // move in the direction of the ship
        
        //double distance = Calculator.getDistance(location, playerLocation);
        
        double angle = Calculator.getAngleBetweenTwoPoints(playerLocation, location);
        //System.out.println(angle);
        RotateToPlayer(angle);
        move(true);
    }
    
    protected void RotateToPlayer(double angle)
    {
        //System.out.println(faceAngle);
        //System.err.println(angle);
        //System.out.println(faceAngle - angle);
        
        double targetAngle = 360 - angle;
                
        if (Math.abs(targetAngle - faceAngle) > angleIcrement)
        {
            if (faceAngle - 360 > targetAngle)
                rotate(false);
            
            else if (faceAngle - 360 < targetAngle)
                rotate(true);
        }
    }
}
