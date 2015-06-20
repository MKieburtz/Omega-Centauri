package MainPackage;

/**
 * @author Michael Kieburtz
 */
public class ShootingCommand implements Command
{
    private final int value;
    
    public static final int SHOOT = 0;
    
    public ShootingCommand(int value)
    {
        this.value = value;
    }
    
    @Override
    public int getValue()
    {
        return value;
    }
}
