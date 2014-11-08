package MainPackage;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Shield {

    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private BufferedImage activeImage;
    private double angle;
    private double[] scaling = new double[2];
    private double regenRate;
    private int strengh;
    private int energy;
    private int maxEnergy;
    private ArrayList<ShieldSegment> shieldSegments = new ArrayList<ShieldSegment>();
    
    Point size;

    public Shield(double angle, Point2D.Double location, Point2D.Double cameraLocation,
            boolean enemy, Point size, int strength, int energy, Resources resources) {
        this.angle = angle;
        this.energy = energy;
        this.maxEnergy = energy; // start at max power
        this.strengh = strength;
        this.size = size;
        if (enemy) {
            activeImage = resources.getImageForObject("resources/FILLERshieldEnemy.png");
        } else {
            activeImage = resources.getImageForObject("resources/shield.png");
        }
        scaling[0] = (double)size.x / activeImage.getWidth();
        scaling[1] = (double)size.y / activeImage.getHeight();
    }

    class ShieldSegment
    {
        private double angle;
        private int opacity = 100;
        
        public ShieldSegment(double angle)
        {
            this.angle = angle;
        }
        
        public double getAngle()
        {
            return angle;
        }
        
        public int getOpacity()
        {
            return opacity;
        }
        
        public void decay()
        {
            opacity -= 3;
        }
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double instanceLocation, double angle,
            Point2D.Double rotationPoint, Point2D.Double translationPoint) {      
            int rule = AlphaComposite.SRC_OVER;
            
            Composite originalComposite = g2d.getComposite();
            Composite comp;
            AffineTransform originalAffineTransform = g2d.getTransform();
            AffineTransform transform = (AffineTransform)originalAffineTransform.clone();

            for (ShieldSegment segment : shieldSegments)
            {
                comp = AlphaComposite.getInstance(rule, (float)segment.getOpacity()/100);

                g2d.setComposite(comp);
                // angleOfCollision - faceangle
                
                
//                g2d.rotate(Math.toRadians(360 - (segment.getAngle() - angle)),
//                        Calculator.getScreenLocationMiddle(cameraLocation, instanceLocation, size.x, size.y).x,
//                        Calculator.getScreenLocationMiddle(cameraLocation, instanceLocation, size.x, size.y).y);
                
                
                transform.translate(translationPoint.x, translationPoint.y - size.y / 2);
                transform.rotate(Math.toRadians(segment.getAngle()));
                
                //g2d.translate(100D, 100D);

                
//                transform.translate(
//                        Calculator.getScreenLocation(cameraLocation, instanceLocation).x,
//                        Calculator.getScreenLocation(cameraLocation, instanceLocation).y);

                //transform.scale(scaling[0], scaling[1]);

                g2d.transform(transform);

                g2d.drawImage(activeImage, 0, 0, null);

                g2d.setComposite(originalComposite);

                g2d.setTransform(originalAffineTransform);
            }
    }

    public void activate(double damage, double angle) {        
        int damageToLose = (int)Math.ceil(damage * (strengh / 10));
        
        energy -= damageToLose;
        
        shieldSegments.add(new ShieldSegment(angle));
    }
    
    public double getEnergy()
    {
        return energy;
    }
    
    public void setEnergy(int newEnergy)
    {
        energy = newEnergy;
    }
    
    public void setMaxEnergy(int newMax)
    {
        maxEnergy = newMax;
    }
    
    public int getMaxEnergy()
    {
        return maxEnergy;
    }
    
    public void setStrength(int newStrength)
    {
        strengh = newStrength;
    }
    
    public boolean isActive()
    {
        return !shieldSegments.isEmpty();
    }
    
    public void decay()
    {
       ArrayList<ShieldSegment> decayedSegments = new ArrayList<>();
       for (ShieldSegment segment : shieldSegments)
       {
           segment.decay();
           if (segment.getOpacity() <= 0)
           {
               decayedSegments.add(segment);
           }
       }
       
       shieldSegments.removeAll(decayedSegments);
    }
    
    public double getRegenRate()
    {
        return regenRate;
    }
    
    public void regen()
    {
        energy += regenRate;
    }
    
    public void setRegenRate(double regenRate)
    {
        this.regenRate = regenRate;
    }
}
