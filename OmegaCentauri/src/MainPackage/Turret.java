package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
    private Point2D.Double rotationPoint = new Point2D.Double();
    private Point2D.Double shotSpawnPoint = new Point2D.Double();
    private Point2D.Double distanceToShotSpawnPoint = new Point2D.Double();
    
    private final int TURRETIMAGE = 0;
    
    public Turret(int maxDurability, int maxRotation, int minRotation, Point2D.Double distanceFromCenter,
            Dimension imageDimensions, Point2D.Double cameraLocation, Point2D.Double distanceToShotSpawnPoint)
    {
        this.maxDurability = maxDurability;
        this.durability = maxDurability;
        
        this.maxRotation = maxRotation;
        this.minRotation = minRotation;
        
        angle = (maxRotation + minRotation) / 2; // average
       
        this.distanceFromCenter = distanceFromCenter;
        this.imageDimensions = imageDimensions;
        this.distanceToShotSpawnPoint = distanceToShotSpawnPoint;
        
        imagePaths.add("resources/Turret.png");
        
        images = loader.loadImages(imagePaths);
        
        images = Calculator.toCompatibleImages(images);
        
        activeImage = images.get(TURRETIMAGE);
    }
    ArrayList<Line2D.Double> lines = new ArrayList<>();
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double entityLocation)
    {        
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        
        transform.rotate(Math.toRadians(360 - angle), rotationPoint.x + activeImage.getWidth() / 2 - 2.5, rotationPoint.y + activeImage.getHeight() / 2);
      
        g2d.transform(transform);
        
        g2d.drawImage(activeImage, (int)distanceFromCenter.x, (int)distanceFromCenter.y, null);

        g2d.setTransform(original);
        
//        lines.add(new Line2D.Double(location, rotationPoint));
//        
//        g2d.setColor(Color.BLUE);
//        for (Line2D.Double line : lines)
//        {
//            g2d.draw(line);
//        }
//        
//        g2d.draw(new Line2D.Double(location, rotationPoint));
//        
    }
    
    public void update(Point2D.Double playerLocation, Point2D.Double shipLocationMiddle, double shipAngle, Point2D.Double cameraLocation)
    {        
        double targetAngle = Math.abs(Calculator.getAngleBetweenTwoPoints(shipLocationMiddle, playerLocation) - shipAngle) % 360;
        
        if (targetAngle > minRotation && targetAngle < maxRotation)
        {
            angle = targetAngle;
        }
        
        
        shotSpawnPoint.x = shipLocationMiddle.x + distanceToShotSpawnPoint.x * Math.cos(Math.toRadians(360 - shipAngle - 65));
        
        shotSpawnPoint.y = shipLocationMiddle.y + distanceToShotSpawnPoint.y * Math.sin(Math.toRadians(360 - shipAngle - 65));
                
        rotationPoint = distanceFromCenter;
    }
}
