package MainPackage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.*;
// @author Michael Kieburtz and Davis Freeman

public class Launcher extends JFrame implements MouseListener{

    private int width = 1000;
    private int height = 600;
    private JFrame launcherFrame = new JFrame("Omega Centauri Launcher");
    private Renderer renderer = new Renderer(width, height);
    private ImageLoader imageLoader = new ImageLoader();
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

    public Launcher() {
        imagePaths.add("resources/GoButton.png");
        images = imageLoader.loadImages(imagePaths);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                renderer.drawLauncher(g, images.get(0));
            }
        };

        panel.setSize(width, height);
        panel.setVisible(true);

        
        launcherFrame.add(panel);

    }

    private void setUpWindow(int width, int height) {
        launcherFrame.setSize(width, height);
        launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        launcherFrame.setVisible(true);
        launcherFrame.setLayout(null);
    }

    private void addButtons() // temp method
    {
        JButton goButton = new JButton("GO!");
        goButton.setVisible(true);
        goButton.setText("GO!");
        goButton.setSize(100, 50);

        goButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                launcherFrame.setVisible(false);
                launcherFrame.dispose();
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
                launcherFrame.setVisible(false);
                launcherFrame.dispose();
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
                launcherFrame.setSize(1440, 900);
            }
        });
        
        launcherFrame.add(goButton);
        launcherFrame.add(closeButton);
        launcherFrame.add(resolution1440by900);
    }
}
