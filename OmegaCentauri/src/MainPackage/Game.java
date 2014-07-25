package MainPackage;

import java.util.*;
import javax.swing.JFrame;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
abstract class Game extends JFrame {

    protected ArrayList<EnemyShip> enemyShips = new ArrayList<>();
    protected ArrayList<Ally> allyShips = new ArrayList<>();
    protected ArrayList<Ship> shipsToDraw = new ArrayList<>();
    protected HashSet<Shot> allShots = new HashSet<>();
    protected Player player;
}
