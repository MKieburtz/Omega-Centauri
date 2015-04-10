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
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) 
    {
        super(damage, range, location, velocity, angle, cameraLocation, owner);
    }
    
    @Override
    public void setUpHitbox(Point2D.Double cameraLocation)
    {
        Point2D.Double[] hitboxPoints = new Point2D.Double[4]; 

        try 
        {
            hitboxPoints[0] = new Point2D.Double(0, 0);
            hitboxPoints[1] = new Point2D.Double(activeImage.getWidth(), 0);
            hitboxPoints[2] = new Point2D.Double(activeImage.getWidth(), activeImage.getHeight());
            hitboxPoints[3] = new Point2D.Double(0, activeImage.getHeight());
            hitbox = new RectangularHitbox(hitboxPoints, false);

        }
        catch (NullPointerException e)
        {
            System.err.println("activeimage not initialized!");
        }
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
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double drawLocation)
    {
        // to do later
    }
    
    @Override
    public void update()
    {
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
    }
    
    @Override
    protected void updateHitbox(Point2D.Double cameraLocation)
    {
        super.updateHitbox(cameraLocation);
        hitbox.rotateToAngle(360 - faceAngle);
    }
}
