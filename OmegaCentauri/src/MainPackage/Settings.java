package MainPackage;

import java.awt.*;
import java.util.*;
import javax.print.attribute.ResolutionSyntax;

/**
 *
 * @author Davis Freeman
 */
public class Settings {

    private Map<Resolutions, Point> resolutions = new EnumMap<Resolutions, Point>(Resolutions.class);

    private Dimension resolution = new Dimension();
    
    public Settings(Dimension screenSize) {
        resolutions.put(Resolutions.FullScreen, new Point(screenSize.width, screenSize.height));
        resolutions.put(Resolutions.Resolution1440x900, new Point(screenSize.width, screenSize.height));
        resolutions.put(Resolutions.Default, new Point(screenSize.width / 2, screenSize.height / 2));
    }

    public Point getWidthAndHeightForResolution(Resolutions r) {
        return resolutions.get(r);
    }
    
    public void setResolution(Resolutions r)
    {
        
        resolution = r.equals(Resolutions.Resolution1440x900) ? null : new Dimension(getWidthAndHeightForResolution(r).x, getWidthAndHeightForResolution(r).y);
    }
    
    public Dimension getResolution()
    {
        return resolution;
    }
}
