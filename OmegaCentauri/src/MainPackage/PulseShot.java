package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
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
            Point2D.Double velocity, double angle, boolean enemy, Ship owner) 
    {

        super(damage, 1200, location, velocity, angle, owner);
        
        if (enemy) 
        {
            activeImage = resources.getImageForObject("resources/EnemyShot.png");
        } 
        else 
        {
            activeImage = resources.getImageForObject("resources/Pulse.png");
        }
        
        setUpHitbox();
        
        explosion = new Explosion(Explosion.Type.range, new Dimension(activeImage.getWidth(), activeImage.getHeight()));
    }
}
