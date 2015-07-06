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
    private BufferedImage activeImage;
    private int strengh;
    private int energy;
    private int maxEnergy;
    private int opacity = 0;
    private Point imageExtra = null; // hardcoded for how much bigger the shield is than the ship
    

    public Shield(Point2D.Double location, Type type, Point size, int strength,
            int energy)
    {
        resources = gameData.getResources();
        this.energy = energy;
        this.maxEnergy = energy; // start at max power
        this.strengh = strength;
        switch (type)
        {
            case fighter:
                activeImage = resources.getImageForObject("resources/FighterShield.png");
                imageExtra = new Point(14, 10);
                break;
            case enemyFighter:
                activeImage = resources.getImageForObject("resources/EnemyFighterShield.png");
                imageExtra = new Point(20, 20);
                break;
            case enemyMediumFighter:
                activeImage = resources.getImageForObject("resources/EnemyMediumFighterShield.png");
                imageExtra = new Point(70, 40);
                break;
        }
    }
    
    public static enum Type
    {
        fighter,
        enemyFighter,
        enemyMediumFighter
    }

    public void draw(Graphics2D g2d, Point2D.Double rotationPoint, Point2D.Double location, double faceAngle) 
    {      
        int rule = AlphaComposite.SRC_OVER;

        Composite originalComposite = g2d.getComposite();
        Composite comp;
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = new AffineTransform();

        transform.setToIdentity();
        comp = AlphaComposite.getInstance(rule, (float)opacity / 100);
        
        transform.rotate(Math.toRadians(360 - faceAngle), rotationPoint.x, rotationPoint.y);
        
        transform.translate(Calculator.getScreenLocation(gameData.getCameraLocation(), location).x,
                Calculator.getScreenLocation(gameData.getCameraLocation(), location).y);
        
        g2d.transform(transform);
        g2d.setComposite(comp);
        
        g2d.drawImage(activeImage, -imageExtra.x / 2, -imageExtra.y / 2, null);

//                g2d.setColor(Color.red);
//                g2d.fillRect(0, 0, 2, 2);

        g2d.setComposite(originalComposite);
        g2d.setTransform(original);
    }
    
    // drawingAngle is the angle on the shield, translationAngle is the angle to the collision point on the shot
    public void activate(double damage)
    {        
        int damageToLose = (int)Math.ceil(damage * (strengh / 10));
        //System.out.println(shieldAngle + " " + collisionAngle);
        energy -= damageToLose;
        opacity = 100;
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
        return opacity > 0;
    }
    
    public void decay()
    {
        opacity -= 5;
    }
}
