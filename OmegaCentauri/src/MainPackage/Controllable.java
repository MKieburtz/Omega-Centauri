package MainPackage;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public interface Controllable {
    public void update(ArrayList<Command> commands);
    public void draw(Graphics2D g2d);
}
