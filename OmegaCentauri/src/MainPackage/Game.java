package MainPackage;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.*;
import javax.swing.JFrame;

// @author Michael Kieburtz
abstract class Game extends JFrame implements KeyListener{

    protected ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    protected ArrayList<Ally> allyShips = new ArrayList<Ally>();
    protected Player player;
    
    
    
    abstract void CheckKeyPressed(KeyEvent e);
    abstract void CheckKeyReleased(KeyEvent e);

    @Override
    public void keyPressed(KeyEvent e) {
        CheckKeyPressed(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        CheckKeyReleased(e);
    }
    
    // WARNING: USELESS METHOD... FOR NOW

    public void keyTyped(KeyEvent ke) {
        
    }
    
    
}
