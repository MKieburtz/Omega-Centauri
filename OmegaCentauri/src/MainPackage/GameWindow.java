package MainPackage;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow extends JFrame implements KeyListener {

    private Game game;
    private boolean up, rotateRight, down, rotateLeft = false;
    private java.util.Timer timer = new java.util.Timer();
    private final int timerDelay = 10;
    private Renderer renderer;
    private Panel panel = new Panel(1000, 600);
    private BufferedImage screenImage;
    private Point middleOfPlayer = new Point();

    public GameWindow(int width, int height, Game game) {

        setUpWindow(width, height);
        timer.schedule(new MovementTimer(), timerDelay);
        this.game = game;
        renderer = new Renderer();

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

        @Override
        public void run() {



            if (up) {

                game.movePlayerRelitive(0, -2);
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;
                repaint();
            }
            if (rotateRight) {
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;
                game.rotatePlayer(true); // positive
                repaint();
            }
            if (down) {
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;
                game.movePlayerRelitive(0, 2);
                repaint();
            }
            if (rotateLeft) {
                middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getImage().getWidth() / 2;
                middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getImage().getHeight() / 2;
                game.rotatePlayer(false); // negitive
                repaint();
            }

            timer.schedule(new MovementTimer(), timerDelay);
        }
    }
    int keyCode;

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W: {
                up = true;
            }
            break;

            case KeyEvent.VK_D: { // rotate eventually 
                rotateRight = true;
            }
            break;

            case KeyEvent.VK_S: {
                down = true;
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
                up = false;
            }
            break;

            case KeyEvent.VK_D: { // rotate eventually 
                rotateRight = false;
            }
            break;

            case KeyEvent.VK_S: {
                down = false;
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
            screenImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            renderer.drawScreen(g, game.getPlayer(), middleOfPlayer.x, middleOfPlayer.y);
        }
    }
    // WARNING: USELESS METHOD.

    public void keyTyped(KeyEvent ke) {
    }
}
