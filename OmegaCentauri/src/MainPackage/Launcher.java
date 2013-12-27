package MainPackage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
// @author Michael Kieburtz and Davis Freeman

public class Launcher extends JFrame implements MouseListener{

    private int width = 1000;
    private int height = 600;
    
    private final Renderer renderer = new Renderer(width, height);
    private final ImageLoader imageLoader = new ImageLoader();
    private final ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    
    private final Panel panel = new Panel(1000, 600); // this will be changed when we do resolution things
    
    public Launcher() {
        imagePaths.add("src/resources/GoButton.png");
        images = imageLoader.loadImages(imagePaths);
        setUpWindow(width, height);
        addButtons();
    }

    private void setUpWindow(int width, int height) {
        this.setSize(width, height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(null);
        
        this.addMouseListener(this);
        this.setTitle("Omega Centauri Launcher");
        this.setResizable(false);
    }

    private void addButtons() // temp method. Davis delete this when you do your button images
    {
        JButton goButton = new JButton("GO!");
        goButton.setVisible(true);
        goButton.setText("GO!");
        goButton.setSize(100, 50);

        goButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
                OmegaCentauri oc = new OmegaCentauri(width, height, 100, renderer);
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
    }
    
    public class Panel extends JPanel
    {
        int width;
        int height;

        public Panel(int width, int height) {
            this.width = width;
            this.height = height;
            setSize(width, height);
            setVisible(true);
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            renderer.drawLauncher(g, images.get(0));
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
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
    
    private void closeWindow()
    {
        this.setVisible(false);
        this.dispose();
    }
    
    private void changeResolution(int width, int height)
    {
        this.setSize(width, height);
    }
}
