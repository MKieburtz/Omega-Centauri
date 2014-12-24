package MainPackage;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public abstract class LaserShot extends Shot 
{
    
    private boolean fading = false;
    private int opacity = 100;

    public LaserShot(int damage, int range, Point2D.Double location,
            Point2D.Double velocity, double angle, Point2D.Double cameraLocation, Ship owner) 
    {
        super(damage, range, location, velocity, angle, cameraLocation, owner);
    }
    
    @Override
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        if (!fading && opacity >= 0)
        {
            super.draw(g2d, cameraLocation); // super.draw is just a general image drawing method in this case
            //hitbox.draw(g2d, cameraLocation);
        }
        else if (fading)
        {
            AffineTransform original = g2d.getTransform();
            AffineTransform transform = (AffineTransform) original.clone();
            
            Composite originalComp = g2d.getComposite();
            Composite transformComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)opacity / 100);
            
             transform.rotate(Math.toRadians(faceAngle),
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x,
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y);

            transform.translate(Calculator.getScreenLocation(cameraLocation, location).x, Calculator.getScreenLocation(cameraLocation, location).y);

            g2d.setComposite(transformComposite);
            
            g2d.transform(transform);
            
            g2d.drawImage(activeImage, 0, 0, null);
            
            g2d.setComposite(originalComp);
            
            g2d.setTransform(original);
            
            //hitbox.draw(g2d, cameraLocation);
        }
    }
    
    @Override
    protected void setUpHitbox(Point2D.Double cameraLocation)
    {
        super.setUpHitbox(cameraLocation);
        hitbox.rotateRelitive(360 - faceAngle);
    }
    
    @Override
    public void update()
    {
        Point2D.Double lastLocation = new Point2D.Double(location.x, location.y);
        location.x += velocity.x;
        location.y += velocity.y;
        
        distanceTraveled += Calculator.getDistance(location, lastLocation);
        
        if (!fading)
        {
            checkForExceededRange();
        }
        else
        {
            opacity -= 5;
            
            if (opacity < 0)
            {
                fading = false;
            }
        }
    }
    
    private void checkForExceededRange()
    {
        if (distanceTraveled > range)
        {
            fading = true;
        }
    }
    
    @Override
    public boolean isDying()
    {
        return fading;
    }
}
