/*
 * THIS CLASS IS GOING TO BE PART OF THIS PROJECT FOR EVER AND EVER AND EVER AND EVER
 * AND EVER AND EVER... PLUS INFINITTY.
 */

package MainPackage;
import java.awt.*;
import javax.swing.*;
// @author Michael Kieburtz

public class DrawingPanel extends JPanel{
    
    private boolean launcher = true;
    private Renderer renderer = new Renderer();
    private Game game;
    

    public DrawingPanel(boolean launcher, Game game)
    {
        this.launcher = launcher;
        this.setVisible(true);
        this.setSize(500, 500);
        this.game = game;
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (!launcher)
            renderer.drawScreen(g, game.getPlayer());
        
    }
}
