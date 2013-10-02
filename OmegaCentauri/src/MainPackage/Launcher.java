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

        JButton goButton = new JButton("GO!");
        goButton.setText("GO!");
        goButton.setSize(100, 50);
        
        goButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launcherFrame.setVisible(false);
                launcherFrame.dispose();
                Game game = new Game();
            }
        });
        
        launcherFrame.getContentPane().setLayout(null);
        launcherFrame.add(goButton);
        
    }
}
