package MainPackage;

// @author Michael Kieburtz
import java.awt.EventQueue;

public class Main {
    
    static Launcher launcher;
    
    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                launcher = new Launcher();
            }
        });
    }
}
