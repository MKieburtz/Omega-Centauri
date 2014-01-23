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
        
        double distance = Calculator.getDistance(location, playerLocation);
        
        double angle = Calculator.getAngleBetweenTwoPoints(location, playerLocation);
        
        move(true);
    }
}
