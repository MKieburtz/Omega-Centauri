package MainPackage;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class EnemyFighter extends EnemyShip {

    public EnemyFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelay, int health, int id) {

        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health, id);

        imagePaths.add("resources/EnemyShip.png");
        images = mediaLoader.loadImages(imagePaths);
        images = Calculator.toCompatibleImages(images);
        activeImage = images.get(0);
        
        setUpHitbox(cameraLocation);

        soundPaths.add("resources/Pulse.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

        shield = new Shield(faceAngle, location, new Point2D.Double(0, 0), true, new Point(activeImage.getWidth(), activeImage.getHeight()), 0, 0);
    }
}
