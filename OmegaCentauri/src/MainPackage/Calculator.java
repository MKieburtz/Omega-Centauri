
package MainPackage;

/**
 * @author Michael Kieburtz
 */
public class Calculator { 
    /* 
     * this class that will have all sorts of useful methods to help
     * calculate stuff
     */
    
    private Calculator() {
        // never used because all methods will be static
    }
    
    public static double CalcAngleMoveX(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double CalcAngleMoveY(double angle) {
        return Math.sin(Math.toRadians(angle));
    }
}
