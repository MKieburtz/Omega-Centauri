package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class TurretShot extends Shot {

    public TurretShot(int damage, int range, boolean animated, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation)
    {
        super(damage, range, animated, location, velocity, angle, cameraLocation);
        
        images = Resources.getImagesForTurretShot();
        
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);
    }
}
