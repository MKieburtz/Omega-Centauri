package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

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

    private int frame = 0;

    public static enum Type 
    {
        fighter, missile, range
    };

    BufferedImage spriteSheet;

    BufferedImage[] images = null;
    
    private final String fighterExplosionPath = "resources/FighterExplosionSpritesheet.png";
    private final String missileExplosionPath = "resources/MissileExplosionSpritesheet.png";
    private final String rangeExplosionPath = "resources/RangeExplosionSpritesheet.png";

    public Explosion(Type type, Dimension imageSize, Resources resources) 
    {
        System.out.println();
        switch (type) 
        {
            case fighter:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(fighterExplosionPath));
                images = new BufferedImage[30];

                loadImages(fighterExplosionSize, spriteSheet, fighterExplosionImageSize);
                explosionImageSize = fighterExplosionImageSize;
                break;

            case missile:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(missileExplosionPath));
                images = new BufferedImage[16];
                
                loadImages(missileExplosionSize, spriteSheet, missileExplosionImageSize);
                
                explosionImageSize = missileExplosionImageSize;
                break;
            
            case range:
                spriteSheet = Calculator.toCompatibleImage(resources.getImageForObject(rangeExplosionPath));
                images = new BufferedImage[14];
                
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
    }

    private void loadImages(Dimension spriteSheetSize, BufferedImage spriteSheet, Dimension imageSize) 
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