package MainPackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class PulseShot extends LaserShot 
{

    public PulseShot(int damage, Point2D.Double location,
            Point2D.Double velocity, double angle, boolean enemy, Point2D.Double cameraLocation, Ship owner, Resources resources) 
    {

        super(damage, 1200, location, velocity, angle, cameraLocation, owner);
        
        if (enemy) 
        {
            activeImage = resources.getImageForObject("resources/EnemyShot.png");
        } 
        else 
        {
            activeImage = resources.getImageForObject("resources/Pulse.png");
        }
        
        setUpHitbox(cameraLocation);
        
        explosion = new Explosion(Explosion.Type.range, new Dimension(activeImage.getWidth(), activeImage.getHeight()), resources);
    }
}
