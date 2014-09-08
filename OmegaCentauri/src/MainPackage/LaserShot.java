package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public abstract class LaserShot extends Shot {

    public LaserShot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) {
        super(damage, range, location, velocity, angle, cameraLocation, owner);
    }
    
    @Override
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        super.draw(g2d, cameraLocation); // super.draw is just a general image drawing method in this case
    }
    
    @Override
    public void update()
    {
        Point2D.Double lastLocation = new Point2D.Double(location.x, location.y);
        location.x += velocity.x;
        location.y += velocity.y;
        
        distanceTraveled += Calculator.getDistance(location, lastLocation);
    }
}
