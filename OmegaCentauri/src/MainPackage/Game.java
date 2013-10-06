package MainPackage;
import java.util.*;

// @author Michael Kieburtz
public class Game {
    private ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    private ArrayList<Ally> allyShips = new ArrayList<Ally>();
    private Player player;
    public Player getPlayer()
    {
        return player;
    }
    GameWindow window;
    
    public Game()
    {
        window = new GameWindow(1000, 600, this);
        player = new Player(100, 100, Type.Fighter, window.gamePanel);
    }
    
    public void movePlayer() // int x, int y
    {
        player.moveTo(100, 100);
    }
    
    public void movePlayerRelitive() // int dx, int dy
    {
        
    }
}
