package MainPackage;

import java.awt.*;
import java.util.*;

/**
 *
 * @author Davis Freeman
 */
public class Settings {

    private Map<Resolutions, Point> resolutions = new EnumMap<Resolutions, Point>(Resolutions.class);

    public Settings(Dimension screenSize) {
        resolutions.put(Resolutions.FullScreen, new Point(screenSize.width, screenSize.height));
        resolutions.put(Resolutions.Resolution1440x900, new Point(screenSize.width, screenSize.height));
    }

    public Point getWidthAndHeightForResolution(Resolutions r) {
        return resolutions.get(r);
    }
}
