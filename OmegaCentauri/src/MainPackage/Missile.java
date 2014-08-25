package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Kieburtz
 */
public class Missile extends Shot{

    private ArrayList<BufferedImage> images = new ArrayList<>();
    private BufferedImage activeImage;
    
    private Point2D.Double location;
    
    private double faceAngle;
    private Ship targetShip;
    
    private final double angleIncrement = 1;
    
    private Hitbox hitbox;
    
    public Missile(int damage, int range, Point2D.Double location,
            Point2D.Double startingVel, double angle, Point2D.Double cameraLocation, Ship targetShip)
    {
        super(damage, range, false, location, startingVel, angle, cameraLocation);
        
        images = Resources.getImagesForMissle();
        activeImage = images.get(0);
        
        this.location = location;
        faceAngle = angle;
        this.targetShip = targetShip;
    }
    
    public void updateTarget()
    {
        double targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetShip.getLocation());
        
        rotateToAngle(targetAngle);
        
        move();
    }
    
    private void rotateToAngle(double angle)
    {
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);
        
        if (Math.abs(angle - faceAngle) > angleIncrement)
        {
            if (distances[0] < distances[1])
            {
                faceAngle += angleIncrement;
            }
            else
            {
                faceAngle -= angleIncrement;
            }
        }
    }
    
    @Override
    public void move()
    {
        location.x += Calculator.CalcAngleMoveX(360 - faceAngle);
        location.y += Calculator.CalcAngleMoveY(360 - faceAngle);
    }
    
    @Override
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        super.draw(g2d, cameraLocation);
    }
    
    public Hitbox getHitbox()
    {
        return hitbox;
    }
}
