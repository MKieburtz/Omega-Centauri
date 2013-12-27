

// @author Michael Kieburtz
import java.awt.EventQueue;
import MainPackage.*;

public class OmegaCentauri {
    
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
