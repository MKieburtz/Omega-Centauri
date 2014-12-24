package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class TurretShot extends LaserShot 
{

    public TurretShot(int damage, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner, Resources resources)
    {
        super(damage, 500, location, velocity, angle, cameraLocation, owner);
        
        activeImage = resources.getImageForObject("resources/Level2Shot.png");
        
        setUpHitbox(cameraLocation);
    }
}
