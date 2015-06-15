package MainPackage;

/**
 * @author Michael Kieburtz
 */
public class MovementCommand implements Command
{
    private final int value;
    
    public static final int MOVE_FORWARD = 0; // decides if it needs to drift
    
    public MovementCommand(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
}
