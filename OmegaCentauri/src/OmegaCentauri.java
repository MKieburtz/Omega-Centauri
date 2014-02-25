
import MainPackage.EDTExceptionHandler;
import MainPackage.Launcher;

/**
* @author Michael Kieburtz
* @version Dev: 1.0.2
*/
public class OmegaCentauri {

    static Launcher launcher;

    public static void main(String args[]) {
        
        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
        
        launcher = new Launcher();
    }
}
