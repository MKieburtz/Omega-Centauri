package MainPackage;

import java.awt.*;
import java.awt.geom.*;

/**
 * @author Michael Kieburtz
 */
public abstract class LaserShot extends Shot 
{
    public LaserShot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) 
    {
        super(damage, range, location, velocity, angle, cameraLocation, owner);
    }
    
    @Override
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        if (!exploding)
        {
            super.draw(g2d, cameraLocation); // super.draw is just a general image drawing method in this case
            //hitbox.draw(g2d, cameraLocation);
        }
        else
        {
            explosion.draw(g2d, hitbox.getCollisionPoint(), cameraLocation, againstShield);
            if (explosion.isDone())
            {
                exploding = false;
            }
        }
    }
    
    @Override
    protected void setUpHitbox(Point2D.Double cameraLocation)
    {
        super.setUpHitbox(cameraLocation);
        hitbox.rotateRelitive(360 - faceAngle);
    }
    
    @Override
    public void update()
    {
        if ((!outOfRange && !exploding) || outOfRange)
        {
            Point2D.Double lastLocation = new Point2D.Double(location.x, location.y);
            location.x += velocity.x;
            location.y += velocity.y;

            distanceTraveled += Calculator.getDistance(location, lastLocation);

            if (!exploding)
            {
                checkForExceededRange();
            }
        }
    }
}
