
import MainPackage.EDTExceptionHandler;
import MainPackage.Launcher;
import javax.swing.SwingUtilities;

/**
 * @author Michael Kieburtz
 * @version Dev: 1.0.2
 */
public class OmegaCentauri {

    static Launcher launcher;

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
                System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());

                new Launcher();
            }
        });

    }
}
