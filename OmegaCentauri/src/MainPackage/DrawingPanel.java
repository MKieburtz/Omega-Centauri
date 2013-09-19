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
    private ArrayList<BufferedImage> images = new ArrayList<>();
    Renderer renderer = new Renderer();

    public DrawingPanel()
    {
        try {
            testImage = ImageIO.read(new File("C:/Users/543021/Documents/OmegaCentauriImages/ball.png"));
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
