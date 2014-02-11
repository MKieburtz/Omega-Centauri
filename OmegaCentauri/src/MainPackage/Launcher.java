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

    public Launcher() {
        imagePaths.add("src/resources/GoButton.png");
        imagePaths.add("src/resources/OmegaCentauriLogo.png");
        imagePaths.add("src/resources/LauncherBackground.jpg");
        images = mediaLoader.loadImages(imagePaths);

        soundPaths.add("src/resources/mouseClick.wav");
        sounds = mediaLoader.loadSounds(soundPaths);

        setUpWindow(width, height);
        addButtons();
    }

    private void setUpWindow(int width, int height) {


        this.setIconImage(images.get(1));
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this.setBackground(new Color(0,0,0,0));

        this.setLayout(null);

        this.addMouseListener(this);
        this.setTitle("Omega Centauri Launcher");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
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
                OmegaCentauri_ oc = new OmegaCentauri_(width, height, 85, renderer);
            }
        });

        JButton closeButton = new JButton("Exit Game.");
        closeButton.setText("Exit Game.");
        closeButton.setLocation(0, 55);
        closeButton.setSize(100, 50);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });

        JButton resolution1440by900 = new JButton("Resoluton: 1440 x 900");
        resolution1440by900.setText("Resolution: 1440 x 900");
        resolution1440by900.setLocation(0, 110);
        resolution1440by900.setSize(200, 50);
        resolution1440by900.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                width = 1440;
                height = 900;
                changeResolution(width, height);
            }
        });

        this.add(goButton);
        this.add(closeButton);
        this.add(resolution1440by900);
        this.add(panel);
        repaint();
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
            OmegaCentauri_ oc = new OmegaCentauri_(width, height, 85, renderer);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    private void closeWindow() {
        refreshTimer.cancel();
        this.setVisible(false);
        this.dispose();
    }

    private void changeResolution(int width, int height) {
        this.setSize(width, height);
    }

    private class refreshTimer extends TimerTask {

        @Override
        public void run() {
            renderer.drawLauncher(panel.getGraphics(), images.get(0)); // use active rendering
            refreshTimer.schedule(new refreshTimer(), 100); // 10 fps
        }
    }
}
