package MainPackage;
import javax.swing.*;
// @author Michael Kieburtz
public class Launcher {
    
    static JFrame launcherFrame = new JFrame("Omega Centauri");
    
    public static void main(String args[])
    {
        launcherFrame.setVisible(true);
        launcherFrame.setSize(1000, 600);
        launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawingPanel panel = new DrawingPanel();
        launcherFrame.add(panel);
        panel.setVisible(true);
        panel.setSize(launcherFrame.getSize());
    }
}
