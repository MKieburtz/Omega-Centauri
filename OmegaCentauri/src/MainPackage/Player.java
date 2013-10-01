package MainPackage;
import java.awt.*;
import java.util.*;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {

    
    public Player(int x, int y, Type shipType)
    {
        location = new Point(x, y);
        type = shipType;
    }
}
