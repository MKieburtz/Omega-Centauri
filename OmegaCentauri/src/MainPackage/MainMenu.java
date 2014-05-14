package MainPackage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    
    private Rectangle startRectangle;
    private Rectangle closeRectangle;
    
    private boolean active;
    
    public MainMenu(OmegaCentauri game)
    {
        active = true;
        loader = new MediaLoader();
        startListener = game;
        
        imagePaths.add("src/resources/GoButtonUnhovered.png");
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
        
        startRectangle = new Rectangle(0, 600 - 30 - images.get(START).getHeight() - 3, images.get(START).getWidth(), images.get(START).getHeight());
        closeRectangle = new Rectangle(1000 - 30 - images.get(CLOSE).getWidth(),
                600 - 13 - images.get(CLOSE).getHeight() * 2, images.get(CLOSE).getWidth(), images.get(CLOSE).getHeight());
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
        g2d.setColor(Color.CYAN);
        g2d.drawLine(0, 466, 1000, 466);
        
//        g2d.draw(startRectangle);
//        g2d.draw(closeRectangle);
        
        g2d.drawImage(images.get(START), 0, drawingImage.getHeight() - 30 - images.get(START).getHeight() - 3, null);
        
        g2d.drawImage(images.get(CLOSE), drawingImage.getWidth() - 30 - images.get(CLOSE).getWidth(), 
                drawingImage.getHeight() - 13 - images.get(CLOSE).getHeight() * 2, null);
        g.drawImage(drawingImage, 0, 0, null);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (startRectangle.contains(e.getPoint()))
        {
            active = false;
            startListener.gameStart();
        }
        if (closeRectangle.contains(e.getPoint()))
        {
            System.exit(0);
        }
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

    
    public boolean isActive()
    {
        return active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
}
