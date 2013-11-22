package MainPackage;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class OmegaCentauri extends Game {

    private boolean forward, rotateRight, rotateLeft = false;
    private final java.util.Timer timer = new java.util.Timer();
    private final int timerDelay;
    private final Renderer renderer;
    private final Panel panel = new Panel(1000, 600);
    private final Point2D.Double middleOfPlayer = new Point2D.Double();
    private boolean Slowingdown = false;
    private double FPS = 0;
    private java.util.List<Long> updateTimes = new ArrayList<Long>();
    private final Point screenSize = new Point(10000, 10000);
    private final Point cameraSize;
    private Point2D.Double cameraPos = new Point2D.Double(0.0, 0.0);
    
    public OmegaCentauri(int width, int height, int desiredFrameRate) {
        
        cameraSize = new Point(width, height);
        timerDelay = 15;
        setUpWindow(width, height);
        player = new Player(((width / 2) - 25), ((width / 2) - 25), MainPackage.Type.Fighter);
        renderer = new Renderer();

        timer.schedule(new MovementTimer(player), timerDelay);
        middleOfPlayer.x = player.getLocation().x + player.getImage().getWidth() / 2;
        middleOfPlayer.y = player.getLocation().y + player.getImage().getWidth() / 2;
    }

    private void setUpWindow(int width, int height) {
        
        setSize(width, height);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setTitle("Omega Centauri");
        add(panel);
        setContentPane(panel);
    }

    private class MovementTimer extends TimerTask {

        Player player;

        public MovementTimer(Player player) {
            this.player = player;
        }
        
        @Override
        public void run() {
            FPS = getFrameRate();
            System.out.println(FPS);
            
            if (forward) {

                player.move(false);
                middleOfPlayer.x = player.getLocation().x + player.getImage().getWidth() / 2;
                middleOfPlayer.y = player.getLocation().y + player.getImage().getHeight() / 2;
                repaint();
            }
            if (rotateRight) {

                player.rotate(true); // positive
                repaint();
            }

            if (rotateLeft) {
                player.rotate(false); // negitive
                repaint();
            }

            if (!forward) {
                Slowingdown = true;
            }

            if (Slowingdown) {
                player.move(true);
                middleOfPlayer.x = player.getLocation().x + player.getImage().getWidth() / 2;
                middleOfPlayer.y = player.getLocation().y + player.getImage().getHeight() / 2;
                repaint();
            }
            
            timer.schedule(new MovementTimer(player), timerDelay);
        }
    }
    int keyCode;
    
    @Override
    public void CheckKeyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        /*
        * 0 = stationary
        * 1 = both thrusters
        * 2 = right thruster
        * 3 = left thruster
        */
        switch (keyCode) {
            case KeyEvent.VK_W: {
                forward = true;
                Slowingdown = false;
                player.changeImage(1);
            }
            break;

            case KeyEvent.VK_D: {
                rotateRight = true;
                if (!forward)
                    player.changeImage(3);
                
            }
            break;

            case KeyEvent.VK_A: {
                rotateLeft = true;
                if (!forward)
                    player.changeImage(2);
            }
            break;

            case KeyEvent.VK_SPACE: {
                player.location.x = 500;
                player.location.y = 250;
                player.setVel(0, 0);
            }

        } // end switch

    } // end method

    @Override
    public void  CheckKeyReleased(KeyEvent e) {
        keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W: {
                forward = false;
                player.changeImage(0);
                if (rotateRight)
                    player.changeImage(3);
                else if (rotateLeft)
                    player.changeImage(2);
            }
            break;

            case KeyEvent.VK_D: {
                rotateRight = false;
                if (!forward)
                    player.changeImage(0);
                else
                    player.changeImage(1);
            }
            break;

            case KeyEvent.VK_A: {
                rotateLeft = false;
                if (!forward)
                    player.changeImage(0);
                else
                    player.changeImage(1);
            }
            
            break;

        } // end switch
    }

    public class Panel extends JPanel {

        int width;
        int height;

        public Panel(int width, int height) {
            this.width = width;
            this.height = height;
            setSize(width, height);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            renderer.drawScreen(g, player, middleOfPlayer.x, middleOfPlayer.y, Math.ceil(FPS));
        }
    }
    

    private float getFrameRate() {
        long time = System.currentTimeMillis();

        updateTimes.add(new Long(time));

        float timeInSec = (time - updateTimes.get(0)) / 1000f;

        float fps = 30f / timeInSec;

        if (updateTimes.size() == 31) {
            updateTimes.remove(0);
        }

        return fps;
    }
}