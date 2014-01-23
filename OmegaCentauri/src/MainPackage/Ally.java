package MainPackage;

// @author Michael Kieburtz

public class Ally extends Ship {

    public Ally(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration)
    {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration);
    }
}
