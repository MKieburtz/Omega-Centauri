package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class EnemyFighter extends EnemyShip {

    public EnemyFighter(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation,
            int shootingDelay, int health) {

        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, health);

        imagePaths.add("src/resources/EnemyShip.png");
        images = mediaLoader.loadImages(imagePaths);
        activeImage = images.get(0);

        setUpHitbox(cameraLocation);

        soundPaths.add("src/resources/Pulse.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

    }
}
