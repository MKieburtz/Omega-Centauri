package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 */
public class Explosion 
{

    private final Dimension fighterExplosionSize = new Dimension(1000, 1200);
    private final Dimension fighterExplosionImageSize = new Dimension(200, 200);

    private final Dimension missileExplosionSize = new Dimension(400, 400);
    private final Dimension missileExplosionImageSize = new Dimension(100, 100);

    private final Dimension rangeExplosionSize = new Dimension(700, 200);
    private final Dimension rangeExplosionImageSize = new Dimension(100, 100);

    private final Dimension entityImageSize;
    
    private Dimension explosionImageSize;
    
    private Point2D.Double drawingManipulation = new Point2D.Double();
    
    private double translationDistance = 0;

    private int frame = 0;

    public static enum Type 
    {
        fighter, missile, range
    };

    BufferedImage spriteSheet;

    BufferedImage[] images = null;
    BufferedImage[] shieldImages = null;
    
    private final String fighterExplosionPath = "resources/FighterExplosionSpritesheet.png";
    private final String missileExplosionPath = "resources/MissileExplosionSpritesheet.png";
    private final String rangeExplosionPath = "resources/RangeExplosionSpritesheet.png";
    
    public Explosion(Type type, Dimension imageSize, Resources resources) 
    {
        switch (type) 
        {
            case fighter:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(fighterExplosionPath));
                images = new BufferedImage[30];
                shieldImages = new BufferedImage[30];

                loadImages(fighterExplosionSize, spriteSheet, fighterExplosionImageSize);
                explosionImageSize = fighterExplosionImageSize;
                break;

            case missile:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(missileExplosionPath));
                images = new BufferedImage[16];
                shieldImages = new BufferedImage[16];
                
                loadImages(missileExplosionSize, spriteSheet, missileExplosionImageSize);
                
                explosionImageSize = missileExplosionImageSize;
                
//                for (int i = 0; i < images.length; i++)
//                {
//                    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, rangeExplosionImageSize.width, rangeExplosionImageSize.height);
//                    Ellipse2D.Double ellipse = new Ellipse2D.Double(-3, 100-50-(37/2), 53, 37); // magic numbers! This ellipse wraps around the shield segment
//                    Area clipping = new Area(rect);
//                    clipping.subtract(new Area(ellipse));
//                    
//                    Graphics2D g2d = shieldImages[i].createGraphics();
//                    
//                    g2d.clip(clipping);
//                    g2d.drawImage(images[i], 0, 0, null);
//                }
                
                break;
            
            case range:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(rangeExplosionPath));
                images = new BufferedImage[14];
                shieldImages = new BufferedImage[14];
                
                loadImages(rangeExplosionSize, spriteSheet, rangeExplosionImageSize);
                
                explosionImageSize = rangeExplosionImageSize;
                
                for (int i = 0; i < images.length; i++)
                {
                    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, rangeExplosionImageSize.width, rangeExplosionImageSize.height);
                    Ellipse2D.Double ellipse = new Ellipse2D.Double(-3, 100-50-(37/2), 53, 37); // magic numbers! This ellipse wraps around the shield segment
                    Area clipping = new Area(rect);
                    clipping.subtract(new Area(ellipse));
                    
                    Graphics2D g2d = shieldImages[i].createGraphics();
                    
                    g2d.clip(clipping);
                    g2d.drawImage(images[i], 0, 0, null);
                }
                
                for (int i = 0; i < shieldImages.length; i++)
                {
                    shieldImages[i] = Calculator.toCompatibleImage(shieldImages[i]);
                }
                
                break;
        }

        this.entityImageSize = imageSize;
        
        drawingManipulation = new Point2D.Double
        (
                (explosionImageSize.width - entityImageSize.width) / 2,
                (explosionImageSize.height - entityImageSize.height) / 2
        );
    }

    private void loadImages(Dimension spriteSheetSize, BufferedImage spriteSheet, Dimension imageSize) 
    {
        int index = 0;
        for (int y = 0; y < spriteSheetSize.height; y += imageSize.height) 
        {
            for (int x = 0; x < spriteSheetSize.width; x += imageSize.width) 
            {
                images[index] = spriteSheet.getSubimage(x, y, imageSize.width, imageSize.height);
                shieldImages[index] = new BufferedImage(imageSize.width, imageSize.height, BufferedImage.TYPE_INT_ARGB);
                index++;
            }
        }
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, double shieldAngle, Point2D.Double translationPoint, double distance,
            double secondTranslation)
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform)original.clone();
        translationDistance = distance;
     
        transform.rotate(Math.toRadians(360 - shieldAngle), translationPoint.x, translationPoint.y);

        transform.translate(translationPoint.x - drawingManipulation.x, translationPoint.y - drawingManipulation.y);

        transform.translate(translationDistance, 0);
        
        transform.rotate(Math.toRadians(360 - secondTranslation), 0, 0);

        g2d.transform(transform);

        g2d.drawImage(shieldImages[frame], 0, 0, null); 

        g2d.setTransform(original); 

        frame++;
    }
    
    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, double shieldAngle, Point2D.Double translationPoint, double distance)
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform)original.clone();
        translationDistance = distance;
     
        transform.rotate(Math.toRadians(360 - shieldAngle), translationPoint.x, translationPoint.y);

        transform.translate(translationPoint.x - drawingManipulation.x, translationPoint.y - drawingManipulation.y);

        transform.translate(translationDistance, 0);

        g2d.transform(transform);

        g2d.drawImage(shieldImages[frame], 0, 0, null); 

        g2d.setTransform(original); 

        frame++;
    }
    
    public void draw(Graphics2D g2d, Point2D.Double location, Point2D.Double cameraLocation) 
    {
        g2d.drawImage(images[frame],
                (int)(Calculator.getScreenLocation(cameraLocation, location).x - drawingManipulation.x),
                (int)(Calculator.getScreenLocation(cameraLocation, location).y - drawingManipulation.y), null);
        frame++;
    }

    public boolean isDone() 
    {
        return frame == images.length;
    }
}