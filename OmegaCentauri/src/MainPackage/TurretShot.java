package MainPackage;

import java.awt.Dimension;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class TurretShot extends LaserShot 
{

    public TurretShot(int damage, Point2D.Double location,
            Point2D.Double velocity, double angle, Ship owner)
    {
        super(damage, 500, location, velocity, angle, owner);
        
        activeImage = resources.getImageForObject("resources/Level2Shot.png");
        
        setUpHitbox();
        
        explosion = new Explosion(Explosion.Type.range, new Dimension(activeImage.getWidth(), activeImage.getHeight()));
    }
}
