package MainPackage;
// @author Michael Kieburtz

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

interface Enemy {
    void fire();
    void draw(Graphics2D g2d, Point2D.Double cameraLocation);
}
