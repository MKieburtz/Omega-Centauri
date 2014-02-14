
import MainPackage.EDTExceptionHandler;
import MainPackage.Launcher;

// @author Michael Kieburtz
public class OmegaCentauri {

    static Launcher launcher;

    public static void main(String args[]) {
        
        Thread.setDefaultUncaughtExceptionHandler(new EDTExceptionHandler());
        System.setProperty("sun.awt.exception.handler", EDTExceptionHandler.class.getName());
        
        launcher = new Launcher();
    }
}
