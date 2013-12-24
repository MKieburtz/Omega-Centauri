package MainPackage;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class PulseShot extends Shot{
    
    public PulseShot(int damage, int lifespan, boolean animated, Point2D.Double location,
            Point velocity, double angle)
    {
        life = 0;
        
        this.damage = damage;
        this.lifespan = lifespan;
        this.animated = animated;
        this.location = location;
        this.velocity = velocity;
        this.angle = angle;
        
        imagePaths.add("resources/Pulse.png");
        loadImages(imagePaths);
        
    }
    
}
