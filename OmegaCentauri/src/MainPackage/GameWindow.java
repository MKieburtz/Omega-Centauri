package MainPackage;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow {
    private JFrame gameFrame;
    private Game game;
    
    public GameWindow(int width, int height, Game game)
    {
       this.game = game;
       setUpWindow(1000, 600);
    }
    
    private void setUpWindow(int width, int height)
    {
       gameFrame = new JFrame("Omega Centauri");
       gameFrame.setVisible(true);
       gameFrame.setSize(width, height);
       gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       DrawingPanel gamePanel = new DrawingPanel(false, game); // not the launcher
       gameFrame.add(gamePanel);
       gamePanel.setSize(gameFrame.getSize());
       gamePanel.setVisible(true);
    }
}
