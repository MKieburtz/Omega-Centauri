package MainPackage;

import java.awt.Dialog;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public abstract class PhysicalShot extends Shot {

    protected Ship targetShip;
    
    protected final double angleIncrement = 1;
    
    protected Explosion explosion;
    protected boolean exploding;
    
    public PhysicalShot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) {
        super(damage, range, location, velocity, angle, cameraLocation, owner);
    }
    
    @Override
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        if (!exploding)
        {
            super.draw(g2d, cameraLocation);
        }
        else
        {
            explosion.draw(g2d, location, cameraLocation);
            if (explosion.isDone())
            {
                exploding = false;
            }
        }
    }
    
    @Override
    public void update()
    {
        double targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetShip.getLocation());
        
        //rotateToAngle(360 - targetAngle);
        
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
    }
    
    private void checkForExceededRange()
    {
        if (distanceTraveled > range)
        {
            exploding = true;
        }
    }
    
    @Override
    public boolean isDying()
    {
        return exploding;
    }
    
    @Override
    protected void updateHitbox(Point2D.Double cameraLocation)
    {
        super.updateHitbox(cameraLocation);
        hitbox.rotateToAngle(360 - faceAngle);
    }
}
