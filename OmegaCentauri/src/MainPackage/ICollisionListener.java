package MainPackage;

import java.util.ArrayList;

/**
 *
 * @author Michael Kieburtz
 */
public interface ICollisionListener {
    public void CollisionEvent(Ship ship, Shot shot, ArrayList<Ship> allShips);
}
