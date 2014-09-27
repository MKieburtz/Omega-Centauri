package MainPackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Kieburtz
 */
public class Missile extends PhysicalShot {
    
    
    public Missile(int damage, Point2D.Double location,
            Point2D.Double startingVel, double angle, Point2D.Double cameraLocation, Ship targetShip, Ship owner, Resources resources)
    {
        super(damage, 2000, location, startingVel, angle, cameraLocation, owner);

        activeImage = resources.getImageForObject("resources/Missile.png");

        this.location = location;
        faceAngle = angle;
        this.targetShip = targetShip;
        setUpHitbox(cameraLocation);
        
        explosion = new Explosion(Explosion.Type.missile, new Dimension(activeImage.getWidth(), activeImage.getHeight()), resources);
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
    
    public void explode()
    {
        exploding = true;
    }
}
