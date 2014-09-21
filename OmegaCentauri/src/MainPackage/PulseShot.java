package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class PulseShot extends LaserShot {

    public PulseShot(int damage, Point2D.Double location,
            Point2D.Double velocity, double angle, boolean enemy, Point2D.Double cameraLocation, Ship owner, Resources resources) {

        super(damage, 1200, location, velocity, angle, cameraLocation, owner);
        
        if (enemy) {
            imagePaths.add("resources/EnemyShot.png");
            images = resources.getImagesForObject(imagePaths);
        } else {
            imagePaths.add("resources/Pulse.png");
            images = resources.getImagesForObject(imagePaths);
        }
        
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);
    }
}
