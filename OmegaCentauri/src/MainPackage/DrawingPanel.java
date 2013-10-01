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
    private boolean launcher = true;
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    Renderer renderer = new Renderer();
    // File -> FileInputStream -> ImageIO -> buffered image
    ArrayList<File> shipFiles = new ArrayList<File>();
    ArrayList<FileInputStream> resourceStreams = new ArrayList<FileInputStream>();
    

    public DrawingPanel(boolean launcher)
    {
        this.launcher = launcher;
        shipFiles.add(new File("resources/FighterGrey.png"));
        shipFiles.add(new File("resources/LargeFighter.jpg"));
        try {
            for (int i = 0; i < shipFiles.size(); i++)
            {
            resourceStreams.add(new FileInputStream(shipFiles.get(i)));
            }
            testImage = ImageIO.read(resourceStreams.get(0));
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
        if (!launcher)
            renderer.drawScreen(g, images);
        
    }
}
