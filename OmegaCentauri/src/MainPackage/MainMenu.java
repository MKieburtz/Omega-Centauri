package MainPackage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class MainMenu implements MouseListener, MouseMotionListener {

    private GameStartListener startListener;
    
    private MediaLoader loader;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<String> soundPaths = new ArrayList<String>();
    
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private ArrayList<BufferedImage> subimages = new ArrayList<BufferedImage>();
    private ArrayList<Clip> sounds = new ArrayList<Clip>();
    
    private ArrayList<TwinklingStarChunk> stars = new ArrayList<TwinklingStarChunk>();
    
    private Settings settings;
            
    private int START = 0;
    private final int STARTHOVER = 1; //filler so hover doesn't turn into the close button :3
    private final int HOVERTILES = 2;
    private final int CLOSENOHOVER = 3;
    private final int CLOSEHOVER = 4;
    
    private int hovered = 0;
    
    private boolean startHover = false;
    private boolean closeHover = false;
    
    private Rectangle startRectangle;
    private Rectangle closeRectangle;
    
    private boolean active;
    
    private Point size;
    private Dimension screenSize;
    
    private Rectangle screenRect;
    
    public MainMenu(OmegaCentauri game)
    {
        active = true;
        loader = new MediaLoader();
        startListener = game;
        

        imagePaths.add("src/resources/StartButtonNoHover.png");
        imagePaths.add("src/resources/StartButtonHover.png");
        imagePaths.add("src/resources/StartButtonTileframe.png");
        imagePaths.add("src/resources/CloseButtonNoHover.png");
        imagePaths.add("src/resources/CloseButtonHover.png");
        images = loader.loadImages(imagePaths);
        images = Calculator.toCompatibleImages(images);
        
        loadSubimages();
        soundPaths.add("src/resources/mouseClick.wav");
        sounds = loader.loadSounds(soundPaths);
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        size = new Point(game.getWidth(), game.getHeight());
        
        screenRect = new Rectangle(0, 0, size.x, size.y);
        
        // load enough stars for the entire screen;
        for (int x = 0; x < screenSize.width; x += 100)
        {
            for (int y = 0; y < screenSize.height; y += 100)
            {
                stars.add(new TwinklingStarChunk(x, y));
            }
        }
        
        setRects();
    }
    
    private void loadSubimages()
    {
        for (int i = 0; i < 3600; i+=200)
        {
            subimages.add(images.get(HOVERTILES).getSubimage(i, 0, 200, 100));
        }
    }
    
    public void draw(Graphics g)
    {
        BufferedImage drawingImage = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = drawingImage.createGraphics();
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, size.x, size.y);
        
        for (TwinklingStarChunk s : stars)
        {
            if (s.getBoundingRect().intersects(screenRect))
                s.draw(g2d);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, size.y - 100, size.x, size.y - 100);
       
        g2d.setColor(Color.RED);
                
        //g2d.drawImage(hovering ? hovered == subimages.size() - 1 ? images.get(STARTHOVER) : subimages.get(hovered): images.get(START), 0, size.y - 36 - images.get(START).getHeight() - 3, null);
        
        //g2d.drawImage(hovering ? images.get(STARTHOVER) : images.get(START),0, size.y - 36 - images.get(START).getHeight() - 3, null);
        
        if(startHover)
        {
            g2d.drawImage(images.get(STARTHOVER), startRectangle.x, startRectangle.y, null);
        } else {
            g2d.drawImage(images.get(START), startRectangle.x, startRectangle.y, null);
        }
        
        //System.out.println(images.get(CLOSEHOVER).getHeight());
        if (closeHover)
        {
            g2d.drawImage(images.get(CLOSEHOVER), closeRectangle.x, closeRectangle.y, null);
        } else{
            g2d.drawImage(images.get(CLOSENOHOVER), closeRectangle.x, closeRectangle.y, null);
        }
        
        g2d.setColor(Color.CYAN);
        g2d.drawLine(0, size.y - 100, size.x, size.y - 100);     
        
//        g2d.setColor(Color.RED);
//        g2d.draw(startRectangle);
//        g2d.draw(closeRectangle);
        
        g.drawImage(drawingImage, 0, 0, null);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (startRectangle.contains(e.getPoint()))
        {
            active = false;
            startListener.gameStart();
            sounds.get(0).setFramePosition(0);
            sounds.get(0).start();
        }
        if (closeRectangle.contains(e.getPoint()))
        {
            sounds.get(0).setFramePosition(0);
            sounds.get(0).start();
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        startHover = false;
        hovered = 0;
    }

        @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (startRectangle.contains(me.getPoint()))
        {
            startHover = true;
        } else if (closeRectangle.contains(me.getPoint()))
        {
            closeHover = true;
        } else
        {
            startHover = false;
            closeHover = false;
        }
    }
    
    public boolean isActive()
    {
        return active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
    

    public Point getSize()
    {
        return size;
    }
    
    public void setSize(int width, int height)
    {
        size.setLocation(width, height);
        setRects();
    }
    
    private void setRects()
    {
        
        startRectangle = new Rectangle((size.x / 2 + 100) - images.get(START).getWidth(), size.y - 13 - images.get(START).getHeight() * 2 + 25, images.get(START).getWidth(), images.get(START).getHeight());
        closeRectangle = new Rectangle(size.x - images.get(CLOSEHOVER).getWidth() - 30, 
                size.y - 13 - images.get(CLOSEHOVER).getHeight() * 2 + 25, images.get(CLOSENOHOVER).getWidth(), images.get(CLOSENOHOVER).getHeight());
        screenRect.setBounds(0, 0, size.x, size.y);
    }
}