package MainPackage;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;

// @author Michael Kieburtz
// might refractor to playerShip
public class Player extends Ship {
    
    private String name;
    private Timer timer = new Timer();
    
    public String getName()
    {
        return this.name;
    }
    
    public Player(int x, int y, Type shipType)
    {
        location = new Point(x, y);
        type = shipType;
        imageFile = new File("resources/FighterGrey.png");
        setUpShipImage();
        timer.schedule(new MovementTimer(), 10); //very very fast
    }
    
    public Point getLocation()
    {
        return location;
    }
    
    private class MovementTimer extends TimerTask
    {
        public void run()
        {
            // look for key presses
        }
    }
    
    private void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            
        case KeyEvent.VK_RIGHT: {

        }
            break;
        case KeyEvent.VK_LEFT: {
            
        }
            break;
        case KeyEvent.VK_DOWN: {
            
        }
            break;
        case KeyEvent.VK_UP: {
            
        }
            break;
        }
    }
    
}
