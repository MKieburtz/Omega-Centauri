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
abstract class Game extends JFrame implements KeyListener, MouseListener {

    protected ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    protected ArrayList<Ally> allyShips = new ArrayList<Ally>();
    protected ArrayList<Ship> shipsToDraw = new ArrayList<Ship>();
    protected ArrayList<Shot> shotsToDraw = new ArrayList<Shot>();
    protected Player player;

    abstract void CheckKeyPressed(KeyEvent e);

    abstract void CheckKeyReleased(KeyEvent e);

    abstract void CheckMousePressed(MouseEvent me);
    
    @Override
    public void keyPressed(KeyEvent e) {
        CheckKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        CheckKeyReleased(e);
    }

    // WARNING: USELESS METHOD... FOR NOW
    @Override
    public void keyTyped(KeyEvent ke) {
    }


    @Override
    public void mousePressed(MouseEvent me) {
        CheckMousePressed(me);
    }
}
