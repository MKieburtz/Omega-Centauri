package MainPackage;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow {
    private JFrame gameFrame;
    public DrawingPanel gamePanel;
    private Game game;
    private boolean move = false;
    private java.util.Timer timer = new java.util.Timer();
    private final int timerDelay = 100;
    
    public GameWindow(int width, int height, Game game)
    {
       
       timer.schedule(new MovementTimer(), timerDelay);
       this.game = game;
       setUpWindow(1000, 600);
    }
    
    private void setUpWindow(int width, int height)
    {
       gameFrame = new JFrame("Omega Centauri");
       gameFrame.setVisible(true);
       gameFrame.setSize(width, height);
       gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       gamePanel = new DrawingPanel(false, game); // not the launcher
       
       gameFrame.add(gamePanel);
       gamePanel.setSize(gameFrame.getSize());
       gamePanel.setVisible(true);
    }
    
    
    private class MovementTimer extends TimerTask
    {
        @Override
        public void run()
        {
            if (!move)
                move = true;
            
            timer.schedule(new MovementTimer(), timerDelay);
        }
    }
    
    private void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            
        case KeyEvent.VK_RIGHT: {
            if (move)
            {
                game.movePlayer();
                move = false;
            }
        }
            break;
        case KeyEvent.VK_LEFT: {
            if (move)
            {
                game.movePlayer();
                move = false;
            }
        }
            break;
        case KeyEvent.VK_DOWN: {
            if (move)
            {
                game.movePlayer();
                move = false;
            }
        }
            break;
        case KeyEvent.VK_UP: {
            if (move)
            {
                game.movePlayer();
                move = false;
            }
        }
            break;
        }
    }
}
