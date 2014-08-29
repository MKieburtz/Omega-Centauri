package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Kieburtz
 */
public class Missile extends Shot{

    private Ship targetShip;
    
    private final double angleIncrement = 1;
    
    private Explosion explosion;
    private boolean exploding;
    
    public Missile(int damage, int range, Point2D.Double location,
            Point2D.Double startingVel, double angle, Point2D.Double cameraLocation, Ship targetShip, Ship owner)
    {
        super(damage, range, false, location, startingVel, angle, cameraLocation, owner);

        images = Resources.getImagesForMissle();
        activeImage = images.get(0);

        this.location = location;
        faceAngle = angle;
        this.targetShip = targetShip;
        
        setUpHitbox(cameraLocation);
        
        explosion = new Explosion(Explosion.Type.missile, new Dimension(activeImage.getWidth(), activeImage.getHeight()));
    }
    
    public void updateTarget()
    {
        double targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetShip.getLocation());
        
        rotateToAngle(360 - targetAngle);
        
        move();
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
    
    @Override
    public void move()
    {
        location.x += Calculator.CalcAngleMoveX(faceAngle) * 4;
        location.y += Calculator.CalcAngleMoveY(faceAngle) * 4;
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
    public boolean collisionEventWithShot(Shot shot, Shot otherShot, ArrayList<Ship> allShips) // this will just make the missile explode
    {
        boolean removed = super.collisionEventWithShot(shot, otherShot, allShips);
        
        if (!exploding)
        {
            if (removed) 
            {
                exploding = true;
            }
        }
        return removed;
    }
    
    public Hitbox getHitbox()
    {
        return hitbox;
    }
    
    public boolean isExploding()
    {
        return exploding;
    }
}
