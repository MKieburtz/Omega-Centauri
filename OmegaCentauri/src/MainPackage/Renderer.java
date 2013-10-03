package MainPackage;
import java.awt.*;
// @author Michael Kieburtz

public class Renderer {
    
    @SuppressWarnings("empty-statement")
    public Renderer()
    {
    ;
    }
    
    public void drawScreen(Graphics g, Player player)
    {
        g.drawImage(player.getImage(), player.getLocation().x, player.getLocation().y, null);
    }
    
}
