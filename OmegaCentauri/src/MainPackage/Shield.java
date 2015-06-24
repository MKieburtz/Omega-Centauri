package MainPackage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Shield 
{
    private GameData gameData = new GameData();
    private Resources resources;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private BufferedImage activeImage;
    private int strengh;
    private int energy;
    private int maxEnergy;
    private ArrayList<ShieldSegment> shieldSegments = new ArrayList<ShieldSegment>();
    
    private Point size;
    private boolean circle;

    public Shield(Point2D.Double location, boolean enemy, Point size, int strength,
            int energy, boolean circle)
    {
        resources = gameData.getResources();
        this.energy = energy;
        this.maxEnergy = energy; // start at max power
        this.strengh = strength;
        this.size = size;
        if (enemy) {
            activeImage = resources.getImageForObject("resources/EnemyShield.png");
        } else {
            activeImage = resources.getImageForObject("resources/shield.png");
        }
        this.circle = circle;
    }

    class ShieldSegment
    {
        private double angleToDraw;
        private double angleToTranslate;
        private int extraTranslation; // to add the the second translation to get over big wings
        private int opacity = 100;       
        
        public ShieldSegment(double drawingAngle, double translationAngle, int extra)
        {
            this.angleToDraw = drawingAngle;
            this.angleToTranslate = translationAngle;
            this.extraTranslation = extra;
        }

        public double getTranslationAngle()
        {
            return angleToTranslate;
        }
        
        public int getOpacity()
        {
            return opacity;
        }
        
        public void decay()
        {
            opacity -= 5;
        }

        public double getDrawingAngle() 
        {
            return angleToDraw;
        }
        
        public void updateAngle(double amount)
        {
            angleToDraw += amount;
            angleToTranslate += amount;
        }
        
        public int getExtra()
        {
            return extraTranslation;
        }
    }
    
    public void draw(Graphics2D g2d, Point2D.Double instanceLocation, Point2D.Double rotationPoint,
            Point2D.Double middle, double faceAngle) 
    {      
            int rule = AlphaComposite.SRC_OVER;
            
            Composite originalComposite = g2d.getComposite();
            Composite comp;
            AffineTransform original = g2d.getTransform();
            AffineTransform transform = new AffineTransform();
            
            for (ShieldSegment segment : shieldSegments)
            {
                transform.setToIdentity();
                comp = AlphaComposite.getInstance(rule, (float)segment.getOpacity() / 100);

                g2d.setComposite(comp);
                transform.rotate(Math.toRadians(360 - segment.getTranslationAngle()), rotationPoint.x, rotationPoint.y);
                double translationAngle = segment.getTranslationAngle() - faceAngle;
                double distance = Calculator.getDistanceToEdgeOfEllipseAtAngle(size.x / 2, size.y / 2, translationAngle);
                
                if (circle)
                {
                    transform.translate(middle.x + size.x / 2, middle.y - activeImage.getHeight() / 2); // middle is the center of the ship
                }
                else
                {
                    transform.translate(middle.x + distance + segment.getExtra(), middle.y - activeImage.getHeight() / 2);
                    //System.out.println(segment.getTranslationAngle() + " " + segment.getDrawingAngle() + " " + faceAngle);
                    transform.rotate(Math.toRadians(360 - -(segment.getTranslationAngle() - segment.getDrawingAngle())), 0, 0);
                }
                                
                g2d.transform(transform);
                
                g2d.drawImage(activeImage, 0, 0, null);
                
//                g2d.setColor(Color.red);
//                g2d.fillRect(0, 0, 2, 2);

                g2d.setComposite(originalComposite);
                g2d.setTransform(original);
            }
    }
    // drawingAngle is the angle on the shield, translationAngle is the angle to the collision point on the shot
    public void activate(double damage, double drawingAngle, double translationAngle, double faceAngle, int extra)
    {        
        int damageToLose = (int)Math.ceil(damage * (strengh / 10));
        //System.out.println(shieldAngle + " " + collisionAngle);
        energy -= damageToLose;
        shieldSegments.add(new ShieldSegment(drawingAngle, translationAngle, extra));
    }
    
    public void updateSegments(double angleChange)
    {
        for (ShieldSegment segment : shieldSegments)
        {
            segment.updateAngle(angleChange);
        }
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
}
