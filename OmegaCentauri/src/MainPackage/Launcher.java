package MainPackage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.*;
// @author Michael Kieburtz and Davis Freeman

public class Launcher {

    static int width = 1000;
    static int height = 600;
    static JFrame launcherFrame = new JFrame("Omega Centauri Launcher");
    static Renderer renderer = new Renderer(width, height);
    static ImageLoader imageLoader = new ImageLoader();
    static ArrayList<String> imagePaths = new ArrayList<String>();
    static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();

    // Launcher -> game -> gamewindow -> renderer
    public static void main(String args[]) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                imagePaths.add("resources/GoButton.png");
                images = imageLoader.loadImages(imagePaths);

                launcherFrame.setSize(width, height);
                launcherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                launcherFrame.setVisible(true);
                launcherFrame.setLayout(null);
                
                /******************** BEGIN BUTTON STUFF ***************/
                
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
                
                /******************** STOP BUTTON STUFF ****************/
                
                JPanel panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        System.out.println("cool");
                        renderer.drawLauncher(g, images.get(0));
                    }
                };
                
                panel.setSize(width, height);
                panel.setVisible(true);

                launcherFrame.add(goButton);
                launcherFrame.add(closeButton);
                launcherFrame.add(resolution1440by900);
                launcherFrame.add(panel);

            }
        }
        );
    }
}
