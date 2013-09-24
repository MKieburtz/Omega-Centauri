package MainPackage;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;
// @author Michael Kieburtz

public class DrawingPanel extends JPanel{
    
    private BufferedImage testImage;
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    Renderer renderer = new Renderer();
    File shipFile = new File("resources/FighterGrey.png");
    

    public DrawingPanel()
    {
        try {
            FileInputStream fis = new FileInputStream(shipFile);
            testImage = ImageIO.read(fis);
            images.add(testImage);
        } catch (IOException ex) {
            System.err.println("IOException, cannot read file");
        }
        this.setVisible(true);
        this.setSize(500, 500);
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        renderer.drawScreen(g, images);
    }
}
