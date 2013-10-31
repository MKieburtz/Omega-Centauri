package MainPackage;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow extends JFrame implements KeyListener {

    private final Game game;
    private boolean forward, rotateRight, backward, rotateLeft = false;
    private final java.util.Timer timer = new java.util.Timer();
    private final int timerDelay = 10;
    private final Renderer renderer;
    private final Panel panel = new Panel(1000, 600);
    private final Point2D.Double middleOfPlayer = new Point2D.Double();
    private final Ellipse2D.Double playerCircle = new Ellipse2D.Double();
    private boolean Slowingdown = false;
    //private JLabel fpsLabel = new JLabel();
    private java.util.List<Long> updateTimes = new ArrayList<Long>();

    public GameWindow(int width, int height, Game game) {

        setUpWindow(width, height);
        this.game = game;
        renderer = new Renderer();
        setIconImage(game.getPlayer().getImage());
        
//        fpsLabel.setVisible(true);
//        fpsLabel.setLocation(0, 0);
//        fpsLabel.setText(String.valueOf(getFrameRate()));
        
        timer.schedule(new MovementTimer(game.getPlayer()), timerDelay);
        middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
        middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getWidth() / 2;
    }

    private void setUpWindow(int width, int height) {

        setSize(width, height);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        
        //add(fpsLabel);
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
            
            
            if (forward) {

                game.movePlayer(true, Slowingdown);
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;
                repaint();
            }
            if (rotateRight) {

                game.rotatePlayer(true); // positive
                repaint();
            }
            if (backward) {
                
                game.movePlayer(false, Slowingdown);
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;
                repaint();
            }
            if (!backward && !forward) {
                Slowingdown = true;
            }
            if (rotateLeft) {
                game.rotatePlayer(false); // negitive
                repaint();
            }

            timer.schedule(new MovementTimer(player), timerDelay);
        }
    }
    int keyCode;

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W: {
                forward = true;
                Slowingdown = false;
            }
            break;

            case KeyEvent.VK_D: {
                rotateRight = true;
            }
            break;

            case KeyEvent.VK_S: {
                backward = true;
                Slowingdown = false;
            }
            break;

            case KeyEvent.VK_A: {
                rotateLeft = true;
            }
            break;

        } // end switch

    } // end method

    @Override
    public void keyReleased(KeyEvent e) {
        keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W: {
                Slowingdown = true;
            }
            break;

            case KeyEvent.VK_D: {
                rotateRight = false;
            }
            break;

            case KeyEvent.VK_S: {
                Slowingdown = true;
            }
            break;

            case KeyEvent.VK_A: {
                rotateLeft = false;
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

            renderer.drawScreen(g, game.getPlayer(), middleOfPlayer.x, middleOfPlayer.y, playerCircle);
        }
    }
    // WARNING: USELESS METHOD.

    public void keyTyped(KeyEvent ke) {
    }

    private float getFrameRate() {
        long time = System.currentTimeMillis();

        updateTimes.add(new Long(time));

        float timeInSec = (time - updateTimes.get(0)) / 1000f;

        float FPS = 30f / timeInSec;

        if (updateTimes.size() == 31) {
            updateTimes.remove(0);
        }

        return FPS;
    }
}
