package MainPackage;

import javax.swing.*;
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
public class Launcher extends JFrame implements MouseListener {

    
    // constants for image drawing:
    private final int GOBUTTON = 0;
    private final int LOGO = 1;
    private final int BACKGROUND = 2;
    private final int CLOSE = 3;
    
    private final Renderer renderer = new Renderer();
    private final MediaLoader mediaLoader = new MediaLoader();
    private final ArrayList<String> imagePaths = new ArrayList<String>();
    private final ArrayList<String> soundPaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    private ArrayList<Clip> sounds = new ArrayList<Clip>();
    private java.util.Timer refreshTimer = new java.util.Timer();
    private boolean fullScreen = false;
    private Settings settings;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public Launcher() {
        imagePaths.add("src/resources/GoButton.png");
        imagePaths.add("src/resources/OmegaCentauriLogo.png");
        imagePaths.add("src/resources/LauncherBackground.png");
        imagePaths.add("src/resources/CloseButton.png");
        images = mediaLoader.loadImages(imagePaths);

        soundPaths.add("src/resources/mouseClick.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

        settings = new Settings(screenSize);
        
        setUpWindow();
    }

    private void setUpWindow() {

        setIconImage(images.get(1));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        
        setContentPane(new BackPanel());
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        
        DrawingPanel panel = new DrawingPanel();
        add(panel);
        addMouseListener(this);
        setTitle("Omega Centauri Launcher");
        
        setVisible(true);
    }

    private void setWindowFullScreen() {
        
    }

    public class BackPanel extends JPanel {

        public BackPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
        // Allow super to paint
        super.paintComponent(g);
        System.out.println("backPanel drew");
        // Apply our own painting effect
        Graphics2D g2d = (Graphics2D) g.create();
        // 50% transparent Alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));

        g2d.setColor(getBackground());
        g2d.fill(getBounds());

        g2d.dispose();
        }
    }
    
    public class DrawingPanel extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            renderer.drawLauncher(g.create(), images.get(GOBUTTON), images.get(BACKGROUND), images.get(CLOSE), screenSize); // use active rendering            
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Rectangle rect = new Rectangle(375, 450, 200, 100);

        if (rect.contains(new Point(me.getX(), me.getY()))) {
            sounds.get(0).start();
            closeWindow();
            OmegaCentauri_ oc = new OmegaCentauri_(getWidth(), getHeight(), 85, renderer, fullScreen, graphicsDevice, images.get(1));
        }
        
        Rectangle fullrect = new Rectangle(225,500,100,50);
        if (fullrect.contains(new Point(me.getX(), me.getY())))
        {
            if (graphicsDevice.isFullScreenSupported()) {
                    setWindowFullScreen();
                } else {
                    System.err.println("Fullscreen is not supported on your system!");
                }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Rectangle rect = new Rectangle(625, 500, 100, 50); // exit game

        if (rect.contains(new Point(me.getX(), me.getY()))) {
            System.exit(0);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    private void closeWindow() {
        refreshTimer.purge();
        refreshTimer.cancel();
        setVisible(false); 
        this.dispose();
    }

}
