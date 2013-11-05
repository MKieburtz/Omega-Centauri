package MainPackage;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.*;

// @author Michael Kieburtz
public class Game {
    private ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
    private ArrayList<Ally> allyShips = new ArrayList<Ally>();
    private Player player;
    
    GameWindow window;
    
    public Game(int WindowWidth, int WindowHeight)
    {
        player = new Player(((WindowWidth / 2)-50),((WindowHeight/ 2)-50), Type.Fighter);
        window = new GameWindow(WindowWidth, WindowHeight, this);
        
    }
    public Player getPlayer()
    {
        return player;
    }
    
    public void movePlayer(int x, int y)
    {
        player.moveTo(x, y);
    }
    
    public void movePlayer(boolean Slowingdown, double driftAngle)
    {
        player.move(Slowingdown, driftAngle);
    }
    
    public void movePlayerRelitive(int dx, int dy)
    {
        player.moveRelitive(dx, dy);
    }
    
    public void rotatePlayer(boolean positive)
    {
        player.rotate(positive);
    }
    public void rotatePlayer(double amount)
    {
        player.rotate(amount);
    }
}
