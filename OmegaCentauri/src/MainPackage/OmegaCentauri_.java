package MainPackage;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;

/**
 * @author Michael Kieburtz
 * @author Davis Freeman
 */
public class OmegaCentauri_ extends Game {

    private final String Version = "Dev 1.0.5";
    /*
     * GAME STATE VARIBLES:
     */
    private boolean forward, rotateRight, rotateLeft, shooting = false; // movement booleans 
    private boolean paused = false;
    private boolean loading = false;
    private final Point screenSize = new Point(10000, 10000);
    private final Point2D.Double middleOfPlayer = new Point2D.Double(); // SCREEN LOCATION of the middle of the player
    // TIMING STUFF
    private int FPS = 0;
    private int UPS = 1;
    private int updates = 1;
    private final long loopTimeUPS = (long) Math.ceil(1000 / 75); // about 15. Change 75 for the target UPS
    private int framesDrawn = 0;
    /*
     * OBJECTS:
     */
    private final Renderer renderer;
    private final Panel panel = new Panel(1000, 600); // this will be changed when we do resolution things
    private Camera camera;
    private ArrayList<CollisionListener> collisionListeners = new ArrayList<CollisionListener>();
    private GraphicsDevice gd;
    private Settings settings;
    // TIMERS
    private java.util.Timer FPSAndUPSTimer = new java.util.Timer();
    private java.util.Timer drawingTimer = new java.util.Timer();
    /*
     * LOADING VARIBLES:
     */
    private int[] yPositions = {-10000, -10000, 0, 0}; // starting y positions
    private int starChunksLoaded = 0;
    private ArrayList<StarChunk> stars = new ArrayList<StarChunk>();

    public OmegaCentauri_(int desiredFrameRate, Renderer renderer,
            boolean fullScreen, GraphicsDevice gd, BufferedImage logo, Settings settings) {

        this.settings = settings;
        this.gd = gd;
        this.renderer = renderer;
        camera = new Camera(settings.getResolution().width, settings.getResolution().height);
        loading = true;

        player = new Player(0, 0, MainPackage.Type.Fighter, 5, 5, 4, .15, camera.getLocation(), 155, 100);
        enemyShips.add(new EnemyFighter(200, 0, MainPackage.Type.Fighter, 5, 3, 5, .15, camera.getLocation(), 500, 100));
        syncGameStateVaribles();

        player.setUpHitbox(camera.getLocation());

        collisionListeners.add(player);

        for (EnemyShip enemy : enemyShips) {
            collisionListeners.add(enemy);
        }

        setUpWindow(settings.getResolution() == null, logo);

        loadGame();
    }

    private void setUpWindow(boolean fullScreen, BufferedImage logo) {
        setEnabled(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        setTitle("Omega Centauri");
        getContentPane().add(panel);

        if (fullScreen) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setIconImage(logo);
            setPreferredSize(new Dimension(1000, 600));
            pack();
        }

        setLocationRelativeTo(null);
        addMouseListener(this);

        setVisible(true);
    }

    private void loadGame() {
        while (loading) {
            // load 100 starChunks from each quadrant
            // load all the horizontal star chunks from each quadrant
            // then move down 100 to the next chunk down

            // quadrant 1

            /*  _______
             * |___|_x_|
             * |___|___|
             */
            if (yPositions[0] < 0) {

                for (int x = 1; x < screenSize.x; x = x + 100) {

                    stars.add(new StarChunk(x, yPositions[0]));
                    starChunksLoaded++;
                }

                yPositions[0] += 100;
            }

            // quadrant 2

            /*  _______
             * |_x_|___|
             * |___|___|
             */
            if (yPositions[1] < 0) {
                for (int x = -1; x > -screenSize.x; x = x - 100) {

                    stars.add(new StarChunk(x, yPositions[1]));
                    starChunksLoaded++;

                }

                yPositions[1] += 100;
            }

            // quadrant 3

            /*  _______
             * |___|___|
             * |_x_|___|
             */
            if (yPositions[2] < 10000) {
                for (int x = -1; x > -screenSize.x; x = x - 100) {

                    stars.add(new StarChunk(x, yPositions[2]));
                    starChunksLoaded++;
                }

                yPositions[2] += 100;
            }

            // quadrant 4

            /*  _______
             * |___|___|
             * |___|_x_|
             */
            if (yPositions[3] < 10000) {
                for (int x = 1; x < screenSize.x; x = x + 100) {

                    stars.add(new StarChunk(x, yPositions[3]));
                    starChunksLoaded++;
                }

                yPositions[3] += 100;
            }

            // base case
            if (starChunksLoaded == (100 * 100) * 4) {
                loading = false;
            }

            // use active rendering to draw the screen
            renderer.drawLoadingScreen(panel.getGraphics(), starChunksLoaded / 400, panel.getWidth(), panel.getHeight());

            if (!loading) {
                break;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }

        }

        startGame();
    }

