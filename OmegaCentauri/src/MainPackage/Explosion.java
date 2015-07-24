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
    
    private final Dimension enemyMediumFighterExplosionSize = new Dimension(2400, 3200);
    private final Dimension enemyMediumFighterExplosionImageSize = new Dimension(400, 400);
    
    private final Dimension EMFWingExplosionSize = new Dimension(2100, 2800);
    private final Dimension EMFWingExplosionImageSize = new Dimension(350, 350);

    private final Dimension entityImageSize;
    
    private Dimension explosionImageSize;
    
    private Point2D.Double drawingManipulation = new Point2D.Double();
    
    private GameData gameData = new GameData();
    private Resources resources;
   
    private int frame = 0;
    
    private Point2D.Double location = new Point2D.Double();

    public static enum Type 
    {
        fighter, missile, range, mediumFighter, wingExplosion
    };

    BufferedImage spriteSheet;

    BufferedImage[] images = null;
    
    private final String fighterExplosionPath = "resources/FighterExplosionSpritesheet.png";
    private final String missileExplosionPath = "resources/MissileExplosionSpritesheet.png";
    private final String rangeExplosionPath = "resources/RangeExplosionSpritesheet.png";
    private final String enemyMediumFighterExplosionPath = "resources/EnemyMediumFighterBodyExplosion.png";
    private final String EMFWingExplosionPath = "resources/EMFWingExplosion.png";
            
    public Explosion(Type type, Dimension imageSize) 
    {
        resources = gameData.getResources();
        switch (type) 
        {
            case fighter:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(fighterExplosionPath));
                images = new BufferedImage[30];

                loadImages(fighterExplosionSize, fighterExplosionImageSize);
                explosionImageSize = fighterExplosionImageSize;
                break;

            case missile:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(missileExplosionPath));
                images = new BufferedImage[16];
                
                loadImages(missileExplosionSize, missileExplosionImageSize);
                
                explosionImageSize = missileExplosionImageSize;
                break;
            
            case range:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(rangeExplosionPath));
                images = new BufferedImage[14];
                
                loadImages(rangeExplosionSize, rangeExplosionImageSize);
                
                explosionImageSize = rangeExplosionImageSize;
                break;
                
            case mediumFighter:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(enemyMediumFighterExplosionPath));
                images = new BufferedImage[48];
                
                loadImages(enemyMediumFighterExplosionSize, enemyMediumFighterExplosionImageSize);
                
                explosionImageSize = enemyMediumFighterExplosionImageSize;
                break;
             
            case wingExplosion:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(EMFWingExplosionPath));
                images = new BufferedImage[48];
                
                loadImages(EMFWingExplosionSize, EMFWingExplosionImageSize);
                
                explosionImageSize = EMFWingExplosionImageSize;
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

    private void loadImages(Dimension spriteSheetSize, Dimension imageSize) 
    {
        int index = 0;
        for (int y = 0; y < spriteSheetSize.height; y += imageSize.height) 
        {
            for (int x = 0; x < spriteSheetSize.width; x += imageSize.width) 
            {
                images[index] = spriteSheet.getSubimage(x, y, imageSize.width, imageSize.height);
                index++;
            }
        }
    }
    
    public void draw(Graphics2D g2d) 
    {
        g2d.drawImage(images[frame],
                (int)(Calculator.getScreenLocation(gameData.getCameraLocation(), location).x - drawingManipulation.x),
                (int)(Calculator.getScreenLocation(gameData.getCameraLocation(), location).y - drawingManipulation.y), null);
        frame++;
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