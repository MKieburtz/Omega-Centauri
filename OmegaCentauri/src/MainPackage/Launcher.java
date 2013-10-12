package MainPackage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
// @author Michael Kieburtz and Davis Freeman
public class Launcher {
    
    static JFrame launcherFrame = new JFrame("Omega Centauri Launcher");
    // Launcher -> game -> gamewindow -> renderer
    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

        launcherFrame.setSize(1000, 600);
        launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcherFrame.setVisible(true);
        launcherFrame.setLayout(null);
        

        JButton goButton = new JButton("GO!");
        goButton.setVisible(true);
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
        
        JButton closeButton = new JButton("Exit Game.");
        closeButton.setText("Exit Game.");
        closeButton.setLocation(0,55);
        closeButton.setSize(100, 50);
        
        
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                launcherFrame.setVisible(false);
                launcherFrame.dispose();
            }
        });
        
        JButton resolution1440by900 = new JButton("Resoluton: 1440 x 900");
        resolution1440by900.setText("Resolution: 1440 x 900");
        resolution1440by900.setLocation(0,110);
        resolution1440by900.setSize(200,50);
        resolution1440by900.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                launcherFrame.setSize(1440,900);
            }
        });
        
        launcherFrame.add(goButton);
        launcherFrame.add(closeButton);
        launcherFrame.add(resolution1440by900);
    }
});
}
}