package MainPackage;

import java.util.ArrayList;

/**
 * @author Michael Kieburtz
 */
public interface Controllable 
{
    public void update(ArrayList<Command> commands);
}
