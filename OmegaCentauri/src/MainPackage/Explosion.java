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

    private final Dimension missileExplosionSize = new Dimension(400, 400);
    private final Dimension missileExplosionImageSize = new Dimension(100, 100);

    private final Dimension entityImageSize;
    
    private Dimension explosionImageSize;

    private int frame = 0;
    private boolean done = false;

    public static enum Type {

        fighter, missile
    };

    BufferedImage spriteSheet;

    BufferedImage[] images = null;
    
    private final String fighterExplosionPath = "resources/FighterExplosionSpritesheet.png";
    private final String missileExplosionPath = "resources/MissileExplosionSpritesheet.png";

    public Explosion(Type type, Dimension imageSize, Resources resources) {
        switch (type) {
            case fighter:
                spriteSheet = resources.getImageForObject(fighterExplosionPath);
                images = new BufferedImage[30];

                loadImages(fighterExplosionSize, spriteSheet, fighterExplosionImageSize);
                explosionImageSize = fighterExplosionImageSize;
                break;

            case missile:
                spriteSheet = resources.getImageForObject(missileExplosionPath);
                images = new BufferedImage[16];
                
                loadImages(missileExplosionSize, spriteSheet, missileExplosionImageSize);
                
                explosionImageSize = missileExplosionImageSize;
                break;
        }

        this.entityImageSize = imageSize;
    }

    private void loadImages(Dimension spriteSheetSize, BufferedImage spriteSheet, Dimension imageSize) {
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

    public void draw(Graphics2D g2d, Point2D.Double location, Point2D.Double cameraLocation) {
        AffineTransform original = g2d.getTransform();
        AffineTransform transform = (AffineTransform) original.clone();

        transform.translate(Calculator.getScreenLocation(cameraLocation, location).x - ((explosionImageSize.width - entityImageSize.width) / 2),
                Calculator.getScreenLocation(cameraLocation, location).y - ((explosionImageSize.height - entityImageSize.height) / 2));

        g2d.transform(transform);

        g2d.drawImage(images[frame], 0, 0, null);

        g2d.setTransform(original);

        frame++;

        if (frame == images.length) {
            done = true;
        }
    }

    public boolean isDone() {
        return done;
    }
}
