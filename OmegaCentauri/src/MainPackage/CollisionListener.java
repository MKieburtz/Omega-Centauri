package MainPackage;

import java.util.ArrayList;

/**
 *
 * @author Michael Kieburtz
 */
public interface CollisionListener {
    public void CollisionEventWithShot(Ship ship, Shot shot, ArrayList<Ship> allShips);
    public void CollisionEventWithShips(Ship ship1, Ship ship2);
    
}
