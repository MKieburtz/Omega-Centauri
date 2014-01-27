package MainPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JFrame;

// @author Michael Kieburtz
abstract class Game extends JFrame implements KeyListener{

    protected ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    protected ArrayList<Ally> allyShips = new ArrayList<Ally>();
    protected ArrayList<Ship> shipsToDraw = new ArrayList<Ship>();
    protected ArrayList<Shot> shotsToDraw = new ArrayList<Shot>();
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
