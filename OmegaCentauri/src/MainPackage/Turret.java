package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Kieburtz
 */
public class Turret {

    private ArrayList<String> imagePaths = new ArrayList<String>();
    
    private BufferedImage activeImage;
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    
    private MediaLoader loader = new MediaLoader();
    
    private int maxDurability;
    private int durability;
    
    private int maxRotation;
    private int minRotation;
    
    private double angle;
    
    private final Point2D.Double distanceFromCenter;
    private Dimension imageDimensions;
    
    private Point2D.Double location = new Point2D.Double();
    
    private final int TURRETIMAGE = 0;
    
    public Turret(int maxDurability, int maxRotation, int minRotation, Point2D.Double distanceFromCenter, Dimension imageDimensions)
    {
        this.maxDurability = maxDurability;
        this.durability = maxDurability;
        
        this.maxRotation = maxRotation;
        this.minRotation = minRotation;
        
        angle = (maxRotation + minRotation) / 2; // average
        
        this.distanceFromCenter = distanceFromCenter;
        this.imageDimensions = imageDimensions;
        
        imagePaths.add("resources/Turret.png");
        
        images = loader.loadImages(imagePaths);
        
        images = Calculator.toCompatibleImages(images);
        
        activeImage = images.get(TURRETIMAGE);
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double entityLocation)
    {
        Point2D.Double middle = Calculator.getScreenLocationMiddle(cameraLocation, location, activeImage.getWidth(), activeImage.getHeight());
        AffineTransform transform = g2d.getTransform();
//        
//        transform.translate(Calculator.getScreenLocation(cameraLocation, location).x, Calculator.getScreenLocation(cameraLocation, location).y);
        
        //transform.rotate(Math.toRadians(360 - angle), middle.x, middle.y);
        
        g2d.drawImage(images.get(TURRETIMAGE), transform, null);
        
        g2d.setColor(Color.RED);
        
        g2d.drawRect((int)Calculator.getScreenLocation(cameraLocation, location).x, (int)Calculator.getScreenLocation(cameraLocation, location).y, 10, 10);
       
        g2d.drawRect((int)middle.x, (int)middle.y, 20, 20);
    }
    
    public void update(Point2D.Double playerLocation, Point2D.Double shipLocation, double shipAngle)
    {
        Point2D.Double middle = new Point2D.Double(shipLocation.x + activeImage.getWidth() / 2, shipLocation.y + activeImage.getHeight() / 2);
        
        double targetAngle = Math.abs(Calculator.getAngleBetweenTwoPoints(shipLocation, playerLocation) - shipAngle) % 360;
        
        if (targetAngle > minRotation && targetAngle < maxRotation)
        {
            angle = targetAngle;
        }
        
        
        
        location.x = middle.x + distanceFromCenter.x;
        location.y = middle.y + distanceFromCenter.y;
        
        System.out.println(middle + " " + distanceFromCenter + " " + location);
    }
}
