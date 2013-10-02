package MainPackage;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;
// @author Michael Kieburtz

public class DrawingPanel extends JPanel{
    
    private boolean launcher = true;
    Renderer renderer = new Renderer();
    

    public DrawingPanel(boolean launcher)
    {
        this.launcher = launcher;
        this.setVisible(true);
        this.setSize(500, 500);
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
//        if (!launcher)
//            renderer.drawScreen(g, images);
        
    }
}
