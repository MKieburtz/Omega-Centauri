package MainPackage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
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
    private final Panel panel = new Panel(1000, 600); // this will be changed when we do resolution things
    private java.util.Timer refreshTimer = new java.util.Timer();
    private boolean fullScreen = false;
    private Settings settings;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public Launcher() {
        imagePaths.add("src/resources/GoButton.png");
        imagePaths.add("src/resources/OmegaCentauriLogo.png");
        imagePaths.add("src/resources/LauncherBackground.jpg");
        imagePaths.add("src/resources/CloseButton.png");
        images = mediaLoader.loadImages(imagePaths);

        soundPaths.add("src/resources/mouseClick.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

        settings = new Settings(screenSize);

        setUpWindow(width, height);
        addButtons();
    }

    private void setUpWindow(int width, int height) {

        this.setIconImage(images.get(1));
        this.setSize(width, height);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this.setBackground(new Color(0,0,0,0));
        this.setLayout(null);

        this.addMouseListener(this);
        this.setTitle("Omega Centauri Launcher");
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.requestFocus();
        this.setVisible(true);

    }

    private void addButtons() {
        JButton goButton = new JButton("GO!");
        goButton.setVisible(true);
        goButton.setText("GO!");
        goButton.setSize(100, 50);

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sounds.get(0).start();
                closeWindow();
                OmegaCentauri_ oc = new OmegaCentauri_(width, height, 85, renderer, fullScreen, graphicsDevice, images.get(1));
            }
        });

        JButton resolution1440by900 = new JButton("Resoluton: 1440 x 900");
        resolution1440by900.setText("1440 x 900");
        resolution1440by900.setLocation(0, 55);
        resolution1440by900.setSize(100, 50);
        resolution1440by900.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResolution(
                        settings.getWidthAndHeightForResolution(Resolutions.Resolution1440x900).x,
                        settings.getWidthAndHeightForResolution(Resolutions.Resolution1440x900).y
                ); // end changeResolution call
                invalidate();
                validate();

                setLocationRelativeTo(null);

            } // end event handler body
        }); // end event handler definition

        JButton fullscreen = new JButton("Fullscreen");
        fullscreen.setText("Fullscreen");
        fullscreen.setLocation(0, 110);
        fullscreen.setSize(100, 50);
        fullscreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graphicsDevice.isFullScreenSupported()) {
                    setWindowFullScreen();
                } else {
                    System.err.println("Fullscreen is not supported on your system!");
                }
            } // end event handler body
        }); // end event handler definition

        this.add(resolution1440by900);
        this.add(fullscreen);
        this.add(panel);
        repaint();
    }

    private void setWindowFullScreen() {
        fullScreen = true;
        graphicsDevice.setFullScreenWindow(this);
    }

    public class Panel extends JPanel {

        public Panel(int width, int height) {
            setSize(width, height);
            setVisible(true);

            //setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            refreshTimer.schedule(new refreshTimer(), 1);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Rectangle rect = new Rectangle(450, 450, 200, 100);

        if (rect.contains(new Point(me.getX(), me.getY()))) {
            sounds.get(0).start();
            closeWindow();
            OmegaCentauri_ oc = new OmegaCentauri_(width, height, 85, renderer, fullScreen, graphicsDevice, images.get(1));
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Rectangle rect = new Rectangle(700, 500, 100, 50); // exit game

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
        this.setVisible(false); 
        this.dispose();
    }

    private void changeResolution(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.pack();
    }

    private class refreshTimer extends TimerTask {

        @Override
        public void run() {
            renderer.drawLauncher(panel.getGraphics(), images.get(0), images.get(2), images.get(images.size() - 1)); // use active rendering
            refreshTimer.schedule(new refreshTimer(), 100); // 10 fps
        }
    }
}
