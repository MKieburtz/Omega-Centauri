package MainPackage;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public abstract class PhysicalShot extends Shot 
{
    protected Ship targetShip;
    
    protected final double angleIncrement = 1;
        
    public PhysicalShot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Ship owner) 
    {
        super(damage, range, location, velocity, angle, owner);
    }
    
    @Override
    public void setUpHitbox()
    {
        super.setUpHitbox();
    }
    
    @Override
    public void update()
    {
        super.update();
        double targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetShip.getLocation());
        
        rotateToAngle(360 - targetAngle);
        
        move();
        
        checkForExceededRange();
    }
    
    private void rotateToAngle(double angle)
    {
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);
        
        if (Math.abs(angle - faceAngle) > angleIncrement)
        {
            if (distances[0] < distances[1])
            {
                faceAngle += angleIncrement;
            }
            else
            {
                faceAngle -= angleIncrement;
            }
            
        }
        
        faceAngle = Calculator.confineAngleToRange(faceAngle);
    }
    

    public void move()
    {
        Point2D.Double lastLocation = new Point2D.Double(location.x, location.y);
        location.x += Calculator.CalcAngleMoveX(faceAngle) * 6;
        location.y += Calculator.CalcAngleMoveY(faceAngle) * 6;
        distanceTraveled += Calculator.getDistance(location, lastLocation);
        explosion.updateLocation(hitbox.getCollisionPoint());
    }
    
    @Override
    protected void updateHitbox()
    {
        super.updateHitbox();
        hitbox.rotateToAngle(360 - faceAngle);
    }
}