    private void startGame() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                shipsToDraw.add(player);
                shipsToDraw.addAll(enemyShips);
                shipsToDraw.addAll(allyShips);

                FPSAndUPSTimer.schedule(new FPSAndUPSTimer(), 1);
                drawingTimer.schedule(new UpdateTimer(), 1);
            }
        });

    }

    private void gameUpdate() {

        System.out.println(player.angularVelocity);
        if (enemyShips.size() + allyShips.size() + (player.getHullHealth() > 0 ? 1 : 0) != shipsToDraw.size()) {
            System.err.println("wierd: " + enemyShips.size() + " " + allyShips.size() + " " + shipsToDraw.size());
            shipsToDraw.clear();

            shipsToDraw.add(player);
            shipsToDraw.addAll(enemyShips);
            shipsToDraw.addAll(allyShips);

        }

        if (camera.getSize().x != getWidth() || camera.getSize().y != getHeight()) {
            camera.setSize(getWidth(), getHeight());
        }

        if (forward) {
            player.move(ShipState.Thrusting);
        }
        if (rotateRight) {

            player.rotate(ShipState.TurningRight);
        }
        if (rotateLeft) {
            player.rotate(ShipState.TurningLeft);
        }
        if (!forward && player.isMoving()) {
            player.move(ShipState.Drifting);
        }
        if (!rotateRight && !rotateRight && player.isRotating())
        {
            player.rotate(player.rotatingRight() ? ShipState.AngleDriftingRight : ShipState.AngleDriftingLeft);
        }
        if (shooting && player.canShoot()) {
            player.shoot(camera.getLocation());
        }
        for (EnemyShip enemyShip : enemyShips) {
            enemyShip.update(player.getLocation(), camera.getLocation());
        }

        
        allShots.clear();
        for (Ship ship : shipsToDraw) {

            
            allShots.addAll(ship.getShots());
            
            for (Shot shot : ship.getShots()) {
                shot.updateLocation();
            }

            for (int i = shipsToDraw.size() - 1; i > -1; i--) {
                if (shipsToDraw.get(i).getHullHealth() <= 0) {
                    if (shipsToDraw.get(i) instanceof EnemyShip) {                    
                        enemyShips.remove(shipsToDraw.get(i)); 
                    }
                    shipsToDraw.remove(i);
                }
            }

            if (ship.getShield().isActive()) {
                ship.getShield().decay();
            }

            for (Ship collisionShip : shipsToDraw) {
                if (!collisionShip.equals(ship)) {
                    if (Calculator.collisionCheck(ship.returnHitbox(), collisionShip.returnHitbox())) {
                        ship.CollisionEventWithShips(ship, collisionShip);
                    }
                }
            }

            ship.purgeShots();
        }
        
        for (Shot shot : allShots)
        {
            for (Ship ship : shipsToDraw)
            {
                if (Calculator.collisionCheck(shot.returnHitbox(), ship.returnHitbox()))
                {
                    ship.CollisionEventWithShot(ship, shot, shipsToDraw);
                }
            }
        }


        syncGameStateVaribles();

    }
    int keyCode;

    @Override
    public void CheckKeyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();
        /*
         * 0 = stationary
         * 1 = thrusting
         * 2 = turning right
         * 3 = turning left
         */
        if (!paused) {
            switch (keyCode) {
                case KeyEvent.VK_W: {
                    forward = true;
                    if (!rotateRight && !rotateLeft) {
                        player.changeImage(ShipState.Thrusting);
                    } else if (rotateLeft && !rotateRight) {
                        player.changeImage(ShipState.TurningLeftThrusting);
                    } else if (!rotateLeft && rotateRight) {
                        player.changeImage(ShipState.TurningRightThrusting);
                    }
                }
                break;

                case KeyEvent.VK_D: {
                    rotateRight = true;
                    if (!forward) {
                        player.changeImage(ShipState.TurningRight);
                    } else if (forward) {
                        player.changeImage(ShipState.TurningRightThrusting);
                    } else if (rotateLeft) {
                        player.changeImage(ShipState.Idle);
                    }

                }
                break;

                case KeyEvent.VK_A: {
                    rotateLeft = true;
                    if (!forward) {
                        player.changeImage(ShipState.TurningLeft);
                    } else if (forward) {
                        player.changeImage(ShipState.TurningLeftThrusting);
                    } else if (rotateRight) {
                        player.changeImage(ShipState.Idle);
                    }

                }
                break;

                case KeyEvent.VK_SHIFT: {
                    player.speedBoost();
                }
                break;

                case KeyEvent.VK_SPACE: {
                    shooting = true;
                    break;
                }

                case KeyEvent.VK_Q: {
                    System.exit(0);
                }

            } // end switch
        }
    } // end method

    @Override
    public void CheckKeyReleased(KeyEvent e) {
        keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_W: {
                forward = false;
                player.changeImage(ShipState.Idle);
                if (rotateRight) {
                    player.changeImage(ShipState.TurningLeft);
                } else if (rotateLeft) {
                    player.changeImage(ShipState.TurningRight);
                }
            }
            break;

            case KeyEvent.VK_D: {
                rotateRight = false;
                if (!forward) {
                    player.changeImage(ShipState.Idle);
                } else {
                    player.changeImage(ShipState.Thrusting);
                }
            }
            break;

            case KeyEvent.VK_A: {
                rotateLeft = false;
                if (!forward) {
                    player.changeImage(ShipState.Idle);
                } else {
                    player.changeImage(ShipState.Thrusting);
                }
            }

            break;

            case KeyEvent.VK_SHIFT: {
                player.stopSpeedBoosting();
            }
            break;

            case KeyEvent.VK_SPACE: {
                shooting = false;
            }
            break;

            case KeyEvent.VK_ESCAPE: {
                paused = !paused;
            }
            break;

        } // end switch
    }

    @Override
    public void CheckMousePressed(MouseEvent me) {
        Rectangle rect = new Rectangle(20, 110, 200, 100);

        if (rect.contains(new Point(me.getX(), me.getY())) && paused) {
            this.setVisible(false);
            this.dispose();
            new Launcher();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
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

    public class Panel extends JPanel {

        public Panel(int width, int height) {
            setSize(width, height);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    private void syncGameStateVaribles() {
        camera.move(player.getLocation().x - (getWidth() / 2), player.getLocation().y - (getHeight() / 2));

        middleOfPlayer.x = player.getLocation().x - camera.getLocation().x + player.getImage().getWidth() / 2;
        middleOfPlayer.y = player.getLocation().y - camera.getLocation().y + player.getImage().getHeight() / 2;
    }

    private class FPSAndUPSTimer extends TimerTask {

        @Override
        public void run() {

            FPS = framesDrawn;
            UPS = updates;

            framesDrawn = 0;
            updates = 0;
            FPSAndUPSTimer.schedule(new FPSAndUPSTimer(), 1000);
        }
    }

    private class UpdateTimer extends TimerTask {

        long startTime, endTime, timeDiff, sleepTime = 0L;

        @Override
        public void run() {

            if (!paused) {
                startTime = System.currentTimeMillis();
                gameUpdate();
                updates++;

                if (panel.getGraphics() != null) {
                    renderer.drawScreen(panel.getGraphics(), shipsToDraw, middleOfPlayer.x, middleOfPlayer.y,
                            FPS, stars, camera, Version, UPS, paused);
                    framesDrawn++;

                    endTime = System.currentTimeMillis();
                    timeDiff = endTime - startTime;
                    sleepTime = loopTimeUPS - timeDiff;


                    if (sleepTime < 0) {
                        sleepTime = 1;
                    }

                }

                drawingTimer.schedule(new UpdateTimer(), sleepTime);
            }
        }
    }
}
