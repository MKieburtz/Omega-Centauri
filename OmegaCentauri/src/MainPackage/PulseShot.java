package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class PulseShot extends Shot {

    public PulseShot(int damage, int range, boolean animated, Point2D.Double location,
            Point2D.Double velocity, double angle, boolean enemy, Point2D.Double cameraLocation) {

        super(damage, range, animated, location, velocity, angle, cameraLocation);
        
        if (enemy) {
            images = Resources.getImagesForEnemyPulseShot();
        } else {
            images = Resources.getImagesForPulseShot();
        }
        
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);
    }
}
