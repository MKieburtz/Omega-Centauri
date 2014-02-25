package MainPackage;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class Ally extends Ship {

    public Ally(int x, int y, Type shipType, double baseMaxVel, double maxVel,
            double angleIncrement, double acceleration, int shootingDelay, int health) {
        super(x, y, shipType, baseMaxVel, maxVel, angleIncrement, acceleration, shootingDelay, 
             health);
    }

    @Override
    public void CollisionEvent(Object object1, Object object2) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
