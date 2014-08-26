package MainPackage;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Kieburtz
 */
public class Missile extends Shot{

    private Ship targetShip;
    
    private final double angleIncrement = 1;
    
    public Missile(int damage, int range, Point2D.Double location,
            Point2D.Double startingVel, double angle, Point2D.Double cameraLocation, Ship targetShip)
    {
        super(damage, range, false, location, startingVel, angle, cameraLocation);

        images = Resources.getImagesForMissle();
        activeImage = images.get(0);

        this.location = location;
        faceAngle = angle;
        this.targetShip = targetShip;
        
        setUpHitbox(cameraLocation);
    }
    
    public void updateTarget()
    {
        double targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetShip.getLocation());
        
        rotateToAngle(360 - targetAngle);
        
        move();
    }
    
    private void rotateToAngle(double angle)
    {
        double[] distances = Calculator.getDistancesBetweenAngles(faceAngle, angle);
        System.out.println(faceAngle);
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
        
        faceAngle = Calculator.confineAngleToRange(faceAngle);
    }
    
    @Override
    public void move()
    {
        location.x += Calculator.CalcAngleMoveX(faceAngle) * 5;
        location.y += Calculator.CalcAngleMoveY(faceAngle) * 5;
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
