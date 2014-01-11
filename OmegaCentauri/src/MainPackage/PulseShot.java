package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * @author Michael Kieburtz
 */
public class PulseShot extends Shot {

    public PulseShot(int damage, int range, boolean animated, Point2D.Double location,
            Point2D.Double velocity, double angle) {
        life = 0;

        this.damage = damage;
        this.range = range;
        this.animated = animated;
        this.location = location;
        this.velocity = velocity;
        
        this.faceAngle = angle;
        this.maxVel = 5;
        
        imagePaths.add("src/resources/Pulse.png");
        
        loadImages(imagePaths);

    }
    

}
