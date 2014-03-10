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

    private int width = 1000;
    private int height = 600;
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
        imagePaths.add("src/resources/FullscreenButton.png");
        images = mediaLoader.loadImages(imagePaths);

        soundPaths.add("src/resources/mouseClick.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

        settings = new Settings(screenSize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize);
        //setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0,255,0,0));
        
        setContentPane(new BackPanel());
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        
        DrawingPanel panel = new DrawingPanel();
        add(panel);
        
        setVisible(true);
        
        //setUpWindow(width, height);
    }

    private void setUpWindow(int width, int height) {

        //setIconImage(images.get(1));
        //setSize(width, height);
        //setUndecorated(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setBackground(new Color(0,255,0,0));
        //setLayout(new BorderLayout());

        //addMouseListener(this);
        //setTitle("Omega Centauri Launcher");
        //setResizable(false);
        //setLocationRelativeTo(null);
        
        //addComponents();
        //requestFocus();
        //setVisible(true);

    }

    private void addComponents() {
        
    }

    private void setWindowFullScreen() {
        fullScreen = true;
        graphicsDevice.setFullScreenWindow(this);
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
            //super.paintComponent(g);
            //refreshTimer.schedule(new refreshTimer(), 1);
            renderer.drawLauncher(g.create(), images.get(0), images.get(2), images.get(images.size() - 2), images.get(4)); // use active rendering            
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
            OmegaCentauri_ oc = new OmegaCentauri_(width, height, 85, renderer, fullScreen, graphicsDevice, images.get(1));
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

    private void changeResolution(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        pack();
    }

    private class refreshTimer extends TimerTask {

        @Override
        public void run() {
//            if (panel.getGraphics() != null)
//                renderer.drawLauncher(panel.getGraphics(), images.get(0), images.get(2), images.get(images.size() - 2), images.get(4)); // use active rendering
//            
//            refreshTimer.schedule(new refreshTimer(), 100);
        }
    }
}
