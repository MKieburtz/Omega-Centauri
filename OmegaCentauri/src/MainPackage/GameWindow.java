package MainPackage;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow extends JFrame implements KeyListener {

    private Game game;
    private boolean forward, rotateRight, backward, rotateLeft = false;
    private java.util.Timer timer = new java.util.Timer();
    private final int timerDelay = 10;
    private Renderer renderer;
    private Panel panel = new Panel(1000, 600);
    private Point middleOfPlayer = new Point();
    private Ellipse2D.Double playerCircle = new Ellipse2D.Double();

    public GameWindow(int width, int height, Game game) {

        setUpWindow(width, height);
        
        this.game = game;
        timer.schedule(new MovementTimer(this.game), timerDelay);
        renderer = new Renderer();

        middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
        middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getWidth(this) / 2;

        playerCircle.x = game.getPlayer().getLocation().x;
        playerCircle.y = game.getPlayer().getLocation().y;
        playerCircle.width = game.getPlayer().getImage().getWidth();
        playerCircle.height = game.getPlayer().getImage().getHeight();
        
        
    }

    private void setUpWindow(int width, int height) {

        setSize(width, height);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        add(panel);
        setContentPane(panel);

    }

    private class MovementTimer extends TimerTask {
        
        Game game;
        
        public MovementTimer(Game game)
        {
            this.game = game;
        }
        
            
        @Override
        public void run() {

            
           int movePlayerToX = (int)Math.sin(Math.toRadians(game.getPlayer().getAngle())) * game.getPlayer().getSpeed();
           int movePlayerToY = (int)Math.cos(Math.toRadians(game.getPlayer().getAngle())) * (-1 * (game.getPlayer().getSpeed()));
            
           int dx = game.getPlayer().getLocation().x + movePlayerToX; 
           int dy = game.getPlayer().getLocation().y + movePlayerToY;
            
            if (forward) {

                game.movePlayerRelitive(dx, dy);
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;

                playerCircle.x = game.getPlayer().getLocation().x;
                playerCircle.y = game.getPlayer().getLocation().y;
                playerCircle.width = game.getPlayer().getImage().getWidth();
                playerCircle.height = game.getPlayer().getImage().getHeight();

                repaint();
            }
            if (rotateRight) {

                game.rotatePlayer(true); // positive
                repaint();
            }
            if (backward) {
                game.movePlayerRelitive(dx, dy);
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;

                playerCircle.x = game.getPlayer().getLocation().x;
                playerCircle.y = game.getPlayer().getLocation().y;
                playerCircle.width = game.getPlayer().getImage().getWidth();
                playerCircle.height = game.getPlayer().getImage().getHeight();

                repaint();
            }
            if (rotateLeft) {
                game.rotatePlayer(false); // negitive
                repaint();
            }

            timer.schedule(new MovementTimer(game), timerDelay);
        }
    }
    int keyCode;

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W: {
                forward = true;
            }
            break;

            case KeyEvent.VK_D: { // rotate eventually 
                rotateRight = true;
            }
            break;

            case KeyEvent.VK_S: {
                backward = true;
            }
            break;

            case KeyEvent.VK_A: { // rotate eventually
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
                forward = false;
            }
            break;

            case KeyEvent.VK_D: { // rotate eventually 
                rotateRight = false;
            }
            break;

            case KeyEvent.VK_S: {
                backward = false;
            }
            break;

            case KeyEvent.VK_A: { // rotate eventually
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
}
