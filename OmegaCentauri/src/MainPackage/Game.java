package MainPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.JFrame;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
abstract class Game extends JFrame implements MouseListener {

    protected ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    protected ArrayList<Ally> allyShips = new ArrayList<Ally>();
    protected ArrayList<Ship> shipsToDraw = new ArrayList<Ship>();
    protected HashSet<Shot> allShots = new HashSet<Shot>();
    protected Player player;

    abstract void CheckMousePressed(MouseEvent me);
    
    @Override
    public void mousePressed(MouseEvent me) {
        CheckMousePressed(me);
    }
}
