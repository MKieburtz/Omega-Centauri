package MainPackage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dialog;
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
            opacity -= 1;
        }
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double instanceLocation,
            Point2D.Double rotationPoint, Point2D.Double translationPoint, AffineTransform original) {      
            int rule = AlphaComposite.SRC_OVER;
            
            Composite originalComposite = g2d.getComposite();
            Composite comp;
            AffineTransform originalAffineTransform = original;
            AffineTransform transform = new AffineTransform();

            for (ShieldSegment segment : shieldSegments)
            {
                transform.setToIdentity();
                comp = AlphaComposite.getInstance(rule, (float)segment.getOpacity()/100);

                g2d.setComposite(comp);
                
                transform.rotate(Math.toRadians(360 - segment.getAngle()), rotationPoint.x, rotationPoint.y);
                transform.translate(translationPoint.x + size.x / 2, translationPoint.y - activeImage.getHeight() / 2);
                g2d.transform(transform);

                g2d.drawImage(activeImage, 0, 0, null);
                
//                g2d.setColor(Color.red);
//                g2d.fillRect(0, 0, 2, 2);

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
