package MainPackage;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow {
    private JFrame gameFrame;
    
    public GameWindow(int width, int height)
    {
       gameFrame = new JFrame("Omega Centauri");
       gameFrame.setVisible(true);
       gameFrame.setSize(1000, 600);
       gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       DrawingPanel gamePanel = new DrawingPanel(false); // not the launcher
       gameFrame.add(gamePanel);
       gamePanel.setSize(gameFrame.getSize());
       gamePanel.setVisible(true);
    }
    
    private void setUpWindow(int width, int height)
    {
       gameFrame = new JFrame("Omega Centauri");
       gameFrame.setVisible(true);
       gameFrame.setSize(width, height);
       gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       DrawingPanel gamePanel = new DrawingPanel(false); // not the launcher
       gameFrame.add(gamePanel);
       gamePanel.setSize(gameFrame.getSize());
       gamePanel.setVisible(true);
    }
}
