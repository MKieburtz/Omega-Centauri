package MainPackage;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

// @author Michael Kieburtz
public class GameWindow extends JFrame implements KeyListener{
    private Game game;
    private boolean up, right, down, left = false;
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
                 middleOfPlayer.x = game.getPlayer().getLocation().x + game.getPlayer().getLocation().x / 2;
                 middleOfPlayer.y = game.getPlayer().getLocation().y + game.getPlayer().getLocation().y / 2;
                repaint();
            }
            if (right) {
                
                game.movePlayerRelitive(2, 0);
                repaint();
            }
            if (down) {
                
                game.movePlayerRelitive(0, 2);
                repaint();
            }
            if (left) {
                game.movePlayerRelitive(-2, 0);
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
                    right = true;
                }
                break;

                case KeyEvent.VK_S: {
                    down = true;
                }
                break;

                case KeyEvent.VK_A: { // rotate eventually
                    left = true;
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
                    right = false;
                }
                break;

                case KeyEvent.VK_S: {
                    down = false;
                }
                break;

                case KeyEvent.VK_A: { // rotate eventually
                    left = false;
                }
                break;

            } // end switch
        }
    
    
    public class Panel extends JPanel
    {
        int width;
        int height;
        
        public Panel(int width, int height)
        {
            this.width = width;
            this.height = height;
            
            setSize(width, height);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            screenImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            renderer.drawScreen(g, game.getPlayer(), middleOfPlayer.x, middleOfPlayer.y);
        }

    }
    // WARNING: USELESS METHOD.
    public void keyTyped(KeyEvent ke) {
        
    }
}
