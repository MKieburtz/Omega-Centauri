package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class PulseShot extends Shot {

    public PulseShot(int damage, int range, boolean animated, Point2D.Double location,
            Point2D.Double velocity, double angle, boolean enemy, Point2D.Double cameraLocation) {
        life = 0;
        this.damage = damage;
        this.range = range;
        this.animated = animated;
        this.location = location;
        this.velocity = velocity;

        this.faceAngle = angle;
        this.maxVel = 5;

        if (enemy) {
            imagePaths.add("resources/EnemyShot.png");
        } else {
            imagePaths.add("resources/Pulse.png");
        }
        
        loadImages(imagePaths);
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);
    }
}
