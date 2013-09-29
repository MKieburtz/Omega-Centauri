package MainPackage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
// @author Michael Kieburtz
public class Launcher {
    
    static JFrame launcherFrame = new JFrame("Omega Centauri Launcher");
    
    public static void main(String args[])
    {
        launcherFrame.setVisible(true);
        launcherFrame.setSize(1000, 600);
        launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        DrawingPanel panel = new DrawingPanel(true);
//        launcherFrame.add(panel);
//        panel.setVisible(true);
//        panel.setSize(launcherFrame.getSize());
        
        JButton goButton = new JButton("GO!");
//        goButton.setText("GO!");
//        goButton.setVisible(true);
//        goButton.setLocation(0, 0);
//        goButton.setSize(100, 50);
        
        goButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launcherFrame.setVisible(false);
                launcherFrame.dispose();
                Game game = new Game();
                GameWindow gameWindow = new GameWindow();
            }
        });
        
        launcherFrame.add(goButton);
        
    }
}
