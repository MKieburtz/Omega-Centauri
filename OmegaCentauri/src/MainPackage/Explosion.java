package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * @author Michael Kieburtz
 */
public class Explosion {

    private final Dimension fighterExplosionSize = new Dimension(1000, 1200);
    private final Dimension fighterExplosionImageSize = new Dimension(200, 200);
    
    private final Dimension shipSize;

    private int frame = 0;
    private boolean done = false;
    
    public static enum Type {

        fighter, missile
    };

    BufferedImage spriteSheet;

    BufferedImage[] images = null;

    public Explosion(Type type, Dimension imageSize) {
        switch (type) {
            case fighter:
                spriteSheet = Resources.getImageForFighterExplosion().get(0);
                images = new BufferedImage[30];

                int index = 0;
                for (int y = 0; y < fighterExplosionSize.height; y += fighterExplosionImageSize.height) 
                {
                    for (int x = 0; x < fighterExplosionSize.width; x += fighterExplosionImageSize.width) 
                    {
                        images[index] = spriteSheet.getSubimage(x, y, fighterExplosionImageSize.width, fighterExplosionImageSize.height);
                        index++;
                    }
                }
                break;
        }
        
        this.shipSize = imageSize;
    }
    
    public void draw(Graphics2D g2d, Point2D.Double location, Point2D.Double cameraLocation)
    {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform)original.clone();
        
        transform.translate(Calculator.getScreenLocation(cameraLocation, location).x + shipSize.width / 2,
                Calculator.getScreenLocation(cameraLocation, location).y + shipSize.height / 2);
        
        g2d.transform(transform);
        
        g2d.drawImage(images[frame], -100, -100, null);
        
        g2d.setTransform(original);
        
        frame++;
        
        if (frame == images.length)
        {
            done = true;
        }
    }
   
    public boolean isDone()
    {
        return done;
    }
}
