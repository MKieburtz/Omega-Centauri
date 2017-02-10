package MainPackage;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * @author Michael Kieburtz
 */
public class KeyActionListener extends AbstractAction
{
    int keycode;
    boolean up;
    boolean checkList;
    public KeyActionListener(int keycode, boolean up, boolean checkList)
    {
        this.keycode = keycode;
        this.up = up;
        this.checkList = checkList;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {}
}
