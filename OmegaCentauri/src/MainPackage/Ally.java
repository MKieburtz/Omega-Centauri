package MainPackage;

// @author Michael Kieburtz

import java.awt.geom.Point2D;


public class Ally extends Ship {

    public Ally(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, Point2D.Double cameraLocation)
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, cameraLocation);
    }
}
