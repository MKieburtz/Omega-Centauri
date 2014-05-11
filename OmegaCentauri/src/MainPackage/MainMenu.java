package MainPackage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.sound.sampled.Clip;
/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class MainMenu implements MouseListener {

    private GameStartListener startListener;
    
    private MediaLoader loader;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<String> soundPaths = new ArrayList<String>();
    
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private ArrayList<Clip> sounds = new ArrayList<Clip>();
    
    private ArrayList<TwinklingStarChunk> stars = new ArrayList<TwinklingStarChunk>();
    
    private Settings settings;
            
    private final int START = 0;
    private final int CLOSE = 1;
    
    public MainMenu(OmegaCentauri game)
    {
        loader = new MediaLoader();
        startListener = game;
        
        imagePaths.add("src/resources/GoButton.png");
        imagePaths.add("src/resources/CloseButton.png");
        images = loader.loadImages(imagePaths);
        
        soundPaths.add("src/resources/mouseClick.wav");
        sounds = loader.loadSounds(soundPaths);
        
        
        for (int x = 0; x < 1000; x += 100)
        {
            for (int y = 0; y < 600; y += 100)
            {
                stars.add(new TwinklingStarChunk(x, y));
            }
        }
    }
    
    public void draw(Graphics g)
    {
        BufferedImage drawingImage = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = drawingImage.createGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 1000, 600);
        
        for (TwinklingStarChunk s : stars)
        {
            s.draw(g2d);
        }
        
        g.drawImage(drawingImage, 0, 0, null);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
