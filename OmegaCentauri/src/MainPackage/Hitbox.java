package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public interface Hitbox 
{
    public void rotateToAngle(double angle);
    public void rotateRelitive(double amount);
    public void moveToLocation(Point2D.Double location);
    public void moveRelitive(Point2D.Double distance);
    public boolean collides(RectangularHitbox other);
    public boolean collides(ShapeHitbox other);
}
