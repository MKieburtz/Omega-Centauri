package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * @author Michael Kieburtz
 */
abstract class Shield {

    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private MediaLoader loader = new MediaLoader();
    private double angle;
    private boolean active = false;
    private int opacity = 100;
    private Point2D.Double relitiveDistance;

    public Shield(double angle, Point2D.Double relitiveDistance) {
        this.angle = angle;
        this.relitiveDistance = relitiveDistance;
    }

    public void draw(Graphics2D g2d, Point2D.Double cameraLocation, Point2D.Double instanceLocation) {
        AffineTransform original = g2d.getTransform();

        AffineTransform transform = (AffineTransform) original.clone();

        transform.rotate(Math.toRadians(angle), getScreenLocationMiddle(cameraLocation, instanceLocation).x,
                getScreenLocationMiddle(cameraLocation, instanceLocation).y);

        transform.translate(getScreenLocation(cameraLocation, instanceLocation).x,
                getScreenLocation(cameraLocation, instanceLocation).y);
    }

    protected Point2D.Double getScreenLocationMiddle(Point2D.Double cameraLocation, Point2D.Double location) {
        return new Point2D.Double((location.x - cameraLocation.x) + (images.get(0).getWidth() / 2),
                (location.y - cameraLocation.y) + (images.get(0).getHeight() / 2));
    }

    protected Point2D.Double getScreenLocation(Point2D.Double cameraLocation, Point2D.Double location) {
        double x = location.x - cameraLocation.x;
        double y = location.y - cameraLocation.y;

        return new Point2D.Double(x, y);
    }
}
