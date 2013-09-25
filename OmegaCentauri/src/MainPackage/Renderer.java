package MainPackage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
// @author Michael Kieburtz

public class Renderer {
    
    public Renderer()
    {
    ;
    }
    
    public void drawScreen(Graphics g, ArrayList<BufferedImage> images)
    {
        for (int i = 0; i < images.size(); i++)
        {
            g.drawImage(images.get(i), 0, 0, null);
        }
    }
    
}
