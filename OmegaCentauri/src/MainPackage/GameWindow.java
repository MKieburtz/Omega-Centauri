package MainPackage;

import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow extends JFrame {

    private Game game;
    private boolean up, right, down, left = false;
    private java.util.Timer timer = new java.util.Timer();
    private final int timerDelay = 10;
    private Renderer renderer;

    public GameWindow(int width, int height, Game game) {

        timer.schedule(new MovementTimer(), timerDelay);
        this.game = game;
        setUpWindow(1000, 600);
        renderer = new Renderer();
        addKeyListener(new AL());
    }

    private void setUpWindow(int width, int height) {

        setVisible(true);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class MovementTimer extends TimerTask {

        @Override
        public void run() {

            if (up) {
                
                game.movePlayerRelitive(0, -1);
            }
            if (right) {
                
                game.movePlayerRelitive(1, 0);
            }
            if (down) {
                
                game.movePlayerRelitive(0, 1);
            }
            if (left) {
                game.movePlayerRelitive(-1, 0);
            }

            timer.schedule(new MovementTimer(), timerDelay);
        }
    }

    private class AL extends KeyAdapter {

        int keyCode;

        @Override
        public void keyPressed(KeyEvent e) {
            keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_UP: {
                    up = true;
                }
                break;

                case KeyEvent.VK_RIGHT: { // rotate eventually 
                    right = true;
                }
                break;

                case KeyEvent.VK_DOWN: {
                    down = true;
                }
                break;

                case KeyEvent.VK_LEFT: { // rotate eventually
                    left = true;
                }
                break;

            } // end switch

        } // end method

        @Override
        public void keyReleased(KeyEvent e) {
            keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_UP: {
                    up = false;
                }
                break;

                case KeyEvent.VK_RIGHT: { // rotate eventually 
                    right = false;
                }
                break;

                case KeyEvent.VK_DOWN: {
                    down = false;
                }
                break;

                case KeyEvent.VK_LEFT: { // rotate eventually
                    left = false;
                }
                break;

            } // end switch
        }
    } // end class

    @Override
    public void paint(Graphics g) {
        renderer.drawScreen(g, game.getPlayer());
        repaint();
    }
}
