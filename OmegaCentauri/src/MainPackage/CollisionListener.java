package MainPackage;

import java.util.ArrayList;

/**
 *
 * @author Michael Kieburtz
 */
public interface CollisionListener {
    public boolean CollisionEventWithShot(Ship ship, Shot shot, ArrayList<Ship> allShips);
}
