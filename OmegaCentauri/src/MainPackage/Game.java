package MainPackage;

import java.util.*;
import javax.swing.JFrame;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
abstract class Game extends JFrame {

    protected ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    protected ArrayList<Ally> allyShips = new ArrayList<Ally>();
    protected ArrayList<Ship> shipsToDraw = new ArrayList<Ship>();
    protected HashSet<Shot> allShots = new HashSet<Shot>();
    protected Player player;
}
