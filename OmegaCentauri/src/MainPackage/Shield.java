package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 */
public class Shield {

    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private BufferedImage activeImage;
    private MediaLoader loader = new MediaLoader();
    private double angle;
    private int opacity = 0;
    private Point2D.Double screenLocationMiddle = new Point2D.Double();
    private Ellipse2D.Double shield;

    public Shield(double angle, Point2D.Double location, Point2D.Double cameraLocation) {
        this.angle = angle;
        shield = new Ellipse2D.Double(location.x, location.y, 30, 30);
        screenLocationMiddle = Calculator.getScreenLocationMiddle(cameraLocation, location, shield.getWidth(), shield.getHeight());
        imagePaths.add("src/resources/FILLERshield.png");
        images = loader.loadImages(imagePaths);
        activeImage = images.get(0);
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double instanceLocation) {
        if (opacity > 0) {
            AffineTransform original = g2d.getTransform();

            AffineTransform transform = (AffineTransform) original.clone();

            transform.rotate(Math.toRadians(angle), screenLocationMiddle.x, screenLocationMiddle.y);

            transform.translate(instanceLocation.x - cameraLocation.x, instanceLocation.y - cameraLocation.y);

            g2d.drawImage(activeImage, transform, null);
        }
    }

    public void activate() {
        opacity = 100;
    }
}
