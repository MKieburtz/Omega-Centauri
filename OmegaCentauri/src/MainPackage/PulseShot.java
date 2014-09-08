package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class PulseShot extends LaserShot {

    public PulseShot(int damage, Point2D.Double location,
            Point2D.Double velocity, double angle, boolean enemy, Point2D.Double cameraLocation, Ship owner) {

        super(damage, 1200, location, velocity, angle, cameraLocation, owner);
        
        if (enemy) {
            images = Resources.getImagesForEnemyPulseShot();
        } else {
            images = Resources.getImagesForPulseShot();
        }
        
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);
    }
}
