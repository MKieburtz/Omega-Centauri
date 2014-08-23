package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Kieburtz
 */
public class Missile {

    private ArrayList<BufferedImage> images = new ArrayList<>();
    private BufferedImage activeImage;
    
    private Point2D.Double location;
    
    private double faceAngle;
    
    private final double angleIncrement = 1;
    
    private Hitbox hitbox;
    
    public Missile(Point2D.Double location, double angle)
    {
        images = Resources.getImagesForMissle();
        activeImage = images.get(0);
        
        this.location = location;
        faceAngle = angle;
    }
    
    public void update(Point2D.Double targetLocation)
    {
        double targetAngle = Calculator.getAngleBetweenTwoPoints(location, targetLocation);
        
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
    
    private void move()
    {
        location.x += Calculator.CalcAngleMoveX(360 - faceAngle);
        location.y += Calculator.CalcAngleMoveY(360 - faceAngle);
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation)
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform)original.clone();
        
        transform.translate(Calculator.getScreenLocation(cameraLocation,
                location).x, Calculator.getScreenLocation(cameraLocation,location).y);
        
        transform.rotate(360 - faceAngle,
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).x,
                Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight()).y);
        
        g2d.transform(transform);
        
        g2d.drawImage(activeImage, 0, 0, null);
        
        g2d.setTransform(original);
    }
    
    public Hitbox getHitbox()
    {
        return hitbox;
    }
}
