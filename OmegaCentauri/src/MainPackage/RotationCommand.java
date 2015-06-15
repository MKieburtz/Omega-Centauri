package MainPackage;

/**
 * @author Michael Kieburtz
 */
public class RotationCommand implements Command
{
    private final int value;
    
    public static final int ROTATE_LEFT = 0;
    public static final int ROTATE_RIGHT = 1;
    
    public RotationCommand(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
}
