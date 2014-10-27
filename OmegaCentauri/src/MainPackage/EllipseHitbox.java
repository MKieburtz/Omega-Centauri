package MainPackage;

import java.awt.geom.Point2D;

/**
 * @author Michael Kieburtz
 */
public class EllipseHitbox {
    
    private Point2D.Double centerPoint = new Point2D.Double();
    // this is the longer "radius" length
    private double semiMajorAxisLength;
    // this is the shorter "radius" length
    private double semiMinorAxisLength;
    
    private double angle = 0;
    
    private boolean circle;
    
    public EllipseHitbox(double semiMajorLength, double semiMinorLength)
    {
        this.semiMajorAxisLength = semiMajorLength;
        this.semiMinorAxisLength = semiMinorLength;
        
        circle = semiMajorLength == semiMinorLength;
    }
}
