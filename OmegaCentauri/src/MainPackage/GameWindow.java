package MainPackage;
import javax.swing.*;

/**
 *
 * @author Kieburtz
 */
public class GameWindow {
    private JFrame gameFrame;
    
    public GameWindow()
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
}
