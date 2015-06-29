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
    
    private GameData gameData = new GameData();
    private Resources resources;
   
    private int frame = 0;
    
    private Point2D.Double location = new Point2D.Double();

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
            
    public Explosion(Type type, Dimension imageSize) 
    {
        resources = gameData.getResources();
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
                
                break;
            
            case range:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(rangeExplosionPath));
                images = new BufferedImage[14];
                shieldImages = new BufferedImage[14];
                
                loadImages(rangeExplosionSize, spriteSheet, rangeExplosionImageSize);
                
                explosionImageSize = rangeExplosionImageSize;
                
                break;
        }

        this.entityImageSize = imageSize;
        
        drawingManipulation = new Point2D.Double
        (
                (explosionImageSize.width - entityImageSize.width) / 2,
                (explosionImageSize.height - entityImageSize.height) / 2
        );
        
        gameData = new GameData();
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
    
    public void draw(Graphics2D g2d, boolean againstShield) 
    {
        if (!againstShield)
        {
            g2d.drawImage(images[frame],
                    (int)(Calculator.getScreenLocation(gameData.getCameraLocation(), location).x - drawingManipulation.x),
                    (int)(Calculator.getScreenLocation(gameData.getCameraLocation(), location).y - drawingManipulation.y), null);
            frame++;
        }
        else
        {
            AffineTransform original = g2d.getTransform();

            AffineTransform transform = (AffineTransform) original.clone();
            
            g2d.transform(transform); // this is the problem
        
            g2d.drawImage(shieldImages[8], (int)-drawingManipulation.x - 1, (int)-drawingManipulation.y + 12, null); 

            g2d.setTransform(original); 

            frame++;
        }
    }

    public boolean isDone() 
    {
        return frame == images.length;
    }
    
    public void updateLocation(Point2D.Double newLocation)
    {
        location = newLocation;
    }
}